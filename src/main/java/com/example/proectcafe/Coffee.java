package com.example.proectcafe;

public class Coffee {
    private Integer id;
    private String coffename;
    private String coffesize;
    private Integer coffeprice;

    public Coffee(String id, String coffename, String coffesize, String coffeprice) {
        this.id = (id != null && !id.trim().isEmpty()) ? Integer.valueOf(id) : null;
        this.coffename = coffename;
        this.coffesize = coffesize;
        this.coffeprice = Integer.valueOf(coffeprice);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCoffename() {
        return coffename;
    }

    public void setCoffename(String coffename) {
        this.coffename = coffename;
    }

    public String getCoffesize() {
        return coffesize;
    }

    public void setCoffesize(String coffesize) {
        this.coffesize = coffesize;
    }

    public Integer getCoffeprice() {
        return coffeprice;
    }

    public void setCoffeprice(Integer coffeprice) {
        this.coffeprice = coffeprice;
    }
}
