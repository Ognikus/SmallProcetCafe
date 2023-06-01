package com.example.proectcafe;

public class Orders {

    private Integer id;
    private String ordersname;
    private String orderssize;
    private Integer orderssum;
    private Integer orderscount;
    private String ordersseller;


    public Orders(String id, String ordersname, String orderssize, String orderscount, String orderssum, String ordersseller) {
        this.id = (id != null && !id.trim().isEmpty()) ? Integer.valueOf(id) : null;
        this.ordersname = ordersname;
        this.orderssize = orderssize;
        this.orderssum = (orderssum != null && !orderssum.trim().isEmpty()) ? Integer.valueOf(orderssum) : null;
        this.orderscount = Integer.valueOf(orderscount);
        this.ordersseller = ordersseller;
    }

    public Integer getOrderscount() {
        return orderscount;
    }

    public void setOrderscount(Integer orderscount) {
        this.orderscount = orderscount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrdersname() {
        return ordersname;
    }

    public void setOrdersname(String ordersname) {
        this.ordersname = ordersname;
    }

    public String getOrderssize() {
        return orderssize;
    }

    public void setOrderssize(String orderssize) {
        this.orderssize = orderssize;
    }

    public Integer getOrderssum() {
        return orderssum;
    }

    public void setOrderssum(Integer orderssum) {
        this.orderssum = orderssum;
    }

    public String getOrdersseller() {
        return ordersseller;
    }

    public void setOrdersseller(String ordersseller) {
        this.ordersseller = ordersseller;
    }
}
