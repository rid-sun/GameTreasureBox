package com.TerminalWork.gametreasurebox.customComponents;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.TerminalWork.gametreasurebox.R;
import com.TerminalWork.gametreasurebox.bean.flags;
import com.TerminalWork.gametreasurebox.bean.img_puzzle;

public class image_moveView extends View {

    int order = -1;
    int view_width;
    int view_height;
    public int bit_num;
    public String bitmapID;
    int xCount;
    int yCount;
    Bitmap ori_bitmap;
    img_puzzle puzzles[];
    int bitmap_width;
    int bitmap_height;
    int map[];
    public boolean isComplete;

    //与界面的左右边距
    int offset_x = 100;
    int offset_y = 100;

    private Paint paint;
    private Intent intent;
    private int steps;
    private Context context;

    public image_moveView(Context context) {
        super(context);
        this.setWillNotDraw(false);
        paint = new Paint();
        this.context = context;
        init();
    }

    public image_moveView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        TypedArray ta = context.obtainStyledAttributes(attributeSet,R.styleable.image_moveView);
        view_width = ta.getDimensionPixelSize(R.styleable.image_moveView_android_layout_width,410) - 200;
        view_height = view_width;
        bit_num = ta.getInteger(R.styleable.image_moveView_bit_num,0);
        bitmapID = ta.getString (R.styleable.image_moveView_bitmapID);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.outWidth = view_width;
        options.outHeight = view_height;
        ori_bitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(bitmapID,"drawable","com.TerminalWork.gametreasurebox"), options);
        System.out.println(R.drawable.pretty_girl);

        ta.recycle();

        this.setWillNotDraw(false);
        paint = new Paint();
        this.context = context;
        init();
    }

    @Override
    protected void onFinishInflate() {
        //这个方法在view及其子控件填充好后才会执行，所以这样可以直接在xml文件中设置matchparent了
        super.onFinishInflate();
    }

    private void init(){
        this.xCount = (int)Math.sqrt(bit_num);
        this.yCount = (int)Math.sqrt(bit_num);
        System.out.println(bit_num+"      "+bitmapID+"    "+view_height);
        bitmap_width = view_width / xCount;
        bitmap_height = view_height / yCount;

        map = new int[bit_num];
        puzzles = new img_puzzle[bit_num];

        for (int k = 0; k < bit_num; k++) {
            puzzles[k] = new img_puzzle();
        }

        for (int i = 0; i < xCount; i++) {
            for (int j = 0; j < yCount; j++) {
                int pos = i * xCount + j;
                int x = offset_x + j * bitmap_width;
                int y = offset_y + i * bitmap_height;
                if (pos == bit_num - 1) {
                    puzzles[pos].bitmap = Bitmap.createBitmap(bitmap_width, bitmap_height, Bitmap.Config.ARGB_8888);
                    puzzles[pos].is_blank = true;
                } else {
                    puzzles[pos].bitmap = Bitmap.createBitmap(ori_bitmap, bitmap_width * j, bitmap_height * i, bitmap_width, bitmap_height);
                }
                puzzles[pos].x = puzzles[pos].orignX = x;
                puzzles[pos].y = puzzles[pos].orignY = y;
                map[pos] = pos;
            }
        }
        isComplete = false;
        paint.setStrokeWidth((float) 5.0);
        intent = new Intent(flags.action_changStepsImageHrd);
        steps = 0;
    }

    @Override
    public void draw(Canvas canvas) {
        System.out.println("执行ondraw");
        super.draw(canvas);
        for (int i = 0; i < xCount; i++) {
            int x = offset_x;
            int y = offset_y + bitmap_height * i;
            for (int j = 0; j < yCount; j++) {
                int pos = i * xCount + j;
                Bitmap bitmap = puzzles[pos].bitmap;
                int x_1 = puzzles[pos].x;
                int y_1 = puzzles[pos].y;
                canvas.drawBitmap(bitmap, x_1, y_1, paint);
                canvas.drawLine(x_1, y_1, x_1, y_1 + bitmap_height, paint);
            }
            canvas.drawLine(x + bitmap_width * 4, y, x + bitmap_width * 4, y + bitmap_height, paint);
            canvas.drawLine(x, y, x + bitmap_width * 4, y, paint);
        }
        canvas.drawLine(offset_x , offset_y + 4 * bitmap_height, offset_x + bitmap_width * 4, offset_y + 4 * bitmap_height, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getX() < offset_x || event.getX() > offset_x + view_width
                || event.getY() < offset_y || event.getY() > offset_y + view_height)
            return true;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                int num_y = (int)(event.getX() - offset_x) / bitmap_width;
                int num_x = (int)(event.getY() - offset_y) / bitmap_height;
                if(switch_pos(num_x,num_y)) {
                    invalidate();
                    judgeComplete();
                    steps++;
                    intent.putExtra("image_steps", steps);
                    context.sendBroadcast(intent);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            default:
                return true;
        }
        return true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        for (int i = 0; i < bit_num; i++) {
            if (null != puzzles[i].bitmap) {
                puzzles[i].bitmap.recycle();
                puzzles[i].bitmap = null;
            }
        }
    }

    private boolean switch_pos(int row, int col) {
        if(puzzles[map[row * xCount + col]].is_blank){
            return false;
        }
        //切记不可以直接用一个imp_puzzle来记录对象，然后在后面进行
        //互相交换，因为他们本来就指向的是同一个实体
        int x = puzzles[map[row * xCount + col]].x;
        int y = puzzles[map[row * xCount + col]].y;
        int num = -1;
        if(row - 1 >= 0){
            if(puzzles[map[(row - 1) * xCount + col]].is_blank)
                num = (row - 1) * xCount + col;
        }
        if(row + 1 <= xCount - 1){
            if(puzzles[map[(row + 1) * xCount + col]].is_blank)
                num = (row + 1) * xCount + col;
        }
        if(col - 1 >= 0){
            if(puzzles[map[row * xCount + col - 1]].is_blank)
                num = row * xCount + col - 1;
        }
        if(col + 1 <= yCount - 1){
            if(puzzles[map[row * xCount + col + 1]].is_blank)
                num = row * xCount + col + 1;
        }
        if(num == -1){
            return false;
        }
        puzzles[map[row * xCount + col]].x = puzzles[map[num]].x;
        puzzles[map[row * xCount + col]].y = puzzles[map[num]].y;
        puzzles[map[num]].x = x;
        puzzles[map[num]].y = y;
        int t = map[row * xCount + col];
        map[row * xCount + col] = map[num];
        map[num] = t;
        return true;
    }

    public void init_Pos() {
        order++;
        for(int i = 0; i < bit_num - 1; i++){
            puzzles[flags.Order16[order % 8][i]].x = puzzles[i].orignX;
            puzzles[flags.Order16[order % 8][i]].y = puzzles[i].orignY;
            map[i] = flags.Order16[order % 8][i];
        }
        invalidate();
        isComplete = false;
        System.out.println("执行");
    }

    public void judgeComplete(){
        for(int i = 0; i < bit_num - 1; i++){
            if(map[i] != i){
                return ;
            }
        }
        isComplete = true;
        Toast.makeText(context, "成功", Toast.LENGTH_SHORT).show();
        steps = 0;
    }

}
