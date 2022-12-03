/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import data.Data;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Timer;
import views.dialog.Bill;
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
    
    private ArrayList<ServiceOrdered> listServicesOrdered = new ArrayList<ServiceOrdered>();
    private Map<String, Integer> transactionTransferred = new HashMap<String, Integer>();

    public Computer(String computerName, ComputerGroup computerGroup) {
        this.computerName = computerName;
        this.status = "Off";
        this.computerGroup = computerGroup;
        transactionTransferred.put("timeFee", 0);
        transactionTransferred.put("serviceFee", 0);
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
        totalMitute = convertMoneyToTimeRemaining(user.getRemainingAmount());
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
                    user.setRemainingAmount(convertTimeRemainingToMoney(remainingBySecond));
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
        transactionTransferred.replace("timeFee", 0);
        transactionTransferred.replace("serviceFee", 0);
        rootView.refreshTable();
    }
    
    public void charge(ComputerClient rootView){
//        int totalAmount = convertTimeRemainingToMoney(usedBySecond, price);
//        final JComponent[] inputs = new JComponent[] {
//            new JLabel("Số tiền phải thanh toán"), new JLabel(totalAmount + ""),
//        };
//        int result = JOptionPane.showConfirmDialog(null, inputs, "Thanh toán " + this.computerName, JOptionPane.PLAIN_MESSAGE);
//        if (result == JOptionPane.OK_OPTION) {
//            turnOffComputer(rootView);
//        }
          Bill bill = new Bill(this);
          bill.setVisible(true);
    }
    
    public void transferTransaction(int timeFee, int serviceFee){
        if(transactionTransferred.get("timeFee") == null){
            transactionTransferred.put("timeFee", timeFee);
        }else{
            transactionTransferred.replace("timeFee", transactionTransferred.get("timeFee") + timeFee);
        }
        
        if(transactionTransferred.get("serviceFee") == null){
            transactionTransferred.put("serviceFee", serviceFee);
        }else{
            transactionTransferred.replace("serviceFee", transactionTransferred.get("serviceFee") + serviceFee);
        }
    }
    
    public int getServiceFee(){
        int total = 0;
        for(int i = 0; i < listServicesOrdered.size(); i++){
            total += listServicesOrdered.get(i).getTotalFee();
        }
        return total;
    }
    
    public int getTotalCharge(){
        int total = 0;
        total += convertTimeRemainingToMoney(usedBySecond);
        total += getServiceFee();
        total += getTransactionTransferred().getOrDefault("timeFee", 0);
        total += getTransactionTransferred().getOrDefault("serviceFee", 0);
        return total;
    }
    
    public int convertMoneyToTimeRemaining(int remainingAmount){
        return Math.round((remainingAmount / (float) price) * 3600);
    }
    
    public int convertTimeRemainingToMoney(int remainingBySecond){
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

    public ArrayList<ServiceOrdered> getListServicesOrdered() {
        return listServicesOrdered;
    }

    public void setListServicesOrdered(ArrayList<ServiceOrdered> listServicesOrdered) {
        this.listServicesOrdered = listServicesOrdered;
    }

    public Map<String, Integer> getTransactionTransferred() {
        return transactionTransferred;
    }

    public void setTransactionTransferred(Map<String, Integer> transactionTransferred) {
        this.transactionTransferred = transactionTransferred;
    }
    
}
