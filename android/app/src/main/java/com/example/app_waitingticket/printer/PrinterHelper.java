package com.example.app_waitingticket.printer;

import com.thirteenrain.jyndklib.jyNativeClass;
import com.thirteenrain.jyndklib.jyprt;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.app_waitingticket.printer.fixedPrint.KovanPrintDefine;
import com.example.app_waitingticket.printer.fixedPrint.KovanPrintUtils;
import com.example.app_waitingticket.printer.fixedPrint.KovanReceiptBuilder;
import com.example.app_waitingticket.printer.fixedPrint.KovanReceiptVo;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class PrinterHelper {

    private static PrinterHelper instance;
    private jyNativeClass nativec;
    private jyprt jpc;
    private boolean isPrinterOpen = false;
    boolean CThreadSwitch;

    private PrinterHelper() {
        System.out.println("PrinterHelper: Singleton instance created. Initializing...");
        try {
            this.nativec = new jyNativeClass();
            this.jpc = new jyprt();
            System.out.println("PrinterHelper: Native objects initialized.");

            openPrinter();
        } catch (Exception e) {
            System.err.println("PrinterHelper CRITICAL: Initialization failed.");
            e.printStackTrace();
        }
    }

    public static synchronized PrinterHelper getInstance() {
        if (instance == null) {
            instance = new PrinterHelper();
        }
        return instance;
    }

    private void openPrinter() {
        if (isPrinterOpen || nativec == null) return;

        if (nativec.jyPrinterOpen() == 0) {
            isPrinterOpen = true;
            System.out.println("PrinterHelper: Printer opened successfully.");
        } else {
            isPrinterOpen = false;
            System.err.println("PrinterHelper: Failed to open printer.");
        }
    }

    public void closePrinter() {
        if (isPrinterOpen && nativec != null) {
            System.out.println("PrinterHelper: Closing printer...");
            nativec.jyPrinterClose();
            isPrinterOpen = false;
        }
    }

    public String printText(String text){
        System.out.println("샘플과 유사한 코드 printText 실행@");
        JSONArray bbbb = new JSONArray();   // 메뉴 리스트

        JSONArray bbbbdtail = new JSONArray();
        JSONObject brrrrdtail = new JSONObject();
        try {
            brrrrdtail.put("byFIXKiMenuOptName","샷추가");
            brrrrdtail.put("byFIXKiMenuOptCount","2");

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        bbbbdtail.put(brrrrdtail);

        JSONObject brrrr = new JSONObject();
        try {
            brrrr.put("byFIXKiMenuName","아이스 아메리카노");
            brrrr.put("byFIXKiMenuCount","2");
            brrrr.put("byFIXKiMenuOptListCount","1");
            brrrr.put("byFIXKIMenuOptList",bbbbdtail);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        bbbb.put(brrrr);

        JSONArray bbbbdtail1 = new JSONArray();
        JSONObject brrrrdtail1 = new JSONObject();
        try {
            brrrrdtail1.put("byFIXKiMenuOptName","샷추가");
            brrrrdtail1.put("byFIXKiMenuOptCount","2");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        bbbbdtail1.put(brrrrdtail1);
        JSONObject brrrrdtail11 = new JSONObject();
        try {
            brrrrdtail11.put("byFIXKiMenuOptName","컵추가");
            brrrrdtail11.put("byFIXKiMenuOptCount","2");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        bbbbdtail1.put(brrrrdtail11);

        JSONObject brrrr1 = new JSONObject();
        try {
            brrrr1.put("byFIXKiMenuName","카푸치노");
            brrrr1.put("byFIXKiMenuCount","10");
            brrrr1.put("byFIXKiMenuOptListCount","2");
            brrrr1.put("byFIXKIMenuOptList",bbbbdtail1);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        bbbb.put(brrrr1);


        jyprt jpc= new jyprt();
        jyNativeClass  nativec = new jyNativeClass();
        KovanReceiptBuilder abcxx = new KovanReceiptBuilder();

        abcxx.setUserType(KovanPrintDefine.USER_TYPE.CUSTOMER)
                .setAuthType(KovanPrintDefine.AUTH_TYPE.AUTH_CREDIT)
                .setTid("1450019998")
                .setBizNo("213-81-40742")
                .setBizName("코밴 단말기 검증용 가맹점")
                .setOwnerName("코밴")
                .setBizTel("070-1234-5678")
                .setBizAddr("서울시 강남구 도곡로 6길 10")
                .setStoreNo("123456789012")
                .setPurchase("현대카드").setIssuer("다이너스카드").setInstall("일시불")
                .setCardNo("36070500********")
                .setAuthDt("22.04.06 19:02:51")
                .setAuthNo("48737712")
                .setStoreNo("123456789012")
                .setSerialNo("000001      ")
                .setAmount("990")
                .setTax("10")
                .setTotalAmount("1,000")
                .setPrintMsg1("프린트메시지1")
                .setPrintMsg2("프린트메시지2")
                .setPrintMsg3("프린트메시지3");
        KovanReceiptVo abcqqd = abcxx.build();
        byte [] ttt = KovanPrintUtils.PrintReceipt(42 , abcqqd);


        try {
            nativec.jyPrintString(jpc.Esc_Initialize().getBytes("euc-kr"),0);
            nativec.jyPrintString(ttt,0);

            nativec.jyPrintString("\n\n\n\n".getBytes("euc-kr"),0);
            nativec.jyPrintString(jpc.Control_FormFeed().getBytes("euc-kr"),0);
            nativec.jyPrintString(jpc.GS_CutPaper((char)0).getBytes("euc-kr"),0);
            nativec.jyPrintString("\n\n".getBytes("euc-kr"),0);
            TestResult.rePRINT=true;
            CThreadSwitch=false;
            return "Print Success";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            CThreadSwitch=false;
            return "Print Failure";
        } catch (java.lang.Exception e) {
            e.printStackTrace();
            return "Print Failure Exception";
        }
    }
}