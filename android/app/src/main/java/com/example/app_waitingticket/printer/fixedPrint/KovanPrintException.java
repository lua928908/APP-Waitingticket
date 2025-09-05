package com.example.app_waitingticket.printer.fixedPrint;

import androidx.annotation.Keep;

public class KovanPrintException extends Exception{
    private String code;
    private String msg;

    public KovanPrintException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    };

    @Keep
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Keep
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
