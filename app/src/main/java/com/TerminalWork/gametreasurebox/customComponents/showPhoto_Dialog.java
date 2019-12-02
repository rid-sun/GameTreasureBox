package com.TerminalWork.gametreasurebox.customComponents;

import android.app.Dialog;
import android.content.Context;
import android.view.MotionEvent;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.TerminalWork.gametreasurebox.R;
import com.github.chrisbanes.photoview.PhotoView;

public class showPhoto_Dialog extends Dialog {

    private PhotoView imageView;
    //自定义dialog需处理
    public showPhoto_Dialog(@NonNull Context context, int themeResId, int id) {
        super(context, themeResId);
        setContentView(R.layout.display_photo_dialog);
        imageView = findViewById(R.id.display_photo);
        imageView.setImageResource(id);
        WindowManager.LayoutParams lp=this.getWindow().getAttributes();
        this.getWindow().setAttributes(lp);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            dismiss();
        }
        return true;
    }
}
