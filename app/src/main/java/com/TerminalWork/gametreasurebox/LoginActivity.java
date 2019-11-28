package com.TerminalWork.gametreasurebox;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class LoginActivity extends Activity {
    private GradientDrawable gradientDrawable;
    private ImageView circleImageView;
    private RelativeLayout relativeLayoutAccount;
    private RelativeLayout relativeLayoutPassword;
    private TextView forgetPassword;
    private ValueAnimator colorChange;
    private TextView visitor;
    private TextView registry;
    private Animation animation;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        relativeLayoutAccount = findViewById(R.id.accountLoginRelativeLayout);
        relativeLayoutPassword = findViewById(R.id.passwrdLoginRelativeLayout);
        forgetPassword = findViewById(R.id.forgetPasswrd);
        circleImageView = findViewById(R.id.login);
        gradientDrawable = (GradientDrawable)circleImageView.getBackground();
        colorChange = ObjectAnimator.ofInt(gradientDrawable,"color", Color.parseColor("#0983F0"), Color.parseColor("#2098F7"),
                Color.parseColor("#1686F2"), Color.parseColor("#03c8Fc"), Color.parseColor("#00CAFC"));
        colorChange.setDuration(3000);
        colorChange.setEvaluator(new ArgbEvaluator());
        colorChange.setRepeatCount(ValueAnimator.INFINITE);
        colorChange.setRepeatMode(ValueAnimator.REVERSE);
        colorChange.start();
        registry = findViewById(R.id.register);
        visitor = findViewById(R.id.visitor);
        registry.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        registry.setTextColor(Color.parseColor("#FF79766F"));
                        break;
                    case MotionEvent.ACTION_UP:
                        registry.setTextColor(Color.parseColor("#FF3D3A33"));
                        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                        break;
                }
                return true;
            }
        });
        visitor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        visitor.setTextColor(Color.parseColor("#FF79766F"));
                        break;
                    case MotionEvent.ACTION_UP:
                        visitor.setTextColor(Color.parseColor("#FF3D3A33"));
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        animation = AnimationUtils.loadAnimation(this, R.anim.slideup_app_login_animation);
        relativeLayoutAccount.startAnimation(animation);
        relativeLayoutPassword.startAnimation(animation);
        circleImageView.startAnimation(animation);
        forgetPassword.startAnimation(animation);
    }
}
