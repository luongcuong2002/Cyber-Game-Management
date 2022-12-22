/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author ADMIN
 */
public class ProductCanBeOrdered {
    private ProductItem productItem;
    private int number;
    private boolean isProvided;

    public ProductCanBeOrdered(ProductItem productItem, int number) {
        this.productItem = productItem;
        this.number = number;
        this.isProvided = false;
    }
    
    public ProductCanBeOrdered(ProductItem productItem, int number, boolean isProvided) {
        this.productItem = productItem;
        this.number = number;
        this.isProvided = isProvided;
    }
    
    public int getTotalFee(){
        if(isProvided){
            return number * productItem.getPrice();
        }else{
            return 0;
        }
    }
    
    public ProductItem getProductItem() {
        return productItem;
    }

    public void setProductItem(ProductItem productItem) {
        this.productItem = productItem;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isIsProvided() {
        return isProvided;
    }

    public void setIsProvided(boolean isProvided) {
        this.isProvided = isProvided;
    }
}
