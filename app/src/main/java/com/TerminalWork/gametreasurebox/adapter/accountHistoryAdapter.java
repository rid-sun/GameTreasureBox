package com.TerminalWork.gametreasurebox.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.TerminalWork.gametreasurebox.R;
import com.TerminalWork.gametreasurebox.bean.accountMessage;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class accountHistoryAdapter extends RecyclerView.Adapter<accountHistoryAdapter.ViewHolder> {

    private List<accountMessage> accountMessagesList;
    private CircleImageView headImage;
    private EditText accountText;
    private PopupWindow popupWindow;
    private ImageView login;
    private RelativeLayout passwordText;

    public accountHistoryAdapter(List<accountMessage> accountMessagesList, EditText accountText,
                                 CircleImageView headImage, PopupWindow popupWindow, ImageView login, RelativeLayout passwordText) {
        this.accountMessagesList = accountMessagesList;
        this.accountText = accountText;
        this.headImage = headImage;
        this.popupWindow = popupWindow;
        this.login = login;
        this.passwordText = passwordText;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.select_account_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.itemSelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                accountMessage msg = accountMessagesList.get(position);
                accountText.setText(msg.getAccount());
                headImage.setImageDrawable(Drawable.createFromPath(msg.getAccountImagePath()));
                popupWindow.dismiss();
                login.setVisibility(View.VISIBLE);
                passwordText.setVisibility(View.VISIBLE);
            }
        });
        holder.deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                accountMessagesList.remove(position);
                //应该弹出一个自定义的dialog来确认是否删除
                accountHistoryAdapter.this.notifyDataSetChanged();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        accountMessage msg = accountMessagesList.get(position);
        holder.accountImage.setImageDrawable(Drawable.createFromPath(msg.getAccountImagePath()));
        holder.account.setText(msg.getAccount());
    }

    @Override
    public int getItemCount() {
        return accountMessagesList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView accountImage;
        TextView account;
        ImageView deleteImage;
        View itemSelf;

        public ViewHolder(View view){
            super(view);
            itemSelf = view;
            accountImage = view.findViewById(R.id.accountImage_item);
            account = view.findViewById(R.id.account_item);
            deleteImage = view.findViewById(R.id.delete_item);
        }
    }

}
