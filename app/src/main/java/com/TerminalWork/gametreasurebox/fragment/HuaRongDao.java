package com.TerminalWork.gametreasurebox.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.TerminalWork.gametreasurebox.R;
import com.TerminalWork.gametreasurebox.customComponents.image_moveView;
import com.TerminalWork.gametreasurebox.customComponents.number_moveView;
import com.TerminalWork.gametreasurebox.methods.freshViewTask;

public class HuaRongDao extends Fragment {

    public HuaRongDao() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.game_three_kingdoms_hrd,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

}
