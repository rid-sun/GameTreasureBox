package com.TerminalWork.gametreasurebox.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.TerminalWork.gametreasurebox.R;

import java.util.Timer;
import java.util.TimerTask;

public class Hanoi extends Fragment {

    private Button confirm;
    private EditText input;
    private AlertDialog dialog;

    public TextView tv[]=new TextView[10];
    public int dx[]={0,26,36,46,56,66,76,86,96,106};
    public int dy[]={0,295,275,255,235,215,195,175,155,135};
    public int dc[][]=new int[10][2];
    public int size[]=new int[3];
    public int store[][]=new int[3][9];
    public int step[][]=new int[512][2];
    public int state1,state2;
    public int direction=1;
    public int floor;
    public Timer timer;
    public TimerTask task;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.game_hanoi,container,false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        confirm = (Button)getActivity().findViewById(R.id.hanoi_confirm);
        confirm.setOnClickListener(mButtonOclickListener);
        prepare();
    }

    private View.OnClickListener mButtonOclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String source = confirm.getText().toString();
            if("确认".equals(source))
            {
                try {
                    input = (EditText) getActivity().findViewById(R.id.hanoi_input);
                    floor = Integer.parseInt(input.getText().toString());
                    if (floor <= 0 || floor > 9) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("请保证层数在1-9之间");
                        builder.show();
                    } else {
                        confirm.setText("复位");
                        for(int i=1;i<=floor;i++)
                        {
                            store[0][i-1]=i;
                            tv[i].setVisibility(View.VISIBLE);
                        }
                        for(int i=floor+1;i<=9;i++)
                            tv[i].setVisibility(View.GONE);
                        size[0] = floor;
                        size[1] = size[2] = 0;
                        state1 = 0;
                        getStep(floor, 1, 2, 3);
                        state2 = 1;

                        //这行非常重要，如果不使ed失去焦点，
                        // 在盘子移动时，对ed输入或按键都会影响视图的正常运行
                        input.setFocusable(false);

                        start_timer();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            else
            {
                if(task==null||timer==null){
                    for (int i=1;i<=floor;i++){
                        int ox=(int)(-(dc[i][0]-dx[i])*3.5);
                        int oy=(int)(-(dc[i][1]-dy[i])*3.5);
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
                else{
                    stop_timer();
                    dialog.show();
                }
            }
        }
    } ;

    public void prepare(){

        tv[9]=(TextView)getActivity().findViewById(R.id.textView17);
        tv[8]=(TextView)getActivity().findViewById(R.id.textView16);
        tv[7]=(TextView)getActivity().findViewById(R.id.textView15);
        tv[6]=(TextView)getActivity().findViewById(R.id.textView14);
        tv[5]=(TextView)getActivity().findViewById(R.id.textView13);
        tv[4]=(TextView)getActivity().findViewById(R.id.textView12);
        tv[3]=(TextView)getActivity().findViewById(R.id.textView11);
        tv[2]=(TextView)getActivity().findViewById(R.id.textView10);
        tv[1]=(TextView)getActivity().findViewById(R.id.textView9);

        for(int i=1;i<=9;i++)
        {
            dc[i][0]=dx[i];
            dc[i][1]=dy[i];
        }

        showDialog();
    }

    public void getStep(int size,int i,int j,int k) {
        if(size==1)
            move(i,j);
        else
        {
            getStep(size-1,i,k,j);
            move(i,j);
            getStep(size-1,k,j,i);
        }
    }
    public void move(int i,int j)
    {
        state1++;
        step[state1][0]=i;
        step[state1][1]=j;
    }
    public void start_timer()
    {
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
                                    dc[id][1] = dc[id][1] - 20;
                                    if (dc[id][1] <= 95)
                                        direction = (step[state2][1] - step[state2][0] > 0 ? 3 : 2);
                                    tv[id].offsetTopAndBottom(-70);
                                    break;
                                case 2://left
                                    dc[id][0] = dc[id][0] - 20;
                                    if (dc[id][0] <= dx[id]+(step[state2][1]-1) * 220)
                                        direction = 4;
                                    tv[id].offsetLeftAndRight(-70);
                                    break;
                                case 3://right
                                    dc[id][0] = dc[id][0] + 20;
                                    if (dc[id][0] >= dx[id] + (step[state2][1]-1) * 220)
                                        direction = 4;
                                    tv[id].offsetLeftAndRight(70);
                                    break;
                                case 4://down
                                    int temp=size[step[state2][1]-1];
                                    dc[id][1] = dc[id][1] + 20;
                                    if (dc[id][1] >= 295 - temp * 20) {
                                        direction = 1;
                                        store[step[state2][1]-1][temp]=id;
                                        size[step[state2][1]-1]++;
                                        size[step[state2][0]-1]--;
                                        state2++;
                                    }
                                    tv[id].offsetTopAndBottom(70);
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

    public void stop_timer()
    {
        timer.cancel();
        task.cancel();
        timer = null;
        task = null;
    }

    public void showDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setTitle("警告");
        builder.setMessage("确定要复位吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i=1;i<=floor;i++){
                    int ox=(int)(-(dc[i][0]-dx[i])*3.5);
                    int oy=(int)(-(dc[i][1]-dy[i])*3.5);
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
                return ;
            }
        });
        dialog=builder.create();
    }

}
