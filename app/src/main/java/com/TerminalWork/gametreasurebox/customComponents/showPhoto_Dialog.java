package com.TerminalWork.gametreasurebox.customComponents;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.TerminalWork.gametreasurebox.R;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.Objects;

public class showPhoto_Dialog extends Dialog {

    public showPhoto_Dialog(@NonNull Context context, int themeResId, int id) {
        super(context, themeResId);
        setContentView(R.layout.display_photo_dialog);
        PhotoView imageView = findViewById(R.id.display_photo);
        imageView.setImageResource(id);
        WindowManager.LayoutParams lp= Objects.requireNonNull(getWindow()).getAttributes();
        Point point = new Point();
        getWindow().getWindowManager().getDefaultDisplay().getSize(point);
        lp.width = point.x;
        lp.height = point.y;
        this.getWindow().setAttributes(lp);
    }
}
