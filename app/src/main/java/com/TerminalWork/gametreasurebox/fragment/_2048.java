package com.TerminalWork.gametreasurebox.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.TerminalWork.gametreasurebox.R;

/*
 * 作者：JiaTai Sun
 * 时间：20-5-26 下午5:32
 * 类名：_2048
 * 功能：2048游戏主体
 */

public class _2048 extends Fragment {

    public _2048() {  }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.game_2048,container,false);
    }

    @Override
    public void onStart() {
        super.onStart();
        TextView scoreView = getActivity().findViewById(R.id.scoreView);
        scoreView.setText("Score: 0");
        scoreView.setTextColor(getContext().getColor(R.color.holo_orange_dark));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("_2048","销毁");
    }
}
