package com.example.app_waitingticket.printer.fixedPrint;

import org.json.JSONArray;

import androidx.annotation.Keep;

public class KovanReceiptVo {
    private KovanPrintDefine.USER_TYPE userType;
    private KovanPrintDefine.AUTH_TYPE authType;
    private String tid;
    private String bizNo;
    private String bizName;
    private String ownerName;
    private String bizTel;
    private String bizAddr;
    private String purchase;
    private String issuer;
    private String install;
    private String cardNo;
    private String authDt;
    private String authNo;
    private String serialNo; //거래 일련 번호
    private String storeNo;
    private String orgDt;
    private String amount;
    private String tax;
    private String sFee;
    private String fee;
    private String totalAmount;
    private String printMsg1;
    private String printMsg2;
    private String printMsg3;
    private String signData;
    private String etcMsg;
    private String dccFlag;
    private KovanPrintDefine.WCC_TYPE wccType;
    private JSONArray menuList;
    private String menuListCount;

    public KovanReceiptVo(KovanPrintDefine.USER_TYPE userType, KovanPrintDefine.AUTH_TYPE authType, String tid, String bizNo, String bizName, String ownerName, String bizTel, String bizAddr, String purchase, String issuer, String install, String cardNo, String authDt, String authNo, String serialNo, String storeNo, String orgDt, String amount, String tax, String sFee, String fee, String totalAmount, String printMsg1, String printMsg2, String printMsg3, String signData, String etcMsg, String dccFlag, KovanPrintDefine.WCC_TYPE wccType , String menuListCount , JSONArray menuList) {
        this.userType = userType;
        this.authType = authType;
        this.tid = tid;
        this.bizNo = bizNo;
        this.bizName = bizName;
        this.ownerName = ownerName;
        this.bizTel = bizTel;
        this.bizAddr = bizAddr;
        this.purchase = purchase;
        this.issuer = issuer;
        this.install = install;
        this.cardNo = cardNo;
        this.authDt = authDt;
        this.authNo = authNo;
        this.serialNo = serialNo;
        this.storeNo = storeNo;
        this.orgDt = orgDt;
        this.amount = amount;
        this.tax = tax;
        this.sFee = sFee;
        this.fee = fee;
        this.totalAmount = totalAmount;
        this.printMsg1 = printMsg1;
        this.printMsg2 = printMsg2;
        this.printMsg3 = printMsg3;
        this.signData = signData;
        this.etcMsg = etcMsg;
        this.dccFlag = dccFlag;
        this.wccType = wccType;
        this.menuListCount =  menuListCount;
        this.menuList = menuList;
    }

    @Keep
    public KovanPrintDefine.USER_TYPE getUserType() {
        return userType;
    }

    @Keep
    public void setUserType(KovanPrintDefine.USER_TYPE userType) {
        this.userType = userType;
    }

    @Keep
    public KovanPrintDefine.AUTH_TYPE getAuthType() {
        return authType;
    }

    @Keep
    public void setAuthType(KovanPrintDefine.AUTH_TYPE authType) {
        this.authType = authType;
    }

    @Keep
    public String getTid() {
        return tid;
    }

    @Keep
    public void setTid(String tid) {
        this.tid = tid;
    }
    @Keep
    public String getBizNo() {
        return bizNo;
    }
    @Keep
    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }
    @Keep
    public String getBizName() {
        return bizName;
    }
    @Keep
    public void setBizName(String bizName) {
        this.bizName = bizName;
    }
    @Keep
    public String getOwnerName() {
        return ownerName;
    }
    @Keep
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
    @Keep
    public String getBizTel() {
        return bizTel;
    }
    @Keep
    public void setBizTel(String bizTel) {
        this.bizTel = bizTel;
    }
    @Keep
    public String getBizAddr() {
        return bizAddr;
    }
    @Keep
    public void setBizAddr(String bizAddr) {
        this.bizAddr = bizAddr;
    }
    @Keep
    public String getPurchase() {
        return purchase;
    }
    @Keep
    public void setPurchase(String purchase) {
        this.purchase = purchase;
    }
    @Keep
    public String getInstall() {
        return install;
    }
    @Keep
    public void setInstall(String install) {
        this.install = install;
    }
    @Keep
    public String getCardNo() {
        return cardNo;
    }
    @Keep
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
    @Keep
    public String getAuthDt() {
        return authDt;
    }
    @Keep
    public void setAuthDt(String authDt) {
        this.authDt = authDt;
    }
    @Keep
    public String getAuthNo() {
        return authNo;
    }
    @Keep
    public void setAuthNo(String authNo) {
        this.authNo = authNo;
    }
    @Keep
    public String getStoreNo() {
        return storeNo;
    }
    @Keep
    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
    }
    @Keep
    public String getAmount() {
        return amount;
    }
    @Keep
    public void setAmount(String amount) {
        this.amount = amount;
    }
    @Keep
    public String getTax() {
        return tax;
    }
    @Keep
    public void setTax(String tax) {
        this.tax = tax;
    }
    @Keep
    public String getsFee() {
        return sFee;
    }
    @Keep
    public void setsFee(String sFee) {
        this.sFee = sFee;
    }
    @Keep
    public String getFee() {
        return fee;
    }
    @Keep
    public void setFee(String fee) {
        this.fee = fee;
    }
    @Keep
    public String getTotalAmount() {
        return totalAmount;
    }
    @Keep
    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
    @Keep
    public String getPrintMsg1() {
        return printMsg1;
    }
    @Keep
    public void setPrintMsg1(String printMsg1) {
        this.printMsg1 = printMsg1;
    }
    @Keep
    public String getPrintMsg2() {
        return printMsg2;
    }
    @Keep
    public void setPrintMsg2(String printMsg2) {
        this.printMsg2 = printMsg2;
    }
    @Keep
    public String getPrintMsg3() {
        return printMsg3;
    }
    @Keep
    public void setPrintMsg3(String printMsg3) {
        this.printMsg3 = printMsg3;
    }
    @Keep
    public String getIssuer() {
        return issuer;
    }
    @Keep
    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }
    @Keep
    public String getSerialNo() {
        return serialNo;
    }
    @Keep
    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo.trim();
    }
    @Keep
    public String getOrgDt() {
        return orgDt;
    }
    @Keep
    public void setOrgDt(String orgDt) {
        this.orgDt = orgDt;
    }
    @Keep
    public String getSignData() {
        return signData;
    }

    /**
     * @param signData row byte base 64
     */
    @Keep
    public void setSignData(String signData) {
        this.signData = signData;
    }

    @Keep
    public String getEtcMsg() {
        return etcMsg;
    }

    @Keep
    public void setEtcMsg(String etcMsg) {
        this.etcMsg = etcMsg;
    }

    @Keep
    public String getDccFlag() {
        return dccFlag;
    }

    @Keep
    public void setDccFlag(String dccFlag) {
        this.dccFlag = dccFlag;
    }

    @Keep
    public KovanPrintDefine.WCC_TYPE getWccType() {
        return wccType;
    }
    @Keep
    public void setWccType(KovanPrintDefine.WCC_TYPE wccType) {
        this.wccType = wccType;
    }

    public JSONArray getMenuList() {
        return menuList;
    }

    public void setMenuList(JSONArray menuList) {
        this.menuList = menuList;
    }

    public String getMenuListCount() {
        return menuListCount;
    }

    public void setMenuListCount(String menuListCount) {
        this.menuListCount = menuListCount;
    }
}
