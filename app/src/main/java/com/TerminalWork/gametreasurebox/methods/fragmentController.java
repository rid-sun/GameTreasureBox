package com.TerminalWork.gametreasurebox.methods;

import android.content.pm.ActivityInfo;
import android.util.Log;

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
    private static String[] fragmentsTag = new String[]{"_2048", "hanoi", "gameSelect", "select_hrd", "hrd"};
    private static fragmentController controller;
    private MainActivity activity;
    private HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();

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
        fragments.add(new _2048());
        hashMap.put(flags._2048Fragment, 0);
        fragments.add(new Hanoi());
        hashMap.put(flags.hanoiFragment, 1);
        fragments.add(new GameSelect());
        hashMap.put(flags.gameSelectFragment, 2);
        fragments.add(new select_hrd_sort());
        hashMap.put(flags.selectHrdFragment, 3);

        FragmentTransaction ft = fm.beginTransaction();
        for(int i = 0; i < fragments.size(); i++) {
            ft.add(containerId, fragments.get(i), fragmentsTag[i]);
        }
        ft.commit();
    }

    public void showFragment(int fragmentID) {
        hideFragments();
        FragmentTransaction ft = fm.beginTransaction();
        if(fragmentID == flags.hrdFragment){
            if(fm.findFragmentByTag("hrd") == null){
                fragments.add(new HuaRongDao(flags.current_sort_hrd));
                hashMap.put(flags.hrdFragment, 4);
                ft.add(containerId, fragments.get(4), fragmentsTag[4]);
            }else{
                if(flags.last_sort_hrd != flags.current_sort_hrd){
                    reloadFragment(flags.current_sort_hrd);
                }
            }
        }
        Fragment fragment = fragments.get(hashMap.get(fragmentID));
        ft.show(fragment);

        if(fragment == fm.findFragmentByTag("hanoi")){
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else{
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        ft.commit();
    }

    private void hideFragments() {
        FragmentTransaction ft = fm.beginTransaction();
        for(Fragment fragment : fragments) {
            if(fragment != null) {
                ft.hide(fragment);
            }
        }
        ft.commit();
    }

    private void reloadFragment(int fragmentID){
        FragmentTransaction ft = fm.beginTransaction();
        ft.remove(fragments.get(4));
        Fragment newHrdFragment = new HuaRongDao(fragmentID);
        ft.add(containerId, newHrdFragment, fragmentsTag[4]);
        ft.commit();
    }

    public Fragment getFragment(int position) {
        return fragments.get(position);
    }

    public static void destroyController(){
        controller = null;
    }
}
