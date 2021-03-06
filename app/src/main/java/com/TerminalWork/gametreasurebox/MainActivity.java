package com.TerminalWork.gametreasurebox;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.TerminalWork.gametreasurebox.bean.flags;
import com.TerminalWork.gametreasurebox.database.userMsg;
import com.TerminalWork.gametreasurebox.customComponents.showPhoto_Dialog;
import com.TerminalWork.gametreasurebox.methods.fragmentController;
import com.google.android.material.navigation.NavigationView;

import org.litepal.LitePal;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    private showPhoto_Dialog dialog;
    private DrawerLayout drawerLayout;
    private CircleImageView account;
    private TextView signature;
    private TextView username;
    private int closeFlag;
    private long lastClickTime;
    private long currentClickTime;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private PopupWindow bottomPop;
    private String imagePath;
    private myReceiver receiver;
    private fragmentController controller;
    private NavigationView navigationView;
    private Boolean isLastPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View drawerView;
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerView = navigationView.inflateHeaderView(R.layout.nav_header);//动态加入头部
        account = drawerView.findViewById(R.id.icon_image);
        signature = drawerView.findViewById(R.id.signature);
        username = drawerView.findViewById(R.id.username);

        dialog = new showPhoto_Dialog(MainActivity.this,R.style.dialog);
        account.setOnLongClickListener(accountLongOnclick);
        account.setOnClickListener(accountOnclick);

        //隐藏上方的actionbar
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        mediaPlayer = MediaPlayer.create(this, R.raw.shooting_star);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        isLastPlaying = true;

        controller = fragmentController.getInstance(this, R.id.fragment_view);
        Log.i("onCreateMain","yes");

    }

    @Override
    protected void onStart() {
        super.onStart();

        if(isLastPlaying){
            mediaPlayer.start();
        }

        String nowUser;
        SharedPreferences spf = getSharedPreferences("loginState", MODE_PRIVATE);
        nowUser = spf.getString("nowUser",null);
        //Log.i("nowUser", nowUser);
        List<userMsg> user = LitePal.select("headSculptureLocalPath", "name", "signature").
                where("name = ?", nowUser).find(userMsg.class);
        account.setImageDrawable(Drawable.createFromPath(user.get(0).getHeadSculptureLocalPath()));
        username.setText(user.get(0).getName());
        signature.setText(user.get(0).getSignature());

        closeFlag = 0;
        lastClickTime = 0;
        currentClickTime = 0;

        Log.i("mainActivity","onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            isLastPlaying = true;
        }
    }

    private NavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.nav_home:
                    controller.showFragment(flags.gameSelectFragment);
                    drawerLayout.closeDrawers();
                    break;
                case R.id.nav_cancelAction:
                    SharedPreferences sharedPreferences = getSharedPreferences("loginState", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("nowUser", null);
                    editor.apply();
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                    break;
                case R.id.nav_rankList:
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setIcon(getDrawable(R.drawable.sorry))
                            .setTitle("Sorry")
                            .setMessage("作者还没有开发此功能")
                            .show();
                    break;
                case R.id.nav_musicControl:
                    if(mediaPlayer.isPlaying()){
                        mediaPlayer.pause();
                        menuItem.setIcon(getDrawable(R.drawable.music_off));
                        isLastPlaying = false;
                    }else{
                        mediaPlayer.start();
                        menuItem.setIcon(getDrawable(R.drawable.music_on));
                        isLastPlaying = true;
                    }
                    break;
                case R.id.nav_logout:
                    finish();
                    break;
            }
            return false;
        }
    };

    private View.OnLongClickListener accountLongOnclick = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            drawerLayout.closeDrawers();
            View contentView = LayoutInflater.from(MainActivity.this).inflate(R.layout.bottom_popup_window, null);
            //让其宽度占满屏幕
            bottomPop = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            View rootView = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_main, null);
            bottomPop.setOutsideTouchable(true);
            bottomPop.setFocusable(true);
            bottomPop.setAnimationStyle(R.style.PopupWindowAnimation);
            bottomPop.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
            TextView localUpload = contentView.findViewById(R.id.localUpload);
            localUpload.setOnClickListener(uploadOnclick);
            TextView cancelUpload = contentView.findViewById(R.id.cancelUpload);
            cancelUpload.setOnClickListener(uploadOnclick);
            TextView cameraUpload = contentView.findViewById(R.id.cameraUpload);
            cameraUpload.setOnClickListener(uploadOnclick);
            localUpload.setAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.popup_window_occur_animation));
            cancelUpload.setAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.popup_window_occur_animation));
            cameraUpload.setAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.popup_window_occur_animation));
            return true;
        }
    };

    private View.OnClickListener accountOnclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            drawerLayout.closeDrawers();
            List<userMsg> msg = LitePal.select("headSculptureLocalPath").where("name = ?", username.getText().toString()).find(userMsg.class);
            dialog.setImageView(R.drawable.logo, msg.get(0).getHeadSculptureLocalPath());
            dialog.show();
        }
    };

    private View.OnClickListener uploadOnclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.cameraUpload:
                    bottomPop.dismiss();
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setIcon(getDrawable(R.drawable.sorry))
                            .setTitle("Sorry")
                            .setMessage("作者还没有开发此功能")
                            .show();
                    break;
                case R.id.cancelUpload:
                    bottomPop.dismiss();
                    break;
                case R.id.localUpload:
                    bottomPop.dismiss();
                    openAlbum();
                    break;
            }
        }
    };

    private void openAlbum(){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, flags.activityRequestCode_upLoadImage);
    }

    @Override
    protected void onResume() {
        super.onResume();
        receiver = new myReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(flags.action_changeScore2048);
        intentFilter.addAction(flags.action_updateAccountImage);
        registerReceiver(receiver, intentFilter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(fragmentController.getController().gameSelectFragmentIsShow()){
                controller.backFragment();
            }else{
                closeFlag++;
                currentClickTime = System.currentTimeMillis();
                if(closeFlag == 1){
                    lastClickTime = currentClickTime;
                    Toast.makeText(this, "再按一次退出游戏", Toast.LENGTH_SHORT).show();
                }else if(currentClickTime - lastClickTime <= flags.BACK_PRESSED_INTERVAL){//使得中间点击间隔为2s
                    super.onBackPressed();
                }else{
                    closeFlag = 0;
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
        unregisterReceiver(receiver);
        fragmentController.destroyController();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case flags.activityRequestCode_upLoadImage:
                if(resultCode == RESULT_OK) {
                    handleImage(data);
                }
                break;
            case flags.activityRequestCode_cameraUpLoad:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 1){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(MainActivity.this, loadImageToLocal.class);
                intent.putExtra("imagePath", imagePath);
                startService(intent);
            }else{
                Toast.makeText(this, "拒绝权限，上传失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //from "Android第一行代码"
    private void handleImage(Intent data){
        Uri uri = data.getData();
        if(DocumentsContract.isDocumentUri(this, uri)){
            // 如果是document类型的uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                this.imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                this.imagePath = getImagePath(contentUri, null);
            }
        }else if("content".equalsIgnoreCase(uri.getScheme())){
            //如果是content类型的、uri，则用普通方式处理
            this.imagePath = getImagePath(uri, null);
        }else if("file".equalsIgnoreCase(uri.getScheme())){
            //如果是file类型的Uri，则直接获取图片路径即可
            this.imagePath = uri.getPath();
        }

        //System.out.println("imagePathFore"+ imagePath);
        //弹框出来
        if(ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }else{
            Intent intent = new Intent(MainActivity.this, loadImageToLocal.class);
            intent.putExtra("imagePath", imagePath);
            startService(intent);
        }
    }

    private String getImagePath(Uri uri, String selection){
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if(cursor != null){
            if(cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    class myReceiver extends BroadcastReceiver{

        Bundle bundle = new Bundle();

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case flags.action_changeScore2048:
                    bundle = intent.getExtras();
                    int score = bundle.getInt("score");
                    TextView scoreView = findViewById(R.id.scoreView);
                    scoreView.setText("Score: " + score);
                    break;
                case flags.action_updateAccountImage:
                    bundle = intent.getExtras();
                    String path = bundle.getString("targetPath");
                    account.setImageDrawable(Drawable.createFromPath(path));
                    NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                    Notification notification = new NotificationCompat.Builder(MainActivity.this, "GameTreasureBox")
                            .setContentTitle("GameTreasureBox")
                            .setContentText("头像设置成功")
                            .setWhen(System.currentTimeMillis())
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo))
                            .build();
                    manager.notify(1, notification);
                    break;
            }
        }
    }

}