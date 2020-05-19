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
 * 作者：rid-sun
 * 时间：20-1-24 下午3:45
 * 类名：_2048_view
 * 功能：2048游戏面板，负责加载游戏、分数显示
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
    private boolean flag;//标志，用于规避掉当面板重新测量大小时，重复计算卡片大小和加载卡片

    public _2048_view(Context context) {
        super(context);
        this.context = context;
        toastView = inflate(context, R.layout.toast, null);//实例化自定义view
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
        if(!flag) {
            flags.card_width = (temp - 30) / 4;//计算正方形卡片的大小
            setMeasuredDimension(temp, temp);//设定面板的大小
            addCard(flags.card_width);//添加卡片到面板
            startGame();//开始游戏
            flag = true;//将标志置为true，规避重复添加问题
        }
        Log.i("_2048_view","onMeasure");
    }

    //判断得出的数字，加以给出相应的提示
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
        setBackgroundColor(ContextCompat.getColor(context, R.color.backGround_color_panel));//设置面板的背景色
        setColumnCount(4);//设置面板的列数
        intent = new Intent(flags.action_changeScore2048);//定义intent用于后面的广播传送
        score = 0;//初始化得分

        //设置接触事件
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN://接触屏幕时记下坐标
                        previousX = (int)event.getX();
                        previousY = (int)event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        //离开屏幕时记下坐标
                        presentX = (int)event.getX();
                        presentY = (int)event.getY();
                        //根据这两组坐标计算手势，然后决定分别执行哪些函数
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
                return true;//返回true，表示不再需要其它接口来继续执行
            }
        });
        cd = new card[4][4];
        numberIsZero = new ArrayList<>();
    }

    //添加边长为card_width的正方形卡片到面板
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

    //开始游戏的初始化工作
    private void startGame(){
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                cd[i][j].setNumber(0);
            }
        }
        addRandomCard(2);//生成两个随机数字的方块
        Log.i("random","生成随机数字");
    }

    //生成指定数目的随机数字的方块
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

    //向上滑动函数
    private void slideToUp(){
        boolean canMove = false;//先将是否有滑块移动标志置为false
        Point tp = new Point(0,0);//用于存储滑块坐标的变量
        for(int j = 0; j < 4; j++){//列循环
            int index = 0;//执行手势移动后，该列当前非空非重复数字个数为0
            int previousNum = 0;//上个非空滑块的数字
            for(int i = 0; i < 4; i++){//行循环
                int temp = cd[i][j].getNumber();//获取当前坐标滑块的数字
                if(temp == 0)//如果该数字为0
                    continue;
                if(previousNum == 0) {//如果该数字不为0，且上个非空滑块的数字为0
                    previousNum = temp;//将该数字记录，放入“等待区”
                    tp.x = i;//并且记录它的坐标
                    tp.y = j;
                }
                else{//如果该数字不为0，且上个非空滑块的数字不为0
                    if(temp == previousNum){//如果该数字与“等待区”的数字是相等的
                        previousNum = 0;//更新“等待区”数字为0
                        cd[i][j].setNumber(0);//更新该滑块的数字
                        cd[tp.x][tp.y].setNumber(0);//更新“等待区”滑块的数字
                        cd[index][j].setNumber(temp * 2);//将合并后的数字依次放在滑块上
                        myUtils.cardMove(cd[tp.x][tp.y], tp.y, tp.x, j, index);//移动动画
                        myUtils.cardMove(cd[i][j], j, i, j, index);//移动动画
                        myUtils.cardScale(cd[index][j], flags.scaleScheme_composite);//合成动画
                        intent.putExtra("score", score += temp * 2);//设置分数
                        context.sendBroadcast(intent);//发送广播
                        judgeNumber(temp * 2);//判断合成的数字
                        canMove = true;//移动标志置为true
                        index++;//当前列累计非空数字个数加1
                    }
                    else{//如果该数字与“等待区”的数字不相等
                        cd[tp.x][tp.y].setNumber(0);//更新该滑块数字
                        cd[index][j].setNumber(previousNum);//该滑块数字移动后对应位置的数字更新
                        if(index != tp.x) {//如果该滑块数字移动了
                            myUtils.cardMove(cd[tp.x][tp.y], tp.y, tp.x, j, index);
                            canMove = true;
                        }
                        previousNum = temp;//更新“等待区”数字
                        tp.y = j;
                        tp.x = i;
                        index++;//当前列累计非空数字个数加1
                    }
                }
            }
            if(previousNum != 0){//列行循环结束，等待区是否仍有数字
                cd[tp.x][tp.y].setNumber(0);
                cd[index][j].setNumber(previousNum);
                if(index != tp.x)
                {
                    myUtils.cardMove(cd[tp.x][tp.y], tp.y, tp.x, j, index);
                    canMove = true;
                }
            }
        }
        if(canMove){//如果移动了，那么生成1个新数字
            addRandomCard(1);
        }
        else{//如果没有移动，手势不对或者游戏失败
            if(checkComplete()){
                Toast.makeText(context, "游戏结束，重来一次吧", Toast.LENGTH_SHORT).show();
                intent.putExtra("score", 0);
                context.sendBroadcast(intent);
                startGame();
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
