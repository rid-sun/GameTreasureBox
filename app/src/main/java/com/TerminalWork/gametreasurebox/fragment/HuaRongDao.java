package com.TerminalWork.gametreasurebox.fragment;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.TerminalWork.gametreasurebox.MainActivity;
import com.TerminalWork.gametreasurebox.R;
import com.TerminalWork.gametreasurebox.bean.flags;
import com.TerminalWork.gametreasurebox.customComponents.image_moveView;
import com.TerminalWork.gametreasurebox.customComponents.number_moveView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HuaRongDao extends Fragment {

    private TextView kingdomSteps;
    private TextView kingdomCheckPoint;
    private TextView kingdomTimes;
    private TextView imageHrdSteps;
    private TextView imageHrdTimes;
    private TextView numberHrdSteps;
    private TextView numberHrdTimes;
    private List<View> checkPointsList = new ArrayList<>();
    private MainActivity mainActivity;
    private image_moveView imageMove;
    private number_moveView numberMove;
    private stepsReceiver steps;
    private Timer timer;
    private TimerTask timerTask;
    private int time;
    private MyHandler handler;
    private int kingdom_steps;
    private int image_steps;
    private int number_steps;

    private int layout_id;
    public HuaRongDao(int layout_id) {
        this.layout_id = layout_id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(layout_id, container,false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity)context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        time = 0;
        handler = new MyHandler(this);

        //三国华容道要实例化的东西
        if(layout_id == R.layout.game_three_kingdoms_hrd){
            kingdomSteps = mainActivity.findViewById(R.id.kingdoms_hrd_steps);
            kingdomTimes = mainActivity.findViewById(R.id.kingdoms_hrd_times);
            kingdomCheckPoint = mainActivity.findViewById(R.id.kingdom_hrd_checkPoints);
            checkPointsList.add(mainActivity.findViewById(R.id.new_check_point1));
            checkPointsList.add(mainActivity.findViewById(R.id.new_check_point2));
            checkPointsList.add(mainActivity.findViewById(R.id.new_check_point3));
            checkPointsList.add(mainActivity.findViewById(R.id.new_check_point4));
            checkPointsList.add(mainActivity.findViewById(R.id.new_check_point5));
            TextView nextCheckPoint = mainActivity.findViewById(R.id.threeKingdom_hrd_next_checkPoint);
            TextView lastCheckPoint = mainActivity.findViewById(R.id.threeKingdom_hrd_last_checkPoint);
            nextCheckPoint.setOnClickListener(checkPointSelect);
            lastCheckPoint.setOnClickListener(checkPointSelect);
        }

        //图像华容道要实例化的东西
        if(layout_id == R.layout.game_image_hrd){
            imageMove = mainActivity.findViewById(R.id.DView);
            imageHrdSteps = mainActivity.findViewById(R.id.image_hrd_steps);
            imageHrdTimes = mainActivity.findViewById(R.id.image_hrd_times);
            Button imageInitPosBtn = mainActivity.findViewById(R.id.imageInitPosBtn);
            imageInitPosBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageMove.init_Pos();
                    restartTimer();
                }
            });
        }

        //数字华容道要实例化的东西
        if(layout_id == R.layout.game_number_hrd){
            numberMove = mainActivity.findViewById(R.id.number_moveView);
            numberHrdSteps = mainActivity.findViewById(R.id.number_hrd_steps);
            numberHrdTimes = mainActivity.findViewById(R.id.number_hrd_times);
            Button numberInitPosBtn = mainActivity.findViewById(R.id.numberInitPosBtn);
            numberInitPosBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    numberMove.init_Pos();
                    restartTimer();
                }
            });
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        kingdom_steps = 0;
        image_steps = 0;
        number_steps = 0;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(flags.action_changStepsKingdomHrd);
        intentFilter.addAction(flags.action_changStepsImageHrd);
        intentFilter.addAction(flags.action_changStepsNumberHrd);
        intentFilter.addAction(flags.action_KingdomHrd_success);
        steps = new stepsReceiver();
        mainActivity.registerReceiver(steps, intentFilter);

        startTimer();
    }

    @Override
    public void onStop() {
        super.onStop();
        stopTimer();
    }

    class stepsReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            kingdom_steps += bundle.getInt("kingdom_steps", 0);
            image_steps = bundle.getInt("image_steps", 0);
            number_steps = bundle.getInt("number_steps", 0);
            switch (intent.getAction()){
                case flags.action_changStepsImageHrd:
                    imageHrdSteps.setText(String.valueOf(image_steps));
                    break;
                case flags.action_changStepsNumberHrd:
                    numberHrdSteps.setText(String.valueOf(number_steps));
                    break;
                case flags.action_changStepsKingdomHrd:
                    kingdomSteps.setText(String.valueOf(kingdom_steps));
                    break;
                case flags.action_KingdomHrd_success:
                    AlertDialog.Builder builder=new AlertDialog.Builder(context);
                    builder.setTitle("恭喜成功通过本关");
                    builder.setMessage("要进行下一关吗？取消则重玩！");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int index = Integer.parseInt(kingdomCheckPoint.getText().toString());
                            checkPointsList.get(index).setVisibility(View.GONE);
                            kingdomCheckPoint.setText(String.valueOf((index + 1) % checkPointsList.size()));
                            checkPointsList.get((index + 1) % checkPointsList.size()).setVisibility(View.VISIBLE);
                            kingdomSteps.setText(String.valueOf(0));
                            kingdom_steps = 0;
                            restartTimer();
                        }
                    });
                    builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            int index = Integer.parseInt(kingdomCheckPoint.getText().toString());
                            checkPointsList.get(index).setVisibility(View.GONE);
                            checkPointsList.get(index).setVisibility(View.VISIBLE);
                            kingdomSteps.setText(String.valueOf(0));
                            kingdom_steps = 0;
                            restartTimer();
                        }
                    });
                    stopTimer();
                    builder.create().show();
                    break;
            }
            Log.i("receiver", "收到");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopTimer();
        mainActivity.unregisterReceiver(steps);
    }

    private View.OnClickListener checkPointSelect = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int index = Integer.parseInt(kingdomCheckPoint.getText().toString());
            switch (v.getId()){
                case R.id.threeKingdom_hrd_next_checkPoint:
                    checkPointsList.get(index).setVisibility(View.GONE);
                    kingdomCheckPoint.setText(String.valueOf((index + 1) % checkPointsList.size()));
                    checkPointsList.get((index + 1) % checkPointsList.size()).setVisibility(View.VISIBLE);
                    kingdomSteps.setText(String.valueOf(0));
                    kingdom_steps = 0;
                    restartTimer();
                    break;
                case R.id.threeKingdom_hrd_last_checkPoint:
                    checkPointsList.get(index).setVisibility(View.GONE);
                    kingdomCheckPoint.setText(String.valueOf((index - 1 + checkPointsList.size()) % checkPointsList.size()));
                    checkPointsList.get((index - 1 + checkPointsList.size()) % checkPointsList.size()).setVisibility(View.VISIBLE);
                    kingdomSteps.setText(String.valueOf(0));
                    kingdom_steps = 0;
                    restartTimer();
                    break;
            }
        }
    };

    private void restartTimer(){
        time = 0;
    }

    private void startTimer(){
        if(timer == null){
            timer = new Timer();
        }
        if(timerTask == null){
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    time++;
                    Message msg = new Message();
                    msg.what = layout_id;
                    handler.sendMessage(msg);
                }
            };
        }
        timer.schedule(timerTask, 100, 1000);
    }

    private void stopTimer(){
        if(timer != null){ timer.cancel(); }
        if(timerTask != null){ timerTask.cancel(); }
        timer = null;
        timerTask = null;
    }

    private static class MyHandler extends Handler {//依据提示写出的对handler的弱引用，避免出现内存泄漏的情况
        WeakReference<HuaRongDao> weakReference;

        private MyHandler(HuaRongDao fragment) {
            this.weakReference = new WeakReference<HuaRongDao>(fragment);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            final HuaRongDao fragment = weakReference.get();
            if (fragment != null) {
                //在这里处理送过来的消息
                switch (msg.what){
                    case R.layout.game_image_hrd:
                        fragment.getImageHrdTimes().setText(String.valueOf(fragment.getTime()));
                        break;
                    case R.layout.game_number_hrd:
                        fragment.getNumberHrdTimes().setText(String.valueOf(fragment.getTime()));
                        break;
                    case R.layout.game_three_kingdoms_hrd:
                        fragment.getKingdomTimes().setText(String.valueOf(fragment.getTime()));
                        break;
                }
            }
        }
    }

    private TextView getKingdomTimes(){
        return kingdomTimes;
    }

    private int getTime(){
        return time;
    }

    private TextView getImageHrdTimes() {
        return imageHrdTimes;
    }

    private TextView getNumberHrdTimes() {
        return numberHrdTimes;
    }
}
