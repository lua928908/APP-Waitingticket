package com.example.app_waitingticket.printer.fixedPrint;

import androidx.annotation.Keep;

public class KovanPrintDefine {
    //protected static final String VERSION_NAME = BuildConfig.VERSION_NAME;
    protected static final String VERSION_NAME = "1.0.0";
    protected static final String FILE_DEVICE_INFO = "DEVICE_INFO";

    protected enum UsbPermission { Unknown, Requested, Granted, Denied }

    protected static final int SIGN_WIDTH = 128;
    protected static final int SIGN_HEIGHT = 64;

    /*protected static final int SIGN_WIDTH = 96;
    protected static final int SIGN_HEIGHT = 48;

    protected static final int SIGN_WIDTH = 64;
    protected static final int SIGN_HEIGHT = 32;*/

    protected enum DEVICE_INFO {
        PORT("PORT"),
        BAUD("BAUD"),
        LEN("LEN"),
        TCP_IP("TCP_IP"),
        TCP_PORT("TCP_PORT");
        //

        private String key;

        DEVICE_INFO(String key){
            this.key = key;
        }

        protected String getKey() {
            return key;
        }
    }
    /**
     * Error Code / Msg
     */
    public enum ERROR {
        @Keep
        S000("S000","성공"),
        @Keep
        E999("E999", "기타 오류"),
        @Keep
        E001("E001", "포트 입력 오류"),
        @Keep
        E002("E002", "통신 속도 입력 오류"),
        @Keep
        E003("E003", "포트 권한 거절"),
        @Keep
        E004("E004", "포트 권한 오류"),
        @Keep
        E005("E005", "장치를 찾을 수 없음"),
        @Keep
        E006("E006", "장치 드라이버를 찾을 수 없음"),
        @Keep
        E007("E007", "가용 포트가 없음"),
        @Keep
        E008("E008", "포트 권한 필요"),
        @Keep
        E009("E009", "포트 오픈 실패"),
        @Keep
        E010("E010", "포트 설정 확인 요망"),
        @Keep
        E011("E011", "통신 속도 설정 확인 요망"),
        @Keep
        E012("E012", "장치 연결 확인 요망"),
        @Keep
        E013("E013", "Serial Write Fail"),
        @Keep
        E014("E014", "입력 값 오류"),
        @Keep
        E015("E015", "인터넷 권한 확인 요망"),
        @Keep
        E016("E016", "알 수 없는 호스트"),
        @Keep
        E017("E017", "소켓 접속 실패"),
        @Keep
        E018("E018", "IP 설정 확인 요망"),
        @Keep
        E019("E019", "소켓 전송 실패 확인 요망"),
        @Keep
        E020("E020", "서명 데이터 확인 요망"),
        @Keep
        E021("E021", "서명 데이터 변환 실패"),
        @Keep
        E022("E022", "서명 이미지 파일 읽기 실패"),
        @Keep
        E023("E023", "서명 이미지 파일 확인 요망"),
        @Keep
        E024("E024", "서명 이미지 사이즈 변경 요청. 권장 사이즈 128 * 64"),
        @Keep
        E025("E025", "파일 읽기 권한 확인 요망"),
        @Keep
        E026("E026", "파일 쓰기 권한 확인 요망"),

        ;
        //

        private String code,msg;

        ERROR(String code, String msg){
            this.code = code;
            this.msg = msg;
        }

        @Keep
        public String getCode(){
            return this.code;
        }
        @Keep
        public String getMsg() {
            return this.msg;
        }
    }

    public enum MAX_LINE_LEN {
        @Keep
        L_40(40),
        @Keep
        L_42(42),
        @Keep
        L_48(48);

        private int length;

        MAX_LINE_LEN(int length) {
            this.length = length;
        }
        @Keep
        public int getLength() {
            return length;
        }
    }

    public enum BMP_OPTION{
        @Keep
        NORMAL,
        @Keep
        DOUBLE_WIDTH,
        @Keep
        DOUBLE_HEIGHT,
        @Keep
        DOUBLE_WIDTH_HEIGHT;
    }

    public enum PRINT_SORT{
        @Keep
        RIGHT,
        @Keep
        CENTER,
        @Keep
        LEFT;
    }

    public enum PRINT_OPTION{
        @Keep
        NORMAL,
        @Keep
        BOLD,
        @Keep
        DOUBLE_WIDTH,
        @Keep
        DOUBLE_HEIGHT,
        @Keep
        UNDER_LINE;
    }



    //영수증 관련
    protected static final String PRINT_BIZNAME = "상호명:";
    protected static final String PRINT_OWNERNAME = "대표자:";
    protected static final String PRINT_SERIALNO = "전표No:";
    protected static final String PRINT_BIZNO = "사업자번호:";
    protected static final String PRINT_TID = "TID:";
    protected static final String PRINT_CARDNO = "카드번호:";
    protected static final String PRINT_AUTHDT = "거래일시:";
    protected static final String PRINT_AMOUNT = "거래금액:";
    protected static final String PRINT_ORGDT = "원거래일자:";
    protected static final String PRINT_TAX = "세    금:";
    protected static final String PRINT_SFEE = "봉 사 료:";
    protected static final String PRINT_FEE = "비 과 세:";
    protected static final String PRINT_TOTALAMT = "총 금 액:";
    protected static final String PRINT_AUTHNO = "승인번호:";
    protected static final String PRINT_PURCHASE = "매입사:";
    protected static final String PRINT_ISSUER = "발급사:";
    protected static final String PRINT_BIZTEL = "가맹점전화:";
    protected static final String PRINT_STROENO = "가맹점번호:";
    protected static final String PRINT_SIGN = "(서명/Signature)";
    protected static final String PRINT_MINUS = "-";
    protected static final String PRINT_MINUS2 = "   -> ";

    protected enum PRINT_KEY{
        NONE("",0),
        BIZNAME(PRINT_BIZNAME, KovanEncodingUtils.getByteLength(PRINT_BIZNAME)),
        OWNERNAME(PRINT_OWNERNAME, KovanEncodingUtils.getByteLength(PRINT_OWNERNAME)),
        SERIALNO(PRINT_SERIALNO, KovanEncodingUtils.getByteLength(PRINT_OWNERNAME)),
        BIZNO(PRINT_BIZNO, KovanEncodingUtils.getByteLength(PRINT_BIZNO)),
        TID(PRINT_TID, KovanEncodingUtils.getByteLength(PRINT_TID)),
        CARDNO(PRINT_CARDNO, KovanEncodingUtils.getByteLength(PRINT_CARDNO)),
        AUTHNO(PRINT_AUTHNO, KovanEncodingUtils.getByteLength(PRINT_AUTHNO)),
        AUTHDT(PRINT_AUTHDT, KovanEncodingUtils.getByteLength(PRINT_AUTHDT)),
        STORE(PRINT_STROENO, KovanEncodingUtils.getByteLength(PRINT_STROENO)),
        ORGDT(PRINT_ORGDT, KovanEncodingUtils.getByteLength(PRINT_ORGDT)),
        AMOUNT(PRINT_AMOUNT, KovanEncodingUtils.getByteLength(PRINT_AMOUNT)),
        TAX(PRINT_TAX, KovanEncodingUtils.getByteLength(PRINT_TAX)),
        SFEE(PRINT_SFEE, KovanEncodingUtils.getByteLength(PRINT_SFEE)),
        FEE(PRINT_FEE, KovanEncodingUtils.getByteLength(PRINT_FEE)),
        TOTALAMT(PRINT_TOTALAMT, KovanEncodingUtils.getByteLength(PRINT_TOTALAMT)),
        PURCHASE(PRINT_PURCHASE, KovanEncodingUtils.getByteLength(PRINT_PURCHASE)),
        ISSUER(PRINT_ISSUER, KovanEncodingUtils.getByteLength(PRINT_ISSUER)),
        BIZTEL(PRINT_BIZTEL, KovanEncodingUtils.getByteLength(PRINT_BIZTEL)),
        SIGN(PRINT_SIGN, KovanEncodingUtils.getByteLength(PRINT_SIGN)),
        MINUS(PRINT_MINUS, KovanEncodingUtils.getByteLength(PRINT_MINUS)),
        MINUS2(PRINT_MINUS2, KovanEncodingUtils.getByteLength(PRINT_MINUS2)),;

        private String key;
        private int length;

        PRINT_KEY(String key, int length) {
            this.key = key;
            this.length = length;
        }

        protected String getKey() {
            return key;
        }

        protected void setKey(String key) {
            this.key = key;
        }

        protected int getLength() {
            return length;
        }

        protected void setLength(int length) {
            this.length = length;
        }
    }



    public enum USER_TYPE{
        @Keep
        CUSTOMER("[회원용]"),
        @Keep
        STORE("[가맹점용]"),
        @Keep
        CARD("[카드사용]"),
        @Keep
        NONE("");

        private String msg;

        USER_TYPE(String msg) {
            this.msg = msg;
        }
        @Keep
        public String getMsg() {
            return msg;
        }
    }

    public enum AUTH_TYPE{
        @Keep
        AUTH_CREDIT("S0","IC신용승인"),
        @Keep
        CANCEL_CREDIT("S1","IC신용취소"),
        @Keep
        AUTH_CREDIT_SWIP("10","신용카드승인"),
        @Keep
        CANCEL_CREDIT_SWIP("11","신용카드취소"),
        @Keep
        AUTH_SIMPLEPAY("Q0","간편결제승인"),
        @Keep
        CANCEL_SIMPLEPAY("Q1","간편결제취소"),
        @Keep
        AUTH_CASH("41","현금영수증승인"),
        @Keep
        CANCEL_CASH("42","현금영수증취소"),
        @Keep
        AUTH_CASH_PRINT("41_03","일반영수증승인"),
        @Keep
        CANCEL_CASH_PRINT("42_03","일반영수증취소"),
        @Keep
        NONE("","");

        private String code, msg;

        AUTH_TYPE(String code, String msg){
            this.code = code;this.msg = msg;
        }
        @Keep
        public String getCode() {
            return code;
        }
        @Keep
        public String getMsg() {
            return msg;
        }

        public static AUTH_TYPE findCode(String code){
            AUTH_TYPE res = null;
            for(AUTH_TYPE i : AUTH_TYPE.values()){
                if(i.getCode().equals(code)){
                    res = i;
                }
            }
            return res;
        }

        public static AUTH_TYPE findMsg(String msg){
            AUTH_TYPE res = null;
            for(AUTH_TYPE i : AUTH_TYPE.values()){
                if(i.getMsg().equals(msg)){
                    res = i;
                }
            }
            return res;
        }
    }
    // 230519 JDB
    // 영수증에 결제 구분 수단을 출력 하기 위하여 , 동반위 때문에 영수증에  QR/NFC 구분표시가 필요함
    public enum WCC_TYPE{
        @Keep
        KEYIN("(KI)"),
        @Keep
        IC("(IC)"),
        @Keep
        SWIPE("(SW)"),
        @Keep
        BACODE("(QR)"),
        @Keep
        QRCODE("(QR)"),
        @Keep
        NFC("(NFC)"),
        @Keep
        NONE("");

        private String msg;

        WCC_TYPE(String msg) {
            this.msg = msg;
        }
        @Keep
        public String getMsg() {
            return msg;
        }
    }
}
