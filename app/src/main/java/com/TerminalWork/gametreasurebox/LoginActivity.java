package com.TerminalWork.gametreasurebox;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.TerminalWork.gametreasurebox.adapter.accountHistoryAdapter;
import com.TerminalWork.gametreasurebox.bean.accountMessage;
import com.TerminalWork.gametreasurebox.customComponents.myRecyclerView;
import com.TerminalWork.gametreasurebox.database.userMsg;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoginActivity extends Activity {
    private GradientDrawable gradientDrawable;
    private ImageView login;
    private RelativeLayout relativeLayoutAccount;
    private RelativeLayout relativeLayoutPassword;
    private EditText accountText;
    private EditText passwordText;
    private CircleImageView accountImage;
    private ImageView arrow_down;
    private TextView forgetPassword;
    private ValueAnimator colorChange;
    private TextView visitor;
    private TextView registry;
    private Animation animation;
    private PopupWindow popupWindow;
    private List<accountMessage> accountMessageList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.login);
        gradientDrawable = (GradientDrawable)login.getBackground();
        colorChange = ObjectAnimator.ofInt(gradientDrawable,"color", Color.parseColor("#0983F0"), Color.parseColor("#2098F7"),
                Color.parseColor("#1686F2"), Color.parseColor("#03c8Fc"), Color.parseColor("#00CAFC"));
        colorChange.setDuration(3000);
        colorChange.setEvaluator(new ArgbEvaluator());
        colorChange.setRepeatCount(ValueAnimator.INFINITE);
        colorChange.setRepeatMode(ValueAnimator.REVERSE);
        colorChange.start();

        registry = findViewById(R.id.register);
        visitor = findViewById(R.id.visitor);
        registry.setOnTouchListener(registryOnTouch);
        visitor.setOnTouchListener(visitorOnTouch);
    }

    @Override
    protected void onStart() {
        super.onStart();
        relativeLayoutAccount = findViewById(R.id.accountLoginRelativeLayout);
        relativeLayoutPassword = findViewById(R.id.passwrdLoginRelativeLayout);
        forgetPassword = findViewById(R.id.forgetPasswrd);
        animation = AnimationUtils.loadAnimation(this, R.anim.slideup_app_login_animation);
        relativeLayoutAccount.startAnimation(animation);
        relativeLayoutPassword.startAnimation(animation);
        login.startAnimation(animation);
        forgetPassword.startAnimation(animation);

        accountText = findViewById(R.id.userNameText);
        passwordText = findViewById(R.id.passwordText);
        accountImage = findViewById(R.id.accountImage);
        arrow_down = findViewById(R.id.arrow_down_login);

        login.setOnClickListener(loginOnclick);
        accountText.addTextChangedListener(accountTextChanged);
        arrow_down.setOnClickListener(arrowDownOnclick);

    }

    private View.OnClickListener loginOnclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!LitePal.select("name").where("name = ?",accountText.getText()
                    .toString()).find(userMsg.class).isEmpty()){
                if(!LitePal.select("password").where("name = ? and password = ?",accountText.getText()
                        .toString(), passwordText.getText().toString())
                        .find(userMsg.class).isEmpty()){
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    SharedPreferences sharedPreferences = getSharedPreferences("loginState", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("nowUser", accountText.getText().toString());
                    editor.apply();
                    userMsg user = new userMsg();
                    user.setLastLoginTime(System.currentTimeMillis());
                    user.updateAll("name = ?", accountText.getText().toString());
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "密码错误",Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(LoginActivity.this, "账号错误",Toast.LENGTH_SHORT).show();
            }
        }
    };

    private TextWatcher accountTextChanged = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            accountImage.setImageDrawable(getDrawable(R.drawable.logo));
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            List<userMsg> user = LitePal.select("headSculptureLocalPath")
                    .where("name = ?",s.toString()).find(userMsg.class);
            if(!user.isEmpty() && user.get(0).getHeadSculptureLocalPath() != null){
                accountImage.setImageDrawable(Drawable.createFromPath(user.get(0).getHeadSculptureLocalPath()));
            }else{
                accountImage.setImageDrawable(getDrawable(R.drawable.logo));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private View.OnTouchListener visitorOnTouch = new View.OnTouchListener() {

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
    };

    private View.OnTouchListener registryOnTouch = new View.OnTouchListener() {
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
    };

    private View.OnClickListener arrowDownOnclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            loadHistoryRecord();

            accountText.setText(null);
            accountImage.setImageDrawable(null);

            View view = LayoutInflater.from(LoginActivity.this)
                    .inflate(R.layout.popup_window_recycler_view, null);
            popupWindow = new PopupWindow(LoginActivity.this);

            myRecyclerView recyclerView = view.findViewById(R.id.account_recycler);
            LinearLayoutManager layoutManager = new LinearLayoutManager(LoginActivity.this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
            accountHistoryAdapter adapter = new accountHistoryAdapter(accountMessageList, accountText
                    , accountImage, popupWindow, login, relativeLayoutPassword);
            recyclerView.setAdapter(adapter);

            login.setVisibility(View.GONE);
            relativeLayoutPassword.setVisibility(View.GONE);

            popupWindow.setWidth(accountText.getWidth());
            popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setContentView(view);
            popupWindow.setBackgroundDrawable(getDrawable(R.drawable.corner_shape_select_account));//颜色全透明，这样布局就是圆角了
            popupWindow.setOutsideTouchable(true);//点击外部区域关闭弹窗
            popupWindow.setFocusable(true);
            popupWindow.showAsDropDown(accountText, 0, 30);
        }
    };

    private void loadHistoryRecord(){
        List<userMsg> users = LitePal.select("name", "headSculptureLocalPath")
                .where("lastLoginTime > 0").order("lastLoginTime desc").find(userMsg.class);
        for( userMsg i : users){
            accountMessage msg = new accountMessage(i.getHeadSculptureLocalPath(), i.getName());
            accountMessageList.add(msg);
            Log.i("add", "success");
        }
    }

}
