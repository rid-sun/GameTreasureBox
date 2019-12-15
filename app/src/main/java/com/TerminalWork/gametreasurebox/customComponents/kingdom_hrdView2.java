package com.TerminalWork.gametreasurebox.customComponents;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.TerminalWork.gametreasurebox.R;
import com.TerminalWork.gametreasurebox.bean.flags;
import com.TerminalWork.gametreasurebox.methods.myUtils;

public class kingdom_hrdView2 extends View {


    private Intent intent;
    private boolean isHasMoved;

    int previousX, presentX, previous_viewX, present_viewX;
    int previousY, presentY, previous_viewY, present_viewY;

    private int checkPointID;
    private int viewID;
    int view_width;
    int view_height;
    private Context context;
    private boolean firstFlag;

    public kingdom_hrdView2(Context context) {
        super(context);
    }

    public kingdom_hrdView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, new int[]{R.attr.checkPointID, R.attr.viewID});
        checkPointID = ta.getInt(0,0);
        viewID = ta.getInt(1,0);
        ta.recycle();
        setBackground(context.getDrawable(flags.check_point_image[checkPointID][viewID]));
        this.context = context;
        intent = new Intent();
        isHasMoved = false;
        firstFlag = true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        view_width = flags.unitWidth * flags.check_point[checkPointID][viewID][0];
        if(viewID > 9){
            view_height = flags.unitHeight / flags.check_point[checkPointID][viewID][1];
        }else{
            view_height = flags.unitHeight * flags.check_point[checkPointID][viewID][1];
        }
        setMeasuredDimension(view_width, view_height);
    }

    //子view应重写layout，而viewGroup应写onLayout，因为onLayout不仅测量自己，还测量子view
    @Override
    public void layout(int l, int t, int r, int b) {
        if(firstFlag){
            int left = flags.gapWidth + flags.check_point_location[checkPointID][viewID][0] * flags.unitWidth;
            int top = flags.gapHeight + flags.check_point_location[checkPointID][viewID][1] * flags.unitHeight;
            int right = left + view_width;
            int bottom = top + view_height;
//        System.out.println("l: "+left+"t: "+top+"r: "+right+"b: "+bottom);
            super.layout(left, top, right, bottom);
            firstFlag = false;
            if(viewID <= 9){
                flags.myView[checkPointID][viewID] = new Rect(left, top, right, bottom);
            }
        }
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if(visibility == VISIBLE){
//            System.out.println("Visible");
            firstFlag = true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(viewID <= 9){
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    previousX = (int)event.getX();
                    previousY = (int)event.getY();
                    previous_viewX = this.getLeft();
                    previous_viewY = this.getTop();
                    break;
                case MotionEvent.ACTION_MOVE:
                    presentX = (int)event.getX();
                    presentY = (int)event.getY();
                    present_viewX = this.getLeft();
                    present_viewY = this.getTop();
                    flags.direction = myUtils.judgeDirection2(previousX,previousY,presentX,presentY
                            ,previous_viewX,previous_viewY,present_viewX,present_viewY);
                    switch (flags.direction){
                        case flags.direction_UP:
                        case flags.direction_DOWN:
                            if(myUtils.isCanMove(checkPointID, viewID, 0, presentY - previousY)) {
                                this.offsetTopAndBottom(presentY - previousY);
                                isHasMoved = true;
                            }
                            break;
                        case flags.direction_LEFT:
                        case flags.direction_RIGHT:
                            if(myUtils.isCanMove(checkPointID, viewID, presentX - previousX, 0)) {
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
                    flags.myView[checkPointID][viewID].offset(present_viewX - previous_viewX, present_viewY - previous_viewY);
                    this.offsetLeftAndRight(present_viewX - previous_viewX);
                    if(viewID == 1 && flags.myView[checkPointID][viewID].bottom > flags.unitHeight * 5 + flags.gapHeight){
                        this.offsetTopAndBottom(present_viewY - previous_viewY - flags.unitHeight / 2);
                        flags.myView[checkPointID][viewID].offset(0, - flags.unitHeight / 2);
                    }else{
                        this.offsetTopAndBottom(present_viewY - previous_viewY);
                    }
                    if(isHasMoved){
                        intent.setAction(flags.action_changStepsKingdomHrd);
                        intent.putExtra("kingdom_steps", 1);
                        context.sendBroadcast(intent);
                        isHasMoved = false;
                    }
                    if(viewID == 1 && flags.myView[checkPointID][viewID].left == flags.gapWidth + flags.unitWidth
                            && flags.myView[checkPointID][viewID].bottom == flags.gapHeight + 5 * flags.unitHeight + flags.unitHeight / 2){
                        intent.setAction(flags.action_KingdomHrd_success);
                        context.sendBroadcast(intent);
                    }
                    break;
            }
        }
        return true;
    }

}
