package com.TerminalWork.gametreasurebox;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 *
 */
public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String logoPath = Environment.getExternalStorageDirectory() + "/" + "logo.PNG";
        Log.i("logoPath", logoPath);
        File imageLogo = new File("logo.PNG");
        FileOutputStream outputStream = null;
        InputStream inputStream = null;
        if(!imageLogo.exists()){
            try{
                inputStream = getApplicationContext().getAssets().open("logo.PNG");
                outputStream = new FileOutputStream(logoPath);
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, length);
                }
            } catch (IOException e) {
                Log.e("写入sd卡默认图片", "failed");
            }finally {
                try {
                    if(outputStream != null && inputStream != null){
                        outputStream.close();
                        inputStream.close();
                    }
                } catch (IOException e) {
                    Log.e("关闭写入sd卡默认图片流", "failed");
                }
            }
        }
    }
}
