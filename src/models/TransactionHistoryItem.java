/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import com.google.gson.annotations.Expose;
import java.util.Date;

/**
 *
 * @author ADMIN
 */
public class TransactionHistoryItem {
    
    @Expose(serialize = true, deserialize = true)
    private String userName;
    
    @Expose(serialize = true, deserialize = true)
    private Date timeStart;
    
    @Expose(serialize = true, deserialize = true)
    private Date timeMadeTransaction;
    
    @Expose(serialize = true, deserialize = true)
    private int amount;
    
    @Expose(serialize = true, deserialize = true)
    private int timeUsedByMinute;
    
    @Expose(serialize = true, deserialize = true)
    private String description;

    public TransactionHistoryItem(String userName, Date timeStart, Date timeMadeTransaction, int amount, int timeUsedByMinute, String description) {
        this.userName = userName;
        this.timeStart = timeStart;
        this.timeMadeTransaction = timeMadeTransaction;
        this.amount = amount;
        this.timeUsedByMinute = timeUsedByMinute;
        this.description = description;
    }
    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Date timeStart) {
        this.timeStart = timeStart;
    }

    public Date getTimeMadeTransaction() {
        return timeMadeTransaction;
    }

    public void setTimeMadeTransaction(Date timeMadeTransaction) {
        this.timeMadeTransaction = timeMadeTransaction;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getTimeUsedByMinute() {
        return timeUsedByMinute;
    }

    public void setTimeUsedByMinute(int timeUsedByMinute) {
        this.timeUsedByMinute = timeUsedByMinute;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
