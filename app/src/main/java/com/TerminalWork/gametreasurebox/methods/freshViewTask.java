package com.TerminalWork.gametreasurebox.methods;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.TerminalWork.gametreasurebox.custom_components.image_moveView;

public class freshViewTask extends Thread {
    private image_moveView imagemoveView;
    public volatile boolean exit = false;
    public freshViewTask(image_moveView imagemoveView){
        this.imagemoveView = imagemoveView;
    }

    @Override
    public void run() {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);

    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:{
                    imagemoveView.init_Pos();
                    imagemoveView.invalidate();
                    break;
                }
            }
        }
    };
}
