package com.TerminalWork.gametreasurebox.custom_components;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.TerminalWork.gametreasurebox.R;
import com.TerminalWork.gametreasurebox.bean.flags;
import com.TerminalWork.gametreasurebox.methods.myUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class _2048_view extends GridLayout {

    private int previousX, previousY;
    private int presentX, presentY;
    private card[][] cd;
    private List<Point> numberIsZero;
    private ObjectAnimator objectAnimator;

    public _2048_view(Context context) {
        super(context);
        initView();
    }

    public _2048_view(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        flags.card_width = (Math.min(w,h)-30)/4;
        addCard(flags.card_width);
        startGame();
    }

    private void judgeNumber(int num){
        switch(num){
            case 8:
                Toast.makeText(getContext(),getResources().getString(R.string.noticeFor_512),Toast.LENGTH_SHORT);
            case 512:
                Toast.makeText(getContext(),getResources().getString(R.string.noticeFor_512),Toast.LENGTH_SHORT);
            case 1024:
                Toast.makeText(getContext(),getResources().getString(R.string.noticeFor_1024),Toast.LENGTH_SHORT);
                break;
            case 4096:
                Toast.makeText(getContext(),getResources().getString(R.string.noticeFor_4096),Toast.LENGTH_SHORT);
                break;
        }
    }

    private void initView(){
        setBackgroundColor(ContextCompat.getColor(getContext(), R.color.backGround_color_panel));
        setColumnCount(4);
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        previousX = (int)event.getX();
                        previousY = (int)event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        presentX = (int)event.getX();
                        presentY = (int)event.getY();
                        switch (myUtils.judgeDirection(previousX, previousY, presentX, presentY)){
                            case flags.direction_UP:
                                slideToUp();
                                break;
                            case flags.direction_LEFT:
                                slideToLeft();
                                break;
                            case flags.direction_DOWN:
                                slideToDown();
                                break;
                            case flags.direction_RIGHT:
                                slideToRight();
                                break;
                        }
                        break;
                }
                return true;
            }
        });
        cd = new card[4][4];
        numberIsZero = new ArrayList<>();
    }

    private void addCard(int card_width){
        card temp;
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                temp = new card(getContext());
                temp.setNumber(0);
                cd[i][j] = temp;
                addView(temp, card_width, card_width);
            }
        }
    }

    private void startGame(){
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                cd[i][j].setNumber(0);
            }
        }
        addRandomCard(2);
//        for (int i = 0; i < 4; i++){
//            for (int j = 0; j < 4; j++){
//                System.out.println(cd[i][j].getNumber());
//            }
//        }
    }

    private void addRandomCard(int count){
        numberIsZero.clear();
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4;  j++){
                if (cd[i][j].getNumber() == 0){
                    numberIsZero.add(new Point(i,j));
                }
            }
        }

        for(int i = 0; i < count; i++){
            //把一张空卡片换成带数字的
            Point p = numberIsZero.remove((int)(Math.random() * numberIsZero.size()));
            cd[p.x][p.y].setNumber(Math.random() > 0.1 ? 2 : 4);
            myUtils.cardScale(cd[p.x][p.y], flags.scaleScheme_production);
//        MainActivity.getMainActivity().getAnimLayer().createScaleTo1(cardMap[p.x][p.y]);
        }
    }

    private void slideToUp(){
        boolean canMove = false;
        Point tp = new Point(0,0);
        for(int j = 0; j < 4; j++){
            int index = 0;
            int previousNum = 0;
            for(int i = 0; i < 4; i++){
                int temp = cd[i][j].getNumber();
                if(temp == 0)
                    continue;
                if(previousNum == 0)
                {
                    previousNum = temp;
                    tp.x = i;
                    tp.y = j;
                }
                else{
                    if(temp == previousNum){
                        previousNum = 0;
                        cd[i][j].setNumber(0);
                        cd[tp.x][tp.y].setNumber(0);
                        cd[index][j].setNumber(temp * 2);
                        judgeNumber(temp * 2);
                        canMove = true;
                        myUtils.cardScale(cd[index][j], flags.scaleScheme_composite);
                        index++;
                    }
                    else{
                        cd[tp.x][tp.y].setNumber(0);
                        cd[index][j].setNumber(previousNum);
                        if(index != tp.x)
                        {
                            canMove = true;
                        }
                        previousNum = temp;
                        tp.y = j;
                        tp.x = i;
                        index++;
                    }
                }
            }
            if(previousNum != 0){
                cd[tp.x][tp.y].setNumber(0);
                cd[index][j].setNumber(previousNum);
                if(index != tp.x)
                {
                    canMove = true;
                }
            }
        }
        if(canMove){
            addRandomCard(1);
//            ckeckComplete();
//            for(int i = 0; i < 4; i++){
//                for(int j = 0; j < 4; j++)
//                    System.out.println(cd[i][j].getNumber());
//            }
        }
        else{
            //游戏失败
        }
    }

    private void slideToLeft(){

        boolean canMove = false;
        Point tp = new Point(0, 0);
        for(int i = 0; i < 4; i++){
            int index = 0;
            int previousNum = 0;
            for(int j = 0; j < 4; j++){
                int temp = cd[i][j].getNumber();
                if(temp == 0)
                    continue;
                if(previousNum == 0)
                {
                    previousNum = temp;
                    tp.x = i;
                    tp.y = j;
                }
                else{
                    if(temp == previousNum){
                        previousNum = 0;
                        cd[i][j].setNumber(0);
                        cd[tp.x][tp.y].setNumber(0);
                        judgeNumber(temp * 2);
                        myUtils.cardScale(cd[i][index], flags.scaleScheme_composite);
                        cd[i][index++].setNumber(temp * 2);
                        canMove = true;
                        //动画播放
                        //动画播放
                    }
                    else{
                        cd[tp.x][tp.y].setNumber(0);
                        cd[i][index++].setNumber(previousNum);
                        if(index - 1 != tp.y)
                        {
                            canMove = true;
                            //动画播放
                        }
                        previousNum = temp;
                        tp.y = j;
                        tp.x = i;
                    }
                }
            }
            if(previousNum != 0){
                cd[tp.x][tp.y].setNumber(0);
                cd[i][index].setNumber(previousNum);
                if(index != tp.y){
                    canMove = true;
                }

            }
        }
        if(canMove){
            addRandomCard(1);
//            ckeckComplete();
        }else{
            //游戏失败
        }
    }

    private void slideToDown(){

        boolean canMove = false;
        Point tp = new Point(0, 0);
        for(int j = 0; j < 4; j++){
            int index = 3;
            int previousNum = 0;
            for(int i = 3; i >= 0; i--){
                int temp = cd[i][j].getNumber();
                if(temp == 0)
                    continue;
                if(previousNum == 0)
                {
                    previousNum = temp;
                    tp.x = i;
                    tp.y = j;
                }
                else{
                    if(temp == previousNum){
                        previousNum = 0;
                        cd[i][j].setNumber(0);
                        cd[tp.x][tp.y].setNumber(0);
                        judgeNumber(temp * 2);
                        myUtils.cardScale(cd[index][j], flags.scaleScheme_composite);
                        cd[index--][j].setNumber(temp * 2);
                        canMove = true;
                        //动画播放
                        //动画播放
                    }
                    else{
                        cd[tp.x][tp.y].setNumber(0);
                        cd[index--][j].setNumber(previousNum);
                        if(index + 1 != tp.x)
                        {
                            canMove = true;
                            //动画播放
                        }
                        previousNum = temp;
                        tp.y = j;
                        tp.x = i;
                    }
                }
            }
            if(previousNum != 0){
                cd[tp.x][tp.y].setNumber(0);
                cd[index][j].setNumber(previousNum);
                if(index != tp.x){
                    canMove = true;
                }
            }
        }
        if(canMove){
            addRandomCard(1);
//            ckeckComplete();
        }else{
            //游戏失败
        }
    }

    private void slideToRight(){

        boolean canMove = false;
        Point tp = new Point(0, 0);
        for(int i = 0; i < 4; i++){
            int index = 3;
            int previousNum = 0;
            for(int j = 3; j >= 0; j--){
                int temp = cd[i][j].getNumber();
                if(temp == 0)
                    continue;
                if(previousNum == 0)
                {
                    previousNum = temp;
                    tp.x = i;
                    tp.y = j;
                }
                else{
                    if(temp == previousNum){
                        previousNum = 0;
                        cd[i][j].setNumber(0);
                        cd[tp.x][tp.y].setNumber(0);
                        judgeNumber(temp * 2);
                        myUtils.cardScale(cd[i][index], flags.scaleScheme_composite);
                        cd[i][index--].setNumber(temp * 2);
                        canMove = true;
                        //动画播放
                        //动画播放
                    }
                    else{
                        cd[tp.x][tp.y].setNumber(0);
                        cd[i][index--].setNumber(previousNum);
                        if(index + 1 != tp.y)
                        {
                            canMove = true;
                            //动画播放
                        }
                        previousNum = temp;
                        tp.y = j;
                        tp.x = i;
                    }
                }
            }
            if(previousNum != 0){
                cd[tp.x][tp.y].setNumber(0);
                cd[i][index].setNumber(previousNum);
                if(tp.y != index){
                    canMove = true;
                }
            }
        }
        if(canMove){
            addRandomCard(1);
//            ckeckComplete();
        }else{
            //游戏失败
        }
    }

}
