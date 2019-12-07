package com.TerminalWork.gametreasurebox;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.TerminalWork.gametreasurebox.bean.flags;
import com.TerminalWork.gametreasurebox.database.userMsg;
import com.TerminalWork.gametreasurebox.fragment.GameSelect;
import com.TerminalWork.gametreasurebox.fragment.Hanoi;
import com.TerminalWork.gametreasurebox.fragment.HuaRongDao;
import com.TerminalWork.gametreasurebox.customComponents.showPhoto_Dialog;
import com.TerminalWork.gametreasurebox.fragment._2048;
import com.TerminalWork.gametreasurebox.fragment.select_hrd_sort;
import com.google.android.material.navigation.NavigationView;

import org.litepal.LitePal;

import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    private showPhoto_Dialog dialog;
    private DrawerLayout drawerLayout;
    private CircleImageView account;
    private TextView signature;
    private TextView username;
    private int closeFlag;
    private long lastClickTime;
    private long currentClickTime;
    private MediaPlayer mediaPlayer = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        closeFlag = 0;
        lastClickTime = 0;
        currentClickTime = 0;

        NavigationView navigationView;
        View drawerView;
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerView = navigationView.inflateHeaderView(R.layout.nav_header);//动态加入头部
        account = drawerView.findViewById(R.id.icon_image);
        signature = drawerView.findViewById(R.id.signature);
        username = drawerView.findViewById(R.id.username);

        dialog = new showPhoto_Dialog(MainActivity.this,R.style.dialog, R.drawable.pretty_girl);
        account.setOnClickListener(accountOnclick);

        //隐藏上方的actionbar
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        mediaPlayer = MediaPlayer.create(this, R.raw.i_want_my_tears_back);
        mediaPlayer.setLooping(true);

        loadFragment(flags._2048Fragment);
        loadFragment(flags.gameSelectFragment);
        Log.i("onCreateMain","yes");


    }

    @Override
    protected void onStart() {
        super.onStart();

        String nowUser;
        SharedPreferences spf = getSharedPreferences("loginState", MODE_PRIVATE);
        nowUser = spf.getString("nowUser",null);
        //Log.i("nowUser", nowUser);
        List<userMsg> user = LitePal.select("headSculptureLocalPath", "name", "signature").
                where("name = ?", nowUser).find(userMsg.class);
        if(user.isEmpty()){
            account.setImageDrawable(getDrawable(R.drawable.logo));
            username.setText(getString(R.string.defaultUsername));
            signature.setText(getString(R.string.defaultSignature));
        }else{
            if(user.get(0).getHeadSculptureLocalPath() == null){
                account.setImageDrawable(getDrawable(R.drawable.logo));
            }else{
                account.setImageDrawable(Drawable.createFromPath(user.get(0).getHeadSculptureLocalPath()));
            }
            username.setText(user.get(0).getName());
            if(user.get(0).getSignature() == null){
                signature.setText(getString(R.string.defaultSignature));
            }else{
                signature.setText(user.get(0).getSignature());
            }

        }

    }

    public void callDrawer(View view){
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void loadFragment(int fragmentID){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment_2048 = fragmentManager.findFragmentByTag("_2048");
        Fragment fragment_hrd = fragmentManager.findFragmentByTag("hrd");
        Fragment fragment_hanoi = fragmentManager.findFragmentByTag("hanoi");
        Fragment fragment_gameSelect = fragmentManager.findFragmentByTag("gameSelect");
        Fragment fragment_select_hrd = fragmentManager.findFragmentByTag("select_hrd");
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
        if(fragment_select_hrd != null){
            fragmentTransaction.hide(fragment_select_hrd);
        }
        switch (fragmentID){
            case flags.hanoiFragment:
                if(fragment_hanoi == null){
                    fragment_hanoi = new Hanoi();
                    fragmentTransaction.add(R.id.fragment_view, fragment_hanoi,"hanoi");
                }else{
                    fragmentTransaction.show(fragment_hanoi);
                }
                Log.i("Hanoi", "Start");
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                break;
            case flags.hrdFragment:
                if(fragment_hrd == null || flags.current_sort_hrd != flags.last_sort_hrd){
                    if(fragment_hrd != null)
                        fragmentTransaction.remove(fragment_hrd);
                    fragment_hrd = new HuaRongDao(flags.current_sort_hrd);
                    fragmentTransaction.add(R.id.fragment_view, fragment_hrd, "hrd");
                }else{
                    fragmentTransaction.show(fragment_hrd);
                }
                Log.i("hrd", "Start");
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            case flags._2048Fragment:
                if(fragment_2048 == null){
                    fragment_2048 = new _2048();
                    fragmentTransaction.add(R.id.fragment_view, fragment_2048, "_2048");
                }else{
                    fragmentTransaction.show(fragment_2048);
                }
                Log.i("_2048", "Start");
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            case flags.gameSelectFragment:
                if(fragment_gameSelect == null){
                    fragment_gameSelect = new GameSelect();
                    fragmentTransaction.add(R.id.fragment_view, fragment_gameSelect, "gameSelect");
                }else{
                    fragmentTransaction.show(fragment_gameSelect);
                }
                Log.i("GameSelect", "Start");
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            case flags.selectHrdFragment:
                if(fragment_select_hrd == null){
                    fragment_select_hrd = new select_hrd_sort();
                    fragmentTransaction.add(R.id.fragment_view, fragment_select_hrd, "select_hrd");
                }else{
                    fragmentTransaction.show(fragment_select_hrd);
                }
                Log.i("select_hrd", "Start");
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        fragmentTransaction.commit();
        Log.i("message", "事务已提交");
    }

    private NavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.nav_call:
                    loadFragment(flags.gameSelectFragment);
                    drawerLayout.closeDrawers();
                    break;
                case R.id.logout:
                    SharedPreferences sharedPreferences = getSharedPreferences("loginState", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("nowUser", null);
                    editor.apply();
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                    break;
            }
            return false;
        }
    };

    private View.OnClickListener accountOnclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//                bottomPopupOption = new BottomPopupOption(TabHostActivity.this);
//                bottomPopupOption.setItemText("拍照","选择相册");
//                bottomPopupOption.showPopupWindow();
            dialog.show();
        }
    };

    @Override
    public void onBackPressed() {
        closeFlag++;
        currentClickTime = System.currentTimeMillis();
        if(closeFlag == 1){
            lastClickTime = currentClickTime;
            Toast.makeText(this, "再按一次退出游戏", Toast.LENGTH_SHORT).show();
        }else if(currentClickTime - lastClickTime >= 2000){//使得中间点击间隔为2s
            super.onBackPressed();
        }else{
            closeFlag = 0;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}