package com.example.app_waitingticket.printer.fixedKichenPrint;

import org.json.JSONArray;

import androidx.annotation.Keep;

@Keep
public class KovanKitchenReceiptVo {

    private String posID;
    private String tableNumber;
    private String orderDate;
    private String orderTime;
    private String orderNumber;
    private String customerMsg;
    private String menuListCount;
    private JSONArray menuList;
    //private List<JSONObject> menuList;


    public KovanKitchenReceiptVo(String posID, String tableNumber, String orderDate, String orderTime, String orderNumber, String customerMsg, String menuListCount, JSONArray menuList ) {

        this.posID = posID;
        this.tableNumber = tableNumber;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.orderNumber = orderNumber;
        this.customerMsg = customerMsg;
        this.menuListCount = menuListCount;
        this.menuList = menuList;



    }
    @Keep
    public String getPosID() {
        return posID;
    }

    @Keep
    public void setPosID(String posID) {
        this.posID = posID;
    }

    @Keep
    public String geTableNumber() {
        return tableNumber;
    }

    @Keep
    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    @Keep
    public String getOrderDate() {
        return orderDate;
    }

    @Keep
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    @Keep
    public String getOrderTime() {
        return orderTime;
    }

    @Keep
    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    @Keep
    public String getOrderNumber() {
        return orderNumber;
    }

    @Keep
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Keep
    public String getCustomerMsg() {
        return customerMsg;
    }

    @Keep
    public void setCustomerMsg(String customerMsg) {
        this.customerMsg = customerMsg;
    }

    @Keep
    public String getMenuListCount() {
        return menuListCount;
    }

    @Keep
    public void setMenuListCount(String customerMsg) {
        this.menuListCount = menuListCount;
    }

    @Keep
    public JSONArray getMenuList() {
        return menuList;
    }

    @Keep
    public void setMenuList(JSONArray menuList) {
        this.menuList = menuList;
    }


}
