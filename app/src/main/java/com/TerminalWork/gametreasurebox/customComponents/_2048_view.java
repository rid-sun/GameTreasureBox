package com.TerminalWork.gametreasurebox.customComponents;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.TerminalWork.gametreasurebox.R;
import com.TerminalWork.gametreasurebox.bean.flags;
import com.TerminalWork.gametreasurebox.methods.myUtils;

import java.util.ArrayList;
import java.util.List;

public class _2048_view extends GridLayout {

    private int previousX, previousY;
    private int presentX, presentY;
    private card[][] cd;
    private List<Point> numberIsZero;
    private int score;
    private Context context;
    private Intent intent;

    public _2048_view(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public _2048_view(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        flags.card_width = (Math.min(w,h) - 30) / 4;
        Log.i("car_width",flags.card_width + "");
        addCard(flags.card_width);
        startGame();
    }

    private void judgeNumber(int num){
        switch(num){
            case 512:
                Toast.makeText(context,getResources().getString(R.string.noticeFor_512),Toast.LENGTH_SHORT).show();
                break;
            case 1024:
                Toast.makeText(context,getResources().getString(R.string.noticeFor_1024),Toast.LENGTH_SHORT).show();
                break;
            case 4096:
                Toast.makeText(context,getResources().getString(R.string.noticeFor_4096),Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void initView(){
        setBackgroundColor(ContextCompat.getColor(getContext(), R.color.backGround_color_panel));
        setColumnCount(4);
        intent = new Intent("changeScore");
        score = 0;
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
        Log.i("random","生成随机数字");
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
                if(previousNum == 0) {
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
                        myUtils.cardMove(cd[tp.x][tp.y], tp.y, tp.x, j, index);
                        myUtils.cardMove(cd[i][j], j, i, j, index);
                        myUtils.cardScale(cd[index][j], flags.scaleScheme_composite);
                        intent.putExtra("score", score += temp * 2);
                        context.sendBroadcast(intent);
                        judgeNumber(temp * 2);
                        canMove = true;
                        index++;
                    }
                    else{
                        cd[tp.x][tp.y].setNumber(0);
                        cd[index][j].setNumber(previousNum);
                        if(index != tp.x) {
                            myUtils.cardMove(cd[tp.x][tp.y], tp.y, tp.x, j, index);
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
                    myUtils.cardMove(cd[tp.x][tp.y], tp.y, tp.x, j, index);
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
            Toast.makeText(context, "游戏结束，重来一次吧", Toast.LENGTH_SHORT).show();
            intent.putExtra("score", 0);
            context.sendBroadcast(intent);
            startGame();
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
                if(previousNum == 0) {
                    previousNum = temp;
                    tp.x = i;
                    tp.y = j;
                }
                else{
                    if(temp == previousNum){
                        previousNum = 0;
                        cd[i][j].setNumber(0);
                        cd[tp.x][tp.y].setNumber(0);
                        cd[i][index].setNumber(temp * 2);
                        myUtils.cardMove(cd[i][j], j, i, index, i);
                        myUtils.cardMove(cd[tp.x][tp.y], tp.y, tp.x, index, i);
                        myUtils.cardScale(cd[i][index], flags.scaleScheme_composite);
                        intent.putExtra("score", score += temp * 2);
                        context.sendBroadcast(intent);
                        judgeNumber(temp * 2);
                        canMove = true;
                        index++;
                    }
                    else{
                        cd[tp.x][tp.y].setNumber(0);
                        cd[i][index].setNumber(previousNum);
                        if(index != tp.y) {
                            myUtils.cardMove(cd[tp.x][tp.y], tp.y, tp.x, index, i);
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
                cd[i][index].setNumber(previousNum);
                if(index != tp.y){
                    myUtils.cardMove(cd[tp.x][tp.y], tp.y, tp.x, index, i);
                    canMove = true;
                }

            }
        }
        if(canMove){
            addRandomCard(1);
//            ckeckComplete();
        }else{
            Toast.makeText(context, "游戏结束，重来一次吧", Toast.LENGTH_SHORT).show();
            intent.putExtra("score", 0);
            context.sendBroadcast(intent);
            startGame();
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
                if(previousNum == 0) {
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
                        myUtils.cardMove(cd[i][j], j, i, j, index);
                        myUtils.cardMove(cd[tp.x][tp.y], tp.y, tp.x, j, index);
                        myUtils.cardScale(cd[index][j], flags.scaleScheme_composite);
                        intent.putExtra("score", score += temp * 2);
                        context.sendBroadcast(intent);
                        canMove = true;
                        judgeNumber(temp * 2);
                        index--;
                    }
                    else{
                        cd[tp.x][tp.y].setNumber(0);
                        cd[index][j].setNumber(previousNum);
                        if(index != tp.x) {
                            myUtils.cardMove(cd[tp.x][tp.y], tp.y, tp.x, j, index);
                            canMove = true;
                        }
                        previousNum = temp;
                        tp.y = j;
                        tp.x = i;
                        index--;
                    }
                }
            }
            if(previousNum != 0){
                cd[tp.x][tp.y].setNumber(0);
                cd[index][j].setNumber(previousNum);
                if(index != tp.x){
                    myUtils.cardMove(cd[tp.x][tp.y], tp.y, tp.x, j, index);
                    canMove = true;
                }
            }
        }
        if(canMove){
            addRandomCard(1);
//            ckeckComplete();
        }else{
            Toast.makeText(context, "游戏结束，重来一次吧", Toast.LENGTH_SHORT).show();
            intent.putExtra("score", 0);
            context.sendBroadcast(intent);
            startGame();
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
                if(previousNum == 0) {
                    previousNum = temp;
                    tp.x = i;
                    tp.y = j;
                }
                else{
                    if(temp == previousNum){
                        previousNum = 0;
                        cd[i][j].setNumber(0);
                        cd[tp.x][tp.y].setNumber(0);
                        cd[i][index].setNumber(temp * 2);
                        myUtils.cardMove(cd[i][j], j, i, index, i);
                        myUtils.cardMove(cd[tp.x][tp.y], tp.y, tp.x, index, i);
                        myUtils.cardScale(cd[i][index], flags.scaleScheme_composite);
                        intent.putExtra("score", score += temp * 2);
                        context.sendBroadcast(intent);
                        judgeNumber(temp * 2);
                        canMove = true;
                        index--;
                    }
                    else{
                        cd[tp.x][tp.y].setNumber(0);
                        cd[i][index].setNumber(previousNum);
                        if(index != tp.y) {
                            myUtils.cardMove(cd[tp.x][tp.y], tp.y, tp.x, index, i);
                            canMove = true;
                        }
                        previousNum = temp;
                        tp.y = j;
                        tp.x = i;
                        index--;
                    }
                }
            }
            if(previousNum != 0){
                cd[tp.x][tp.y].setNumber(0);
                cd[i][index].setNumber(previousNum);
                if(tp.y != index){
                    myUtils.cardMove(cd[tp.x][tp.y], tp.y, tp.x, index, i);
                    canMove = true;
                }
            }
        }
        if(canMove){
            addRandomCard(1);
//            ckeckComplete();
        }else{
            Toast.makeText(context, "游戏结束，重来一次吧", Toast.LENGTH_SHORT).show();
            intent.putExtra("score", 0);
            context.sendBroadcast(intent);
            startGame();
        }
    }

}
