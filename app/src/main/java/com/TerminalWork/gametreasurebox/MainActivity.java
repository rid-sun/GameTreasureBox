package com.TerminalWork.gametreasurebox;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.TerminalWork.gametreasurebox.fragment.Hanoi;
import com.TerminalWork.gametreasurebox.fragment.HuaRongDao;
import com.TerminalWork.gametreasurebox.custom_components.showPhoto_Dialog;
import com.TerminalWork.gametreasurebox.fragment._2048;
import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    showPhoto_Dialog dialog;
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        View drawerView = navigationView.inflateHeaderView(R.layout.nav_header);
        CircleImageView account = (CircleImageView) drawerView.findViewById(R.id.icon_image);
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

        loadFragment(1);

    }

    public void callDrawer(View view){
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void loadFragment(int id){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment1 = fragmentManager.findFragmentByTag("Hanoi");
        Fragment fragment2 = fragmentManager.findFragmentByTag("HuaRongDao");
        Fragment fragment3 = fragmentManager.findFragmentByTag("_2048");
        switch (id){
            case 0:{
                if(fragment1 == null){
                    fragment1 = new Hanoi();
                    fragmentTransaction.replace(R.id.fragment_view, fragment1,"Hanoi");
                }
                else
                    fragmentTransaction.show(fragment1);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                break;
            }
            case 1:{
                if(fragment2 == null){
                    fragment2 = new HuaRongDao();
                    fragmentTransaction.replace(R.id.fragment_view, fragment2,"HuaRongdao");
                }
                else
                    fragmentTransaction.show(fragment2);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            }
            case 2:{
                if(fragment3 == null){
                    fragment3 = new _2048();
                    fragmentTransaction.replace(R.id.fragment_view, fragment3,"_2048");
                }
                else
                    fragmentTransaction.show(fragment3);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            }
        }
        fragmentTransaction.commit();
    }

}
