package com.TerminalWork.gametreasurebox.customComponents;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.TerminalWork.gametreasurebox.R;
import com.TerminalWork.gametreasurebox.bean.flags;
import com.TerminalWork.gametreasurebox.methods.myUtils;

import java.util.ArrayList;
import java.util.List;

/*
 * 作者：JiaTai Sun
 * 时间：20-1-24 下午3:45
 * 类名：_2048_view
 * 功能：2048游戏面板
 */

public class _2048_view extends GridLayout {

    private int previousX, previousY;
    private int presentX, presentY;//判断手势需要的坐标
    private card[][] cd;//滑块实体
    private List<Point> numberIsZero;//记录滑块数字为零的数组
    private int score;//分数
    private Context context;
    private Intent intent;
    private View toastView;//自定义提示view
    private boolean flag;

    public _2048_view(Context context) {
        super(context);
        this.context = context;
        toastView = inflate(context, R.layout.toast, null);
        flag = false;
        initView();
    }

    public _2048_view(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        toastView = inflate(context, R.layout.toast, null);
        flag = false;
        initView();
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        int w = MeasureSpec.getSize(widthSpec);
        int h = MeasureSpec.getSize(heightSpec);
        int temp = Math.min(w, h);
        //避免重复进行下面的函数
        //由于是载入xml文件，所以需要在这一回调函数才能获得该自定义
        //View的宽度
        if(!flag) {
            flags.card_width = (temp - 30) / 4;
            setMeasuredDimension(temp, temp);
            addCard(flags.card_width);
            startGame();
            flag = true;
        }
    }

    //判断合成的数字，给出相应的提示
    private void judgeNumber(int num){
        Toast toast = new Toast(context);
        switch(num){
            case 32:
                ((TextView)(toastView.findViewById(R.id.toast_text)))
                        .setText(getResources().getString(R.string.noticeFor_32));
                toast.setView(toastView);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.show();
                break;
            case 1024:
                ((TextView)(toastView.findViewById(R.id.toast_text)))
                        .setText(getResources().getString(R.string.noticeFor_1024));
                toast.setView(toastView);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.show();
                break;
            case 4096:
                ((TextView)(toastView.findViewById(R.id.toast_text)))
                        .setText(getResources().getString(R.string.noticeFor_4096));
                toast.setView(toastView);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.show();
                break;
        }
    }

    //初始化面板
    private void initView(){
        setColumnCount(4);//设置gridLayout它的列数
        //设置面板背景色，由于是在自定义控件中，所以通过下面的方式来寻找对应资源
        setBackgroundColor(ContextCompat.getColor(getContext(), R.color.backGround_color_panel));
        //初始化intent，用来向主界面的通信，传递分数
        intent = new Intent(flags.action_changeScore2048);
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

    //向这个自定义view中添加滑块
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

    //游戏启动函数，负责初始化滑块数值
    private void startGame(){
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                cd[i][j].setNumber(0);
            }
        }
        addRandomCard(2);
        Log.i("random","生成随机数字");
    }

    //随机在空白处生成数字
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

    //处理向上滑动的函数
    private void slideToUp(){
        boolean canMove = false;//标记本次滑动是否有滑块移动
        Point tp = new Point(0,0);//用于存储待移动滑块的位置
        for(int j = 0; j < 4; j++){//进行从左到右的扫描
            int index = 0;//“浮动标志”，用于标记下一个待移动滑块的目标“位置”
            int previousNum = 0;//暂存前一滑块数值，用于判断是否可以合并
            for(int i = 0; i < 4; i++){//从上到下扫描，因为滑块最终是从上到下累积的
                int temp = cd[i][j].getNumber();
                if(temp == 0)
                    continue;
                if(previousNum == 0) {//暂存数字
                    previousNum = temp;
                    tp.x = i;
                    tp.y = j;
                }
                else{
                    if(temp == previousNum){//如果可以合并
                        previousNum = 0;//将暂存区归零
                        cd[i][j].setNumber(0);//更新该滑块数字
                        cd[tp.x][tp.y].setNumber(0);//更新另一合并滑块数字
                        cd[index][j].setNumber(temp * 2);//更新目标位置滑块数字
                        myUtils.cardMove(cd[tp.x][tp.y], tp.y, tp.x, j, index);//移动动画
                        myUtils.cardMove(cd[i][j], j, i, j, index);//移动动画
                        myUtils.cardScale(cd[index][j], flags.scaleScheme_composite);//合并动画
                        intent.putExtra("score", score += temp * 2);//设置分数
                        context.sendBroadcast(intent);//进行通信
                        judgeNumber(temp * 2);//判断数字，是否需要给出提示
                        canMove = true;//移动标志置为true
                        index++;//累积滑块数增加1
                    }
                    else{//如果不能合并
                        cd[tp.x][tp.y].setNumber(0);//待移动位置滑块数字更新
                        cd[index][j].setNumber(previousNum);//目标位置滑块数字更新
                        if(index != tp.x) {//如果移动前后位置不同
                            myUtils.cardMove(cd[tp.x][tp.y], tp.y, tp.x, j, index);
                            canMove = true;
                        }
                        previousNum = temp;//g更新暂存区
                        tp.y = j;//更新待移动位置
                        tp.x = i;//更新待移动位置
                        index++;//滑块累积数增加1
                    }
                }
            }
            if(previousNum != 0){//如果移动完毕后，暂存区还剩非零数字
                cd[tp.x][tp.y].setNumber(0);//更新待移动滑块数字
                cd[index][j].setNumber(previousNum);//更新目标位置滑块数字
                if(index != tp.x)//如果位置发生改变
                {
                    myUtils.cardMove(cd[tp.x][tp.y], tp.y, tp.x, j, index);
                    canMove = true;
                }
            }
        }
        if(canMove){//如果本次用户操作移动了滑块
            addRandomCard(1);
        }
        else{
            if(checkComplete()){//如果用户已经不能再移动滑块了，那么游戏失败
                Toast.makeText(context, "游戏结束，重来一次吧", Toast.LENGTH_SHORT).show();
                intent.putExtra("score", 0);//得分归零
                context.sendBroadcast(intent);//通信
                startGame();//重开游戏
            }
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
        }else{
            if(checkComplete()){
                Toast.makeText(context, "游戏结束，重来一次吧", Toast.LENGTH_SHORT).show();
                intent.putExtra("score", 0);
                context.sendBroadcast(intent);
                startGame();
            }
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
        }else{
            if(checkComplete()){
                Toast.makeText(context, "游戏结束，重来一次吧", Toast.LENGTH_SHORT).show();
                intent.putExtra("score", 0);
                context.sendBroadcast(intent);
                startGame();
            }
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
        }else{
            if(checkComplete()){
                Toast.makeText(context, "游戏结束，重来一次吧", Toast.LENGTH_SHORT).show();
                intent.putExtra("score", 0);
                context.sendBroadcast(intent);
                startGame();
            }
        }
    }

    private Boolean checkComplete(){
        numberIsZero.clear();
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4;  j++){
                if (cd[i][j].getNumber() == 0){
                    numberIsZero.add(new Point(i,j));
                }
            }
        }
        return numberIsZero.isEmpty();
    }
}
