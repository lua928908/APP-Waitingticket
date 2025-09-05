package com.example.app_waitingticket.printer;

import java.io.UnsupportedEncodingException;
import com.thirteenrain.jyndklib.jyprt;
import com.thirteenrain.jyndklib.jyNativeClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.app_waitingticket.printer.fixedPrint.KovanPrintDefine;
import com.example.app_waitingticket.printer.fixedPrint.KovanPrintUtils;
import com.example.app_waitingticket.printer.fixedPrint.KovanReceiptBuilder;
import com.example.app_waitingticket.printer.fixedPrint.KovanReceiptVo;

public class PrinterHelper {

    private jyNativeClass nativec;
    private jyprt jpc;
    private boolean isPrinterOpen = false;
    private final int printsync = 1;
    boolean CThreadSwitch;

    public PrinterHelper() {
        System.out.println("PrinterHelper: Constructor called. Initializing objects...");
        try {
            // [수정] 생성자에서 객체를 단 한번만 안정적으로 생성합니다.
            this.nativec = new jyNativeClass();
            this.jpc = new jyprt();
            System.out.println("PrinterHelper: Native objects initialized successfully.");
            // 생성자에서 바로 프린터를 엽니다.
            openPrinter();
        } catch (Exception e) {
            System.err.println("PrinterHelper CRITICAL: Initialization failed.");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void openPrinter() {
        if (isPrinterOpen) return;
        try {
            if (nativec != null && nativec.jyPrinterOpen() == 0) {
                isPrinterOpen = true;
                System.out.println("PrinterHelper: Printer opened successfully.");
            } else {
                isPrinterOpen = false;
                System.err.println("PrinterHelper: Failed to open printer.");
            }
        } catch (Exception e) {
            isPrinterOpen = false;
            System.err.println("PrinterHelper: Exception while opening printer.");
            e.printStackTrace();
        }
    }

    public int printerStatus() {
        if (!isPrinterOpen) return -1;
        try {
            if (nativec.jyPrinter_PaperCheck() != 0) return -1;
            if (nativec.jyPrinter_CoverCheck() != 0) return -1;
            if (nativec.jyPrinter_OverheatCheck() != 0) return -1;
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void closePrinter() {
        if (isPrinterOpen && nativec != null) {
            nativec.jyPrinterClose();
            isPrinterOpen = false;
            System.out.println("PrinterHelper: Printer closed.");
        }
    }

    // public String printText(String text) {
    //     if (!isPrinterOpen) {
    //         System.err.println("Print command ignored: Printer is not open.");
    //         return "Print Failed: Printer not open.";
    //     }
    //     try {
    //         System.out.println("PrinterHelper: Preparing to print text.");
    //
    //         // 가장 단순하고 확실한 명령어 조합 사용
    //         String initCmd = jpc.Esc_Initialize();
    //         String contentCmd = text;
    //         String cutCmd = "\n\n\n\n" + jpc.GS_CutPaper((char) 0);
    //
    //         nativec.jyPrintString(initCmd.getBytes("euc-kr"), printsync);
    //         nativec.jyPrintString(contentCmd.getBytes("euc-kr"), printsync);
    //         nativec.jyPrintString(cutCmd.getBytes("euc-kr"), printsync);
    //
    //         System.out.println("PrinterHelper: Print commands sent to native layer.");
    //         return "Print Success";
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return "Print Failed: " + e.getMessage();
    //     }
    // }

    public String printText(String text){
        JSONArray bbbb = new JSONArray();   // 메뉴 리스트
        //List<JSONObject> bbbb = new ArrayList<>();   // 메뉴 리스트

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
        //bbbb.add(brrrr);

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

    public String printSimpleTest() {
        try {
            System.out.println("PrinterHelper: printSimpleTest() 실행");

            jyNativeClass nativec = new jyNativeClass();
            jyprt jpc = new jyprt();

            // 프린터 열기
            int openResult = nativec.jyPrinterOpen();
            if (openResult != 0) {
                System.err.println("PrinterHelper: Failed to open printer. Result: " + openResult);
                return "Print Failed: Cannot open printer";
            }

            // 아주 단순한 출력 테스트 (제조사 예제처럼)
            nativec.jyPrintString(jpc.Esc_Initialize().getBytes("euc-kr"), 0);
            nativec.jyPrintString("=== PRINT TEST START ===\n".getBytes("euc-kr"), 0);
            nativec.jyPrintString("안녕하세요, 프린터 테스트입니다.\n".getBytes("euc-kr"), 0);
            nativec.jyPrintString("1234567890\n\n\n".getBytes("euc-kr"), 0);
            nativec.jyPrintString(jpc.Control_FormFeed().getBytes("euc-kr"),0); // 안되면 주석 해보기
            // nativec.jyPrintString(jpc.GS_CutPaper((char) 0).getBytes("euc-kr"), 0);


            System.out.println("PrinterHelper: printTest() 완료");
            return "Print Success";
        } catch (Exception e) {
            e.printStackTrace();
            return "Print Failed: " + e.getMessage();
        }
    }
}

