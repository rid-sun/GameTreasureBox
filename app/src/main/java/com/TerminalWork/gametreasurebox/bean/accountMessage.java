package com.TerminalWork.gametreasurebox.bean;

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

    public String getAccount() {
        return account;
    }

}
