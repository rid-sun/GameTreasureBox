package com.TerminalWork.gametreasurebox.methods;

import android.content.pm.ActivityInfo;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.TerminalWork.gametreasurebox.MainActivity;
import com.TerminalWork.gametreasurebox.bean.flags;
import com.TerminalWork.gametreasurebox.fragment.GameSelect;
import com.TerminalWork.gametreasurebox.fragment.Hanoi;
import com.TerminalWork.gametreasurebox.fragment.HuaRongDao;
import com.TerminalWork.gametreasurebox.fragment._2048;
import com.TerminalWork.gametreasurebox.fragment.select_hrd_sort;

import java.util.ArrayList;
import java.util.HashMap;

public class fragmentController {

    private int containerId;
    private FragmentManager fm;
    private ArrayList<Fragment> fragments;
    private static fragmentController controller;
    private MainActivity activity;

    public static fragmentController getInstance(MainActivity activity, int containerId) {
        if (controller == null) {
            controller = new fragmentController(activity, containerId);
        }
        return controller;
    }

    private fragmentController(MainActivity activity, int containerId) {
        this.containerId = containerId;
        fm = activity.getSupportFragmentManager();
        this.activity = activity;
        initFragment();
    }

    private void initFragment() {
        fragments = new ArrayList<Fragment>();
        fragments.add(new GameSelect());
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(containerId, fragments.get(0), "gameSelect");
        ft.commit();
    }

    public void showFragment(int fragmentID) {
        hideFragments();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment fragment1;
        switch (fragmentID){
            case flags.hrdFragment:
                fragment1 = new HuaRongDao(flags.current_sort_hrd);
                fragments.add(fragment1);
                ft.add(containerId, fragment1, "hrd");
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            case flags._2048Fragment:
                fragment1 = new _2048();
                fragments.add(fragment1);
                ft.add(containerId, fragment1, "_2048");
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            case flags.gameSelectFragment:
                fragment1 = new GameSelect();
                fragments.add(fragment1);
                ft.add(containerId, fragment1, "gameSelect");
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            case flags.selectHrdFragment:
                fragment1 = new select_hrd_sort();
                fragments.add(fragment1);
                ft.add(containerId, fragment1, "select_hrd");
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            case flags.hanoiFragment:
                fragment1 = new Hanoi();
                fragments.add(fragment1);
                ft.add(containerId, fragment1, "hanoi");
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                break;
        }
        ft.commit();
    }

    public void backFragment(){
        int index = fragments.size() - 1;
        FragmentTransaction ft = fm.beginTransaction();
        if(index > 0){
            ft.remove(fragments.get(index));
            fragments.remove(index);
            ft.show(fragments.get(index - 1));
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        ft.commit();
    }

    public void hideFragments() {
        FragmentTransaction ft = fm.beginTransaction();
        for(Fragment fragment : fragments) {
            if(fragment != null) {
                ft.hide(fragment);
            }
        }
        ft.commit();
    }

    public void onStartShowFragments(){
        FragmentTransaction ft = fm.beginTransaction();
        for(Fragment fragment : fragments) {
            if(fragment != null) {
                ft.show(fragment);
            }
        }
        ft.commit();
    }

    public Boolean gameSelectFragmentIsShow() {
        return fm.findFragmentByTag("gameSelect").isHidden();
    }

    public static void destroyController(){
        controller = null;
    }

    public static fragmentController getController() {
        return controller;
    }
}
