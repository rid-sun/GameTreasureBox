package com.TerminalWork.gametreasurebox.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
import androidx.fragment.app.Fragment;

import com.TerminalWork.gametreasurebox.MainActivity;
import com.TerminalWork.gametreasurebox.R;

import java.util.Timer;
import java.util.TimerTask;

/*
 * 作者：JiaTai Sun
 * 时间：20-5-26 下午5:33
 * 类名：Hanoi
 * 功能：汉诺塔演示游戏主体
 */

public class Hanoi extends Fragment {

    private Button confirm;
    private EditText input;
    private AlertDialog dialog;
    private int onFontCounter;

    private TextView[] tv = new TextView[10];
    private int[] dx;
    private int[] dy;
    private int[][] dc = new int[10][2];
    private int[] size = new int[3];
    private int[][] store = new int[3][9];
    private int[][] step = new int[512][2];
    private int state1, state2;
    private int direction;
    private int floor;
    private Timer timer;
    private TimerTask task;
    private View view;
    private int unitHeight;
    private int unitWidth;
    private int maxHeight;
    private boolean flag;
    private boolean isRunning;

    public Hanoi() {
        onFontCounter = 0;
        state1 = 0;
        state2 = 0;
        direction = 1;
        isRunning = false;
        floor = 9;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.game_hanoi,container,false);
        view.addOnLayoutChangeListener(changeListener);
        flag = true;
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        confirm = view.findViewById(R.id.hanoi_confirm);
        confirm.setOnClickListener(mButtonOnClickListener);
        if(onFontCounter != 0 && isRunning){
            Log.i("hanoiMessage", "state1: " + state1 + "state2: " + state2 + "direction: " + direction);
            direction = 1;
            state2 = 1;
            size[0] = floor;
            size[1] = size[2] = 0;
            for(int i = 1;i <= 9;i++) {
                dc[i][0]=dx[i];
                dc[i][1]=dy[i];
            }
            start_timer();
        }
        onFontCounter++;
        Log.i("HanoiFragment","onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        stop_timer();
    }

    private View.OnLayoutChangeListener changeListener = new View.OnLayoutChangeListener() {
        @Override
        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
            if(flag){
                originPrepare();
                flag = false;
            }
        }
    };

    private View.OnClickListener mButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String source = confirm.getText().toString();
            if("确认".equals(source))
            {
                try {
                    input = view.findViewById(R.id.hanoi_input);
                    floor = Integer.parseInt(input.getText().toString());
                    if (floor <= 0 || floor > 9) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("请保证层数在1-9之间");
                        builder.show();
                    } else {
                        confirm.setText("复位");
                        for(int i = 1; i <= floor; i++) {
                            store[0][i-1]=i;
                            tv[i].setVisibility(View.VISIBLE);
                        }
                        for(int i = floor + 1; i <= 9; i++) {
                            tv[i].setVisibility(View.GONE);
                        }
                        size[0] = floor;
                        size[1] = size[2] = 0;
                        state1 = 0;
                        getStep(floor, 1, 2, 3);
                        state2 = 1;

                        //这行非常重要，如果不使ed失去焦点，
                        // 在盘子移动时，对ed输入或按键都会影响视图的正常运行
                        input.setFocusable(false);

                        start_timer();
                        isRunning = true;
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            else
            {
                if(task == null || timer == null){
                    resetPosition();
                }
                else{
                    stop_timer();
                    dialog.show();
                }
            }
        }
    } ;

    private void resetPosition(){
        for(int i = 1; i <= floor; i++){
            int offsetX= -(dc[i][0] - dx[i]);
            int offsetY= -(dc[i][1] - dy[i]);
            dc[i][0] = dx[i];
            dc[i][1] = dy[i];
            tv[i].offsetLeftAndRight(offsetX);
            tv[i].offsetTopAndBottom(offsetY);
        }
        confirm.setText("确认");
        input.setFocusable(true);
        input.setFocusableInTouchMode(true);
        input.requestFocus();
        direction = 1;
        state2 = 1;
    }

    private void originPrepare(){

        tv[9]= view.findViewById(R.id.textView17);
        tv[8]= view.findViewById(R.id.textView16);
        tv[7]= view.findViewById(R.id.textView15);
        tv[6]= view.findViewById(R.id.textView14);
        tv[5]= view.findViewById(R.id.textView13);
        tv[4]= view.findViewById(R.id.textView12);
        tv[3]= view.findViewById(R.id.textView11);
        tv[2]= view.findViewById(R.id.textView10);
        tv[1]= view.findViewById(R.id.textView9);

        TextView tv7 = view.findViewById(R.id.textView7);
        TextView tv6 = view.findViewById(R.id.textView6);
        unitWidth = tv7.getLeft() - tv6.getLeft();
        unitHeight = tv[1].getHeight();
        maxHeight = tv[9].getTop() - 2 * unitHeight;

        dx = new int[10];
        dy = new int[10];
        for(int i = 1; i < 10; i++){
            dx[i] = tv[i].getLeft();
            dy[i] = tv[i].getTop();
        }

        for(int i = 1;i <= 9;i++)
        {
            dc[i][0]=dx[i];
            dc[i][1]=dy[i];
        }

        originDialog();
    }

    private void start_timer() {
        if(timer==null){
            timer=new Timer();
        }
        if(task==null){
            task = new TimerTask() {
                @Override
                public void run() {
                    try {
                        if (state2 > state1) {
                            stop_timer();
                        } else {
                            int id = store[step[state2][0] - 1][size[step[state2][0] - 1] - 1];
                            switch (direction) {
                                case 1://up
                                    dc[id][1] = dc[id][1] - unitHeight;
                                    if (dc[id][1] <= maxHeight)
                                        direction = (step[state2][1] - step[state2][0] > 0 ? 3 : 2);
                                    tv[id].offsetTopAndBottom(-unitHeight);
                                    break;
                                case 2://left
                                    int tempWidthLeft = 0;
                                    dc[id][0] = dc[id][0] - unitWidth / 5;
                                    if (dc[id][0] <= dx[id] + (step[state2][1] - 1) * unitWidth)
                                    {
                                        direction = 4;
                                        tempWidthLeft = dx[id] + (step[state2][1] - 1) * unitWidth - dc[id][0];
                                        dc[id][0] += tempWidthLeft;
                                        tempWidthLeft = unitWidth / 5 - tempWidthLeft;
                                    }else{
                                        tempWidthLeft = unitWidth / 5;
                                    }
                                    tv[id].offsetLeftAndRight(- tempWidthLeft);
                                    break;
                                case 3://right
                                    int tempWidthRight = 0;
                                    dc[id][0] = dc[id][0] + unitWidth / 5;
                                    if (dc[id][0] >= dx[id] + (step[state2][1] - 1) * unitWidth)
                                    {
                                        direction = 4;
                                        tempWidthRight = dc[id][0] - dx[id] - (step[state2][1] - 1) * unitWidth;
                                        dc[id][0] -= tempWidthRight;
                                        tempWidthRight = unitWidth / 5 - tempWidthRight;
                                    }else{
                                        tempWidthRight = unitWidth / 5;
                                    }
                                    tv[id].offsetLeftAndRight(tempWidthRight);
                                    break;
                                case 4://down
                                    int temp=size[step[state2][1]-1];
                                    dc[id][1] = dc[id][1] + unitHeight;
                                    if (dc[id][1] >= dy[1] - temp * unitHeight) {
                                        direction = 1;
                                        store[step[state2][1]-1][temp]=id;
                                        size[step[state2][1]-1]++;
                                        size[step[state2][0]-1]--;
                                        state2++;
                                    }
                                    tv[id].offsetTopAndBottom(unitHeight);
                                    break;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
        }
        //这里必须有delay，不然准备程序运行不够，导致出错
        timer.schedule(task,2000,80);
    }

    private void stop_timer() {
        if(timer != null){ timer.cancel(); }
        if(task != null){ task.cancel(); }
        timer = null;
        task = null;
    }

    private void originDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setTitle("警告");
        builder.setMessage("确定要复位吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i=1;i<=floor;i++){
                    int ox= -(dc[i][0]-dx[i]);
                    int oy= -(dc[i][1]-dy[i]);
                    dc[i][0]=dx[i];
                    dc[i][1]=dy[i];
                    tv[i].offsetLeftAndRight(ox);
                    tv[i].offsetTopAndBottom(oy);
                }
                confirm.setText("确认");
                input.setFocusable(true);
                input.setFocusableInTouchMode(true);
                input.requestFocus();
                direction=1;
            }
        });
        builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                start_timer();
            }
        });
        dialog = builder.create();
    }

    private void getStep(int size, int i, int j, int k) {
        if(size==1)
            move(i,j);
        else
        {
            getStep(size-1,i,k,j);
            move(i,j);
            getStep(size-1,k,j,i);
        }
    }

    private void move(int i,int j) {
        state1++;
        step[state1][0]=i;
        step[state1][1]=j;
    }
}
