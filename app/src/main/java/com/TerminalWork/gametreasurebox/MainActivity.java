package com.TerminalWork.gametreasurebox;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.TerminalWork.gametreasurebox.fragment.GameSelect;
import com.TerminalWork.gametreasurebox.fragment.Hanoi;
import com.TerminalWork.gametreasurebox.fragment.HuaRongDao;
import com.TerminalWork.gametreasurebox.custom_components.showPhoto_Dialog;
import com.TerminalWork.gametreasurebox.fragment._2048;
import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    private showPhoto_Dialog dialog;
    private DrawerLayout drawerLayout;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        drawerLayout = findViewById(R.id.drawer_layout);
        View drawerView = navigationView.inflateHeaderView(R.layout.nav_header);
        CircleImageView account = drawerView.findViewById(R.id.icon_image);
        dialog = new showPhoto_Dialog(MainActivity.this,R.style.dialog, R.drawable.pretty_girl);
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                bottomPopupOption = new BottomPopupOption(TabHostActivity.this);
//                bottomPopupOption.setItemText("拍照","选择相册");
//                bottomPopupOption.showPopupWindow();
                dialog.show();

            }
        });
        //隐藏上方的actionbar
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        loadFragment(3);
        loadFragment(4);
        Log.i("onCreateMain","yes");

    }

    public void callDrawer(View view){
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void loadFragment(int id){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment_2048 = fragmentManager.findFragmentByTag("_2048");
        Fragment fragment_hrd = fragmentManager.findFragmentByTag("hrd");
        Fragment fragment_hanoi = fragmentManager.findFragmentByTag("hanoi");
        Fragment fragment_gameSelect = fragmentManager.findFragmentByTag("gameSelect");
        if(fragment_2048 != null){
            fragmentTransaction.hide(fragment_2048);
        }
        if(fragment_gameSelect != null){
            fragmentTransaction.hide(fragment_gameSelect);
        }
        if(fragment_hanoi != null){
            fragmentTransaction.hide(fragment_hanoi);
        }
        if(fragment_hrd != null){
            fragmentTransaction.hide(fragment_hrd);
        }
        switch (id){
            case 1:
                if(fragment_hanoi == null){
                    fragment_hanoi = new Hanoi();
                    fragmentTransaction.add(R.id.fragment_view, fragment_hanoi,"hanoi");
                }else{
                    fragmentTransaction.show(fragment_hanoi);
                }
                Log.i("Hanoi", "Start");
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                break;
            case 2:
                if(fragment_hrd == null){
                    fragment_hrd = new HuaRongDao();
                    fragmentTransaction.add(R.id.fragment_view, fragment_hrd, "hrd");
                }else{
                    fragmentTransaction.show(fragment_hrd);
                }
                Log.i("hrd", "Start");
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            case 3:
                if(fragment_2048 == null){
                    fragment_2048 = new _2048();
                    fragmentTransaction.add(R.id.fragment_view, fragment_2048, "_2048");
                }else{
                    fragmentTransaction.show(fragment_2048);
                }
                Log.i("_2048", "Start");
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            case 4:
                if(fragment_gameSelect == null){
                    fragment_gameSelect = new GameSelect();
                    fragmentTransaction.add(R.id.fragment_view, fragment_gameSelect, "gameSelect");
                }else{
                    fragmentTransaction.show(fragment_gameSelect);
                }
                Log.i("GameSelect", "Start");
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
        }
        fragmentTransaction.commit();
        Log.i("message", "事务已提交");
    }

    private NavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.nav_call:
                    loadFragment(4);
                    drawerLayout.closeDrawers();
                    break;
            }
            return false;
        }
    };
}