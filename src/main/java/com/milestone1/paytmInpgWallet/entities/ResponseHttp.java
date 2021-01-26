package com.milestone1.paytmInpgWallet.entities;

public class ResponseHttp {

    private String msg, status;

    public ResponseHttp(String msg, String status) {
        this.msg = msg;
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
