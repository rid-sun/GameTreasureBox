package com.TerminalWork.gametreasurebox.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.TerminalWork.gametreasurebox.MainActivity;
import com.TerminalWork.gametreasurebox.R;
import com.TerminalWork.gametreasurebox.bean.flags;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.github.siyamed.shapeimageview.RoundedImageView;

public class GameSelect extends Fragment {

    private RoundedImageView hrd;
    private RoundedImageView _2048;
    private CircularImageView hanoi;
    private MainActivity mainActivity;

    public GameSelect() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.game_select_fragment, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        mainActivity = (MainActivity)getActivity();
        hrd = getActivity().findViewById(R.id.game_select_hrd);
        _2048 = getActivity().findViewById(R.id.game_select_2048);
        hanoi = getActivity().findViewById(R.id.game_select_hanoi);
        hrd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        hrd.setScaleX(0.95f);
                        hrd.setScaleY(0.95f);
                        break;
                    case MotionEvent.ACTION_UP:
                        hrd.setScaleX(1f);
                        hrd.setScaleY(1f);
                        mainActivity.loadFragment(flags.selectHrdFragment);
                        break;
                }
                return true;
            }
        });

        _2048.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        _2048.setScaleX(0.95f);
                        _2048.setScaleY(0.95f);
                        break;
                    case MotionEvent.ACTION_UP:
                        _2048.setScaleX(1f);
                        _2048.setScaleY(1f);
                        mainActivity.loadFragment(flags._2048Fragment);
                        break;
                }
                return true;
            }
        });
        hanoi.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        hanoi.setScaleX(0.95f);
                        hanoi.setScaleY(0.95f);
                        break;
                    case MotionEvent.ACTION_UP:
                        hanoi.setScaleX(1f);
                        hanoi.setScaleY(1f);
                        mainActivity.loadFragment(flags.hanoiFragment);
                        break;
                }
                return true;
            }
        });

        //这里的话得提前打开，因为onHiddenChanged方法只会在改变状态时调用，它刚创建的时候不会被调用
        mainActivity.getMediaPlayer().start();

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){
            mainActivity.getMediaPlayer().pause();
        }else{
            mainActivity.getMediaPlayer().start();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("GameSelect","销毁");
    }
}
