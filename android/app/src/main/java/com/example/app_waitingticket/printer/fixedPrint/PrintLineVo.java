package com.example.app_waitingticket.printer.fixedPrint;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class PrintLineVo {
    private ArrayList<KovanPrintDefine.PRINT_OPTION> options;
    private KovanPrintDefine.PRINT_KEY key;
    private String value;

    protected PrintLineVo() {
    }

    protected PrintLineVo(ArrayList<KovanPrintDefine.PRINT_OPTION> options, @NonNull KovanPrintDefine.PRINT_KEY key, String value) {
        this.options = options;
        this.key = key;
        this.value = value;
    }

    protected ArrayList<KovanPrintDefine.PRINT_OPTION> getOptions() {
        return options;
    }

    protected void setOptions(ArrayList<KovanPrintDefine.PRINT_OPTION> options) {
        this.options = options;
    }

    protected KovanPrintDefine.PRINT_KEY getKey() {
        return key;
    }

    protected void setKey(KovanPrintDefine.PRINT_KEY key) {
        this.key = key;
    }

    protected String getValue() {
        return value;
    }

    protected void setValue(String value) {
        this.value = value;
    }
}
