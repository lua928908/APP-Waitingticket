package printer;

//import java.io.FileDescriptor;

import java.io.FileDescriptor;

public class jyNativeClass {
    static {
        try {
            System.out.println("jyNativeClass: ===== NATIVE LIBRARY LOADING START =====");
            System.out.println("jyNativeClass: Loading native library 'jyndklib'");
            System.out.println("jyNativeClass: Current working directory: " + System.getProperty("user.dir"));
            System.out.println("jyNativeClass: Java library path: " + System.getProperty("java.library.path"));
            System.out.println("jyNativeClass: OS name: " + System.getProperty("os.name"));
            System.out.println("jyNativeClass: OS arch: " + System.getProperty("os.arch"));
            
            // 라이브러리 파일 존재 여부 확인
            try {
                String libraryPath = System.getProperty("java.library.path");
                if (libraryPath != null) {
                    String[] paths = libraryPath.split(":");
                    System.out.println("jyNativeClass: Checking library paths:");
                    for (String path : paths) {
                        System.out.println("jyNativeClass:   - " + path);
                    }
                }
            } catch (Exception e) {
                System.out.println("jyNativeClass: Error checking library paths: " + e.getMessage());
            }
            
            System.out.println("jyNativeClass: Attempting to load library...");
            System.loadLibrary("jyndklib");							//apk install
            System.out.println("jyNativeClass: Native library 'jyndklib' loaded successfully");
            System.out.println("jyNativeClass: ===== NATIVE LIBRARY LOADING SUCCESS =====");
            //System.load("/system/lib/libjyndklib.so");		//system app
        } catch (UnsatisfiedLinkError e) {
            System.out.println("jyNativeClass: ===== NATIVE LIBRARY LOADING FAILED =====");
            System.out.println("jyNativeClass: Failed to load native library 'jyndklib': " + e.getMessage());
            System.out.println("jyNativeClass: UnsatisfiedLinkError details: " + e.toString());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("jyNativeClass: ===== NATIVE LIBRARY LOADING EXCEPTION =====");
            System.out.println("jyNativeClass: Exception while loading native library: " + e.getMessage());
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
