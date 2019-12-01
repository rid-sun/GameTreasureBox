package com.TerminalWork.gametreasurebox.database;

import org.litepal.crud.LitePalSupport;

public class achievement extends LitePalSupport {
    private long ID;
    private String name;
    private String Email;

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
