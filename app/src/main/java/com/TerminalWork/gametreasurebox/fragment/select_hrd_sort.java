package com.TerminalWork.gametreasurebox.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.TerminalWork.gametreasurebox.MainActivity;
import com.TerminalWork.gametreasurebox.R;
import com.TerminalWork.gametreasurebox.bean.flags;

public class select_hrd_sort extends Fragment {

    private View view;
    private MainActivity mainActivity;

    public select_hrd_sort(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.select_sort_hrd, container, false);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity)context;
    }

    @Override
    public void onStart() {
        super.onStart();
        Button threeKingdom_hrd = view.findViewById(R.id.threeKingdom_hrd);
        Button image_hrd = view.findViewById(R.id.image_hrd);
        Button number_hrd = view.findViewById(R.id.number_hrd);

        threeKingdom_hrd.setOnTouchListener(OnTouch);
        image_hrd.setOnTouchListener(OnTouch);
        number_hrd.setOnTouchListener(OnTouch);
    }

    private View.OnTouchListener OnTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                   v.setScaleX(0.95f);
                   v.setScaleY(0.95f);
                   break;
                case MotionEvent.ACTION_UP:
                    v.setScaleY(1.0f);
                    v.setScaleX(1.0f);
                    switch (v.getId()){
                        case R.id.number_hrd:
                            if(flags.current_sort_hrd != R.layout.game_number_hrd){
                                flags.last_sort_hrd = flags.current_sort_hrd;
                                flags.current_sort_hrd = R.layout.game_number_hrd;
                            }
                            break;
                        case R.id.image_hrd:
                            if(flags.current_sort_hrd != R.layout.game_image_hrd){
                                flags.last_sort_hrd = flags.current_sort_hrd;
                                flags.current_sort_hrd = R.layout.game_image_hrd;
                            }
                            break;
                        case R.id.threeKingdom_hrd:
                            if(flags.current_sort_hrd != R.layout.game_three_kingdoms_hrd){
                                flags.last_sort_hrd = flags.current_sort_hrd;
                                flags.current_sort_hrd = R.layout.game_three_kingdoms_hrd;
                            }
                            break;
                    }
                    mainActivity.loadFragment(flags.hrdFragment);
                    break;
            }
            return true;
        }
    };
}
