package printer;

import printer.jyNativeClass;
import java.io.UnsupportedEncodingException;

public class PrinterHelper {

    private jyNativeClass nativec;
    private boolean isPrinterOpen = false;

    public PrinterHelper() {
        // .so 라이브러리 로딩은 jyNativeClass의 static 블록에서 자동으로 처리됩니다.
        nativec = new jyNativeClass();
    }

    // 프린터 열기
    public String openPrinter() {
        if (isPrinterOpen) {
            return "Printer is already open.";
        }
        int result = nativec.jyPrinterOpen();
        if (result == 0) {
            isPrinterOpen = true;
            return "Printer opened successfully.";
        } else {
            return "Failed to open printer. Error code: " + result;
        }
    }

    // 프린터 닫기
    public void closePrinter() {
        if (isPrinterOpen) {
            nativec.jyPrinterClose();
            isPrinterOpen = false;
        }
    }

    // 텍스트 인쇄
    public String printText(String text) {
        if (!isPrinterOpen) {
            return "Printer is not open.";
        }
        try {
            // 한글 포스기는 보통 EUC-KR 인코딩을 사용합니다.
            byte[] textBytes = text.getBytes("EUC-KR");
            int result = nativec.jyPrintString(textBytes, textBytes.length);
            return "Print command sent. Result: " + result;
        } catch (UnsupportedEncodingException e) {
            return "Failed to print text due to encoding error: " + e.getMessage();
        }
    }

    // 금전함 열기
    public String openCashDrawer() {
        if (!isPrinterOpen) {
            return "Printer is not open.";
        }
        int result = nativec.jyCashdraw_Signal(100);
        if (result == 0) {
            return "Cash drawer signal sent successfully.";
        } else {
            return "Failed to open cash drawer. Error code: " + result;
        }
    }
}

