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
    private boolean isProvided;

    public ServiceCanBeOrdered(ServiceItem serviceItem, int number) {
        this.serviceItem = serviceItem;
        this.number = number;
        this.isProvided = false;
    }
    
    public ServiceCanBeOrdered(ServiceItem serviceItem, int number, boolean isProvided) {
        this.serviceItem = serviceItem;
        this.number = number;
        this.isProvided = isProvided;
    }
    
    public int getTotalFee(){
        if(isProvided){
            return number * serviceItem.getPrice();
        }else{
            return 0;
        }
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

    public boolean isIsProvided() {
        return isProvided;
    }

    public void setIsProvided(boolean isProvided) {
        this.isProvided = isProvided;
    }
}
