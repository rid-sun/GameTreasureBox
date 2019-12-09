package com.TerminalWork.gametreasurebox;

import android.app.Application;

import com.yatoooon.screenadaptation.ScreenAdapterTools;

public class myApplication extends Application {
    public myApplication() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ScreenAdapterTools.init(this);
    }
}
