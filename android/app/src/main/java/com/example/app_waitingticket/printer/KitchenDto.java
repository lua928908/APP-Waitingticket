package com.example.app_waitingticket.printer;

import java.util.List;

public class KitchenDto {
    private String byDeviceIndex;
    private String byFIXKiPOSNum;
    private String byFIXKiTableNum;
    private String byFIXKiOrderDate;
    private String byFIXKiOrderTime;
    private String byFIXKiUnqOrderNum;
    private String byFIXKiOrderDatea;

    private String byFIXKiMenuListCount;
    private List<ByMenu> byFIXKiMenuList;


    public String getByDeviceIndex() {
        return byDeviceIndex;
    }

    public void setByDeviceIndex(String byDeviceIndex) {
        this.byDeviceIndex = byDeviceIndex;
    }

    public String getByFIXKiPOSNum() {
        return byFIXKiPOSNum;
    }

    public void setByFIXKiPOSNum(String byFIXKiPOSNum) {
        this.byFIXKiPOSNum = byFIXKiPOSNum;
    }

    public String getByFIXKiTableNum() {
        return byFIXKiTableNum;
    }

    public void setByFIXKiTableNum(String byFIXKiTableNum) {
        this.byFIXKiTableNum = byFIXKiTableNum;
    }

    public String getByFIXKiOrderDate() {
        return byFIXKiOrderDate;
    }

    public void setByFIXKiOrderDate(String byFIXKiOrderDate) {
        this.byFIXKiOrderDate = byFIXKiOrderDate;
    }

    public String getByFIXKiOrderTime() {
        return byFIXKiOrderTime;
    }

    public void setByFIXKiOrderTime(String byFIXKiOrderTime) {
        this.byFIXKiOrderTime = byFIXKiOrderTime;
    }

    public String getByFIXKiUnqOrderNum() {
        return byFIXKiUnqOrderNum;
    }

    public void setByFIXKiUnqOrderNum(String byFIXKiUnqOrderNum) {
        this.byFIXKiUnqOrderNum = byFIXKiUnqOrderNum;
    }

    public String getByFIXKiOrderDatea() {
        return byFIXKiOrderDatea;
    }

    public void setByFIXKiOrderDatea(String byFIXKiOrderDatea) {
        this.byFIXKiOrderDatea = byFIXKiOrderDatea;
    }

    public String getByFIXKiMenuListCount() {
        return byFIXKiMenuListCount;
    }

    public void setByFIXKiMenuListCount(String byFIXKiMenuListCount) {
        this.byFIXKiMenuListCount = byFIXKiMenuListCount;
    }

    public List<ByMenu> getByFIXKiMenuList() {
        return byFIXKiMenuList;
    }

    public void setByFIXKiMenuList(List<ByMenu> byFIXKiMenuList) {
        this.byFIXKiMenuList = byFIXKiMenuList;
    }
}
