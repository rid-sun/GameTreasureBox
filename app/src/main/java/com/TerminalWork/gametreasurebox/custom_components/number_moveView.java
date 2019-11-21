package com.TerminalWork.gametreasurebox.custom_components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.TerminalWork.gametreasurebox.R;
import com.TerminalWork.gametreasurebox.bean.flags;
import com.TerminalWork.gametreasurebox.bean.num_puzzle;

public class number_moveView extends View {

    int view_width;
    int view_height;
    int blockCount;
    int offsetX;
    int offsetY;
    int block_width;
    int block_height;
    int singleBlockCount;
    int gap;
    num_puzzle[] puzzles;
    Paint paint;
    int index;
    public boolean isComplete;

    public number_moveView(Context context) {
        super(context);
        this.setWillNotDraw(false);
        init();
    }

    public number_moveView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        this.setWillNotDraw(false);

        TypedArray ta = context.obtainStyledAttributes(attributeSet, new int[]{ R.attr.blockCount, android.R.attr.layout_width});
        view_width = ta.getDimensionPixelSize(1,0) - 200;
        view_height = view_width;
        blockCount = ta.getInt(0,0);
        ta.recycle();
        System.out.println("宽为："+view_height+"   个数："+blockCount);
        init();
    }

    private void init(){
        offsetX = offsetY = 100;
        gap = 20;
        singleBlockCount = (int)(Math.sqrt(blockCount));
        block_width = (view_width - (singleBlockCount - 1) * gap) / singleBlockCount;
        block_height = block_width;
        puzzles = new num_puzzle[blockCount];
        for (int k = 0; k < blockCount; k++) {
            puzzles[k] = new num_puzzle();
        }
        for(int i = 0; i < singleBlockCount; i++){
            int y = offsetY + block_height * i + i * gap;
            for(int j = 0; j < singleBlockCount; j++){
                int x = offsetX + block_width * j + j * gap;
                int pos = i * singleBlockCount + j;
                puzzles[pos].x = x;
                puzzles[pos].y = y;
                if(pos != blockCount - 1){
                    puzzles[pos].number = pos;
                }
                else{
                    puzzles[pos].isBlank = true;
                }
            }
        }
        paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);
        paint.setTextSize(block_width * 0.5f);
        paint.setColor(Color.WHITE);
        paint.setFakeBoldText(false);
        paint.setTextAlign(Paint.Align.CENTER);

        isComplete = false;
        index = -1;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        System.out.println("执行draw");
        draw_rectF(canvas);
        for(int i = 0; i < singleBlockCount; i++){
            for(int j = 0; j < singleBlockCount; j++){
                int pos = i * singleBlockCount + j;
                if(!puzzles[pos].isBlank){
                    canvas.drawText("" + puzzles[pos].number, puzzles[pos].rect_centerX, puzzles[pos].baselineY, paint);
                }
                else
                    canvas.drawText("", puzzles[pos].rect_centerX, puzzles[pos].baselineY, paint);
            }
        }
        canvas.save();
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getX() < offsetX || event.getX() > offsetX + view_width
                || event.getY() < offsetY || event.getY() > offsetY + view_height)
            return true;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                int num_y = (int)(event.getX() - offsetX) / (block_width + gap);
                int num_x = (int)(event.getY() - offsetY) / (block_height + gap);
                if(switch_pos(num_x,num_y)) {
                    invalidate();
                    judgeComplete();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            default:
                return true;
        }
        return true;
    }

    private boolean switch_pos(int row, int col){
        if(puzzles[row * singleBlockCount + col].isBlank){
            return false;
        }
        int temp_number = puzzles[row * singleBlockCount + col].number;
        int num = -1;
        if(row - 1 >= 0){
            if(puzzles[(row - 1) * singleBlockCount + col].isBlank)
                num = (row - 1) * singleBlockCount + col;
        }
        if(row + 1 <= singleBlockCount - 1){
            if(puzzles[(row + 1) * singleBlockCount + col].isBlank)
                num = (row + 1) * singleBlockCount + col;
        }
        if(col - 1 >= 0){
            if(puzzles[row * singleBlockCount + col - 1].isBlank)
                num = row * singleBlockCount + col - 1;
        }
        if(col + 1 <= singleBlockCount - 1){
            if(puzzles[row * singleBlockCount + col + 1].isBlank)
                num = row * singleBlockCount + col + 1;
        }
        if(num == -1){
            return false;
        }
        puzzles[row * singleBlockCount + col].number = puzzles[num].number;
        puzzles[row * singleBlockCount + col].isBlank = true;
        puzzles[num].number = temp_number;
        puzzles[num].isBlank = false;
        return true;
    }

    private void draw_rectF(Canvas canvas)
    {
        Paint rect_paint = new Paint();
        rect_paint.setColor(Color.GRAY);
        rect_paint.setStyle(Paint.Style.FILL);
        for(int i = 0; i < singleBlockCount; i++){
            for(int j = 0; j < singleBlockCount; j++){
                int pos = i * singleBlockCount + j;
                RectF rect = new RectF(puzzles[pos].x, puzzles[pos].y, puzzles[pos].x + block_width, puzzles[pos].y + block_height);
                canvas.drawRoundRect(rect, 30, 30, rect_paint);
                Paint.FontMetrics fontMetrics = paint.getFontMetrics();
                float top = fontMetrics.top;
                float bottom = fontMetrics.bottom;
                puzzles[pos].baselineY = (int) (rect.centerY() - top / 2 - bottom / 2);
                puzzles[pos].rect_centerX = rect.centerX();
            }
        }
    }

    public void init_Pos() {
        index++;
        for(int i = 0; i < blockCount - 1; i++){
            puzzles[i].number = flags.Order16[index % 8][i];
            puzzles[i].isBlank = false;
        }
        puzzles[blockCount - 1].isBlank = true;
        isComplete = false;
        System.out.println("执行");
    }

    public void judgeComplete(){
        for(int i = 0; i < blockCount - 1; i++){
            if(puzzles[i].number != i){
                return ;
            }
        }
        isComplete = true;
    }

}
