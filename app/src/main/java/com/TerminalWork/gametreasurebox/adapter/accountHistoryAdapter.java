package com.TerminalWork.gametreasurebox.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.TerminalWork.gametreasurebox.R;
import com.TerminalWork.gametreasurebox.bean.accountMessage;
import com.TerminalWork.gametreasurebox.database.userMsg;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/*
 * 作者：rid-sun
 * 时间：20-1-24 下午3:27
 * 类名：accountHistoryAdapter
 * 功能：为登录界面的用户历史列表添加适配器
 */

public class accountHistoryAdapter extends RecyclerView.Adapter<accountHistoryAdapter.ViewHolder> {

    private List<accountMessage> accountMessagesList;
    private CircleImageView headImage;
    private EditText accountText;
    private PopupWindow popupWindow;
//    BitmapFactory.Options options = new BitmapFactory.Options();
//    Bitmap bitmap;

    public accountHistoryAdapter(List<accountMessage> accountMessagesList, EditText accountText,
                                 CircleImageView headImage, PopupWindow popupWindow) {
        this.accountMessagesList = accountMessagesList;
        this.accountText = accountText;
        this.headImage = headImage;
        this.popupWindow = popupWindow;
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
            }
        });
        holder.deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                String name = accountMessagesList.get(position).getAccount();
                userMsg msg = new userMsg();
                msg.setLastLoginTime("0");
                msg.updateAll("name = ?", name);
                accountMessagesList.remove(position);
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

        private ViewHolder(View view){
            super(view);
            itemSelf = view;
            accountImage = view.findViewById(R.id.accountImage_item);
            account = view.findViewById(R.id.account_item);
            deleteImage = view.findViewById(R.id.delete_item);
        }
    }

}
