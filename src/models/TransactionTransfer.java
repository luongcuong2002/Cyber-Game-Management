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
public class TransactionTransfer {
    private String fromUser;
    private int timeFee;
    private int serviceFee;

    public TransactionTransfer(String fromUser, int timeFee, int serviceFee) {
        this.fromUser = fromUser;
        this.timeFee = timeFee;
        this.serviceFee = serviceFee;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public int getTimeFee() {
        return timeFee;
    }

    public void setTimeFee(int timeFee) {
        this.timeFee = timeFee;
    }

    public int getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(int serviceFee) {
        this.serviceFee = serviceFee;
    }
    
}
