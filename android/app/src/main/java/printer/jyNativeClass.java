package printer;

//import java.io.FileDescriptor;

import java.io.FileDescriptor;

public class jyNativeClass {
    static {
        try {
            System.loadLibrary("jyndklib");
            // 성공 로그
            System.out.println("JNI_DEBUG: libjyndklib.so loaded successfully!");
        } catch (UnsatisfiedLinkError e) {
            // 실패 로그
            System.err.println("JNI_DEBUG: CRITICAL - Failed to load libjyndklib.so!");
            e.printStackTrace();
        }
    }

    public native int jyUart_WriteByte(int hDevice,String bByte);
    public native int jyUart_ReadByte();
    public native String jyUart_readDataSend();

    // public static native FileDescriptor jyUart_Open(String path, int baudrate, int flags);
    //public native void jyUart_close(FileDescriptor mfd);


    public native int jyPrinterOpen();
    //           OK =0;
    //           printer open error -1;
    public native int jyPrinter_PaperCheck();       //    OK =0;       paper no exist      -2;
    public native int jyPrinter_CoverCheck();       //    OK =0;      cover open          -3;
    public native int jyPrinter_OverheatCheck();    //    OK =0;      printer overheat    -4;
    public native int jyPrinterClose();

    public native int jyPrintString(byte[] string, int sync); /// Use String to byteArray
    public native int jyPrinterFeed(int printer_fd, int dwDirection);
    public native int jyPrinterRawData(byte[] pByte);
    //    public native int jyPrinterRawData2D(byte[] pByte,int count);
//    public native void jyPrinterSendRawData2D(byte[] pByte,int count);
    public native int jyPrinterSensor();


    public native int jyCashdraw_Open();    /// printer Open과 동일
    public native int jyCashdraw_Close(int hDevice);    /// printer Close와 동일
    public native int jyCashdraw_Signal(int dwMs);
//    public native int jyCashdraw_PosSignal(int dwMs);



    public native int kwPrinter_PosUartCheck(int hDevice, int hUart, int data1, int data2);

    public native byte[] arraytest(byte[] t);

    public native FileDescriptor jyUart_open(String path, int baudrate, int flags);
    public native void jyUart_close(FileDescriptor mfd);


}
