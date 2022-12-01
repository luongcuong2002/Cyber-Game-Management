/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import data.Data;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Timer;
import views.tabs.ComputerClient;

/**
 *
 * @author ADMIN
 */
public class Computer {
    private String computerName;
    private String status;
    private User userUsing;
    private Date timeStart;
    private int usedBySecond;
    private int remainingBySecond;
    private int price = 0;
    private Date currentDate;
    private ComputerGroup computerGroup;
    private Timer timer;
    private String note = "";
    private int totalMitute = 0;

    public Computer(String computerName, ComputerGroup computerGroup) {
        this.computerName = computerName;
        this.status = "Off";
        this.computerGroup = computerGroup;
    }
    
    public void turnOnComputer(User user, ComputerClient rootView){
        if(timer != null){
            timer.stop();
            timer = null;
        }
        if(!user.getUserGroupName().equals("Admin")){
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
            if(user.getUserGroupName().equals("Guest") && !user.isIsPrepaid()){
                user.setRemainingAmount(1000 * price); // postpaid user, remaining start with 1000h
            }
        }
        userUsing = user;
        timeStart = new Date();
        usedBySecond = 0;
        totalMitute = convertMoneyToTimeRemaining(user.getRemainingAmount(), price);
        remainingBySecond = totalMitute;
        currentDate = new Date();
        status = "Using";
        
        rootView.refreshTable();
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usedBySecond++;
                if(!user.getUserGroupName().equals("Admin")){
                    remainingBySecond = totalMitute - usedBySecond;
                    user.setRemainingAmount(convertTimeRemainingToMoney(remainingBySecond, price));
                    if(user.getRemainingAmount() <= 0){
                        user.setRemainingAmount(0);
                        turnOffComputer(rootView);
                    }
                }
                rootView.refreshTable();
            }
        });
        timer.start();
    }
    
    public void turnOffComputer(ComputerClient rootView){
        if(timer != null){
            timer.stop();
            timer = null;
        }
        price = 0;
        userUsing = null;
        timeStart = null;
        usedBySecond = 0;
        remainingBySecond = 0;
        currentDate = null;
        status = "Off";
        rootView.refreshTable();
    }
    
    public void charge(ComputerClient rootView){
        int totalAmount = convertTimeRemainingToMoney(usedBySecond, price);
        final JComponent[] inputs = new JComponent[] {
            new JLabel("Số tiền phải thanh toán"), new JLabel(totalAmount + ""),
        };
        int result = JOptionPane.showConfirmDialog(null, inputs, "Thanh toán " + this.computerName, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            turnOffComputer(rootView);
        }
    }
    
    public int convertMoneyToTimeRemaining(int remainingAmount, int price){
        return Math.round((remainingAmount / (float) price) * 3600);
    }
    
    public int convertTimeRemainingToMoney(int remainingBySecond, int price){
        return (int) ((remainingBySecond / 3600.0) * price);
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

    public int getUsedBySecond() {
        return usedBySecond;
    }

    public void setUsedBySecond(int usedBySecond) {
        this.usedBySecond = usedBySecond;
    }

    public int getRemainingBySecond() {
        return remainingBySecond;
    }

    public void setRemainingBySecond(int remainingBySecond) {
        this.remainingBySecond = remainingBySecond;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getTotalMitute() {
        return totalMitute;
    }

    public void setTotalMitute(int totalMitute) {
        this.totalMitute = totalMitute;
    }
    
}
