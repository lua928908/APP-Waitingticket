package com.example.app_waitingticket.printer.fixedPrint;

import android.graphics.Bitmap;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

public class KovanPrintUtils {
    private static final String TAG = KovanPrintUtils.class.getSimpleName();

    /**
     * 프린트 테스트 양식
     *
     * @return
     */
    public static KovanReceiptVo PrintTextDocument() {
        KovanReceiptBuilder kovanReceiptBuilder = new KovanReceiptBuilder();
        kovanReceiptBuilder.setUserType(KovanPrintDefine.USER_TYPE.CUSTOMER)
                .setAuthType(KovanPrintDefine.AUTH_TYPE.CANCEL_CREDIT)
                .setTid("1234567890")
                .setBizNo("213-81-40742")
                .setBizName("코밴 단말기 검증용 가맹점")
                .setOwnerName("코밴")
                .setBizTel("070-1234-5678")
                .setBizAddr("서울시 강남구 도곡로 6길 10")
                .setStoreNo("123456789012")
                .setOrgDt("22.04.30 00:00:00")
                .setPurchase("신한카드").setIssuer("신한신용카드").setInstall("일시불")
                .setCardNo("123456**********")
                .setAuthDt("22.05.01 17:17:17")
                .setAuthNo("123456789012")
                .setStoreNo("123456789012")
                .setSerialNo("000001      ")
                .setAmount("999,999,999원")
                .setTax("88,888,888원")
                .setFee("666,666원")
                .setsFee("7,777,777원")
                .setTotalAmount("333,333,333원")
                .setPrintMsg1("프린트메시지1")
                .setPrintMsg2("프린트메시지2")
                .setPrintMsg3("프린트메시지3");
        KovanReceiptVo kovanReceiptVo = kovanReceiptBuilder.build();

        return kovanReceiptVo;
    }

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
        String keyValue = "";
        if (vo.getKey() != KovanPrintDefine.PRINT_KEY.NONE) {
            sb.append(vo.getKey().getKey());
            keyValue = vo.getKey().getKey();
        }

        String printValue = vo.getValue();
        if (length > lineLen) {
            String tempValue = keyValue + printValue;
            while(true) {
                byte[] tempArray = KovanEncodingUtils.StringToByteArray(tempValue);
                if(tempArray.length > lineLen) {
                    tempValue = tempValue.substring(0, tempValue.length() - 1);
                } else {
                    break;
                }
            }
            idx = 0;
            String printLine1String = tempValue.substring(keyValue.length());
            String printLine2String = printValue.substring(tempValue.length());
            pLine1 = KovanEncodingUtils.StringToByteArray(printLine1String);
            pLine2 = KovanEncodingUtils.StringToByteArray(printLine2String);

//            pTemp = KovanEncodingUtils.StringToByteArray(vo.getValue());
//            pLine1 = new byte[lineLen - vo.getKey().getLength()];
//            pLine2 = new byte[length - lineLen];
//            System.arraycopy(pTemp, idx, pLine1, 0, pLine1.length);
//            idx += pLine1.length;
//
//            System.arraycopy(pTemp, idx, pLine2, 0, pLine2.length);

            sb.append(KovanEncodingUtils.ByteArrayToString(pLine1));
            sb.append(PrintCRLFByString(true, 1));
            sb.append(KovanEncodingUtils.ByteArrayToString(pLine2));

//            KovanEncodingUtils.clearArray(pLine1, pLine2, pTemp);
            KovanEncodingUtils.clearArray(pLine1, pLine2);
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
    private static byte[] PrintLine(int lineLen, PrintLineVo vo){
        return KovanEncodingUtils.StringToByteArray(new String(PrintLineByString(lineLen,vo)));
    }

    @Keep
    private static byte[] PrintLine(int lineLen, PrintLineVo left, PrintLineVo right){
        return KovanEncodingUtils.StringToByteArray(new String(PrintLineByString(lineLen,left,right)));
    }

    @Keep
    public static byte[] PrintReceipt(KovanPrintDefine.MAX_LINE_LEN len, @NonNull KovanReceiptVo vo) {
        return PrintReceipt(len.getLength(), vo, false);
    }

    @Keep
    public static byte[] PrintReceipt(int len, @NonNull KovanReceiptVo vo) {
        return PrintReceipt(len, vo, false);
    }

    @Keep
    public static byte[] PrintReceiptByZoa(int len, @NonNull KovanReceiptVo vo) {
        return PrintReceiptByZoa(len, vo, false);
    }

    @Keep
    public static byte[] PrintReceiptByC1it(int len, @NonNull KovanReceiptVo vo) {
        return PrintReceiptByC1it(len, vo, false);
    }

    @Keep
    public static byte[] PrintReceipt_HandSign(KovanPrintDefine.MAX_LINE_LEN len, @NonNull KovanReceiptVo vo) {
        return PrintReceipt(len.getLength(), vo, true);
    }

    @Keep
    public static byte[] PrintReceipt_HandSign(int len, @NonNull KovanReceiptVo vo) {
        return PrintReceipt(len, vo, true);
    }

    @Keep
    private static byte[] PrintReceipt(int lineLen, @NonNull KovanReceiptVo vo, boolean isHandSign){
        StringBuilder Msg = new StringBuilder();
        byte[] etc = null;
        byte[] sign = null;
        byte[] data = null;
        byte[] cut = KovanEncodingUtils.mergeArrays(KovanEncodingUtils.StringToByteArray(PrintCRLFByString(true, 3).toString()), KovanEncodingUtils.StringToByteArray(PrintCutByString().toString()));
        byte[] cashDrawer = KovanEncodingUtils.mergeArrays(KovanEncodingUtils.StringToByteArray(PrintCashDrawerGeneral().toString()));

        ArrayList<KovanPrintDefine.PRINT_OPTION> L_Option = new ArrayList<>();
        ArrayList<KovanPrintDefine.PRINT_OPTION> R_Option = new ArrayList<>();
        PrintLineVo L_PrintVo;
        PrintLineVo R_PrintVo;

        Msg.append(PrintInitByString());

        //고객사용 Header 부분
        if (vo.getUserType() == KovanPrintDefine.USER_TYPE.CUSTOMER) {
            Msg.append(PrintSortByString(KovanPrintDefine.PRINT_SORT.CENTER));
            L_Option.add(KovanPrintDefine.PRINT_OPTION.BOLD);
            Msg.append(PrintLineByString(lineLen, new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.NONE, "가맹점명,주소가 실제와 다른경우 신고안내")));
            Msg.append(PrintSortByString(KovanPrintDefine.PRINT_SORT.CENTER));
            Msg.append(PrintLineByString(lineLen, new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.NONE, "여신금융협회 02-2011-0777포상금10만원지급")));
            Msg.append(PrintInitByString());
            L_Option.clear();
        }

        Msg.append(PrintSortByString(KovanPrintDefine.PRINT_SORT.LEFT));

        //거래 종류 + 전표 종류
        L_Option.add(KovanPrintDefine.PRINT_OPTION.BOLD);
        L_Option.add(KovanPrintDefine.PRINT_OPTION.DOUBLE_WIDTH);

        L_PrintVo = new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.NONE, vo.getAuthType().getMsg());
        R_PrintVo = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, vo.getUserType().getMsg());

        Msg.append(PrintLineByString(lineLen, L_PrintVo, R_PrintVo));

        L_Option.clear();
        R_Option.clear();

        //상호 명
        Msg.append(PrintLineByString(lineLen, new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.BIZNAME, vo.getBizName())));

        //대표자 명 + 전표 No
        if (vo.getSerialNo() != null && !vo.getSerialNo().equals("") && !vo.getSerialNo().isEmpty()) {
            L_PrintVo = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.OWNERNAME, vo.getOwnerName());
            R_PrintVo = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.SERIALNO, vo.getSerialNo());

            Msg.append(PrintLineByString(lineLen, L_PrintVo, R_PrintVo));
        } else {
            Msg.append(PrintLineByString(lineLen, new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.OWNERNAME, vo.getOwnerName())));
        }

        //사업자 번호 + TID
        L_PrintVo = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.BIZNO, vo.getBizNo());
        R_PrintVo = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.TID, vo.getTid());

        Msg.append(PrintLineByString(lineLen, L_PrintVo, R_PrintVo));

        //주소
        Msg.append(PrintLineByString(lineLen, new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, vo.getBizAddr())));

        //Line
        Msg.append(PrintLoopByString((char) 0x2D, lineLen));
        Msg.append(PrintCRLFByString(true, 1));
        //카드번호 + 할부
        if (vo.getAuthType() != KovanPrintDefine.AUTH_TYPE.AUTH_CASH_PRINT && vo.getAuthType() != KovanPrintDefine.AUTH_TYPE.CANCEL_CASH_PRINT) {
            L_PrintVo = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.CARDNO, vo.getCardNo());
            R_PrintVo = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, vo.getInstall());
            Msg.append(PrintLineByString(lineLen, L_PrintVo, R_PrintVo));
        }

        String dccFlag = "";
        if (vo.getStoreNo() != null && !vo.getStoreNo().equals("") && !vo.getStoreNo().isEmpty()) {
            //가맹점 번호 + DCC Flag
            L_PrintVo = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.STORE, vo.getStoreNo());
            R_PrintVo = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, vo.getDccFlag());
            Msg.append(PrintLineByString(lineLen, L_PrintVo, R_PrintVo));

        }

        //거래일시 + WCC Flag
        L_PrintVo = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.AUTHDT, vo.getAuthDt());
        if(vo.getWccType() != null && !vo.getWccType().getMsg().equals("") && !vo.getWccType().getMsg().isEmpty() ) {
            R_PrintVo = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, vo.getWccType().getMsg());
            Msg.append(PrintLineByString(lineLen, L_PrintVo, R_PrintVo));
        }else{
            Msg.append(PrintLineByString(lineLen, new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.AUTHDT, vo.getAuthDt())));
        }

        //원거래 일자
        if (vo.getOrgDt() != null && !vo.getOrgDt().equals("") && !vo.getOrgDt().isEmpty()) {
            Msg.append(PrintLineByString(lineLen, new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.ORGDT, vo.getOrgDt())));
        }

        if( vo.getMenuListCount() != null && !vo.getMenuListCount().equals("") && !vo.getMenuListCount().isEmpty() ){

            //for 처리 하면서
            // 상품 리스트

            //Line
            Msg.append(PrintLoopByString((char) 0x3D, lineLen));
            Msg.append(PrintCRLFByString(true, 1));
            L_PrintVo = new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.NONE, new String("메 뉴 명"));
            R_PrintVo = new PrintLineVo(R_Option, KovanPrintDefine.PRINT_KEY.NONE, new String("단가 수량       금액"));
            Msg.append(PrintLineByString(lineLen, L_PrintVo, R_PrintVo));
            Msg.append(PrintLoopByString((char) 0x3D, lineLen));
            Msg.append(PrintCRLFByString(true, 1));

            JSONArray list = vo.getMenuList();
            int list_count =  Integer.parseInt(vo.getMenuListCount());

            PrintLineVo itemName = null , price = null , quantity = null , amount = null;

            String FIXItemName = "";
            String FIXItemPrice = "";
            String FIXItemQuantity = "";
            String FIXItemAmount = "";
            String FIXItemAmountState = "";
            int FIXMenuOptListCount = 0;
            for( int i = 0; i < list_count;i++){
                JSONObject byFIXKiMenuList = list.optJSONObject(i);
                FIXItemName = byFIXKiMenuList.optString("byFIXMenuName");
                FIXItemPrice = byFIXKiMenuList.optString("byFIXMenuPrice");
                FIXItemQuantity = byFIXKiMenuList.optString("byFIXMenuCount");
                // 단가와 수량으로 합계를 구한다.
                FIXItemAmount = GetAdditionPriceAmount( FIXItemPrice , FIXItemQuantity);
                FIXItemAmountState = byFIXKiMenuList.optString("byFIXItemAmountState");

                itemName = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, new String(FIXItemName));
                price = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE,new String(UtilManager.ConvertCurrencyFormat(FIXItemPrice)));
                quantity = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, new String(FIXItemQuantity));
                amount = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, new String(UtilManager.ConvertCurrencyFormat(FIXItemAmount)));
                Msg.append(PrintItemLineByString(lineLen, itemName , price, quantity, amount) );

                String strFIXMenuOptListCount = byFIXKiMenuList.optString("byFIXMenuOptListCount");
                if( strFIXMenuOptListCount != null && !strFIXMenuOptListCount.equals("") && !strFIXMenuOptListCount.isEmpty() ) {
                    FIXMenuOptListCount = Integer.parseInt(byFIXKiMenuList.optString("byFIXMenuOptListCount"));
                    JSONArray FIXMenuOptList = byFIXKiMenuList.optJSONArray("byFIXMenuOptList");
                    // 옵션 리스트
                    for (int k = 0; k < FIXMenuOptListCount; k++) {
                        String FIXItemOptName = "";
                        String FIXItemOptPrice = "";
                        String FIXItemOptQuantity = "";
                        String FIXItemOptAmount = "";

                        JSONObject byFIXMenuOptList = FIXMenuOptList.optJSONObject(k);
                        FIXItemOptName = byFIXMenuOptList.optString("byFIXMenuOptName");
                        FIXItemOptQuantity = byFIXMenuOptList.optString("byFIXMenuOptCount");
                        FIXItemOptPrice = byFIXMenuOptList.optString("byFIXMenuOptPrice");
                        FIXItemOptAmount = GetAdditionPriceAmount( FIXItemOptPrice , FIXItemOptQuantity);
                        itemName = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.MINUS2, new String(FIXItemOptName));
                        price = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE,new String(UtilManager.ConvertCurrencyFormat(FIXItemOptPrice)));
                        quantity = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, new String(FIXItemOptQuantity));
                        amount = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, new String(UtilManager.ConvertCurrencyFormat(FIXItemOptAmount)));
                        Msg.append(PrintItemLineByString(lineLen, itemName, price , quantity,amount));
                    }
                }
            }

        }
        //Line
        Msg.append(PrintLoopByString((char) 0x2D, lineLen));
        Msg.append(PrintCRLFByString(true, 1));

        //거래금액
        if (vo.getAmount() != null && !vo.getAmount().equals("") && !vo.getAmount().isEmpty()) {
            L_PrintVo = new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.AMOUNT, null);
            R_PrintVo = new PrintLineVo(R_Option, KovanPrintDefine.PRINT_KEY.NONE, vo.getAmount());
            Msg.append(PrintLineByString(lineLen, L_PrintVo, R_PrintVo));
        }
        //세금
        if (vo.getTax() != null && !vo.getTax().equals("") && !vo.getTax().isEmpty()) {
            L_PrintVo = new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.TAX, null);
            R_PrintVo = new PrintLineVo(R_Option, KovanPrintDefine.PRINT_KEY.NONE, vo.getTax());
            Msg.append(PrintLineByString(lineLen, L_PrintVo, R_PrintVo));
        }
        //봉사료
        if (vo.getsFee() != null && !vo.getsFee().equals("") && !vo.getsFee().isEmpty()) {
            L_PrintVo = new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.SFEE, null);
            R_PrintVo = new PrintLineVo(R_Option, KovanPrintDefine.PRINT_KEY.NONE, vo.getsFee());
            Msg.append(PrintLineByString(lineLen, L_PrintVo, R_PrintVo));
        }
        //비과세
        if (vo.getFee() != null && !vo.getFee().equals("") && !vo.getFee().isEmpty()) {
            L_PrintVo = new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.FEE, null);
            R_PrintVo = new PrintLineVo(R_Option, KovanPrintDefine.PRINT_KEY.NONE, vo.getFee());
            Msg.append(PrintLineByString(lineLen, L_PrintVo, R_PrintVo));
        }
        //총합
        long totalAmount = 0;
        if (vo.getTotalAmount() != null && !vo.getTotalAmount().equals("") && !vo.getTotalAmount().isEmpty()) {
            L_Option.add(KovanPrintDefine.PRINT_OPTION.BOLD);
            L_Option.add(KovanPrintDefine.PRINT_OPTION.DOUBLE_HEIGHT);
            R_Option.add(KovanPrintDefine.PRINT_OPTION.BOLD);
            R_Option.add(KovanPrintDefine.PRINT_OPTION.DOUBLE_HEIGHT);
            L_PrintVo = new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.TOTALAMT, null);
            R_PrintVo = new PrintLineVo(R_Option, KovanPrintDefine.PRINT_KEY.NONE, vo.getTotalAmount());
            Msg.append(PrintLineByString(lineLen, L_PrintVo, R_PrintVo));
            try {
                totalAmount = Long.parseLong(vo.getTotalAmount().replace("원", "").replace(",", "").replace(" ", ""));
            } catch (Exception e) {

            }
        }

        L_Option.clear();
        R_Option.clear();

        //Line
        Msg.append(PrintLoopByString((char) 0x2D, lineLen));
        Msg.append(PrintCRLFByString(true, 1));
        //승인번호
        if (vo.getAuthNo() != null && !vo.getAuthNo().equals("") && !vo.getAuthNo().isEmpty()) {
            L_Option.add(KovanPrintDefine.PRINT_OPTION.BOLD);
            R_Option.add(KovanPrintDefine.PRINT_OPTION.BOLD);

            L_PrintVo = new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.AUTHNO, null);
            R_PrintVo = new PrintLineVo(R_Option, KovanPrintDefine.PRINT_KEY.NONE, vo.getAuthNo());
            Msg.append(PrintLineByString(lineLen, L_PrintVo, R_PrintVo));
        }

        L_Option.clear();
        R_Option.clear();

        if (vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.AUTH_CREDIT || vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.CANCEL_CREDIT ||
                vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.AUTH_CREDIT_SWIP || vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.CANCEL_CREDIT_SWIP ||
                vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.AUTH_SIMPLEPAY || vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.CANCEL_SIMPLEPAY) {
            L_PrintVo = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.PURCHASE, vo.getPurchase());
            R_PrintVo = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.ISSUER, vo.getIssuer());
            Msg.append(PrintLineByString(lineLen, L_PrintVo, R_PrintVo));

            if (vo.getUserType() == KovanPrintDefine.USER_TYPE.CUSTOMER) {
                Msg.append(PrintLineByString(lineLen, new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.BIZTEL, vo.getBizTel())));
            }

            if (vo.getPrintMsg1() != null && !vo.getPrintMsg1().equals("") && !vo.getPrintMsg1().isEmpty()) {
                Msg.append(PrintLineByString(lineLen, new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, vo.getPrintMsg1())));
            }

            if (vo.getPrintMsg2() != null && !vo.getPrintMsg2().equals("") && !vo.getPrintMsg2().isEmpty()) {
                Msg.append(PrintLineByString(lineLen, new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, vo.getPrintMsg2())));
            }

            if (vo.getPrintMsg3() != null && !vo.getPrintMsg3().equals("") && !vo.getPrintMsg3().isEmpty()) {
                Msg.append(PrintLineByString(lineLen, new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, vo.getPrintMsg3())));
            }
        }

        if (vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.AUTH_CASH || vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.CANCEL_CASH) {
            if (vo.getUserType() == KovanPrintDefine.USER_TYPE.CUSTOMER) {
                Msg.append(PrintLineByString(lineLen, new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.BIZTEL, vo.getBizTel())));
            }

            if (vo.getPrintMsg1() != null) {
                Msg.append(PrintLineByString(lineLen, new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, vo.getPrintMsg1())));
            }

            if (vo.getPrintMsg2() != null) {
                Msg.append(PrintLineByString(lineLen, new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, vo.getPrintMsg2())));
            }

            if (vo.getPrintMsg3() != null) {
                Msg.append(PrintLineByString(lineLen, new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, vo.getPrintMsg3())));
            }
        }

        if (vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.AUTH_CASH_PRINT || vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.CANCEL_CASH_PRINT) {
            if (vo.getUserType() == KovanPrintDefine.USER_TYPE.CUSTOMER) {
                //주소
                Msg.append(PrintLineByString(lineLen, new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, "본 전표는 현금(소득공제) 미적용 대상임")));

                //Line
                Msg.append(PrintLoopByString((char) 0x2D, lineLen));
                Msg.append(PrintCRLFByString(true, 1));
                //Msg.append(PrintLineByString(lineLen, new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.BIZTEL, vo.getBizTel())));
            }
        }

        if (vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.AUTH_CREDIT || vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.CANCEL_CREDIT ||
                vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.AUTH_CREDIT_SWIP || vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.CANCEL_CREDIT_SWIP ||
                vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.AUTH_SIMPLEPAY || vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.CANCEL_SIMPLEPAY) {
            if(!isHandSign) {
                if (vo.getSignData() != null && !vo.getSignData().equals("") && !vo.getSignData().isEmpty()) {
                    //Base 64 Decode
                    byte[] encSign = KovanEncodingUtils.getBase64DecodeByte(vo.getSignData());
                    Log.e(TAG, KovanEncodingUtils.ByteArrayToHexString(encSign));
                    if (encSign != null) {
                        //Byte array to Bitmap
                        Bitmap convBmp = KovanEncodingUtils.ByteArrayToBitmap(encSign);

                        if (convBmp != null) {
                            //LOG
                            KovanBmpUtils.bmpInfo(convBmp);

                            StringBuilder sbSignHead = new StringBuilder();
                            sbSignHead.append(PrintLoopByString((char) 0x2D, lineLen));
                            sbSignHead.append(PrintCRLFByString(true, 1));
                            sbSignHead.append(PrintLineByString(lineLen, new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.SIGN, "")));

                            encSign = PrintBmp(convBmp, KovanPrintDefine.PRINT_SORT.CENTER, KovanPrintDefine.BMP_OPTION.DOUBLE_WIDTH_HEIGHT);

                            sign = KovanEncodingUtils.mergeArrays(KovanEncodingUtils.StringToByteArray(sbSignHead.toString()), encSign);
                        }
                    }
                } else {
                    if(totalAmount > 50000 || totalAmount < -50000) {
                        StringBuilder sbSignHead = new StringBuilder();
                        sbSignHead.append(PrintLoopByString((char) 0x2D, lineLen));
                        sbSignHead.append(PrintCRLFByString(true, 1));
                        sbSignHead.append(PrintLineByString(lineLen, new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.SIGN, "")));
                        sbSignHead.append(PrintCRLFByString(true, 4));
                        sign = KovanEncodingUtils.StringToByteArray(sbSignHead.toString());
                    }
                }
            }
            else{
                StringBuilder sbSignHead = new StringBuilder();
                sbSignHead.append(PrintLoopByString((char) 0x2D, lineLen));
                sbSignHead.append(PrintCRLFByString(true, 1));
                sbSignHead.append(PrintLineByString(lineLen, new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.SIGN, "")));
                sbSignHead.append(PrintCRLFByString(true, 4));
                sign = KovanEncodingUtils.StringToByteArray(sbSignHead.toString());
            }
        }

        if (vo.getEtcMsg() != null && !vo.getEtcMsg().equals("") && !vo.getEtcMsg().isEmpty()) {
            StringBuilder sbEtc = new StringBuilder();
            sbEtc.append(PrintSortByString(KovanPrintDefine.PRINT_SORT.RIGHT));
            sbEtc.append(PrintLoopByString((char) 0x2D, lineLen));
            sbEtc.append(PrintLineByString(lineLen, new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.NONE, vo.getEtcMsg())));
            etc = KovanEncodingUtils.StringToByteArray(sbEtc.toString());
        }

        if (vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.AUTH_CASH_PRINT || vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.CANCEL_CASH_PRINT
        || vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.AUTH_CASH || vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.CANCEL_CASH) {
            if (vo.getUserType() == KovanPrintDefine.USER_TYPE.CUSTOMER) {
                data = KovanEncodingUtils.mergeArrays(KovanEncodingUtils.StringToByteArray(new String(Msg)), sign, etc, cut,cashDrawer);
            }else{
                data = KovanEncodingUtils.mergeArrays(KovanEncodingUtils.StringToByteArray(new String(Msg)), sign, etc, cut);
            }
        }else{
            data = KovanEncodingUtils.mergeArrays(KovanEncodingUtils.StringToByteArray(new String(Msg)), sign, etc, cut);
        }

        Log.e(TAG, "[Receipt]\n" + KovanEncodingUtils.ByteArrayToString(data));

        return data;
    }

    @Keep
    private static byte[] PrintReceiptByZoa(int lineLen, @NonNull KovanReceiptVo vo, boolean isHandSign) {
        StringBuilder Msg = new StringBuilder();
        byte[] data = null;

        ArrayList<KovanPrintDefine.PRINT_OPTION> L_Option = new ArrayList<>();
        ArrayList<KovanPrintDefine.PRINT_OPTION> R_Option = new ArrayList<>();
        PrintLineVo L_PrintVo;
        PrintLineVo R_PrintVo;

        Msg.append(PrintInitByString());

        //고객사용 Header 부분
        if (vo.getUserType() == KovanPrintDefine.USER_TYPE.CUSTOMER) {
            Msg.append(PrintSortByString(KovanPrintDefine.PRINT_SORT.CENTER));
            L_Option.add(KovanPrintDefine.PRINT_OPTION.BOLD);
            Msg.append(PrintLineByString(lineLen, new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.NONE, "가맹점명,주소가 실제와 다른경우 신고안내")));
            Msg.append(PrintSortByString(KovanPrintDefine.PRINT_SORT.CENTER));
            Msg.append(PrintLineByString(lineLen, new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.NONE, "여신금융협회 02-2011-0777포상금10만원지급")));
            Msg.append(PrintInitByString());
            L_Option.clear();
        }

        Msg.append(PrintSortByString(KovanPrintDefine.PRINT_SORT.LEFT));

        //거래 종류 + 전표 종류
        L_Option.add(KovanPrintDefine.PRINT_OPTION.BOLD);
        L_Option.add(KovanPrintDefine.PRINT_OPTION.DOUBLE_WIDTH);

        L_PrintVo = new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.NONE, vo.getAuthType().getMsg());
        R_PrintVo = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, vo.getUserType().getMsg());

        Msg.append(PrintLineByString(lineLen, L_PrintVo, R_PrintVo));

        L_Option.clear();
        R_Option.clear();

        //상호 명
        Msg.append(PrintLineByString(lineLen, new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.BIZNAME, vo.getBizName())));

        //대표자 명 + 전표 No
        if (vo.getSerialNo() != null && !vo.getSerialNo().equals("") && !vo.getSerialNo().isEmpty()) {
            L_PrintVo = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.OWNERNAME, vo.getOwnerName());
            R_PrintVo = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.SERIALNO, vo.getSerialNo());

            Msg.append(PrintLineByString(lineLen, L_PrintVo, R_PrintVo));
        } else {
            Msg.append(PrintLineByString(lineLen, new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.OWNERNAME, vo.getOwnerName())));
        }

        //사업자 번호 + TID
        L_PrintVo = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.BIZNO, vo.getBizNo());
        R_PrintVo = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.TID, vo.getTid());

        Msg.append(PrintLineByString(lineLen, L_PrintVo, R_PrintVo));

        //주소
        Msg.append(PrintLineByString(lineLen, new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, vo.getBizAddr())));

        //Line
        Msg.append(PrintLoopByString((char) 0x2D, lineLen));
        Msg.append(PrintCRLFByString(true, 1));
        //카드번호 + 할부
        if (vo.getAuthType() != KovanPrintDefine.AUTH_TYPE.AUTH_CASH_PRINT && vo.getAuthType() != KovanPrintDefine.AUTH_TYPE.CANCEL_CASH_PRINT) {
            L_PrintVo = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.CARDNO, vo.getCardNo());
            R_PrintVo = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, vo.getInstall());
            Msg.append(PrintLineByString(lineLen, L_PrintVo, R_PrintVo));
        }

        String dccFlag = "";
        if (vo.getStoreNo() != null && !vo.getStoreNo().equals("") && !vo.getStoreNo().isEmpty()) {
            //가맹점 번호 + DCC Flag
            L_PrintVo = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.STORE, vo.getStoreNo());
            R_PrintVo = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, vo.getDccFlag());
            Msg.append(PrintLineByString(lineLen, L_PrintVo, R_PrintVo));

        }

        //거래일시 + WCC Flag
        L_PrintVo = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.AUTHDT, vo.getAuthDt());
        if(vo.getWccType() != null && !vo.getWccType().getMsg().equals("") && !vo.getWccType().getMsg().isEmpty() ) {
            R_PrintVo = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, vo.getWccType().getMsg());
            Msg.append(PrintLineByString(lineLen, L_PrintVo, R_PrintVo));
        }else{
            Msg.append(PrintLineByString(lineLen, new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.AUTHDT, vo.getAuthDt())));
        }

        //원거래 일자
        if (vo.getOrgDt() != null && !vo.getOrgDt().equals("") && !vo.getOrgDt().isEmpty()) {
            Msg.append(PrintLineByString(lineLen, new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.ORGDT, vo.getOrgDt())));
        }

        if( vo.getMenuListCount() != null && !vo.getMenuListCount().equals("") && !vo.getMenuListCount().isEmpty() ){

            //for 처리 하면서
            // 상품 리스트

            //Line
            Msg.append(PrintLoopByString((char) 0x3D, lineLen));
            Msg.append(PrintCRLFByString(true, 1));
            L_PrintVo = new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.NONE, new String("메 뉴 명"));
            R_PrintVo = new PrintLineVo(R_Option, KovanPrintDefine.PRINT_KEY.NONE, new String("단가 수량       금액"));
            Msg.append(PrintLineByString(lineLen, L_PrintVo, R_PrintVo));
            Msg.append(PrintLoopByString((char) 0x3D, lineLen));
            Msg.append(PrintCRLFByString(true, 1));

            JSONArray list = vo.getMenuList();
            int list_count =  Integer.parseInt(vo.getMenuListCount());

            PrintLineVo itemName = null , price = null , quantity = null , amount = null;

            String FIXItemName = "";
            String FIXItemPrice = "";
            String FIXItemQuantity = "";
            String FIXItemAmount = "";
            String FIXItemAmountState = "";
            int FIXMenuOptListCount = 0;
            for( int i = 0; i < list_count;i++){
                JSONObject byFIXKiMenuList = list.optJSONObject(i);
                FIXItemName = byFIXKiMenuList.optString("byFIXMenuName");
                FIXItemPrice = byFIXKiMenuList.optString("byFIXMenuPrice");
                FIXItemQuantity = byFIXKiMenuList.optString("byFIXMenuCount");
                // 단가와 수량으로 합계를 구한다.
                FIXItemAmount = GetAdditionPriceAmount( FIXItemPrice , FIXItemQuantity);
                FIXItemAmountState = byFIXKiMenuList.optString("byFIXItemAmountState");

                itemName = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, new String(FIXItemName));
                price = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE,new String(UtilManager.ConvertCurrencyFormat(FIXItemPrice)));
                quantity = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, new String(FIXItemQuantity));
                amount = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, new String(UtilManager.ConvertCurrencyFormat(FIXItemAmount)));
                Msg.append(PrintItemLineByString(lineLen, itemName , price, quantity, amount) );

                String strFIXMenuOptListCount = byFIXKiMenuList.optString("byFIXMenuOptListCount");
                if( strFIXMenuOptListCount != null && !strFIXMenuOptListCount.equals("") && !strFIXMenuOptListCount.isEmpty() ) {
                    FIXMenuOptListCount = Integer.parseInt(byFIXKiMenuList.optString("byFIXMenuOptListCount"));
                    JSONArray FIXMenuOptList = byFIXKiMenuList.optJSONArray("byFIXMenuOptList");
                    // 옵션 리스트
                    for (int k = 0; k < FIXMenuOptListCount; k++) {
                        String FIXItemOptName = "";
                        String FIXItemOptPrice = "";
                        String FIXItemOptQuantity = "";
                        String FIXItemOptAmount = "";

                        JSONObject byFIXMenuOptList = FIXMenuOptList.optJSONObject(k);
                        FIXItemOptName = byFIXMenuOptList.optString("byFIXMenuOptName");
                        FIXItemOptQuantity = byFIXMenuOptList.optString("byFIXMenuOptCount");
                        FIXItemOptPrice = byFIXMenuOptList.optString("byFIXMenuOptPrice");
                        FIXItemOptAmount = GetAdditionPriceAmount( FIXItemOptPrice , FIXItemOptQuantity);
                        itemName = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.MINUS2, new String(FIXItemOptName));
                        price = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE,new String(UtilManager.ConvertCurrencyFormat(FIXItemOptPrice)));
                        quantity = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, new String(FIXItemOptQuantity));
                        amount = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, new String(UtilManager.ConvertCurrencyFormat(FIXItemOptAmount)));
                        Msg.append(PrintItemLineByString(lineLen, itemName, price , quantity,amount));
                    }
                }
            }

        }

        //Line
        Msg.append(PrintLoopByString((char) 0x2D, lineLen));
        Msg.append(PrintCRLFByString(true, 1));

        //거래금액
        if (vo.getAmount() != null && !vo.getAmount().equals("") && !vo.getAmount().isEmpty()) {
            L_PrintVo = new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.AMOUNT, null);
            R_PrintVo = new PrintLineVo(R_Option, KovanPrintDefine.PRINT_KEY.NONE, vo.getAmount());
            Msg.append(PrintLineByString(lineLen, L_PrintVo, R_PrintVo));
        }
        //세금
        if (vo.getTax() != null && !vo.getTax().equals("") && !vo.getTax().isEmpty()) {
            L_PrintVo = new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.TAX, null);
            R_PrintVo = new PrintLineVo(R_Option, KovanPrintDefine.PRINT_KEY.NONE, vo.getTax());
            Msg.append(PrintLineByString(lineLen, L_PrintVo, R_PrintVo));
        }
        //봉사료
        if (vo.getsFee() != null && !vo.getsFee().equals("") && !vo.getsFee().isEmpty()) {
            L_PrintVo = new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.SFEE, null);
            R_PrintVo = new PrintLineVo(R_Option, KovanPrintDefine.PRINT_KEY.NONE, vo.getsFee());
            Msg.append(PrintLineByString(lineLen, L_PrintVo, R_PrintVo));
        }
        //비과세
        if (vo.getFee() != null && !vo.getFee().equals("") && !vo.getFee().isEmpty()) {
            L_PrintVo = new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.FEE, null);
            R_PrintVo = new PrintLineVo(R_Option, KovanPrintDefine.PRINT_KEY.NONE, vo.getFee());
            Msg.append(PrintLineByString(lineLen, L_PrintVo, R_PrintVo));
        }
        //총합
        long totalAmount = 0;
        if (vo.getTotalAmount() != null && !vo.getTotalAmount().equals("") && !vo.getTotalAmount().isEmpty()) {
            L_Option.add(KovanPrintDefine.PRINT_OPTION.BOLD);
            L_Option.add(KovanPrintDefine.PRINT_OPTION.DOUBLE_HEIGHT);
            R_Option.add(KovanPrintDefine.PRINT_OPTION.BOLD);
            R_Option.add(KovanPrintDefine.PRINT_OPTION.DOUBLE_HEIGHT);
            L_PrintVo = new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.TOTALAMT, null);
            R_PrintVo = new PrintLineVo(R_Option, KovanPrintDefine.PRINT_KEY.NONE, vo.getTotalAmount());
            Msg.append(PrintLineByString(lineLen, L_PrintVo, R_PrintVo));
            try {
                totalAmount = Long.parseLong(vo.getTotalAmount().replace("원", "").replace(",", "").replace(" ", ""));
            } catch (Exception e) {

            }
        }

        L_Option.clear();
        R_Option.clear();

        //Line
        Msg.append(PrintLoopByString((char) 0x2D, lineLen));
        Msg.append(PrintCRLFByString(true, 1));
        //승인번호
        if (vo.getAuthNo() != null && !vo.getAuthNo().equals("") && !vo.getAuthNo().isEmpty()) {
            L_Option.add(KovanPrintDefine.PRINT_OPTION.BOLD);
            R_Option.add(KovanPrintDefine.PRINT_OPTION.BOLD);

            L_PrintVo = new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.AUTHNO, null);
            R_PrintVo = new PrintLineVo(R_Option, KovanPrintDefine.PRINT_KEY.NONE, vo.getAuthNo());
            Msg.append(PrintLineByString(lineLen, L_PrintVo, R_PrintVo));
        }

        L_Option.clear();
        R_Option.clear();

        if (vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.AUTH_CREDIT || vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.CANCEL_CREDIT ||
                vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.AUTH_CREDIT_SWIP || vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.CANCEL_CREDIT_SWIP ||
                vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.AUTH_SIMPLEPAY || vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.CANCEL_SIMPLEPAY) {
            L_PrintVo = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.PURCHASE, vo.getPurchase());
            R_PrintVo = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.ISSUER, vo.getIssuer());
            Msg.append(PrintLineByString(lineLen, L_PrintVo, R_PrintVo));

            if (vo.getUserType() == KovanPrintDefine.USER_TYPE.CUSTOMER) {
                Msg.append(PrintLineByString(lineLen, new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.BIZTEL, vo.getBizTel())));
            }

            if (vo.getPrintMsg1() != null && !vo.getPrintMsg1().equals("") && !vo.getPrintMsg1().isEmpty()) {
                Msg.append(PrintLineByString(lineLen, new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, vo.getPrintMsg1())));
            }

            if (vo.getPrintMsg2() != null && !vo.getPrintMsg2().equals("") && !vo.getPrintMsg2().isEmpty()) {
                Msg.append(PrintLineByString(lineLen, new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, vo.getPrintMsg2())));
            }

            if (vo.getPrintMsg3() != null && !vo.getPrintMsg3().equals("") && !vo.getPrintMsg3().isEmpty()) {
                Msg.append(PrintLineByString(lineLen, new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, vo.getPrintMsg3())));
            }
        }

        if (vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.AUTH_CASH || vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.CANCEL_CASH) {
            if (vo.getUserType() == KovanPrintDefine.USER_TYPE.CUSTOMER) {
                Msg.append(PrintLineByString(lineLen, new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.BIZTEL, vo.getBizTel())));
            }

            if (vo.getPrintMsg1() != null) {
                Msg.append(PrintLineByString(lineLen, new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, vo.getPrintMsg1())));
            }

            if (vo.getPrintMsg2() != null) {
                Msg.append(PrintLineByString(lineLen, new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, vo.getPrintMsg2())));
            }

            if (vo.getPrintMsg3() != null) {
                Msg.append(PrintLineByString(lineLen, new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, vo.getPrintMsg3())));
            }
        }

        if (vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.AUTH_CASH_PRINT || vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.CANCEL_CASH_PRINT) {
            if (vo.getUserType() == KovanPrintDefine.USER_TYPE.CUSTOMER) {
                Msg.append(PrintLineByString(lineLen, new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, "본 전표는 현금(소득공제) 미적용 대상임")));
            }
        }

        Msg.append(PrintLoopByString((char) 0x2D, lineLen));
        Msg.append(PrintCRLFByString(true, 1));

        if (vo.getEtcMsg() != null && !vo.getEtcMsg().equals("") && !vo.getEtcMsg().isEmpty()) {
            Msg.append(PrintSortByString(KovanPrintDefine.PRINT_SORT.RIGHT));
            Msg.append(PrintLoopByString((char) 0x2D, lineLen));
            Msg.append(PrintLineByString(lineLen, new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.NONE, vo.getEtcMsg())));
        }

        if (vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.AUTH_CREDIT || vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.CANCEL_CREDIT ||
                vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.AUTH_CREDIT_SWIP || vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.CANCEL_CREDIT_SWIP ||
                vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.AUTH_SIMPLEPAY || vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.CANCEL_SIMPLEPAY) {
            if (vo.getSignData() != null && !vo.getSignData().equals("") && !vo.getSignData().isEmpty()) {
                Msg.append(PrintLineByString(lineLen, new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.SIGN, "")));
            } else {
                if(totalAmount > 50000 || totalAmount < -50000) {
                    Msg.append(PrintLineByString(lineLen, new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.SIGN, "")));
                    Msg.append(PrintCRLFByString(true, 4));
                } else {
                    Msg.append(PrintCRLFByString(true, 2));
                }
            }
        }

        data = KovanEncodingUtils.StringToByteArray(new String(Msg));

        return data;
    }

    @Keep
    private static byte[] PrintReceiptByC1it(int lineLen, @NonNull KovanReceiptVo vo, boolean isHandSign) {
        StringBuilder Msg = new StringBuilder();
        byte[] etc = null;
        byte[] sign = null;
        byte[] data = null;
        byte[] cut = KovanEncodingUtils.StringToByteArray(PrintCRLFByString(true, 1).toString());
        byte[] cutSign = KovanEncodingUtils.StringToByteArray(PrintCRLFByString(true, 4).toString());

        ArrayList<KovanPrintDefine.PRINT_OPTION> L_Option = new ArrayList<>();
        ArrayList<KovanPrintDefine.PRINT_OPTION> R_Option = new ArrayList<>();
        PrintLineVo L_PrintVo;
        PrintLineVo R_PrintVo;

        Msg.append(PrintInitByString());

        //고객사용 Header 부분
        if (vo.getUserType() == KovanPrintDefine.USER_TYPE.CUSTOMER) {
            Msg.append(PrintSortByString(KovanPrintDefine.PRINT_SORT.CENTER));
            L_Option.add(KovanPrintDefine.PRINT_OPTION.BOLD);
            Msg.append(PrintLineByString(lineLen, new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.NONE, "가맹점명,주소가 실제와 다른경우 신고안내")));
            Msg.append(PrintSortByString(KovanPrintDefine.PRINT_SORT.CENTER));
            Msg.append(PrintLineByString(lineLen, new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.NONE, "여신금융협회 02-2011-0777포상금10만원지급")));
            Msg.append(PrintInitByString());
            L_Option.clear();
        }

        Msg.append(PrintSortByString(KovanPrintDefine.PRINT_SORT.LEFT));

        //거래 종류 + 전표 종류
        L_Option.add(KovanPrintDefine.PRINT_OPTION.BOLD);
        L_Option.add(KovanPrintDefine.PRINT_OPTION.DOUBLE_WIDTH);

        L_PrintVo = new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.NONE, vo.getAuthType().getMsg());
        R_PrintVo = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, vo.getUserType().getMsg());

        Msg.append(PrintLineByString(lineLen, L_PrintVo, R_PrintVo));

        L_Option.clear();
        R_Option.clear();

        //상호 명
        Msg.append(PrintLineByString(lineLen, new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.BIZNAME, vo.getBizName())));

        //대표자 명 + 전표 No
        if (vo.getSerialNo() != null && !vo.getSerialNo().equals("") && !vo.getSerialNo().isEmpty()) {
            L_PrintVo = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.OWNERNAME, vo.getOwnerName());
            R_PrintVo = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.SERIALNO, vo.getSerialNo());

            Msg.append(PrintLineByString(lineLen, L_PrintVo, R_PrintVo));
        } else {
            Msg.append(PrintLineByString(lineLen, new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.OWNERNAME, vo.getOwnerName())));
        }

        //사업자 번호 + TID
        L_PrintVo = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.BIZNO, vo.getBizNo());
        R_PrintVo = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.TID, vo.getTid());

        Msg.append(PrintLineByString(lineLen, L_PrintVo, R_PrintVo));

        //주소
        Msg.append(PrintLineByString(lineLen, new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, vo.getBizAddr())));

        //Line
        Msg.append(PrintLoopByString((char) 0x2D, lineLen));
        Msg.append(PrintCRLFByString(true, 1));
        //카드번호 + 할부
        if (vo.getAuthType() != KovanPrintDefine.AUTH_TYPE.AUTH_CASH_PRINT && vo.getAuthType() != KovanPrintDefine.AUTH_TYPE.CANCEL_CASH_PRINT) {
            L_PrintVo = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.CARDNO, vo.getCardNo());
            R_PrintVo = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, vo.getInstall());
            Msg.append(PrintLineByString(lineLen, L_PrintVo, R_PrintVo));
        }

        String dccFlag = "";
        if (vo.getStoreNo() != null && !vo.getStoreNo().equals("") && !vo.getStoreNo().isEmpty()) {
            //가맹점 번호 + DCC Flag
            L_PrintVo = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.STORE, vo.getStoreNo());
            R_PrintVo = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, vo.getDccFlag());
            Msg.append(PrintLineByString(lineLen, L_PrintVo, R_PrintVo));

        }

        //거래일시 + WCC Flag
        L_PrintVo = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.AUTHDT, vo.getAuthDt());
        if(vo.getWccType() != null && !vo.getWccType().getMsg().equals("") && !vo.getWccType().getMsg().isEmpty() ) {
            R_PrintVo = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, vo.getWccType().getMsg());
            Msg.append(PrintLineByString(lineLen, L_PrintVo, R_PrintVo));
        }else{
            Msg.append(PrintLineByString(lineLen, new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.AUTHDT, vo.getAuthDt())));
        }

        //원거래 일자
        if (vo.getOrgDt() != null && !vo.getOrgDt().equals("") && !vo.getOrgDt().isEmpty()) {
            Msg.append(PrintLineByString(lineLen, new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.ORGDT, vo.getOrgDt())));
        }

        if( vo.getMenuListCount() != null && !vo.getMenuListCount().equals("") && !vo.getMenuListCount().isEmpty() ){

            //for 처리 하면서
            // 상품 리스트

            //Line
            Msg.append(PrintLoopByString((char) 0x3D, lineLen));
            Msg.append(PrintCRLFByString(true, 1));
            L_PrintVo = new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.NONE, new String("메 뉴 명"));
            R_PrintVo = new PrintLineVo(R_Option, KovanPrintDefine.PRINT_KEY.NONE, new String("단가 수량       금액"));
            Msg.append(PrintLineByString(lineLen, L_PrintVo, R_PrintVo));
            Msg.append(PrintLoopByString((char) 0x3D, lineLen));
            Msg.append(PrintCRLFByString(true, 1));

            JSONArray list = vo.getMenuList();
            int list_count =  Integer.parseInt(vo.getMenuListCount());

            PrintLineVo itemName = null , price = null , quantity = null , amount = null;

            String FIXItemName = "";
            String FIXItemPrice = "";
            String FIXItemQuantity = "";
            String FIXItemAmount = "";
            String FIXItemAmountState = "";
            int FIXMenuOptListCount = 0;
            for( int i = 0; i < list_count;i++){
                JSONObject byFIXKiMenuList = list.optJSONObject(i);
                FIXItemName = byFIXKiMenuList.optString("byFIXMenuName");
                FIXItemPrice = byFIXKiMenuList.optString("byFIXMenuPrice");
                FIXItemQuantity = byFIXKiMenuList.optString("byFIXMenuCount");
                // 단가와 수량으로 합계를 구한다.
                FIXItemAmount = GetAdditionPriceAmount( FIXItemPrice , FIXItemQuantity);
                FIXItemAmountState = byFIXKiMenuList.optString("byFIXItemAmountState");

                itemName = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, new String(FIXItemName));
                price = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE,new String(UtilManager.ConvertCurrencyFormat(FIXItemPrice)));
                quantity = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, new String(FIXItemQuantity));
                amount = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, new String(UtilManager.ConvertCurrencyFormat(FIXItemAmount)));
                Msg.append(PrintItemLineByString(lineLen, itemName , price, quantity, amount) );

                String strFIXMenuOptListCount = byFIXKiMenuList.optString("byFIXMenuOptListCount");
                if( strFIXMenuOptListCount != null && !strFIXMenuOptListCount.equals("") && !strFIXMenuOptListCount.isEmpty() ) {
                    FIXMenuOptListCount = Integer.parseInt(byFIXKiMenuList.optString("byFIXMenuOptListCount"));
                    JSONArray FIXMenuOptList = byFIXKiMenuList.optJSONArray("byFIXMenuOptList");
                    // 옵션 리스트
                    for (int k = 0; k < FIXMenuOptListCount; k++) {
                        String FIXItemOptName = "";
                        String FIXItemOptPrice = "";
                        String FIXItemOptQuantity = "";
                        String FIXItemOptAmount = "";

                        JSONObject byFIXMenuOptList = FIXMenuOptList.optJSONObject(k);
                        FIXItemOptName = byFIXMenuOptList.optString("byFIXMenuOptName");
                        FIXItemOptQuantity = byFIXMenuOptList.optString("byFIXMenuOptCount");
                        FIXItemOptPrice = byFIXMenuOptList.optString("byFIXMenuOptPrice");
                        FIXItemOptAmount = GetAdditionPriceAmount( FIXItemOptPrice , FIXItemOptQuantity);
                        itemName = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.MINUS2, new String(FIXItemOptName));
                        price = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE,new String(UtilManager.ConvertCurrencyFormat(FIXItemOptPrice)));
                        quantity = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, new String(FIXItemOptQuantity));
                        amount = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, new String(UtilManager.ConvertCurrencyFormat(FIXItemOptAmount)));
                        Msg.append(PrintItemLineByString(lineLen, itemName, price , quantity,amount));
                    }
                }
            }

        }

        //Line
        Msg.append(PrintLoopByString((char) 0x2D, lineLen));
        Msg.append(PrintCRLFByString(true, 1));

        //거래금액
        if (vo.getAmount() != null && !vo.getAmount().equals("") && !vo.getAmount().isEmpty()) {
            L_PrintVo = new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.AMOUNT, null);
            R_PrintVo = new PrintLineVo(R_Option, KovanPrintDefine.PRINT_KEY.NONE, vo.getAmount());
            Msg.append(PrintLineByString(lineLen, L_PrintVo, R_PrintVo));
        }
        //세금
        if (vo.getTax() != null && !vo.getTax().equals("") && !vo.getTax().isEmpty()) {
            L_PrintVo = new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.TAX, null);
            R_PrintVo = new PrintLineVo(R_Option, KovanPrintDefine.PRINT_KEY.NONE, vo.getTax());
            Msg.append(PrintLineByString(lineLen, L_PrintVo, R_PrintVo));
        }
        //봉사료
        if (vo.getsFee() != null && !vo.getsFee().equals("") && !vo.getsFee().isEmpty()) {
            L_PrintVo = new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.SFEE, null);
            R_PrintVo = new PrintLineVo(R_Option, KovanPrintDefine.PRINT_KEY.NONE, vo.getsFee());
            Msg.append(PrintLineByString(lineLen, L_PrintVo, R_PrintVo));
        }
        //비과세
        if (vo.getFee() != null && !vo.getFee().equals("") && !vo.getFee().isEmpty()) {
            L_PrintVo = new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.FEE, null);
            R_PrintVo = new PrintLineVo(R_Option, KovanPrintDefine.PRINT_KEY.NONE, vo.getFee());
            Msg.append(PrintLineByString(lineLen, L_PrintVo, R_PrintVo));
        }
        //총합
        long totalAmount = 0;
        if (vo.getTotalAmount() != null && !vo.getTotalAmount().equals("") && !vo.getTotalAmount().isEmpty()) {
            L_Option.add(KovanPrintDefine.PRINT_OPTION.BOLD);
            L_Option.add(KovanPrintDefine.PRINT_OPTION.DOUBLE_HEIGHT);
            R_Option.add(KovanPrintDefine.PRINT_OPTION.BOLD);
            R_Option.add(KovanPrintDefine.PRINT_OPTION.DOUBLE_HEIGHT);
            L_PrintVo = new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.TOTALAMT, null);
            R_PrintVo = new PrintLineVo(R_Option, KovanPrintDefine.PRINT_KEY.NONE, vo.getTotalAmount());
            Msg.append(PrintLineByString(lineLen, L_PrintVo, R_PrintVo));
            //Msg.append(PrintLineByString(lineLen, L_PrintVo, R_PrintVo));
            try {
                totalAmount = Long.parseLong(vo.getTotalAmount().replace("원", "").replace(",", "").replace(" ", ""));
            } catch (Exception e) {

            }
        }

        L_Option.clear();
        R_Option.clear();

        //Line
        Msg.append(PrintLoopByString((char) 0x2D, lineLen));
        Msg.append(PrintCRLFByString(true, 1));
        //승인번호
        if (vo.getAuthNo() != null && !vo.getAuthNo().equals("") && !vo.getAuthNo().isEmpty()) {
            L_Option.add(KovanPrintDefine.PRINT_OPTION.BOLD);
            R_Option.add(KovanPrintDefine.PRINT_OPTION.BOLD);

            L_PrintVo = new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.AUTHNO, null);
            R_PrintVo = new PrintLineVo(R_Option, KovanPrintDefine.PRINT_KEY.NONE, vo.getAuthNo());
            Msg.append(PrintLineByString(lineLen, L_PrintVo, R_PrintVo));
        }

        L_Option.clear();
        R_Option.clear();

        if (vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.AUTH_CREDIT || vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.CANCEL_CREDIT ||
                vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.AUTH_CREDIT_SWIP || vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.CANCEL_CREDIT_SWIP ||
                vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.AUTH_SIMPLEPAY || vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.CANCEL_SIMPLEPAY) {
            L_PrintVo = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.PURCHASE, vo.getPurchase());
            R_PrintVo = new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.ISSUER, vo.getIssuer());
            Msg.append(PrintLineByString(lineLen, L_PrintVo, R_PrintVo));

            if (vo.getUserType() == KovanPrintDefine.USER_TYPE.CUSTOMER) {
                Msg.append(PrintLineByString(lineLen, new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.BIZTEL, vo.getBizTel())));
            }

            if (vo.getPrintMsg1() != null && !vo.getPrintMsg1().equals("") && !vo.getPrintMsg1().isEmpty()) {
                Msg.append(PrintLineByString(lineLen, new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, vo.getPrintMsg1())));
            }

            if (vo.getPrintMsg2() != null && !vo.getPrintMsg2().equals("") && !vo.getPrintMsg2().isEmpty()) {
                Msg.append(PrintLineByString(lineLen, new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, vo.getPrintMsg2())));
            }

            if (vo.getPrintMsg3() != null && !vo.getPrintMsg3().equals("") && !vo.getPrintMsg3().isEmpty()) {
                Msg.append(PrintLineByString(lineLen, new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, vo.getPrintMsg3())));
            }
        }

        if (vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.AUTH_CASH || vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.CANCEL_CASH) {
            if (vo.getUserType() == KovanPrintDefine.USER_TYPE.CUSTOMER) {
                Msg.append(PrintLineByString(lineLen, new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.BIZTEL, vo.getBizTel())));
            }

            if (vo.getPrintMsg1() != null) {
                Msg.append(PrintLineByString(lineLen, new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, vo.getPrintMsg1())));
            }

            if (vo.getPrintMsg2() != null) {
                Msg.append(PrintLineByString(lineLen, new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, vo.getPrintMsg2())));
            }

            if (vo.getPrintMsg3() != null) {
                Msg.append(PrintLineByString(lineLen, new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, vo.getPrintMsg3())));
            }
        }

        if (vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.AUTH_CASH_PRINT || vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.CANCEL_CASH_PRINT) {
            if (vo.getUserType() == KovanPrintDefine.USER_TYPE.CUSTOMER) {
                Msg.append(PrintLineByString(lineLen, new PrintLineVo(null, KovanPrintDefine.PRINT_KEY.NONE, "본 전표는 현금(소득공제) 미적용 대상임")));

                //Line
                Msg.append(PrintLoopByString((char) 0x2D, lineLen));
                Msg.append(PrintCRLFByString(true, 1));
            }
        }

        boolean signPrintYn = false;
        if (vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.AUTH_CREDIT || vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.CANCEL_CREDIT ||
                vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.AUTH_CREDIT_SWIP || vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.CANCEL_CREDIT_SWIP ||
                vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.AUTH_SIMPLEPAY || vo.getAuthType() == KovanPrintDefine.AUTH_TYPE.CANCEL_SIMPLEPAY) {
            if(!isHandSign) {
                if (vo.getSignData() != null && !vo.getSignData().equals("") && !vo.getSignData().isEmpty()) {
                    //Base 64 Decode
                    byte[] encSign = KovanEncodingUtils.getBase64DecodeByte(vo.getSignData());
                    Log.e(TAG, KovanEncodingUtils.ByteArrayToHexString(encSign));
                    if (encSign != null) {
                        //Byte array to Bitmap
                        Bitmap convBmp = KovanEncodingUtils.ByteArrayToBitmap(encSign);

                        if (convBmp != null) {
                            //LOG
                            KovanBmpUtils.bmpInfo(convBmp);

                            StringBuilder sbSignHead = new StringBuilder();
                            sbSignHead.append(PrintLoopByString((char) 0x2D, lineLen));
                            sbSignHead.append(PrintCRLFByString(true, 1));
                            sbSignHead.append(PrintLineByString(lineLen, new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.SIGN, "")));

                            encSign = PrintBmp(convBmp, KovanPrintDefine.PRINT_SORT.CENTER, KovanPrintDefine.BMP_OPTION.DOUBLE_WIDTH_HEIGHT);

                            //서명이미지 전송하지않도록 주석처리
//                            sign = KovanEncodingUtils.mergeArrays(KovanEncodingUtils.StringToByteArray(sbSignHead.toString()), encSign);
//                            signPrintYn = true;
                        }
                    }
                } else {
                    if(totalAmount > 50000 || totalAmount < -50000) {
                        StringBuilder sbSignHead = new StringBuilder();
                        sbSignHead.append(PrintLoopByString((char) 0x2D, lineLen));
                        sbSignHead.append(PrintCRLFByString(true, 1));
                        sbSignHead.append(PrintLineByString(lineLen, new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.SIGN, "")));
                        sbSignHead.append(PrintCRLFByString(true, 4));
                        sign = KovanEncodingUtils.StringToByteArray(sbSignHead.toString());
                    }
                }
            }
            else{
                StringBuilder sbSignHead = new StringBuilder();
                sbSignHead.append(PrintLoopByString((char) 0x2D, lineLen));
                sbSignHead.append(PrintCRLFByString(true, 1));
                sbSignHead.append(PrintLineByString(lineLen, new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.SIGN, "")));
                sbSignHead.append(PrintCRLFByString(true, 4));
                sign = KovanEncodingUtils.StringToByteArray(sbSignHead.toString());
            }
        }

        if (vo.getEtcMsg() != null && !vo.getEtcMsg().equals("") && !vo.getEtcMsg().isEmpty()) {
            StringBuilder sbEtc = new StringBuilder();
            sbEtc.append(PrintSortByString(KovanPrintDefine.PRINT_SORT.RIGHT));
            sbEtc.append(PrintLoopByString((char) 0x2D, lineLen));
            sbEtc.append(PrintLineByString(lineLen, new PrintLineVo(L_Option, KovanPrintDefine.PRINT_KEY.NONE, vo.getEtcMsg())));
            etc = KovanEncodingUtils.StringToByteArray(sbEtc.toString());
        }

        if(signPrintYn) {
            data = KovanEncodingUtils.mergeArrays(KovanEncodingUtils.StringToByteArray(new String(Msg)), sign, etc, cutSign);
        } else {
            data = KovanEncodingUtils.mergeArrays(KovanEncodingUtils.StringToByteArray(new String(Msg)), sign, etc, cut);
        }

        Log.e(TAG, "[Receipt]\n" + KovanEncodingUtils.ByteArrayToString(data));

        return data;
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
    private static StringBuilder PrintItemLineByString(int lineLen, PrintLineVo itemName, PrintLineVo price, PrintLineVo quantity, PrintLineVo amount) {
        StringBuilder sb = new StringBuilder();
        int lLen = 0;
        int rLen = 0;
        int pLen = 0;
        int idx = 0;
        int itemNameLen = 0;
        int priceLen = 0 , MAX_ITEM_PRICE_LEN = 12;
        int quantityLen = 0 , MAX_ITEM_QUANTITY_LEN = 4;
        int amountLen = 0 , MAX_AMOUNT_LEN = 12;

        int MAX_ITEM_ITEMNAME_INDEX = lineLen - (MAX_ITEM_PRICE_LEN + MAX_ITEM_QUANTITY_LEN +MAX_AMOUNT_LEN );
        int MAX_ITEM_PRICE_INDEX = lineLen - ( MAX_ITEM_QUANTITY_LEN + MAX_AMOUNT_LEN);
        int MAX_ITEM_QUANTITY_INDEX = lineLen - (  MAX_AMOUNT_LEN);
        byte[] pLine1, pLine2, pTemp;
        //int lineLen = PreferenceUtils.getInteger(context, PrintDefine.FILE_DEVICE_INFO, PrintDefine.DEVICE_INFO.LEN.getKey(),42);

        //왼쪽 길이 Set
        if (itemName.getValue() == null) {
            itemName.setValue("");
        }

        itemNameLen = itemName.getKey().getLength();
        itemNameLen += KovanEncodingUtils.getByteLength(itemName.getValue());

        if (itemName.getOptions() != null) {
            for (KovanPrintDefine.PRINT_OPTION o : itemName.getOptions()) {
                if (o == KovanPrintDefine.PRINT_OPTION.DOUBLE_WIDTH) {
                    itemNameLen *= 2;
                }
            }
        }

        //왼쪽 옵션 Set
        if (itemName.getOptions() != null) {
            for (KovanPrintDefine.PRINT_OPTION o : itemName.getOptions()) {
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
        if (itemName.getKey() != KovanPrintDefine.PRINT_KEY.NONE) {
            sb.append(itemName.getKey().getKey());
        }

        //텍스트를 붙임
        if (itemNameLen > lineLen ) {
            idx = 0;
            pTemp = KovanEncodingUtils.StringToByteArray(itemName.getValue());
            pLine1 = new byte[lineLen - itemName.getKey().getLength()];
            pLine2 = new byte[itemNameLen - lineLen];
            System.arraycopy(pTemp, idx, pLine1, 0, pLine1.length);
            idx += pLine1.length;

            System.arraycopy(pTemp, idx, pLine2, 0, pLine2.length);

            sb.append(KovanEncodingUtils.ByteArrayToString(pLine1));
            sb.append(PrintCRLFByString(true, 1));
            sb.append(KovanEncodingUtils.ByteArrayToString(pLine2));

            KovanEncodingUtils.clearArray(pLine1, pLine2, pTemp);

            //바뀐 줄의 길이
            itemNameLen = itemNameLen - lineLen;
        } else {
            sb.append(itemName.getValue());
        }
        //옵션 초기화
        sb.append(PrintModeSetByString(KovanPrintDefine.PRINT_OPTION.NORMAL));

        // 단가 길이 set
        if (price.getValue() == null) {
            price.setValue("");
        }
        // 단가 길이 Set
        priceLen = price.getKey().getLength();
        priceLen += KovanEncodingUtils.getByteLength(price.getValue());

        if (price.getOptions() != null) {
            for (KovanPrintDefine.PRINT_OPTION o : price.getOptions()) {
                if (o == KovanPrintDefine.PRINT_OPTION.DOUBLE_WIDTH) {
                    priceLen *= 2;
                }
            }
        }
        //둘이 합친 길이가 MAX 넘은 경우 오른쪽 TEXT는 아랫줄에 표기
        if (itemNameLen + priceLen > MAX_ITEM_PRICE_INDEX) {
            sb.append(PrintCRLFByString(true, 1));
            pLen = MAX_ITEM_PRICE_INDEX - priceLen;
            sb.append(PrintLoopByString((char) 0x20, pLen));
        }
        //아니면 Padding Space
        else {
            pLen = MAX_ITEM_PRICE_INDEX - (itemNameLen + priceLen);
            sb.append(PrintLoopByString((char) 0x20, pLen));
        }


        //오른쪽 옵션 Set
        if (price.getOptions() != null) {
            for (KovanPrintDefine.PRINT_OPTION o : price.getOptions()) {
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
        if (price.getKey() != KovanPrintDefine.PRINT_KEY.NONE) {
            sb.append(price.getKey().getKey());
        }

        //텍스트를 붙임
        if (priceLen > lineLen) {
            idx = 0;
            pTemp = KovanEncodingUtils.StringToByteArray(price.getValue());
            pLine1 = new byte[MAX_ITEM_PRICE_INDEX - price.getKey().getLength()];
            pLine2 = new byte[priceLen - MAX_ITEM_PRICE_INDEX];
            System.arraycopy(pTemp, idx, pLine1, 0, pLine1.length);
            idx += pLine1.length;

            System.arraycopy(pTemp, idx, pLine2, 0, pLine2.length);

            sb.append(KovanEncodingUtils.ByteArrayToString(pLine1));
            sb.append(PrintCRLFByString(true, 1));
            sb.append(KovanEncodingUtils.ByteArrayToString(pLine2));

            KovanEncodingUtils.clearArray(pLine1, pLine2, pTemp);
        } else {
            sb.append(price.getValue());
        }
        //sb.append(price.getValue());
        sb.append(PrintModeSetByString(KovanPrintDefine.PRINT_OPTION.NORMAL));


        // 수량 Set
        if (quantity.getValue() == null) {
            quantity.setValue("");
        }
        //오른쪽 길이 Set
        quantityLen = quantity.getKey().getLength();
        quantityLen += KovanEncodingUtils.getByteLength(quantity.getValue());

        if (quantity.getOptions() != null) {
            for (KovanPrintDefine.PRINT_OPTION o : quantity.getOptions()) {
                if (o == KovanPrintDefine.PRINT_OPTION.DOUBLE_WIDTH) {
                    quantityLen *= 2;
                }
            }
        }
        //둘이 합친 길이가 MAX 넘은 경우 오른쪽 TEXT는 아랫줄에 표기
        if (MAX_ITEM_PRICE_INDEX + quantityLen > MAX_ITEM_QUANTITY_INDEX) {
            sb.append(PrintCRLFByString(true, 1));
            pLen = MAX_ITEM_QUANTITY_INDEX - priceLen;
            sb.append(PrintLoopByString((char) 0x20, pLen));
        }
        //아니면 Padding Space
        else {
            pLen = MAX_ITEM_QUANTITY_INDEX - (MAX_ITEM_PRICE_INDEX + quantityLen);
            sb.append(PrintLoopByString((char) 0x20, pLen));
        }


        //오른쪽 옵션 Set
        if (quantity.getOptions() != null) {
            for (KovanPrintDefine.PRINT_OPTION o : quantity.getOptions()) {
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
        if (quantity.getKey() != KovanPrintDefine.PRINT_KEY.NONE) {
            sb.append(quantity.getKey().getKey());
        }

        //텍스트를 붙임
//        if (quantityLen > lineLen) {
//            idx = 0;
//            pTemp = KovanEncodingUtils.StringToByteArray(quantity.getValue());
//            pLine1 = new byte[lineLen - quantity.getKey().getLength()];
//            pLine2 = new byte[quantityLen - lineLen];
//            System.arraycopy(pTemp, idx, pLine1, 0, pLine1.length);
//            idx += pLine1.length;
//
//            System.arraycopy(pTemp, idx, pLine2, 0, pLine2.length);
//
//            sb.append(KovanEncodingUtils.ByteArrayToString(pLine1));
//            sb.append(PrintCRLFByString(true, 1));
//            sb.append(KovanEncodingUtils.ByteArrayToString(pLine2));
//
//            KovanEncodingUtils.clearArray(pLine1, pLine2, pTemp);
//        } else {
//            sb.append(quantity.getValue());
//        }
        sb.append(quantity.getValue());
        sb.append(PrintModeSetByString(KovanPrintDefine.PRINT_OPTION.NORMAL));

        // 금액 set
        if (amount.getValue() == null) {
            amount.setValue("");
        }
        //오른쪽 길이 Set
        amountLen = amount.getKey().getLength();
        amountLen += KovanEncodingUtils.getByteLength(amount.getValue());

        if (amount.getOptions() != null) {
            for (KovanPrintDefine.PRINT_OPTION o : amount.getOptions()) {
                if (o == KovanPrintDefine.PRINT_OPTION.DOUBLE_WIDTH) {
                    amountLen *= 2;
                }
            }
        }
        //둘이 합친 길이가 MAX 넘은 경우 오른쪽 TEXT는 아랫줄에 표기
        if (MAX_ITEM_QUANTITY_INDEX + amountLen > lineLen) {
            sb.append(PrintCRLFByString(true, 1));
            pLen = lineLen - priceLen;
            sb.append(PrintLoopByString((char) 0x20, pLen));
        }
        //아니면 Padding Space
        else {
            pLen = lineLen - (MAX_ITEM_QUANTITY_INDEX + amountLen);
            sb.append(PrintLoopByString((char) 0x20, pLen));
        }


        //오른쪽 옵션 Set
        if (amount.getOptions() != null) {
            for (KovanPrintDefine.PRINT_OPTION o : amount.getOptions()) {
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
        if (amount.getKey() != KovanPrintDefine.PRINT_KEY.NONE) {
            sb.append(amount.getKey().getKey());
        }

        //텍스트를 붙임
//        if (amountLen > lineLen) {
//            idx = 0;
//            pTemp = KovanEncodingUtils.StringToByteArray(amount.getValue());
//            pLine1 = new byte[lineLen - amount.getKey().getLength()];
//            pLine2 = new byte[amountLen - lineLen];
//            System.arraycopy(pTemp, idx, pLine1, 0, pLine1.length);
//            idx += pLine1.length;
//
//            System.arraycopy(pTemp, idx, pLine2, 0, pLine2.length);
//
//            sb.append(KovanEncodingUtils.ByteArrayToString(pLine1));
//            sb.append(PrintCRLFByString(true, 1));
//            sb.append(KovanEncodingUtils.ByteArrayToString(pLine2));
//
//            KovanEncodingUtils.clearArray(pLine1, pLine2, pTemp);
//        } else {
//            sb.append(amount.getValue());
//        }
        sb.append(amount.getValue());
        sb.append(PrintModeSetByString(KovanPrintDefine.PRINT_OPTION.NORMAL));

        sb.append(PrintCRLFByString(true, 1));

        return sb;
    }

    public static String GetAdditionPriceAmount( String price, String count ){
        String totalAmount = "";
        if( price != null && !price.equals("") && !price.isEmpty() ) {
            if( count != null && !count.equals("") && !count.isEmpty()) {
                long lPriceAmount = Long.parseLong(price.replace(",", ""));
                long lCount = Long.parseLong(count.replace(",", ""));
                long lTotalAmount = lPriceAmount * lCount;
                totalAmount = String.valueOf(lTotalAmount);
            }
        }
        return totalAmount;
    }

    public static StringBuilder PrintCashDrawerGeneral( ){
        StringBuilder sb = new StringBuilder();

        sb.append((char) 0x1B);         // ESC
        sb.append((char) 0x70);         // p
        sb.append((char) 0x31);         // m
        sb.append((char) 0x08);         // t1
        sb.append((char) 0x08);         // t2
        return sb;
    }
}
