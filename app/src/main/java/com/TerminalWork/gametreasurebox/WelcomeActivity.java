package com.TerminalWork.gametreasurebox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

public class WelcomeActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_start);
        final TextView curtain = findViewById(R.id.curtain);
        final ShimmerTextView Game = findViewById(R.id.app_name_part1);
        final ShimmerTextView Treasure = findViewById(R.id.app_name_part2);
        final ShimmerTextView Box = findViewById(R.id.app_name_part3);
        final Shimmer shimmer1 = new Shimmer();
        final Shimmer shimmer2 = new Shimmer();
        final Shimmer shimmer3 = new Shimmer();
        shimmer1.start(Game);
        shimmer2.start(Treasure);
        shimmer3.start(Box);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.treasure_app_start_animation);
        Treasure.startAnimation(animation);
        animation = AnimationUtils.loadAnimation(this, R.anim.box_app_start_animation);
        Box.startAnimation(animation);
        animation = AnimationUtils.loadAnimation(this, R.anim.curtain_app_start_animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
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
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        curtain.startAnimation(animation);
    }
}
