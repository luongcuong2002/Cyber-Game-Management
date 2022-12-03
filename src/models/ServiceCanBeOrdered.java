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
public class ServiceCanBeOrdered {
    private ServiceItem serviceItem;
    private int number;

    public ServiceCanBeOrdered(ServiceItem serviceItem, int number) {
        this.serviceItem = serviceItem;
        this.number = number;
    }
    
    public int getTotalFee(){
        return number * serviceItem.getPrice();
    }
    
    public ServiceItem getServiceItem() {
        return serviceItem;
    }

    public void setServiceItem(ServiceItem serviceItem) {
        this.serviceItem = serviceItem;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    
    
}
