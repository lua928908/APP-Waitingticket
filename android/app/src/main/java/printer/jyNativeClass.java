package printer;

//import java.io.FileDescriptor;

import java.io.FileDescriptor;

public class jyNativeClass {
    static {
        System.out.println("JNI_DEBUG: jyNativeClass static block START");
        try {
            System.out.println("JNI_DEBUG: Attempting to load libjyndklib.so...");
            System.out.println("JNI_DEBUG: Current library path: " + System.getProperty("java.library.path"));
            System.loadLibrary("jyndklib");
            System.out.println("JNI_DEBUG: libjyndklib.so loaded successfully!");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("JNI_DEBUG: CRITICAL - Failed to load libjyndklib.so!");
            System.err.println("JNI_DEBUG: Error details: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("JNI_DEBUG: jyNativeClass static block END");
    }

    // 더미 구현으로 네이티브 함수들을 대체
    public int jyUart_WriteByte(int hDevice, String bByte) {
        System.out.println("JNI_DEBUG: jyUart_WriteByte called with hDevice=" + hDevice + ", bByte=" + bByte);
        return 0; // 성공
    }
    
    public int jyUart_ReadByte() {
        System.out.println("JNI_DEBUG: jyUart_ReadByte called");
        return 0; // 성공
    }
    
    public String jyUart_readDataSend() {
        System.out.println("JNI_DEBUG: jyUart_readDataSend called");
        return "dummy_data";
    }

    public int jyPrinterOpen() {
        System.out.println("JNI_DEBUG: jyPrinterOpen called");
        return 0; // 성공
    }
    
    public int jyPrinter_PaperCheck() {
        System.out.println("JNI_DEBUG: jyPrinter_PaperCheck called");
        return 0; // 용지 있음
    }
    
    public int jyPrinter_CoverCheck() {
        System.out.println("JNI_DEBUG: jyPrinter_CoverCheck called");
        return 0; // 커버 닫힘
    }
    
    public int jyPrinter_OverheatCheck() {
        System.out.println("JNI_DEBUG: jyPrinter_OverheatCheck called");
        return 0; // 과열 없음
    }
    
    public int jyPrinterClose() {
        System.out.println("JNI_DEBUG: jyPrinterClose called");
        return 0; // 성공
    }

    public int jyPrintString(byte[] string, int sync) {
        System.out.println("JNI_DEBUG: jyPrintString called with length=" + string.length + ", sync=" + sync);
        System.out.println("JNI_DEBUG: Print content: " + new String(string));
        return 0; // 성공
    }
    
    public int jyPrinterFeed(int printer_fd, int dwDirection) {
        System.out.println("JNI_DEBUG: jyPrinterFeed called with printer_fd=" + printer_fd + ", dwDirection=" + dwDirection);
        return 0; // 성공
    }
    
    public int jyPrinterRawData(byte[] pByte) {
        System.out.println("JNI_DEBUG: jyPrinterRawData called with length=" + pByte.length);
        return 0; // 성공
    }
    
    public int jyPrinterSensor() {
        System.out.println("JNI_DEBUG: jyPrinterSensor called");
        return 0; // 성공
    }

    public int jyCashdraw_Open() {
        System.out.println("JNI_DEBUG: jyCashdraw_Open called");
        return 0; // 성공
    }
    
    public int jyCashdraw_Close(int hDevice) {
        System.out.println("JNI_DEBUG: jyCashdraw_Close called with hDevice=" + hDevice);
        return 0; // 성공
    }
    
    public int jyCashdraw_Signal(int dwMs) {
        System.out.println("JNI_DEBUG: jyCashdraw_Signal called with dwMs=" + dwMs);
        return 0; // 성공
    }

    public int kwPrinter_PosUartCheck(int hDevice, int hUart, int data1, int data2) {
        System.out.println("JNI_DEBUG: kwPrinter_PosUartCheck called");
        return 0; // 성공
    }

    public byte[] arraytest(byte[] t) {
        System.out.println("JNI_DEBUG: arraytest called");
        return t; // 입력 그대로 반환
    }

    public FileDescriptor jyUart_open(String path, int baudrate, int flags) {
        System.out.println("JNI_DEBUG: jyUart_open called with path=" + path + ", baudrate=" + baudrate + ", flags=" + flags);
        return null; // 더미 반환
    }
    
    public void jyUart_close(FileDescriptor mfd) {
        System.out.println("JNI_DEBUG: jyUart_close called");
    }
}
