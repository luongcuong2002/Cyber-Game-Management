/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.Timer;

/**
 *
 * @author ADMIN
 */
public class Computer {
    private String computerName;
    private String status;
    private User userUsing;
    private Date timeStart;
    private int usedByMinute;
    private int remainingByMinute;
    private int price = 0;
    private Date currentDate;
    private ComputerGroup computerGroup;
    private Timer timer;
    
    private int count = 0;
    private int totalMinutes = 0;

    public Computer(String computerName, ComputerGroup computerGroup) {
        this.computerName = computerName;
        this.status = "Off";
        this.computerGroup = computerGroup;
    }
    
    public void turnOnComputer(User user){
        if(timer != null){
            timer.stop();
            timer = null;
        }
        for(int i = 0; i < computerGroup.getPriceForEachUserGroups().size(); i++){
            if(computerGroup.getPriceForEachUserGroups().get(i).getUserGroupName().equals(user.getUserGroupName())){
                price = computerGroup.getPriceForEachUserGroups().get(i).getPrice();
                break;
            }
        }
        if(price <= 0){
            System.out.println("Không tìm thấy giá dành cho người dùng!");
            return;
        }
        userUsing = user;
        timeStart = new Date();
        totalMinutes = convertMoneyToTimeRemaining(user.getRemainingAmount(), price);
        usedByMinute = 0;
        remainingByMinute = totalMinutes;
        currentDate = new Date();
        status = "Using";
        
        count = 0;
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                count++;
                usedByMinute = count / 60;
                remainingByMinute = totalMinutes - usedByMinute;
                user.setRemainingAmount(convertTimeRemainingToMoney(remainingByMinute, price));
            }
        });
        timer.start();
    }
    
    public void turnOffComputer(){
        if(timer != null){
            timer.stop();
            timer = null;
        }
        price = 0;
        userUsing = null;
        timeStart = null;
        totalMinutes = 0;
        usedByMinute = 0;
        remainingByMinute = 0;
        currentDate = null;
        status = "Off";
    }
    
    public int convertMoneyToTimeRemaining(int remainingAmount, int price){
        return Math.round((remainingAmount / (float) price) * 60);
    }
    
    public int convertTimeRemainingToMoney(int remainingByMninute, int price){
        return (int) ((remainingByMninute / 60.0) * price);
    }
    
    public String getComputerName() {
        return computerName;
    }

    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUserUsing() {
        return userUsing;
    }

    public void setUserUsing(User userUsing) {
        this.userUsing = userUsing;
    }

    public Date getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Date timeStart) {
        this.timeStart = timeStart;
    }

    public int getUsedByMinute() {
        return usedByMinute;
    }

    public void setUsedByMinute(int usedByMinute) {
        this.usedByMinute = usedByMinute;
    }

    public int getRemainingByMinute() {
        return remainingByMinute;
    }

    public void setRemainingByMinute(int remainingByMinute) {
        this.remainingByMinute = remainingByMinute;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public ComputerGroup getComputerGroup() {
        return computerGroup;
    }

    public void setComputerGroup(ComputerGroup computerGroup) {
        this.computerGroup = computerGroup;
    }
    
    
    
}
