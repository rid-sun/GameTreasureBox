package com.TerminalWork.gametreasurebox.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.TerminalWork.gametreasurebox.R;
import com.TerminalWork.gametreasurebox.custom_components.image_moveView;
import com.TerminalWork.gametreasurebox.custom_components.number_moveView;
import com.TerminalWork.gametreasurebox.methods.freshViewTask;

public class HuaRongDao extends Fragment {

    private freshViewTask fresh_ViewTask;
    private  image_moveView imagemoveView;
    private number_moveView view;

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
//        imagemoveView = (image_moveView)getActivity().findViewById(R.id.DView);
//        Button button = getActivity().findViewById(R.id.button);
//        fresh_ViewTask = new freshViewTask(imagemoveView);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                imagemoveView.init_Pos();
//                imagemoveView.invalidate();
//            }
//        });
//        view = getActivity().findViewById(R.id.number_moveView);
//        Button button = getActivity().findViewById(R.id.button2);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                view.init_Pos();
//                view.invalidate();
//            }
//        });

    }

}
