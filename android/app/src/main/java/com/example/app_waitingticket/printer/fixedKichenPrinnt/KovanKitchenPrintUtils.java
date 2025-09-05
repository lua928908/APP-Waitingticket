package com.example.app_waitingticket.printer.fixedKichenPrint;

import android.graphics.Bitmap;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

public class KovanKitchenPrintUtils {

    private static final String TAG = KovanKitchenPrintUtils.class.getSimpleName();

    /**
     * 프린트 초기화
     *
     * @return
     */
    @Keep
    private static StringBuilder PrintInitByString() {
        StringBuilder sb = new StringBuilder();
        sb.append((char) 0x1B);
        sb.append((char) 0x40);

        return sb;
    }

    @Keep
    public static byte[] PrintInit(){
        return KovanEncodingUtils.StringToByteArray(new String(PrintInitByString()));
    }

    /**
     * select Print Mode
     *
     * @param mode 0x00 : normal
     *              [Font - 0x01 (8 x 17)]
     *              [Bold - 0x08]
     *              [Double Height - 0x10]
     *              [Double Width - 0x20]
     *              [UnderLine - 0x80]
     * @return
     */
    @Keep
    private static StringBuilder PrintModeSetByString(char mode) {
        StringBuilder sb = new StringBuilder();
        sb.append((char) 0x1B);
        sb.append((char) 0x21);
        sb.append((char) mode);

        return sb;
    }

    @Keep
    private static StringBuilder PrintModeSetByString(KovanPrintDefine.PRINT_OPTION mode) {
        char c;

        if (mode == KovanPrintDefine.PRINT_OPTION.BOLD)
            c = (char) 0x08;
        else if (mode == KovanPrintDefine.PRINT_OPTION.DOUBLE_WIDTH)
            c = (char) 0x20;
        else if (mode == KovanPrintDefine.PRINT_OPTION.DOUBLE_HEIGHT)
            c = (char) 0x10;
        else if (mode == KovanPrintDefine.PRINT_OPTION.UNDER_LINE)
            c = (char) 0x80;
        else {
            c = (char) 0x00;
        }

        return PrintModeSetByString(c);
    }

    @Keep
    public static byte[] PrintModeSet(char mode){
        return KovanEncodingUtils.StringToByteArray(new String(PrintModeSet(mode)));
    }

    @Keep
    public static byte[] PrintModeSet(KovanPrintDefine.PRINT_OPTION mode){
        return KovanEncodingUtils.StringToByteArray(new String(PrintModeSet(mode)));
    }

    /**
     * 0x30 Left
     * 0x31 Mid
     * 0x32 Right
     *
     * @param sort
     * @return
     */
    @Keep
    public static StringBuilder PrintSortByString(char sort) {
        StringBuilder sb = new StringBuilder();

        sb.append((char) 0x1B);          //1B
        sb.append((char) 0x61);          //61
        sb.append((char) sort);         //30
        return sb;
    }

    @Keep
    public static StringBuilder PrintSortByString(KovanPrintDefine.PRINT_SORT sort) {
        char c;

        if (sort == KovanPrintDefine.PRINT_SORT.CENTER)
            c = (char) 0x31;
        else if (sort == KovanPrintDefine.PRINT_SORT.RIGHT)
            c = (char) 0x32;
        else
            c = (char) 0x30;

        return PrintSortByString(c);
    }

    @Keep
    public static byte[] PrintSort(char sort){
        return KovanEncodingUtils.StringToByteArray(new String(PrintSortByString(sort)));
    }

    @Keep
    public static byte[] PrintSort(KovanPrintDefine.PRINT_SORT sort){
        return KovanEncodingUtils.StringToByteArray(new String(PrintSortByString(sort)));
    }


    /**
     * CR
     *
     * @param
     * @return
     */
    @Keep
    private static StringBuilder PrintCRByString() {
        StringBuilder sb = new StringBuilder();
        sb.append((char) 0x0D);          //61
        return sb;
    }

    @Keep
    private static byte[] PrintCR() {
        return KovanEncodingUtils.StringToByteArray(new String(PrintCR()));
    }


    /**
     * CR + Line Feed
     *
     * @param n
     * @return
     */
    @Keep
    private static StringBuilder PrintCRLFByString(boolean isCr, int n) {
        StringBuilder sb = new StringBuilder();

        if (isCr) {
            sb.append((char) 0x0D);
        }

        for (int i = 0; i < n; i++)
            sb.append((char) 0x0A);

        return sb;
    }

    @Keep
    public static byte[] PrintCRLF(boolean isCr, int n) {
        return KovanEncodingUtils.StringToByteArray(new String(PrintCRLFByString(isCr,n)));
    }

    /**
     * 반복 출력
     *
     * @param c     ascii ex(0x30)
     * @param n
     * @return
     */
    @Keep
    private static StringBuilder PrintLoopByString(char c, int n) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < n; i++)
            sb.append(c);

        return sb;
    }

    @Keep
    public static byte[] PrintLoop(char c, int n) {
        return KovanEncodingUtils.StringToByteArray(new String(PrintLoop(c,n)));
    }

    @Keep
    private static StringBuilder PrintCutByString() {
        StringBuilder sb = new StringBuilder();
        //Feed Padding
        sb.append(PrintCRLFByString(true, 1));

        sb.append((char) 0x1B);
        sb.append((char) 0x69);

        return sb;
    }

    @Keep
    public static byte[] PrintCut() {
        return KovanEncodingUtils.StringToByteArray(new String(PrintCutByString()));
    }


    @Keep
    private static StringBuilder PrintPageRange() {
        StringBuilder sb = new StringBuilder();

        sb.append((char) 0x1D);
        sb.append((char) 0x57);
        sb.append((char) 0x00);
        sb.append((char) 0x00);

        return sb;
    }

    @Keep
    private static StringBuilder PrintLineByString(int lineLen, PrintLineVo vo) {
        StringBuilder sb = new StringBuilder();
        int length = 0;
        int idx = 0;
        //int maxLen = PreferenceUtils.getInteger(context, PrintDefine.FILE_DEVICE_INFO, PrintDefine.DEVICE_INFO.LEN.getKey(),42);

        byte[] pLine1, pLine2, pTemp;

        if (vo.getValue() == null) {
            vo.setValue("");
        }

        length = vo.getKey().getLength();
        length += KovanEncodingUtils.getByteLength(vo.getValue());

        if (vo.getOptions() != null) {
            for (KovanPrintDefine.PRINT_OPTION o : vo.getOptions()) {
                if (o == KovanPrintDefine.PRINT_OPTION.BOLD) {
                    sb.append(PrintModeSetByString((char) 0x08));
                } else if (o == KovanPrintDefine.PRINT_OPTION.DOUBLE_WIDTH) {
                    length *= 2;
                    sb.append(PrintModeSetByString((char) 0x20));
                } else if (o == KovanPrintDefine.PRINT_OPTION.DOUBLE_HEIGHT) {
                    sb.append(PrintModeSetByString((char) 0x10));
                } else if (o == KovanPrintDefine.PRINT_OPTION.UNDER_LINE) {
                    sb.append(PrintModeSetByString((char) 0x80));
                } else {
                    sb.append(PrintModeSetByString((char) 0x00));
                }
            }
        } else {
            sb.append(PrintModeSetByString((char) 0x00));
        }

        //키를 붙임
        if (vo.getKey() != KovanPrintDefine.PRINT_KEY.NONE) {
            sb.append(vo.getKey().getKey());
        }

        if (length > lineLen) {
            idx = 0;
            pTemp = KovanEncodingUtils.StringToByteArray(vo.getValue());
            pLine1 = new byte[lineLen - vo.getKey().getLength()];
            pLine2 = new byte[length - lineLen];
            System.arraycopy(pTemp, idx, pLine1, 0, pLine1.length);
            idx += pLine1.length;

            System.arraycopy(pTemp, idx, pLine2, 0, pLine2.length);

            sb.append(KovanEncodingUtils.ByteArrayToString(pLine1));
            sb.append(PrintCRLFByString(true, 1));
            sb.append(KovanEncodingUtils.ByteArrayToString(pLine2));

            KovanEncodingUtils.clearArray(pLine1, pLine2, pTemp);
        } else {
            sb.append(vo.getValue());
        }
        sb.append(PrintModeSetByString(KovanPrintDefine.PRINT_OPTION.NORMAL));
        sb.append(PrintCRLFByString(true, 1));
        return sb;
    }

    @Keep
    private static StringBuilder PrintLineByString(int lineLen, PrintLineVo left, PrintLineVo right) {
        StringBuilder sb = new StringBuilder();
        int lLen = 0;
        int rLen = 0;
        int pLen = 0;
        int idx = 0;
        byte[] pLine1, pLine2, pTemp;
        //int lineLen = PreferenceUtils.getInteger(context, PrintDefine.FILE_DEVICE_INFO, PrintDefine.DEVICE_INFO.LEN.getKey(),42);

        //왼쪽 길이 Set
        if (left.getValue() == null) {
            left.setValue("");
        }

        lLen = left.getKey().getLength();
        lLen += KovanEncodingUtils.getByteLength(left.getValue());

        if (left.getOptions() != null) {
            for (KovanPrintDefine.PRINT_OPTION o : left.getOptions()) {
                if (o == KovanPrintDefine.PRINT_OPTION.DOUBLE_WIDTH) {
                    lLen *= 2;
                }
            }
        }

        //왼쪽 옵션 Set
        if (left.getOptions() != null) {
            for (KovanPrintDefine.PRINT_OPTION o : left.getOptions()) {
                if (o == KovanPrintDefine.PRINT_OPTION.BOLD) {
                    sb.append(PrintModeSetByString((char) 0x08));
                } else if (o == KovanPrintDefine.PRINT_OPTION.DOUBLE_WIDTH) {
                    sb.append(PrintModeSetByString((char) 0x20));
                } else if (o == KovanPrintDefine.PRINT_OPTION.DOUBLE_HEIGHT) {
                    sb.append(PrintModeSetByString((char) 0x10));
                } else if (o == KovanPrintDefine.PRINT_OPTION.UNDER_LINE) {
                    sb.append(PrintModeSetByString((char) 0x80));
                } else {
                    sb.append(PrintModeSetByString((char) 0x00));
                }
            }
        } else {
            sb.append(PrintModeSetByString((char) 0x00));
        }

        //키를 붙임
        if (left.getKey() != KovanPrintDefine.PRINT_KEY.NONE) {
            sb.append(left.getKey().getKey());
        }

        //텍스트를 붙임
        if (lLen > lineLen) {
            idx = 0;
            pTemp = KovanEncodingUtils.StringToByteArray(left.getValue());
            pLine1 = new byte[lineLen - left.getKey().getLength()];
            pLine2 = new byte[lLen - lineLen];
            System.arraycopy(pTemp, idx, pLine1, 0, pLine1.length);
            idx += pLine1.length;

            System.arraycopy(pTemp, idx, pLine2, 0, pLine2.length);

            sb.append(KovanEncodingUtils.ByteArrayToString(pLine1));
            sb.append(PrintCRLFByString(true, 1));
            sb.append(KovanEncodingUtils.ByteArrayToString(pLine2));

            KovanEncodingUtils.clearArray(pLine1, pLine2, pTemp);

            //바뀐 줄의 길이
            lLen = lLen - lineLen;
        } else {
            sb.append(left.getValue());
        }

        //옵션 초기화
        sb.append(PrintModeSetByString(KovanPrintDefine.PRINT_OPTION.NORMAL));

        if (right.getValue() == null) {
            right.setValue("");
        }
        //오른쪽 길이 Set
        rLen = right.getKey().getLength();
        rLen += KovanEncodingUtils.getByteLength(right.getValue());

        if (right.getOptions() != null) {
            for (KovanPrintDefine.PRINT_OPTION o : right.getOptions()) {
                if (o == KovanPrintDefine.PRINT_OPTION.DOUBLE_WIDTH) {
                    rLen *= 2;
                }
            }
        }
        //둘이 합친 길이가 MAX 넘은 경우 오른쪽 TEXT는 아랫줄에 표기
        if (lLen + rLen > lineLen) {
            sb.append(PrintCRLFByString(true, 1));
            lLen = 0;
        }
        //아니면 Padding Space
        else {
            pLen = lineLen - (lLen + rLen);
            sb.append(PrintLoopByString((char) 0x20, pLen));
        }


        //오른쪽 옵션 Set
        if (right.getOptions() != null) {
            for (KovanPrintDefine.PRINT_OPTION o : right.getOptions()) {
                if (o == KovanPrintDefine.PRINT_OPTION.BOLD) {
                    sb.append(PrintModeSetByString((char) 0x08));
                } else if (o == KovanPrintDefine.PRINT_OPTION.DOUBLE_WIDTH) {
                    sb.append(PrintModeSetByString((char) 0x20));
                } else if (o == KovanPrintDefine.PRINT_OPTION.DOUBLE_HEIGHT) {
                    sb.append(PrintModeSetByString((char) 0x10));
                } else if (o == KovanPrintDefine.PRINT_OPTION.UNDER_LINE) {
                    sb.append(PrintModeSetByString((char) 0x80));
                } else {
                    sb.append(PrintModeSetByString((char) 0x00));
                }
            }
        } else {
            sb.append(PrintModeSetByString((char) 0x00));
        }

        //키를 붙임
        if (right.getKey() != KovanPrintDefine.PRINT_KEY.NONE) {
            sb.append(right.getKey().getKey());
        }

        //텍스트를 붙임
        if (rLen > lineLen) {
            idx = 0;
            pTemp = KovanEncodingUtils.StringToByteArray(right.getValue());
            pLine1 = new byte[lineLen - right.getKey().getLength()];
            pLine2 = new byte[rLen - lineLen];
            System.arraycopy(pTemp, idx, pLine1, 0, pLine1.length);
            idx += pLine1.length;

            System.arraycopy(pTemp, idx, pLine2, 0, pLine2.length);

            sb.append(KovanEncodingUtils.ByteArrayToString(pLine1));
            sb.append(PrintCRLFByString(true, 1));
            sb.append(KovanEncodingUtils.ByteArrayToString(pLine2));

            KovanEncodingUtils.clearArray(pLine1, pLine2, pTemp);
        } else {
            sb.append(right.getValue());
        }

        sb.append(PrintModeSetByString(KovanPrintDefine.PRINT_OPTION.NORMAL));
        sb.append(PrintCRLFByString(true, 1));

        return sb;
    }
    @Keep
    private static StringBuilder PrintItemLineByString(int lineLen, PrintLineVo left, PrintLineVo right) {
        StringBuilder sb = new StringBuilder();
        int lLen = 0;
        int rLen = 0;
        int pLen = 0;
        int idx = 0;
        byte[] pLine1, pLine2, pTemp;
        //int lineLen = PreferenceUtils.getInteger(context, PrintDefine.FILE_DEVICE_INFO, PrintDefine.DEVICE_INFO.LEN.getKey(),42);

        //왼쪽 길이 Set
        if (left.getValue() == null) {
            left.setValue("");
        }

        lLen = left.getKey().getLength();
        lLen += KovanEncodingUtils.getByteLength(left.getValue());

        if (left.getOptions() != null) {
            for (KovanPrintDefine.PRINT_OPTION o : left.getOptions()) {
                if (o == KovanPrintDefine.PRINT_OPTION.DOUBLE_WIDTH) {
                    lLen *= 2;
                }
            }
        }

        //왼쪽 옵션 Set
        if (left.getOptions() != null) {
            for (KovanPrintDefine.PRINT_OPTION o : left.getOptions()) {
                if (o == KovanPrintDefine.PRINT_OPTION.BOLD) {
                    sb.append(PrintModeSetByString((char) 0x08));
                } else if (o == KovanPrintDefine.PRINT_OPTION.DOUBLE_WIDTH) {
                    sb.append(PrintModeSetByString((char) 0x20));
                } else if (o == KovanPrintDefine.PRINT_OPTION.DOUBLE_HEIGHT) {
                    sb.append(PrintModeSetByString((char) 0x10));
                } else if (o == KovanPrintDefine.PRINT_OPTION.UNDER_LINE) {
                    sb.append(PrintModeSetByString((char) 0x80));
                } else {
                    sb.append(PrintModeSetByString((char) 0x00));
                }
            }
        } else {
            sb.append(PrintModeSetByString((char) 0x00));
        }

        //키를 붙임
        if (left.getKey() != KovanPrintDefine.PRINT_KEY.NONE) {
            sb.append(left.getKey().getKey());
        }

        //텍스트를 붙임
        if (lLen > lineLen) {
            idx = 0;
            pTemp = KovanEncodingUtils.StringToByteArray(left.getValue());
            pLine1 = new byte[lineLen - left.getKey().getLength()];
            pLine2 = new byte[lLen - lineLen];
            System.arraycopy(pTemp, idx, pLine1, 0, pLine1.length);
            idx += pLine1.length;

            System.arraycopy(pTemp, idx, pLine2, 0, pLine2.length);

            sb.append(KovanEncodingUtils.ByteArrayToString(pLine1));
            sb.append(PrintCRLFByString(true, 1));
            sb.append(KovanEncodingUtils.ByteArrayToString(pLine2));

            KovanEncodingUtils.clearArray(pLine1, pLine2, pTemp);

            //바뀐 줄의 길이
            lLen = lLen - lineLen;
        } else {
            sb.append(left.getValue());
        }

        //옵션 초기화
        sb.append(PrintModeSetByString(KovanPrintDefine.PRINT_OPTION.NORMAL));

        if (right.getValue() == null) {
            right.setValue("");
        }
        //오른쪽 길이 Set
        rLen = right.getKey().getLength();
        rLen += KovanEncodingUtils.getByteLength(right.getValue());

        if (right.getOptions() != null) {
            for (KovanPrintDefine.PRINT_OPTION o : right.getOptions()) {
                if (o == KovanPrintDefine.PRINT_OPTION.DOUBLE_WIDTH) {
                    rLen *= 2;
                }
            }
        }
        //둘이 합친 길이가 MAX 넘은 경우 오른쪽 TEXT는 아랫줄에 표기
        if (lLen + rLen > lineLen) {
            sb.append(PrintCRLFByString(true, 1));
            lLen = 0;
        }
        //아니면 Padding Space
        else {
            pLen = lineLen - (lLen + rLen);
            sb.append(PrintLoopByString((char) 0x20, pLen));
        }


        //오른쪽 옵션 Set
        if (right.getOptions() != null) {
            for (KovanPrintDefine.PRINT_OPTION o : right.getOptions()) {
                if (o == KovanPrintDefine.PRINT_OPTION.BOLD) {
                    sb.append(PrintModeSetByString((char) 0x08));
                } else if (o == KovanPrintDefine.PRINT_OPTION.DOUBLE_WIDTH) {
                    sb.append(PrintModeSetByString((char) 0x20));
                } else if (o == KovanPrintDefine.PRINT_OPTION.DOUBLE_HEIGHT) {
                    sb.append(PrintModeSetByString((char) 0x10));
                } else if (o == KovanPrintDefine.PRINT_OPTION.UNDER_LINE) {
                    sb.append(PrintModeSetByString((char) 0x80));
                } else {
                    sb.append(PrintModeSetByString((char) 0x00));
                }
            }
        } else {
            sb.append(PrintModeSetByString((char) 0x00));
        }

        //키를 붙임
        if (right.getKey() != KovanPrintDefine.PRINT_KEY.NONE) {
            sb.append(right.getKey().getKey());
        }

        //텍스트를 붙임
        if (rLen > lineLen) {
            idx = 0;
            pTemp = KovanEncodingUtils.StringToByteArray(right.getValue());
            pLine1 = new byte[lineLen - right.getKey().getLength()];
            pLine2 = new byte[rLen - lineLen];
            System.arraycopy(pTemp, idx, pLine1, 0, pLine1.length);
            idx += pLine1.length;

            System.arraycopy(pTemp, idx, pLine2, 0, pLine2.length);

            sb.append(KovanEncodingUtils.ByteArrayToString(pLine1));
            sb.append(PrintCRLFByString(true, 1));
            sb.append(KovanEncodingUtils.ByteArrayToString(pLine2));

            KovanEncodingUtils.clearArray(pLine1, pLine2, pTemp);
        } else {
            sb.append(right.getValue());
        }

        sb.append(PrintModeSetByString(KovanPrintDefine.PRINT_OPTION.NORMAL));
        sb.append(PrintCRLFByString(true, 1));

        return sb;
    }


    @Keep
    private static byte[] PrintLine(int lineLen, PrintLineVo vo){
        return KovanEncodingUtils.StringToByteArray(new String(PrintLineByString(lineLen,vo)));
    }

    @Keep
    private static byte[] PrintLine(int lineLen, PrintLineVo left, PrintLineVo right){
        return KovanEncodingUtils.StringToByteArray(new String(PrintLineByString(lineLen,left,right)));
    }

    @Keep
    public static byte[] PrintReceipt(KovanPrintDefine.MAX_LINE_LEN len, @NonNull KovanKitchenReceiptVo vo) {
        return PrintReceipt(len.getLength(), vo, false);
    }

    @Keep
    public static byte[] PrintReceipt(int len, @NonNull KovanKitchenReceiptVo vo) {
        return PrintReceipt(len, vo, false);
    }

    @Keep
    public static byte[] PrintReceipt_HandSign(KovanPrintDefine.MAX_LINE_LEN len, @NonNull KovanKitchenReceiptVo vo) {
        return PrintReceipt(len.getLength(), vo, true);
    }

    @Keep
    public static byte[] PrintReceipt_HandSign(int len, @NonNull KovanKitchenReceiptVo vo) {
        return PrintReceipt(len, vo, true);
    }

    @Keep
    private static byte[] PrintReceipt(int lineLen, @NonNull KovanKitchenReceiptVo vo, boolean isHandSign) {
        StringBuilder Msg = new StringBuilder();
        byte[] etc = null;
        byte[] sign = null;
        byte[] data = null;
        byte[] cut = KovanEncodingUtils.mergeArrays(KovanEncodingUtils.StringToByteArray(PrintCRLFByString(true, 3).toString()), KovanEncodingUtils.StringToByteArray(PrintCutByString().toString()));

        ArrayList<KovanPrintDefine.PRINT_OPTION> L_Option = new ArrayList<>();
        ArrayList<KovanPrintDefine.PRINT_OPTION> R_Option = new ArrayList<>();
        PrintLineVo L_PrintVo;
        PrintLineVo R_PrintVo;

        Msg.append(PrintInitByString());

        Msg.append(PrintSortByString(KovanPrintDefine.PRINT_SORT.LEFT));

        //POS 번호
        Msg.append(PrintLineByString(lineLen, new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.POSID, vo.getPosID())));

        // 상품수령 + 주문번호
        L_PrintVo = new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.TABLENUMBER, vo.geTableNumber());
        R_PrintVo = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.ORDERNUMBER, vo.getOrderNumber());

        Msg.append(PrintLineByString(lineLen, L_PrintVo, R_PrintVo));

        //Line
        Msg.append(PrintLoopByString((char) 0x2D, lineLen));
        Msg.append(PrintCRLFByString(true, 1));

        // 상품명 + 수량
        L_PrintVo = new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.TITLEMENU, null);
        R_PrintVo = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.TITLEMENUNUM, null);

        Msg.append(PrintLineByString(lineLen, L_PrintVo, R_PrintVo));
        //Line
        Msg.append(PrintLoopByString((char) 0x2D, lineLen));
        Msg.append(PrintCRLFByString(true, 1));
        // 상품 리스트
        JSONArray list = vo.getMenuList();
        //List<JSONObject> list = vo.getMenuList();
        int list_count =  Integer.parseInt(vo.getMenuListCount());
        String FIXKiMenuName = "";
        String FIXKiMenuCount = "";
        int FIXKiMenuOptListCount = 0;
        for( int i = 0; i < list_count;i++){
            JSONObject byFIXKiMenuList = list.optJSONObject(i);
            FIXKiMenuName = Integer.toString(i + 1 ) + ")" + byFIXKiMenuList.optString("byFIXKiMenuName");
            FIXKiMenuCount = byFIXKiMenuList.optString("byFIXKiMenuCount");
            if(FIXKiMenuCount == null ){
                FIXKiMenuCount = "00";
            }

            L_Option.add(com.example.app_waitingticket.printer.fixedKichenPrint.KovanPrintDefine.PRINT_OPTION.BOLD);

            L_PrintVo = new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.NONE, FIXKiMenuName);
            R_PrintVo = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, FIXKiMenuCount);
            Msg.append(PrintLineByString(lineLen, L_PrintVo, R_PrintVo));
            L_Option.clear();

            FIXKiMenuOptListCount = Integer.parseInt( byFIXKiMenuList.optString("byFIXKiMenuOptListCount") );
            JSONArray FIXKIMenuOptList = byFIXKiMenuList.optJSONArray("byFIXKIMenuOptList");
            // 옵션 리스트
            for( int k=0;k < FIXKiMenuOptListCount ;k++){
                String FIXKiMenuOptName = "";
                String FIXKiMenuOptCount = "";

                JSONObject  byFIXKIMenuOptList = FIXKIMenuOptList.optJSONObject(k);
                FIXKiMenuOptName = byFIXKIMenuOptList.optString("byFIXKiMenuOptName");
                FIXKiMenuOptCount = byFIXKIMenuOptList.optString("byFIXKiMenuOptCount");
                L_PrintVo = new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.MINUS, FIXKiMenuOptName);
                R_PrintVo = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, FIXKiMenuOptCount);
                Msg.append(PrintLineByString(lineLen, L_PrintVo, R_PrintVo));
            }
        }
        //Line
        Msg.append(PrintLoopByString((char) 0x2D, lineLen));
        Msg.append(PrintCRLFByString(true, 1));

        // 주문일자 + 주문시간
        String tranDateTime = vo.getOrderDate() + vo.getOrderTime();
        String authDt = "";
        try {
            SimpleDateFormat orgDateFormat = new SimpleDateFormat("yyMMddHHmmss");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yy.MM.dd HH:mm:ss");
            authDt = dateFormat.format(orgDateFormat.parse(tranDateTime));
        } catch (Exception e) {

        }
        Msg.append(PrintLineByString(lineLen, new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.ORDERDATE, authDt)));

        if (vo.getCustomerMsg()!= null && !vo.getCustomerMsg().equals("") && !vo.getCustomerMsg().isEmpty()) {
            Msg.append(PrintLineByString(lineLen, new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.CUSTOMERMSG, vo.getCustomerMsg())));
        }

        data = KovanEncodingUtils.mergeArrays(KovanEncodingUtils.StringToByteArray(new String(Msg)), cut);

        Log.e(TAG, "[Receipt]\n" + KovanEncodingUtils.ByteArrayToString(data));

        return data;
    }


    @Keep
    public static byte[] PrintTestForm(KovanPrintDefine.MAX_LINE_LEN lineLen) {
        return PrintTestForm(lineLen.getLength());
    }

    @Keep
    public static byte[] PrintTestForm(int lineLen) {
        StringBuilder Msg = new StringBuilder();

        ArrayList<KovanPrintDefine.PRINT_OPTION> L_Option = new ArrayList<>();
        ArrayList<KovanPrintDefine.PRINT_OPTION> R_Option = new ArrayList<>();
        PrintLineVo L_PrintVo;
        PrintLineVo R_PrintVo;

        Msg.append(PrintSortByString(KovanPrintDefine.PRINT_SORT.CENTER));

        L_Option.add(KovanPrintDefine.PRINT_OPTION.BOLD);
        L_Option.add(KovanPrintDefine.PRINT_OPTION.DOUBLE_HEIGHT);
        Msg.append(PrintLineByString(lineLen, new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.NONE, "[KOVAN TEST PRINT]")));

        L_Option.clear();

        Msg.append(PrintSortByString(KovanPrintDefine.PRINT_SORT.LEFT));

        L_PrintVo = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, "KovanPrintLib");
        R_PrintVo = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, "[VERSION] ");

        Msg.append(PrintLineByString(lineLen, L_PrintVo, R_PrintVo));

        L_Option.clear();
        R_Option.clear();

        //Line
        Msg.append(PrintLoopByString((char) 0x2D, lineLen));
        Msg.append(PrintCRLFByString(true, 1));

        L_PrintVo = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, "|   NORMAL LEFT");
        R_PrintVo = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, "NORMAL RIGHT  |");
        Msg.append(PrintLineByString(lineLen, L_PrintVo, R_PrintVo));

        L_Option.add(KovanPrintDefine.PRINT_OPTION.BOLD);
        R_Option.add(KovanPrintDefine.PRINT_OPTION.BOLD);
        L_PrintVo = new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.NONE, "|   BOLD LEFT");
        R_PrintVo = new PrintLineVo(R_Option, KovanPrintDefine.PRINT_KEY.NONE, "BOLD RIGHT  |");
        Msg.append(PrintLineByString(lineLen, L_PrintVo, R_PrintVo));
        L_Option.clear();
        R_Option.clear();
        Msg.append(PrintLoopByString((char) 0x2D, lineLen));
        Msg.append(PrintCRLFByString(true, 1));

        Msg.append(PrintSortByString(KovanPrintDefine.PRINT_SORT.CENTER));
        L_Option.add(KovanPrintDefine.PRINT_OPTION.DOUBLE_HEIGHT);
        Msg.append(PrintLineByString(lineLen, new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.NONE, "DOUBLE_HEIGHT")));
        L_Option.clear();
        R_Option.clear();

        Msg.append(PrintLoopByString((char) 0x2D, lineLen));
        Msg.append(PrintCRLFByString(true, 1));

        Msg.append(PrintSortByString(KovanPrintDefine.PRINT_SORT.CENTER));
        L_Option.add(KovanPrintDefine.PRINT_OPTION.DOUBLE_WIDTH);
        Msg.append(PrintLineByString(lineLen, new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.NONE, "DOUBLE_WIDTH")));
        L_Option.clear();
        R_Option.clear();

        Msg.append(PrintSortByString(KovanPrintDefine.PRINT_SORT.LEFT));
        Msg.append(PrintLoopByString((char) 0x2D, lineLen));
        Msg.append(PrintCRLFByString(true, 1));

        Msg.append(PrintLineByString(lineLen, new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.NONE, "[Line] " + lineLen)));
        Msg.append(PrintLoopByString((char) 0x2D, lineLen));
        Msg.append(PrintCRLFByString(true, 1));

        Msg.append(PrintLineByString(lineLen, new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.NONE, "테스트 출력 입니다")));
        Msg.append(PrintLoopByString((char) 0x2D, lineLen));

        Msg.append(PrintCRLFByString(true, 3));
        Msg.append(PrintCutByString());

        return KovanEncodingUtils.StringToByteArray(Msg.toString());
    }

    @Keep
    public static byte[] PrintBmp(@NonNull byte[] bmp) {
        Bitmap bitmap = KovanEncodingUtils.ByteArrayToBitmap(bmp);

        byte[] cmd = null;

        if (bitmap != null) {
            try {
                if (!KovanBmpUtils.isReceiptSize(bitmap)) {
                    bitmap = KovanBmpUtils.reSizeBitmap(bitmap);
                }
                cmd = KovanBmpUtils.getBmpEpson(bitmap);
            } catch (KovanPrintException e) {
                e.printStackTrace();
            }
        }

        if (cmd != null) {
            Log.e(TAG, "[TEMP] \n" + KovanEncodingUtils.ByteArrayToHexString(cmd));
        }
        return cmd;
    }

    @Keep
    public static byte[] PrintBmp(@NonNull Bitmap bmp) {
        Bitmap bitmap = bmp;

        byte[] cmd = null;

        if (bitmap != null) {
            try {
                if (!KovanBmpUtils.isReceiptSize(bitmap)) {
                    bitmap = KovanBmpUtils.reSizeBitmap(bitmap);
                }
                cmd = KovanBmpUtils.getBmpEpson(bitmap);
            } catch (KovanPrintException e) {
                e.printStackTrace();
            }
        }

        if (cmd != null) {
            Log.e(TAG, "[TEMP] \n" + KovanEncodingUtils.ByteArrayToHexString(cmd));
        }
        return cmd;
    }

    @Keep
    public static byte[] PrintBmp(@NonNull byte[] bmp, @NonNull KovanPrintDefine.PRINT_SORT sort, @NonNull KovanPrintDefine.BMP_OPTION mode) {
        Bitmap bitmap = KovanEncodingUtils.ByteArrayToBitmap(bmp);

        byte[] cmd = null;

        if (bitmap != null) {
            try {
                if (!KovanBmpUtils.isReceiptSize(bitmap)) {
                    bitmap = KovanBmpUtils.reSizeBitmap(bitmap);
                }
                cmd = KovanBmpUtils.getBmpEpson(bitmap, sort, mode);
            } catch (KovanPrintException e) {
                e.printStackTrace();
            }
        }

        if (cmd != null) {
            Log.e(TAG, "[TEMP] \n" + KovanEncodingUtils.ByteArrayToHexString(cmd));
        }
        return cmd;
    }

    @Keep
    public static byte[] PrintBmp(@NonNull Bitmap bmp, @NonNull KovanPrintDefine.PRINT_SORT sort, @NonNull KovanPrintDefine.BMP_OPTION option) {
        Bitmap bitmap = bmp;

        byte[] cmd = null;

        if (bitmap != null) {
            try {
                if (!KovanBmpUtils.isReceiptSize(bitmap)) {
                    bitmap = KovanBmpUtils.reSizeBitmap(bitmap);
                }
                cmd = KovanBmpUtils.getBmpEpson(bitmap, sort, option);
            } catch (KovanPrintException e) {
                e.printStackTrace();
            }
        }

        if (cmd != null) {
            Log.e(TAG, "[TEMP] \n" + KovanEncodingUtils.ByteArrayToHexString(cmd));
        }
        return cmd;
    }

}
