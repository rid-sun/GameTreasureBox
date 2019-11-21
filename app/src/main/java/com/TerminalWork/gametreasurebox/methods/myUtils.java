package com.TerminalWork.gametreasurebox.methods;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;

import androidx.gridlayout.widget.GridLayout;

import com.TerminalWork.gametreasurebox.bean.flags;
import com.TerminalWork.gametreasurebox.custom_components.card;

public class myUtils {

    public static int judgeDirection(int previousX, int previousY, int presentX, int presentY){

        int tempX = previousX - presentX;
        int tempY = previousY - presentY;

        //计算横纵坐标相对于初始坐标的偏移量
        int x_x=Math.abs(tempX);//横坐标偏移量
        int y_y=Math.abs(tempY);//纵坐标偏移量

        //通过两个坐标偏移量的正负、大小来确定相对于初始位置的移动方向
        if(tempY > 0)
        {
            if(x_x == 0 || x_x / y_y < 1)
                return flags.direction_UP;
            else if(tempX > 0)
                return flags.direction_LEFT;
            else
                return flags.direction_RIGHT;
        }
        else if(y_y == 0)
        {
            if(tempX > 0)
                return flags.direction_LEFT;
            else
                return flags.direction_RIGHT;
        }
        else
        {
            if(x_x == 0 || x_x / y_y < 1)
                return flags.direction_DOWN;
            else if(tempX > 0)
                return flags.direction_LEFT;
            else
                return flags.direction_RIGHT;
        }
    }

    public static boolean isCanMove(int check_PointID, int id, int dx, int dy){
        Rect temp = flags.myView[check_PointID][id];
        temp.offset(dx,dy);
        for(int i=0;i<=9;i++)
        {
            Rect temp2=flags.myView[check_PointID][i];
            if(temp.intersects(temp2.left, temp2.top, temp2.right, temp2.bottom) && id != i || temp.top < 420 || temp.bottom > 2170 || temp.left < 21 || temp.right > 1421)
            {
                temp.offset(-dx, -dy);
                return false;
            }
        }
        return true;
    }

    public static void cardScale(card self, int scale_scheme){
        ObjectAnimator objectAnimator1, objectAnimator2;
        AnimatorSet animatorSet=new AnimatorSet();
        switch (scale_scheme){
            case flags.scaleScheme_production:
                objectAnimator1=ObjectAnimator.ofFloat(self,"scaleX",0.5f, 1f);
                objectAnimator1.setInterpolator(new OvershootInterpolator());
                objectAnimator2=ObjectAnimator.ofFloat(self,"scaleY",0.5f, 1f);
                objectAnimator2.setInterpolator(new OvershootInterpolator());
                animatorSet.play(objectAnimator1).with(objectAnimator2);
                animatorSet.setDuration(500);
                animatorSet.start();
                break;
            case flags.scaleScheme_composite:
                objectAnimator1=ObjectAnimator.ofFloat(self,"scaleX",1.2f, 1f);
                objectAnimator1.setInterpolator(new OvershootInterpolator());
                objectAnimator2=ObjectAnimator.ofFloat(self,"scaleY",1.2f, 1f);
                objectAnimator2.setInterpolator(new OvershootInterpolator());
                animatorSet.play(objectAnimator1).with(objectAnimator2);
                animatorSet.setDuration(500);
                animatorSet.start();
                break;
        }

    }

}
