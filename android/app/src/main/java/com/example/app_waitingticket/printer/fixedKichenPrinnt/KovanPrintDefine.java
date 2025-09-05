package com.example.app_waitingticket.printer.fixedKichenPrint;

import androidx.annotation.Keep;

public class KovanPrintDefine {
    //protected static final String VERSION_NAME = BuildConfig.VERSION_NAME;

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
    protected static final String PRINT_POSID = "POS 번호:";
    protected static final String PRINT_TABLENUMBER = "테이블번호:";
    protected static final String PRINT_ORDERDATE = "주문일자:";
    protected static final String PRINT_ORDERNUMBER = "주문번호:";
    protected static final String PRINT_TITLEMENU = "상품명";
    protected static final String PRINT_TITLEMENUNUM = "수량";
    protected static final String PRINT_CUSTOMERMSG = "요청사항:";
    protected static final String PRINT_MINUS = "   -> ";

    protected enum PRINT_KEY{
        NONE("",0),
        POSID(PRINT_POSID, KovanEncodingUtils.getByteLength(PRINT_POSID)),
        TABLENUMBER(PRINT_TABLENUMBER, KovanEncodingUtils.getByteLength(PRINT_TABLENUMBER)),
        ORDERDATE(PRINT_ORDERDATE, KovanEncodingUtils.getByteLength(PRINT_ORDERDATE)),
        ORDERNUMBER(PRINT_ORDERNUMBER, KovanEncodingUtils.getByteLength(PRINT_ORDERNUMBER)),
        TITLEMENU(PRINT_TITLEMENU, KovanEncodingUtils.getByteLength(PRINT_TITLEMENU)),
        TITLEMENUNUM(PRINT_TITLEMENUNUM, KovanEncodingUtils.getByteLength(PRINT_TITLEMENUNUM)),
        CUSTOMERMSG(PRINT_CUSTOMERMSG, KovanEncodingUtils.getByteLength(PRINT_CUSTOMERMSG)),

        MINUS(PRINT_MINUS, KovanEncodingUtils.getByteLength(PRINT_MINUS)),;

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

}
