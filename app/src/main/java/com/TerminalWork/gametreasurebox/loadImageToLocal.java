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
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 *
 */
public class loadImageToLocal extends IntentService {

    public loadImageToLocal() {
        super("loadImageToLocal");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String oldImagePath = null;
        String targetImagePath;
        if(intent != null){
            Bundle bundle = new Bundle();
            bundle = intent.getExtras();
            oldImagePath = bundle.getString("imagePath");
            targetImagePath = Environment.getExternalStorageDirectory() + "/" + "treasureBox" + oldImagePath.substring(oldImagePath.lastIndexOf("/"));
        }else{
            targetImagePath = Environment.getExternalStorageDirectory() + "/" + "treasureBox" + "/" + "logo.PNG";
        }
        File targetImage = new File(targetImagePath);
        FileOutputStream outputStream = null;
        InputStream inputStream = null;
        if(!targetImage.exists()){
            targetImage.mkdirs();//创建父级目录
            try{
                if(intent != null){
                    inputStream = getApplicationContext().getAssets().open("logo.PNG");
                }else{
                    inputStream = new FileInputStream(oldImagePath);
                }
                outputStream = new FileOutputStream(targetImagePath);
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
