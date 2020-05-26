package com.TerminalWork.gametreasurebox.database;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

/*
 * 作者：JiaTai Sun
 * 时间：20-5-26 下午5:29
 * 类名：userMsg
 * 功能：存储用户信息
 */

public class userMsg extends LitePalSupport {

    @Column(unique = true, defaultValue = "0")
    private long ID;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(defaultValue = "技术永无止境，勇攀高峰方显年轻本色")
    private String signature;
    private String game_record;

    @Column(defaultValue = "/storage/emulated/0/treasureBoxImages/logo.PNG")
    private String headSculptureLocalPath;

    //这里如果数据表用long型数据的话，在后面adapter更新数据时会失败，string就可以了
    @Column(defaultValue = "0")
    private String lastLoginTime;

    public String getHeadSculptureLocalPath() {
        return headSculptureLocalPath;
    }

    public void setHeadSculptureLocalPath(String headSculptureLocalPath) {
        this.headSculptureLocalPath = headSculptureLocalPath;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void setGame_record(String game_record) {
        this.game_record = game_record;
    }

    public long getID() {
        return ID;
    }

    public String getGame_record() {
        return game_record;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getSignature() {
        return signature;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}
