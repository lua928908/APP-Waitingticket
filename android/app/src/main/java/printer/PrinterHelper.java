package printer;

import java.io.UnsupportedEncodingException;
import android.app.AlertDialog;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PrinterHelper {

    private jyNativeClass nativec;
    AlertDialog alertDialog;
    String Errmess="";
    int intBright=5;
    int intCycle=1;
    boolean CThreadSwitch;
    boolean PrinterOpenFlag=false;
    boolean printFlag=true;
    int Printerstop=0;
    boolean sensorFlag=true;
    Button btnPrtStart;
    Button btnPrtStop;
    Button btnPrtQR;
    Button btnHPrtTest;
    TextView tvCycle;
    TextView tvLastCycle;
    EditText etCycle;
    TextView tvBright;
    EditText etBright;
    TextView tvSensorValue;
    TextView tvSensorTime;
    int printsync = 1;
    int status=0;
    public PrinterHelper() {
        System.out.println("PrinterHelper: Constructor called.");
        System.out.println("PrinterHelper: Thread: " + Thread.currentThread().getName());
        try {
            // 명시적으로 jyNativeClass 클래스를 로드하여 static 블록이 실행되도록 함
            System.out.println("PrinterHelper: Loading jyNativeClass class...");
            Class.forName("printer.jyNativeClass");
            System.out.println("PrinterHelper: jyNativeClass class loaded successfully");
            
            // 생성자에서 단 한번만 jyNativeClass 인스턴스를 생성합니다.
            // 이 시점에 jyNativeClass의 static 블록이 실행되어야 합니다.
            this.nativec = new jyNativeClass();
            System.out.println("PrinterHelper: jyNativeClass instance created successfully.");
        } catch (ClassNotFoundException e) {
            System.err.println("PrinterHelper CRITICAL: jyNativeClass class not found!");
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (UnsatisfiedLinkError e) {
            System.err.println("PrinterHelper CRITICAL: Failed to link native library. Check .so file.");
            e.printStackTrace();
            // 오류를 던져서 상위 호출자(MainActivity)가 알 수 있도록 합니다.
            throw e;
        } catch (Exception e) {
            System.err.println("PrinterHelper CRITICAL: An unexpected error occurred during initialization.");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // 프린터 열기
    public String openPrinter() {
        try {
            System.out.println("PrinterHelper: openPrinter() called");
            // jyNativeClass nativec = new jyNativeClass();
            // int OpenCheck = nativec.jyPrinterOpen();
            int OpenCheck = this.nativec.jyPrinterOpen(); // this.nativec 사용

            System.out.println("openPrinter > OpenCheck = " + OpenCheck);

            if(OpenCheck == -1){
                // 프린터 열기 실패
                // return -1;
                return "-1";
            } else {
                PrinterOpenFlag = true;
                // return 0;  // 성공
                return "0";  // 성공
            }
        } catch (Exception e) {
            System.out.println("PrinterHelper: Exception in openPrinter(): " + e.getMessage());
            e.printStackTrace();
            return "Exception occurred while opening printer: " + e.getMessage();
        }
    }

    // 프린터 상태 확인
    public int printerStatus(){
        System.out.println("PrinterHelper > printerStatus 메서드 실행@@");

        // nativec가 null인지 확인
        if (nativec == null) {
            System.out.println("PrinterHelper > printerStatus: nativec is null!");
            return -1;
        }

        System.out.println("PrinterHelper > printerStatus: nativec is not null, proceeding...");

        int overheatCheck=0;
        int coverCheck = 0;
        int paperCheck = nativec.jyPrinter_PaperCheck(); // this.nativec 사용

        if (paperCheck == -2) {

            Errmess = "paper no exist";
            printFlag=false;
            // Test_printer.this.runOnUiThread(new Runnable() {
            //     public void run() {
            //         if(alertDialog!=null) {
            //             alertDialog.dismiss();
            //
            //         }
            //         // dialogview(Errmess);
            //     }
            // });
            return -1;
        }
        if(paperCheck==0) {
            coverCheck= nativec.jyPrinter_CoverCheck();
            if (coverCheck == -3) {

                Errmess = "cover open";
                printFlag=false;
                // Test_printer.this.runOnUiThread(new Runnable() {
                //     public void run() {
                //         if(alertDialog!=null) {
                //             alertDialog.dismiss();
                //
                //         }
                //         // dialogview(Errmess);
                //     }
                // });
                return -1;
            }
        }
        if(coverCheck==0) {
            overheatCheck = nativec.jyPrinter_OverheatCheck();
            if (overheatCheck == -4) {

                Errmess = "printer overheat";
                printFlag=false;
                // Test_printer.this.runOnUiThread(new Runnable() {
                //     public void run() {
                //         if(alertDialog!=null) {
                //             alertDialog.dismiss();
                //
                //         }
                //         // dialogview(Errmess);
                //     }
                // });
                return -1;
                //  break;
            }
        }

        return 0;
    }

    // 프린터 닫기
    public void closePrinter() {
        if (nativec != null) {
            try {
                nativec.jyPrinterClose();
                System.out.println("PrinterHelper: Printer closed successfully");
            } catch (Exception e) {
                System.out.println("PrinterHelper: Exception in closePrinter(): " + e.getMessage());
            }
        }
    }

    // 텍스트 인쇄 (간단한 버전)
    public String printText(String text) {
        System.out.println("PrinterHelper > printText 메서드 실행@");
        System.out.println("nativec = " + nativec);
        if (nativec == null) {
            return "Native class is not initialized.";
        }

        try {
            System.out.println("PrinterHelper: Printing text: " + text);
            // 한글 포스기는 보통 EUC-KR 인코딩을 사용합니다.
            byte[] textBytes = text.getBytes("EUC-KR");
            int result = nativec.jyPrintString(textBytes, textBytes.length);
            System.out.println("PrinterHelper: Print result: " + result);

            // 실제 프린트 호출되는 메서드
            printTest();

            return "Print command sent. Result: " + result;
        } catch (UnsupportedEncodingException e) {
            System.out.println("PrinterHelper: Encoding error: " + e.getMessage());
            return "Failed to print text due to encoding error: " + e.getMessage();
        } catch (Exception e) {
            System.out.println("PrinterHelper: Exception in printText(): " + e.getMessage());
            return "Exception occurred while printing: " + e.getMessage();
        }
    }

    public void printTest(){
        jyprt jpc = new jyprt();
        jyNativeClass nativec = new jyNativeClass();
        String cmd = "";

        // 1. 프린터 초기화
        cmd += jpc.Esc_Initialize();

        // 2. 밝기 설정
        cmd += jpc.FS_Print_Bright((char)intBright);

        // 3. 문자 크기 설정 및 텍스트 출력
        cmd += jpc.GS_SelectCharaterSize((char)0x11); // 48*48 크기
        cmd += "   <제이와이컴퍼니>\n";

        cmd += jpc.GS_SelectCharaterSize((char)0x0); // 12*24 크기
        cmd += "1234567890 가나다라마바사아자차카타파하 ABCDEFGHIJKLMNOPQRSTUVWXYZ abcdefghijklmnopqrstuvwxyz\n";

        // 4. 밑줄 효과
        cmd += jpc.Esc_Underline((char)0x1); // 밑줄 on
        cmd += "텍스트 내용";
        cmd += jpc.Esc_Underline((char)0x0); // 밑줄 off

        // 5. 반전 효과
        cmd += jpc.GS_TurnsWhiteBlack((char)1); // 반전 on
        cmd += "반전 텍스트";
        cmd += jpc.GS_TurnsWhiteBlack((char)0); // 반전 off

        // 6. 용지 이송 및 절단
        cmd += "\n\n\n\n";
        cmd += jpc.Control_FormFeed();
        cmd += jpc.GS_CutPaper((char)0);

        // 7. 출력 실행
        try {
            nativec.jyPrintString(jpc.Esc_Initialize().getBytes("euc-kr"), printsync);
            nativec.jyPrintString(cmd.getBytes("euc-kr"), printsync);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public int printerOpen(){

        jyNativeClass nativec = new jyNativeClass();

        int OpenCheck=nativec.jyPrinterOpen();
        String  Errmess="";
        if(OpenCheck==-1){
            Errmess="printer open error";
            // dialogview(Errmess);
            nativec.jyPrinterClose();
            return -1;
        }
        else {
            PrinterOpenFlag=true;
            return 0;
        }
    }

    //프린터 ERROR 시에 팝업알림
    // void dialogview(String Message){
    //     AlertDialog.Builder builder = new AlertDialog.Builder(this);
    //
    //     builder.setTitle("ERORR").setMessage(Message);
    //
    //     builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
    //         @Override
    //         public void onClick(DialogInterface dialog, int id)
    //         {
    //             //Toast.makeText(getApplicationContext(), "OK Click", Toast.LENGTH_SHORT).show();
    //             int status = 0;
    //             if(TestResult.ATTest){
    //
    //                 //ATPrinterTest();
    //                 if(PrinterOpenFlag) {
    //                     PrinterOpenFlag=false;
    //                     Htestflag=false;
    //                     jyNativeClass nativec = new jyNativeClass();
    //                     nativec.jyPrinterClose();
    //                 }
    //                 Intent NextIntent = new Intent(getApplicationContext(), TestResult.class);
    //                 startActivity(NextIntent);
    //                 finish();
    //
    //             }
    //             else {
    //                 if (!PrinterOpenFlag) {
    //
    //                     status = printerOpen();
    //                     if (status == 0)  PrinterOpenFlag = true;
    //                     else {
    //                         if(alertDialog!=null) {
    //                             alertDialog.dismiss();
    //                         }
    //                         String Errmess = "Printer Open Fail";
    //                         dialogview(Errmess);
    //                     }
    //                 } else {
    //                     if (status == 0) {
    //                         //PrinterOpenFlag = true;
    //                         if(!sensorFlag) {
    //                             sensorFlag = true;
    //                             prSensorThread pSThread = new prSensorThread();
    //                             pSThread.start();
    //                         }
    //                         status = printerStatus();
    //
    //                         if (status == 0) {
    //
    //                             printFlag = true;
    //                             btnPrtStart.setEnabled(false);
    //                             PrinterThread pr = new PrinterThread();
    //                             pr.start();
    //                         }
    //                     }
    //                 }
    //             }
    //         }
    //     });
    //
    //
    //     alertDialog = builder.create();
    //     alertDialog.show();
    // }
    //
    // void dialogview_prStop(){    ///프린터 ERROR 시에 팝업알림
    //     AlertDialog.Builder builder = new AlertDialog.Builder(this);
    //
    //     builder.setTitle("STOP").setMessage("Print Stop");
    //
    //     builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
    //         @Override
    //         public void onClick(DialogInterface dialog, int id)
    //         {
    //             jyNativeClass n = new jyNativeClass();
    //             n.jyPrinterOpen();
    //             //Toast.makeText(getApplicationContext(), "OK Click", Toast.LENGTH_SHORT).show();
    //         }
    //     });
    //
    //
    //
    //     alertDialog = builder.create();
    //     alertDialog.show();
    // }
}

