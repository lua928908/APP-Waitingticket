package com.example.app_waitingticket.printer.fixedPrint;

import org.json.JSONArray;

public class KovanReceiptBuilder {
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
    private KovanPrintDefine.WCC_TYPE wccType; // 230519 JDB 영수증 결제 구분 출력하기 위하여 추가

    private JSONArray menuList;

    private String menuListCount;

    public KovanReceiptBuilder setUserType(KovanPrintDefine.USER_TYPE userType) {
        this.userType = userType;
        return this;
    }

    public KovanReceiptBuilder setAuthType(KovanPrintDefine.AUTH_TYPE authType) {
        this.authType = authType;
        return this;
    }

    public KovanReceiptBuilder setTid(String tid) {
        this.tid = tid;
        return this;
    }

    public KovanReceiptBuilder setBizNo(String bizNo) {
        this.bizNo = bizNo;
        return this;
    }

    public KovanReceiptBuilder setBizName(String bizName) {
        this.bizName = bizName;
        return this;
    }

    public KovanReceiptBuilder setOwnerName(String ownerName) {
        this.ownerName = ownerName;
        return this;
    }

    public KovanReceiptBuilder setBizTel(String bizTel) {
        this.bizTel = bizTel;
        return this;
    }

    public KovanReceiptBuilder setBizAddr(String bizAddr) {
        this.bizAddr = bizAddr;
        return this;
    }

    public KovanReceiptBuilder setPurchase(String purchase) {
        this.purchase = purchase;
        return this;
    }

    public KovanReceiptBuilder setIssuer(String issuer) {
        this.issuer = issuer;
        return this;
    }

    public KovanReceiptBuilder setInstall(String install) {
        this.install = install;
        return this;
    }

    public KovanReceiptBuilder setCardNo(String cardNo) {
        this.cardNo = cardNo;
        return this;
    }

    public KovanReceiptBuilder setAuthDt(String authDt) {
        this.authDt = authDt;
        return this;
    }

    public KovanReceiptBuilder setAuthNo(String authNo) {
        this.authNo = authNo;
        return this;
    }

    public KovanReceiptBuilder setSerialNo(String serialNo) {
        this.serialNo = serialNo;
        return this;
    }

    public KovanReceiptBuilder setStoreNo(String storeNo) {
        this.storeNo = storeNo;
        return this;
    }

    public KovanReceiptBuilder setOrgDt(String orgDt) {
        this.orgDt = orgDt;
        return this;
    }

    public KovanReceiptBuilder setAmount(String amount) {
        this.amount = amount;
        return this;
    }

    public KovanReceiptBuilder setTax(String tax) {
        this.tax = tax;
        return this;
    }

    public KovanReceiptBuilder setsFee(String sFee) {
        this.sFee = sFee;
        return this;
    }

    public KovanReceiptBuilder setFee(String fee) {
        this.fee = fee;
        return this;
    }

    public KovanReceiptBuilder setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public KovanReceiptBuilder setPrintMsg1(String printMsg1) {
        this.printMsg1 = printMsg1;
        return this;
    }

    public KovanReceiptBuilder setPrintMsg2(String printMsg2) {
        this.printMsg2 = printMsg2;
        return this;
    }

    public KovanReceiptBuilder setPrintMsg3(String printMsg3) {
        this.printMsg3 = printMsg3;
        return this;
    }

    public KovanReceiptBuilder setSignData(String signData) {
        this.signData = signData;
        return this;
    }

    public KovanReceiptBuilder setEtcMsg(String etcMsg) {
        this.etcMsg = etcMsg;
        return this;
    }

    public KovanReceiptBuilder setDccFlag(String dccFlag) {
        this.dccFlag = dccFlag;
        return this;
    }

    public KovanReceiptBuilder setWccType(KovanPrintDefine.WCC_TYPE wccType) {
        this.wccType = wccType;
        return this;
    }
    public KovanReceiptBuilder setMenuList(JSONArray menuList) {
        this.menuList = menuList;
        return this;
    }

    public KovanReceiptBuilder setMenuListCount(String menuListCount) {
        this.menuListCount = menuListCount;
        return this;
    }
    public KovanReceiptVo build(){
        KovanReceiptVo vo = new KovanReceiptVo(userType, authType,  tid,  bizNo,  bizName,  ownerName,  bizTel,  bizAddr,  purchase,  issuer,  install,  cardNo,  authDt,  authNo,  serialNo,  storeNo,  orgDt,  amount,  tax,  sFee,  fee,  totalAmount,  printMsg1,  printMsg2,  printMsg3,  signData,  etcMsg, dccFlag, wccType , menuListCount , menuList);

        return vo;
    }
}
