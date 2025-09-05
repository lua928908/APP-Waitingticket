package com.example.app_waitingticket.printer;

import java.util.List;

public class ByMenu {
    private String byFIXKiMenuName;
    private String byFIXKiMenuCount;
    private String byFIXKiMenuOptListCount;
    private List<ByOption> byFIXKIMenuOptList;

    public String getByFIXKiMenuName() {
        return byFIXKiMenuName;
    }

    public void setByFIXKiMenuName(String byFIXKiMenuName) {
        this.byFIXKiMenuName = byFIXKiMenuName;
    }

    public String getByFIXKiMenuCount() {
        return byFIXKiMenuCount;
    }

    public void setByFIXKiMenuCount(String byFIXKiMenuCount) {
        this.byFIXKiMenuCount = byFIXKiMenuCount;
    }

    public String getByFIXKiMenuOptListCount() {
        return byFIXKiMenuOptListCount;
    }

    public void setByFIXKiMenuOptListCount(String byFIXKiMenuOptListCount) {
        this.byFIXKiMenuOptListCount = byFIXKiMenuOptListCount;
    }

    public List<ByOption> getByFIXKIMenuOptList() {
        return byFIXKIMenuOptList;
    }

    public void setByFIXKIMenuOptList(List<ByOption> byFIXKIMenuOptList) {
        this.byFIXKIMenuOptList = byFIXKIMenuOptList;
    }
}
