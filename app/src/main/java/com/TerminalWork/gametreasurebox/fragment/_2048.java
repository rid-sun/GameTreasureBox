package com.TerminalWork.gametreasurebox.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.TerminalWork.gametreasurebox.R;

public class _2048 extends Fragment {

    private IntentFilter intentFilter;
    private scoreReceiver score;
    private TextView scoreView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.game_2048,container,false);
    }

    @Override
    public void onStart() {
        super.onStart();
        scoreView = getActivity().findViewById(R.id.scoreView);
        scoreView.setText("Score: 0");
        scoreView.setTextColor(getContext().getColor(R.color.holo_orange_dark));
        intentFilter = new IntentFilter();
        intentFilter.addAction("changeScore");
        score = new scoreReceiver();
        getActivity().registerReceiver(score, intentFilter);
    }

    class scoreReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            int score = bundle.getInt("score");
            scoreView.setText("Score: " + score);
        }
    }

}
