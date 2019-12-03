package com.TerminalWork.gametreasurebox;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;

import com.TerminalWork.gametreasurebox.database.userMsg;
import com.google.android.material.textfield.TextInputLayout;

import org.litepal.LitePal;

public class RegisterActivity extends Activity {

    private TextInputLayout InputLayout_account, InputLayout_email;
    private EditText editTextAccount, editTextEmail;
    private TextInputLayout InputLayout_password, InputLayout_confirmPassword;
    private EditText editTextPassword, editTextConfirmPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);

        VideoView videoView = findViewById(R.id.videoView);
        videoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.long_board_girl));
        videoView.start();
        videoView.setOnCompletionListener(completion);

        InputLayout_account = findViewById(R.id.InputLayout_account);
        editTextAccount = InputLayout_account.getEditText();
        InputLayout_email = findViewById(R.id.InputLayout_email);
        editTextEmail = InputLayout_email.getEditText();
        InputLayout_password = findViewById(R.id.InputLayout_password);
        editTextPassword = InputLayout_password.getEditText();
        InputLayout_confirmPassword = findViewById(R.id.InputLayout_confirmPassword);
        editTextConfirmPassword = InputLayout_confirmPassword.getEditText();
    }

    @Override
    protected void onStart() {
        super.onStart();
        editTextAccount.addTextChangedListener(accountWatcher);
        editTextEmail.addTextChangedListener(emailWatcher);
        editTextConfirmPassword.addTextChangedListener(confirmPasswordWatcher);
        editTextPassword.addTextChangedListener(passwordWatcher);
    }

    public void onclick(View v){
        switch (v.getId()){
            case R.id.registry_confirm:
                if(editTextAccount.getText().toString().isEmpty() && editTextEmail.getText().toString().isEmpty()){
                    Toast.makeText(RegisterActivity.this, "账号和邮箱至少一个不为空", Toast.LENGTH_SHORT);
                    return ;
                }else{
                    if(!LitePal.select("name").where("name = ?",editTextAccount.getText().toString())
                            .find(userMsg.class).isEmpty()){
                        Toast.makeText(RegisterActivity.this, "账号已存在请更换", Toast.LENGTH_SHORT);
                        return ;
                    }
                    if(!LitePal.select("email").where("email = ?",editTextEmail.getText().toString())
                            .find(userMsg.class).isEmpty()){
                        Toast.makeText(RegisterActivity.this, "邮箱已被注册请更换", Toast.LENGTH_SHORT);
                        return ;
                    }
                    if(editTextConfirmPassword.getText().toString().equals(editTextPassword.getText().toString())
                            && !editTextPassword.getText().toString().isEmpty()){
                        userMsg user = new userMsg();
                        user.setEmail(editTextEmail.getText().toString());
                        user.setName(editTextAccount.getText().toString());
                        user.setPassword(editTextPassword.getText().toString());
                        user.save();
                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT);
                    }else{
                        Toast.makeText(RegisterActivity.this, "请重新确认密码", Toast.LENGTH_SHORT);
                        return ;
                    }
                }
                finish();
                break;
            case R.id.registry_cancel:
                finish();
                break;
        }
    }

    private MediaPlayer.OnCompletionListener completion = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            mp.start();
        }
    };

    private TextWatcher accountWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String account = s.toString();
            if(editTextEmail.getText().toString().length() == 0 && account.length() == 0){
                InputLayout_account.setError("账号不能为空");
            }else {
                InputLayout_account.setError(null);
                if(!LitePal.select("name").where("name = ?",account)
                        .find(userMsg.class).isEmpty()){
                    InputLayout_account.setError("账号已存在");
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private TextWatcher emailWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(editTextAccount.getText().toString().length() == 0 && s.toString().length() == 0){
                InputLayout_email.setError("邮箱不能为空");
            }else {
                InputLayout_email.setError(null);
                if(!LitePal.select("email").where("email = ?",s.toString())
                        .find(userMsg.class).isEmpty()){
                    InputLayout_email.setError("邮箱已被注册过");
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private TextWatcher passwordWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(!editTextPassword.getText().toString().isEmpty()){
                editTextConfirmPassword.setEnabled(true);
            }
        }
    };

    private TextWatcher confirmPasswordWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            if(editTextPassword.getText().toString().isEmpty()){
                editTextConfirmPassword.setEnabled(false);
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(editTextPassword.getText().toString().equals(s.toString())){
                InputLayout_confirmPassword.setErrorEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}
