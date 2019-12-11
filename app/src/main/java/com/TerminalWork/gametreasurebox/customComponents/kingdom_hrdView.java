package com.TerminalWork.gametreasurebox.customComponents;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.TerminalWork.gametreasurebox.R;
import com.TerminalWork.gametreasurebox.bean.flags;
import com.TerminalWork.gametreasurebox.methods.myUtils;

import me.jessyan.autosize.AutoSizeCompat;

public class kingdom_hrdView extends View {

    private Intent intent;
    private boolean isHasMoved;

    int view_id;
    int check_pointID;

    int view_width;
    int view_height;

    final int CHECK_POINT_ID = 0;
    final int LAYOUT_WIDTH = 1;
    final int LAYOUT_HEIGHT = 2;
    final int LAYOUT_MARGIN_TOP = 3;
    final int LAYOUT_MARGIN_START = 4;
    final int VIEW_ID = 5;

    Paint paint;
    int previousX, presentX, previous_viewX, present_viewX;
    int previousY, presentY, previous_viewY, present_viewY;

    private Context context;

    public kingdom_hrdView(Context context) {
        super(context);
    }

    public kingdom_hrdView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, new int[]{R.attr.check_pointID, android.R.attr.layout_width,
                android.R.attr.layout_height, android.R.attr.layout_marginTop, android.R.attr.layout_marginStart, R.attr.view_id});
        check_pointID = ta.getInt(CHECK_POINT_ID,1);
        view_width = ta.getDimensionPixelSize(LAYOUT_WIDTH,0);
        view_height = ta.getDimensionPixelSize(LAYOUT_HEIGHT,0);
        present_viewX = ta.getDimensionPixelSize(LAYOUT_MARGIN_START,0);
        present_viewY = ta.getDimensionPixelSize(LAYOUT_MARGIN_TOP,0);
        view_id = ta.getInt(VIEW_ID,0);
//        System.out.println("id: "+ view_id +"x: "+present_viewX+"y: "+present_viewY+"width: "+view_width+"height: "+view_height);

//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.outWidth = view_width;
//        options.outHeight = view_height;
//        view_bitmap = BitmapFactory.decodeResource(getResources(),
//                getResources().getIdentifier(ta.getString(BITMAP_NAME),"drawable",
//                        "com.TerminalWork.gametreasurebox"), options);

        ta.recycle();
        if(flags.gapWidth >= present_viewX){
            flags.gapWidth = present_viewX;
        }
        if(flags.gapHeight >= present_viewY){
            flags.gapHeight = present_viewY;
        }
        if(view_id == 6){
            flags.unitWidth = view_width;//初始化宽度，用于屏幕适配
            flags.unitHeight = view_height;//初始化高度，用于屏幕适配
        }
        flags.myView[check_pointID][view_id] = new Rect(present_viewX, present_viewY, present_viewX + view_width, present_viewY + view_height);
        System.out.println("chid: "+ check_pointID+"id: " + view_id+"l:"+present_viewX+"t:"+present_viewY+"r:"+(present_viewX + view_width)+"b:"+(present_viewY + view_height));
        intent = new Intent(flags.action_changStepsKingdomHrd);
        isHasMoved = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                previousX = (int)event.getX();
                previousY = (int)event.getY();
                previous_viewX = this.getLeft();
                previous_viewY = this.getTop();
//                System.out.println("xia x: "+previous_viewX+" y: "+previous_viewY);
                break;
            case MotionEvent.ACTION_MOVE:
                presentX = (int)event.getX();
                presentY = (int)event.getY();
                present_viewX = this.getLeft();
                present_viewY = this.getTop();
//                System.out.println("dong x: "+present_viewX+" y: "+present_viewY);
//                flags.direction = myUtils.judgeDirection(previousX,previousY,presentX,presentY);
//                System.out.println("dir: "+flags.direction);
                flags.direction = myUtils.judgeDirection2(previousX,previousY,presentX,presentY
                        ,previous_viewX,previous_viewY,present_viewX,present_viewY);
                switch (flags.direction){
                    case flags.direction_UP:
                    case flags.direction_DOWN:
                        if(myUtils.isCanMove(check_pointID, view_id, 0, presentY - previousY)) {
                            present_viewX = previous_viewX;
                            present_viewY = previous_viewY + presentY - previousY;
                            this.offsetTopAndBottom(presentY - previousY);
                            System.out.println("dada");
                            isHasMoved = true;
                        }
                        break;
                    case flags.direction_LEFT:
                    case flags.direction_RIGHT:
                        if(myUtils.isCanMove(check_pointID, view_id, presentX - previousX, 0)) {
                            present_viewX = previous_viewX + presentX - previousX;
                            present_viewY = previous_viewY;
                            this.offsetLeftAndRight(presentX - previousX);
                            isHasMoved = true;
                        }
                        break;
                }
                break;
            case MotionEvent.ACTION_UP:
                previous_viewX = this.getLeft();
                previous_viewY = this.getTop();
                switch (flags.direction){
                    case flags.direction_UP:
                        present_viewY = (previous_viewY - flags.gapHeight) / flags.unitHeight * flags.unitHeight + flags.gapHeight;
                        present_viewX = previous_viewX;
                        break;
                    case flags.direction_DOWN:
                        if((previous_viewY + view_height - flags.gapHeight) % flags.unitHeight != 0)
                            present_viewY = ((previous_viewY + view_height - flags.gapHeight) / flags.unitHeight + 1) * flags.unitHeight + flags.gapHeight - view_height;
                        else
                            present_viewY = previous_viewY;
                        present_viewX = previous_viewX;
                        break;
                    case flags.direction_LEFT:
                        present_viewX = (previous_viewX - flags.gapWidth) /  flags.unitWidth *  flags.unitWidth + flags.gapWidth;
                        present_viewY = previous_viewY;
                        break;
                    case flags.direction_RIGHT:
                        if((previous_viewX + view_width - flags.gapWidth) % flags.unitWidth != 0)
                            present_viewX = ((previous_viewX + view_width - flags.gapWidth) / flags.unitWidth + 1) * flags.unitWidth + flags.gapWidth - view_width;
                        else
                            present_viewX = previous_viewX;
                        present_viewY = previous_viewY;
                        break;
                }
                this.offsetLeftAndRight(present_viewX - previous_viewX);
                this.offsetTopAndBottom(present_viewY - previous_viewY);
                flags.myView[check_pointID][view_id].offset(present_viewX - previous_viewX, present_viewY - previous_viewY);
                if(isHasMoved){
                    intent.putExtra("kingdom_steps", 1);
                    context.sendBroadcast(intent);
                    isHasMoved = false;
                }
                if(view_id == 1 && flags.myView[check_pointID][view_id].left == flags.gapWidth + flags.unitWidth
                        && flags.myView[check_pointID][view_id].top == flags.gapHeight + 4 * flags.unitWidth){
                    intent.setAction(flags.action_KingdomHrd_success);
                }
                break;
        }
//        for(int i = 0; i < 10; i++){
//            System.out.println(i+"  "+flags.myView[i].left + "   "+flags.myView[i].top);
//        }
        return true;
    }

    @Override
    public Resources getResources() {
        AutoSizeCompat.autoConvertDensity((super.getResources()), 560, false);//如果有自定义需求就用这个方法
        return super.getResources();
    }
}
