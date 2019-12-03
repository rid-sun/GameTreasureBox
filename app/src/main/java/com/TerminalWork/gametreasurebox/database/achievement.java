package com.TerminalWork.gametreasurebox.database;

import org.litepal.crud.LitePalSupport;

public class achievement extends LitePalSupport {
    private long ID;
    private String name;

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
}
