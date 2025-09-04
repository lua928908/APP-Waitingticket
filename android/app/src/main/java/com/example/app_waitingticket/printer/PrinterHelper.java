package com.example.app_waitingticket.printer;

import java.io.UnsupportedEncodingException;
import com.thirteenrain.jyndklib.*;

public class PrinterHelper {

    private jyNativeClass nativec;
    private jyprt jpc;
    private boolean isPrinterOpen = false;
    private final int printsync = 1;

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

    public String printText(String text) {
        if (!isPrinterOpen) {
            System.err.println("Print command ignored: Printer is not open.");
            return "Print Failed: Printer not open.";
        }
        try {
            System.out.println("PrinterHelper: Preparing to print text.");

            // 가장 단순하고 확실한 명령어 조합 사용
            String initCmd = jpc.Esc_Initialize();
            String contentCmd = text;
            String cutCmd = "\n\n\n\n" + jpc.GS_CutPaper((char) 0);

            nativec.jyPrintString(initCmd.getBytes("euc-kr"), printsync);
            nativec.jyPrintString(contentCmd.getBytes("euc-kr"), printsync);
            nativec.jyPrintString(cutCmd.getBytes("euc-kr"), printsync);

            System.out.println("PrinterHelper: Print commands sent to native layer.");
            return "Print Success";
        } catch (Exception e) {
            e.printStackTrace();
            return "Print Failed: " + e.getMessage();
        }
    }
}

