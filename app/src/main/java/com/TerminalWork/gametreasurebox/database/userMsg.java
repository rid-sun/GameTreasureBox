package com.TerminalWork.gametreasurebox.database;

import org.litepal.crud.LitePalSupport;

public class userMsg extends LitePalSupport {
    private long ID;
    private String name;
    private String Email;
    private String password;
    private String signature;
    private String game_record;
    private String headSculptureLocalPath;

    public String getHeadSculptureLocalPath() {
        return headSculptureLocalPath;
    }

    public void setHeadSculptureLocalPath(String headSculptureLocalPath) {
        this.headSculptureLocalPath = headSculptureLocalPath;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
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

}
