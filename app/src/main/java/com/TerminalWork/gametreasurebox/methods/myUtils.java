package com.TerminalWork.gametreasurebox.methods;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Rect;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;

import com.TerminalWork.gametreasurebox.bean.flags;
import com.TerminalWork.gametreasurebox.customComponents.card;

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

    public static int judgeDirection2(int previousX, int previousY, int presentX, int presentY,
                                        int previous_viewX, int previous_viewY, int present_viewX, int present_viewY){

        int tempX = (previousX + previous_viewX) - (presentX + present_viewX);
        int tempY = (previousY + previous_viewY) - (presentY + present_viewY);

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
        for(int i = 0; i <= 9; i++)
        {
            Rect temp2 = flags.myView[check_PointID][i];
            if(id == 1){
                if(temp.left == 106 * 3.5 && temp.bottom <= 670 * 3.5 && temp.top >= 420 * 3.5){
                    return true;
                }
            }
            if(temp.intersects(temp2.left, temp2.top, temp2.right, temp2.bottom) && id != i || temp.top < 420 || temp.bottom > 2170 || temp.left < 21 || temp.right > 1421)
            {
                temp.offset(-dx, -dy);
                return false;
            }
        }
        return true;
    }

    public static void cardMove(final card self, final int fromX, final int fromY, int toX, int toY){
        TranslateAnimation translateAnimation = new TranslateAnimation(fromX, toX, fromY, toY);
        translateAnimation.setDuration(500);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                self.clearAnimation();
//                GridLayout.LayoutParams lp = (GridLayout.LayoutParams)self.getLayoutParams();
//                lp.leftMargin = fromX * flags.card_width;
//                lp.topMargin = fromY * flags.card_width;
//                self.setLayoutParams(lp);
                self.setVisibility(View.VISIBLE);//translation是视图动画，并不会真正地去移动位置
                                                    //，所以重新使其visible时，便能在原来地方显示出来
                                                    //而属性动画常用于和用户交互，需要真正地移动。
//                Log.i("message","恢复位置");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        self.startAnimation(translateAnimation);
//        Log.i("message","移动动画执行");
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
