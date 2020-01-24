package com.TerminalWork.gametreasurebox.fragment;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.TerminalWork.gametreasurebox.MainActivity;
import com.TerminalWork.gametreasurebox.R;
import com.TerminalWork.gametreasurebox.bean.flags;
import com.TerminalWork.gametreasurebox.methods.fragmentController;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.github.siyamed.shapeimageview.RoundedImageView;

public class GameSelect extends Fragment {

    private RoundedImageView hrd;
    private RoundedImageView _2048;
    private CircularImageView hanoi;
    private MainActivity mainActivity;
    private fragmentController controller;
    private View view;

    public GameSelect() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainActivity = (MainActivity)getActivity();
        controller = fragmentController.getController();
        view = inflater.inflate(R.layout.game_select_fragment, container,false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        VideoView videoView = view.findViewById(R.id.select_videoView);
        videoView.setVideoURI(Uri.parse("android.resource://"+ mainActivity.getPackageName() + "/" + R.raw.forest));
        videoView.start();
        videoView.setOnCompletionListener(completion);
        hrd = mainActivity.findViewById(R.id.game_select_hrd);
        _2048 = mainActivity.findViewById(R.id.game_select_2048);
        hanoi = mainActivity.findViewById(R.id.game_select_hanoi);
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
                        controller.showFragment(flags.selectHrdFragment);
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
                        controller.showFragment(flags._2048Fragment);
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
                        controller.showFragment(flags.hanoiFragment);
                        break;
                }
                return true;
            }
        });

    }

    private MediaPlayer.OnCompletionListener completion = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            mp.start();
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("GameSelect","销毁");
    }
}
