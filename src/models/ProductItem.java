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
public class ProductItem {
    
    @Expose(serialize = true, deserialize = true)
    private String name;
    
    @Expose(serialize = true, deserialize = true)
    private int price;
    
    @Expose(serialize = true, deserialize = true)
    private String unit;
    
    @Expose(serialize = true, deserialize = true)
    private String category;

    public ProductItem(String name, int price, String unit, String category) {
        this.name = name;
        this.price = price;
        this.unit = unit;
        this.category = category;
    }
    
    public ProductItem getCloneProduct(){
        return new ProductItem(this.name, this.price, this.unit, this.category);
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
}
