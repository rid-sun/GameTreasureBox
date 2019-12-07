package com.TerminalWork.gametreasurebox;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import org.litepal.LitePal;

public class WelcomeActivity extends Activity {

    private Shimmer shimmer1;
    private Shimmer shimmer2;
    private Shimmer shimmer3;
    private ShimmerTextView Game;
    private ShimmerTextView Treasure;
    private ShimmerTextView Box;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        TextView curtain = findViewById(R.id.curtain);
        Game = findViewById(R.id.app_name_part1);
        Treasure = findViewById(R.id.app_name_part2);
        Box = findViewById(R.id.app_name_part3);

        shimmer1 = new Shimmer();
        shimmer1.start(Game);
        shimmer2 = new Shimmer();
        shimmer2.start(Treasure);
        shimmer3 = new Shimmer();
        shimmer3.start(Box);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.treasure_app_start_animation);
        Treasure.startAnimation(animation);
        animation = AnimationUtils.loadAnimation(this, R.anim.box_app_start_animation);
        Box.startAnimation(animation);
        animation = AnimationUtils.loadAnimation(this, R.anim.curtain_app_start_animation);
        animation.setAnimationListener(welcomeAnimationListener);
        curtain.startAnimation(animation);

        //创建数据库
        LitePal.getDatabase();
    }

    private Animation.AnimationListener welcomeAnimationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            shimmer1.cancel();
            shimmer2.cancel();
            shimmer3.cancel();
            Game.setTextColor(getColor(R.color.foreGround_color_8_4096));
            Treasure.setTextColor(getColor(R.color.foreGround_color_8_4096));
            Box.setTextColor(getColor(R.color.foreGround_color_8_4096));
            changeActivity();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    private void changeActivity(){
        SharedPreferences sharedPreferences = getSharedPreferences("loginState", MODE_PRIVATE);
        String user = sharedPreferences.getString("nowUser", null);
        if(user != null){
            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
        }else{
            startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
        }
        finish();
    }
}
