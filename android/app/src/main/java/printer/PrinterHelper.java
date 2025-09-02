package printer;

import printer.jyNativeClass;

public class PrinterHelper {

    private jyNativeClass nativec;
    private boolean isPrinterOpen = false;

    public PrinterHelper() {
        nativec = new jyNativeClass();
    }

    // 프린터 열기 (앱 실행 시 한번만 호출)
    public String openPrinter() {
        if (isPrinterOpen) {
            return "Printer is already open.";
        }
        try {
            // [수정] jyNativeClass.java에 정의된 실제 함수 이름으로 변경
            int result = nativec.jyPrinterOpen();
            if (result == 0) {
                isPrinterOpen = true;
                return "Printer opened successfully.";
            } else {
                return "Failed to open printer. Error code: " + result;
            }
        } catch (UnsatisfiedLinkError e) {
            return "Native library not loaded: " + e.getMessage();
        } catch (Exception e) {
            return "Exception while opening printer: " + e.getMessage();
        }
    }

    // 프린터 닫기 (앱 종료 시 한번만 호출)
    public void closePrinter() {
        if (isPrinterOpen) {
            // [수정] jyNativeClass.java에 정의된 실제 함수 이름으로 변경
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
            // NDK가 요구하는 인코딩에 맞게 텍스트를 byte 배열로 변환 (한글 포스기는 보통 EUC-KR)
            byte[] textBytes = text.getBytes("EUC-KR");
            // [수정] jyNativeClass.java에 정의된 실제 함수 이름으로 변경
            int result = nativec.jyPrintString(textBytes, textBytes.length);
            return "Print command sent. Result: " + result;
        } catch (UnsatisfiedLinkError e) {
            return "Native library not loaded: " + e.getMessage();
        } catch (Exception e) {
            return "Failed to print text: " + e.getMessage();
        }
    }

    // 금전함 열기
    public String openCashDrawer() {
        if (!isPrinterOpen) {
            return "Printer is not open.";
        }
        // 문서에 따르면 10~200msec 사이의 값
        int result = nativec.jyCashdraw_Signal(100);
        if (result == 0) {
            return "Cash drawer signal sent successfully.";
        } else {
            return "Failed to open cash drawer. Error code: " + result;
        }
    }
}
