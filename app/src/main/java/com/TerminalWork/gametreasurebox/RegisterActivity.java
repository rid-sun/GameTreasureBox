package com.TerminalWork.gametreasurebox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

public class RegisterActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);
    }

    public void onclick(View v){
        switch (v.getId()){
            case R.id.registry_confirm:
                //将数据写入数据库
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                finish();
                break;
            case R.id.registry_cancel:
                finish();
                break;
        }
    }

}
