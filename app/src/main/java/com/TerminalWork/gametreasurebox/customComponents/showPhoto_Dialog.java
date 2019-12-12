package com.TerminalWork.gametreasurebox.customComponents;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.TerminalWork.gametreasurebox.R;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.Objects;

public class showPhoto_Dialog extends Dialog {

    private PhotoView imageView;

    public showPhoto_Dialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.display_photo_dialog);
        imageView = findViewById(R.id.display_photo);
        WindowManager.LayoutParams lp= Objects.requireNonNull(getWindow()).getAttributes();
        Point point = new Point();
        getWindow().getWindowManager().getDefaultDisplay().getSize(point);
        lp.width = point.x;
        lp.height = point.y;
        this.getWindow().setAttributes(lp);
    }

    public void setImageView(int id, String path) {
        if(path == null){
            imageView.setImageResource(id);
        }else{
            imageView.setImageDrawable(Drawable.createFromPath(path));
        }
    }
}
