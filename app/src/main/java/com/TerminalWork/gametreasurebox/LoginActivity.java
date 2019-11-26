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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class LoginActivity extends Activity {
    private GradientDrawable gradientDrawable;
    private ImageView circleImageView;
    private ValueAnimator colorChange;
    private TextView visitor;
    private TextView registry;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
                        break;
                }
                return true;
            }
        });
    }

}
