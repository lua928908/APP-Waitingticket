package com.example.app_waitingticket.printer.fixedKichenPrint;

import org.json.JSONArray;

import androidx.annotation.Keep;




@Keep
public class KovanKitchenReceiptBuilder {
    private String posID;
    private String tableNumber;
    private String orderDate;
    private String orderTime;
    private String orderNumber;
    private String customerMsg;
    private String menuListCount;
    private JSONArray menuList;

    public KovanKitchenReceiptBuilder setPosID(String posID) {
        this.posID = posID;
        return this;
    }

    public KovanKitchenReceiptBuilder setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
        return this;
    }

    public KovanKitchenReceiptBuilder setOrderDate(String orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public KovanKitchenReceiptBuilder setOrderTime(String orderTime) {
        this.orderTime = orderTime;
        return this;
    }

    public KovanKitchenReceiptBuilder setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public KovanKitchenReceiptBuilder setCustomerMsg(String customerMsg) {
        this.customerMsg = customerMsg;
        return this;
    }

    public KovanKitchenReceiptBuilder setMenuListCount(String menuListCount) {
        this.menuListCount = menuListCount;
        return this;
    }

    public KovanKitchenReceiptBuilder setMenuList(JSONArray menuList) {
        this.menuList = menuList;
        return this;
    }

    public KovanKitchenReceiptVo build(){
        KovanKitchenReceiptVo vo = new KovanKitchenReceiptVo(posID,tableNumber,orderDate,orderTime,orderNumber,customerMsg,menuListCount,menuList);
        return vo;
    }

//    public KovanKitchenReceiptBuilder SetJsonString( JSONObject jsonString){
//        Gson gson = new GsonBuilder().create();
//        try {
//            KovanKitchenReceiptVo vo = gson.fromJson(jsonString.toString(), KovanKitchenReceiptVo.class);
//            this.posID = vo.getPosID();
//            this.tableNumber = vo.geTableNumber();
//            this.orderDate = vo.getOrderDate();
//            this.orderTime = vo.getOrderTime();
//            this.orderNumber = vo.getOrderNumber();
//            this.customerMsg = vo.getCustomerMsg();
//            this.menuListCount = vo.getMenuListCount();
//            this.menuList =  vo.getMenuList();
//        }catch (JsonSyntaxException e){
//            Log.e("ERROR" , "JsonSyntaxException msg " + e.getMessage());
//        }
//        return this;
//    }

}
