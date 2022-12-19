/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import com.google.gson.annotations.Expose;

/**
 *
 * @author ADMIN
 */
public class ServiceItem {
    
    @Expose(serialize = true, deserialize = true)
    private String name;
    
    @Expose(serialize = true, deserialize = true)
    private int price;
    
    @Expose(serialize = true, deserialize = true)
    private String unit;
    
    @Expose(serialize = true, deserialize = true)
    private int stock;
    
    @Expose(serialize = true, deserialize = true)
    private String category;

    public ServiceItem(String name, int price, String unit, String category) {
        this.name = name;
        this.price = price;
        this.unit = unit;
        this.stock = -1;
        this.category = category;
    }
    
    public ServiceItem(String name, int price, String unit, int stock, String category) {
        this.name = name;
        this.price = price;
        this.unit = unit;
        this.stock = stock;
        this.category = category;
    }
    
    public ServiceItem getCloneService(){
        return new ServiceItem(this.name, this.price, this.unit, this.stock, this.category);
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
}
