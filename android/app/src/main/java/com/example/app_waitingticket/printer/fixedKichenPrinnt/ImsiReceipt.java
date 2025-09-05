package com.example.app_waitingticket.printer.fixedKichenPrint;

import java.util.List;

import com.example.app_waitingticket.printer.ByMenu;

public class ImsiReceipt {
    private String posID;
    private String tableNumber;
    private String orderDate;
    private String orderTime;
    private String orderNumber;
    private String customerMsg;
    private String menuListCount;

    List<ByMenu> menuList;

    public String getPosID() {
        return posID;
    }

    public void setPosID(String posID) {
        this.posID = posID;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCustomerMsg() {
        return customerMsg;
    }

    public void setCustomerMsg(String customerMsg) {
        this.customerMsg = customerMsg;
    }

    public String getMenuListCount() {
        return menuListCount;
    }

    public void setMenuListCount(String menuListCount) {
        this.menuListCount = menuListCount;
    }

    public List<ByMenu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<ByMenu> menuList) {
        this.menuList = menuList;
    }
}
