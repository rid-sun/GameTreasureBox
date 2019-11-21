package com.TerminalWork.gametreasurebox.custom_components;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.TerminalWork.gametreasurebox.R;

public class card extends FrameLayout {

    private TextView card;
    private int number;
    private boolean is2048;
    GradientDrawable myGrad;


    public card(@NonNull Context context) {
        super(context);
        card = new TextView(getContext());
        card.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.card_shape));
        myGrad = (GradientDrawable)card.getBackground();
        card.setTextSize(40f);
        card.setGravity(Gravity.CENTER);
        card.getPaint().setFakeBoldText(true);
        LayoutParams lp = new LayoutParams(-1, -1);
        lp.setMargins(30, 30, 0, 0);
        addView(card, lp);
    }

    public void setNumber(int num){
        number = num;
        if(num == 0)
            card.setText("");
        else
            card.setText("" + num);
        switch (num){
            case 0:
                myGrad.setColor(ContextCompat.getColor(getContext(),R.color.backGround_color_0));
                break;
            case 2:
//                card.setBackgroundColor(Color.rgb(238,228,218));
//                card.setTextColor(Color.rgb(119,110,101));
                myGrad.setColor(ContextCompat.getColor(getContext(),R.color.backGround_color_2));
                card.setTextColor(ContextCompat.getColor(getContext(),R.color.foreGround_color_2_4));
                break;
            case 4:
//                card.setBackgroundColor(Color.rgb(237,224,200));
//                card.setTextColor(Color.rgb(119,110,101));
                myGrad.setColor(ContextCompat.getColor(getContext(),R.color.backGround_color_4));
                card.setTextColor(ContextCompat.getColor(getContext(),R.color.foreGround_color_2_4));
                break;
            case 8:
//                card.setBackgroundColor(Color.rgb(242,177,121));
//                card.setTextColor(Color.rgb(249,246,242));
                myGrad.setColor(ContextCompat.getColor(getContext(),R.color.backGround_color_8));
                card.setTextColor(ContextCompat.getColor(getContext(),R.color.foreGround_color_8_4096));
                break;
            case 16:
//                card.setBackgroundColor(Color.rgb(245,149,99));
//                card.setTextColor(Color.rgb(249,246,242));
                myGrad.setColor(ContextCompat.getColor(getContext(),R.color.backGround_color_16));
                card.setTextColor(ContextCompat.getColor(getContext(),R.color.foreGround_color_8_4096));
                break;
            case 32:
//                card.setBackgroundColor(Color.rgb(246,124,95));
//                card.setTextColor(Color.rgb(249,246,242));
                myGrad.setColor(ContextCompat.getColor(getContext(),R.color.backGround_color_32));
                card.setTextColor(ContextCompat.getColor(getContext(),R.color.foreGround_color_8_4096));
                break;
            case 64:
//                card.setBackgroundColor(Color.rgb(246,94,59));
//                card.setTextColor(Color.rgb(249,246,242));
                myGrad.setColor(ContextCompat.getColor(getContext(),R.color.backGround_color_64));
                card.setTextColor(ContextCompat.getColor(getContext(),R.color.foreGround_color_8_4096));
                break;
            case 128:
//                card.setBackgroundColor(Color.rgb(237,207,114));
//                card.setTextColor(Color.rgb(249,246,242));
                myGrad.setColor(ContextCompat.getColor(getContext(),R.color.backGround_color_128));
                card.setTextColor(ContextCompat.getColor(getContext(),R.color.foreGround_color_8_4096));
                break;
            case 256:
//                card.setBackgroundColor(Color.rgb(237,204,97));
//                card.setTextColor(Color.rgb(249,246,242));
                myGrad.setColor(ContextCompat.getColor(getContext(),R.color.backGround_color_256));
                card.setTextColor(ContextCompat.getColor(getContext(),R.color.foreGround_color_8_4096));
                break;
            case 512:
//                card.setBackgroundColor(Color.rgb(236,200,80));
//                card.setTextColor(Color.rgb(249,246,242));
                myGrad.setColor(ContextCompat.getColor(getContext(),R.color.backGround_color_512));
                card.setTextColor(ContextCompat.getColor(getContext(),R.color.foreGround_color_8_4096));
                break;
            case 1024:
//                card.setBackgroundColor(Color.rgb(237,197,63));
//                card.setTextColor(Color.rgb(249,246,242));
                myGrad.setColor(ContextCompat.getColor(getContext(),R.color.backGround_color_1024));
                card.setTextColor(ContextCompat.getColor(getContext(),R.color.foreGround_color_8_4096));
                break;
            case 2048:
//                card.setBackgroundColor(Color.rgb(238,194,46));
//                card.setTextColor(Color.rgb(249,246,242));
                myGrad.setColor(ContextCompat.getColor(getContext(),R.color.backGround_color_2048));
                card.setTextColor(ContextCompat.getColor(getContext(),R.color.foreGround_color_8_4096));
                break;
            case 4096:
//                card.setBackgroundColor(Color.rgb(61,58,51));
//                card.setTextColor(Color.rgb(249,246,242));
                myGrad.setColor(ContextCompat.getColor(getContext(),R.color.backGround_color_4096));
                card.setTextColor(ContextCompat.getColor(getContext(),R.color.foreGround_color_8_4096));
                break;
        }
    }

//    private void judgeNumber(int num){
//        switch(num){
//            case 32:
//                Toast.makeText(getContext(),getResources().getString(R.string.noticeFor_512),Toast.LENGTH_SHORT);
//            case 512:
//                Toast.makeText(getContext(),getResources().getString(R.string.noticeFor_512),Toast.LENGTH_SHORT);
//            case 1024:
//                Toast.makeText(getContext(),getResources().getString(R.string.noticeFor_1024),Toast.LENGTH_SHORT);
//                break;
//            case 4096:
//                Toast.makeText(getContext(),getResources().getString(R.string.noticeFor_4096),Toast.LENGTH_SHORT);
//                break;
//        }
//    }

    public boolean equal(card another){
        return this.number == another.getNumber();
    }

    public int getNumber(){
        return number;
    }

    public TextView getCard() {
        return card;
    }
}
