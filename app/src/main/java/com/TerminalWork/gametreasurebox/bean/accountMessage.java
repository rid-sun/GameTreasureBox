package com.TerminalWork.gametreasurebox.bean;

import android.widget.ImageView;

public class accountMessage {
    private String accountImagePath;
    private String account;

    public accountMessage(String accountImagePath, String account) {
        this.accountImagePath = accountImagePath;
        this.account = account;
    }

    public String getAccountImagePath() {
        return accountImagePath;
    }

    public void setAccountImagePath(String accountImagePath) {
        this.accountImagePath = accountImagePath;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

}
