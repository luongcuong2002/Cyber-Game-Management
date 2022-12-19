/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import com.google.gson.annotations.Expose;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.Timer;
import views.dialog.BillDialog;
import views.tabs.ComputerClient;

/**
 *
 * @author ADMIN
 */
public class Computer {

    @Expose(serialize = true, deserialize = true)
    private String computerName;
    
    @Expose(serialize = true, deserialize = true)
    private ComputerGroup computerGroup;
    
    @Expose(serialize = false, deserialize = false)
    private String status;
    
    @Expose(serialize = false, deserialize = false)
    private User userUsing;
    
    @Expose(serialize = false, deserialize = false)
    private Date timeStart;
    
    @Expose(serialize = false, deserialize = false)
    private int usedBySecond;
    
    @Expose(serialize = false, deserialize = false)
    private int remainingBySecond;
    
    @Expose(serialize = false, deserialize = false)
    private int price = 0;
    
    @Expose(serialize = false, deserialize = false)
    private Date currentDate;
    
    @Expose(serialize = false, deserialize = false)
    private Timer timer;
    
    @Expose(serialize = false, deserialize = false)
    private String note = "";
    
    @Expose(serialize = false, deserialize = false)
    private int totalMitute = 0;

    @Expose(serialize = false, deserialize = false)
    private ArrayList<ServiceCanBeOrdered> listServicesOrdered = new ArrayList<ServiceCanBeOrdered>();
    
    @Expose(serialize = false, deserialize = false)
    private ArrayList<TransactionTransfer> listTransactionsTransfer = new ArrayList<TransactionTransfer>();

    public Computer(String computerName, ComputerGroup computerGroup) {
        this.computerName = computerName;
        this.status = "Off";
        this.computerGroup = computerGroup;
    }

    public void turnOnComputer(User user, ComputerClient rootView) {
        if (timer != null) {
            timer.stop();
            timer = null;
        }
        if (!user.getUserGroupName().equals("Admin")) {
            for (int i = 0; i < computerGroup.getPriceForEachUserGroups().size(); i++) {
                if (computerGroup.getPriceForEachUserGroups().get(i).getUserGroupName().equals(user.getUserGroupName())) {
                    price = computerGroup.getPriceForEachUserGroups().get(i).getPrice();
                    break;
                }
            }
            if (price <= 0) {
                System.out.println("Không tìm thấy giá dành cho người dùng!");
                return;
            }
            if (user.getUserGroupName().equals("Guest") && !user.isIsPrepaid()) {
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
                if (!user.getUserGroupName().equals("Admin")) {
                    remainingBySecond = totalMitute - usedBySecond;
                    user.setRemainingAmount(convertTimeRemainingToMoney(remainingBySecond));
                    if (user.getRemainingAmount() <= 0) {
                        user.setRemainingAmount(0);
                        turnOffComputer(rootView);
                    }
                }
                rootView.refreshTable();
            }
        });
        timer.start();
    }

    public void turnOffComputer(ComputerClient rootView) {
        if (timer != null) {
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
        listTransactionsTransfer.clear();
        listServicesOrdered.clear();
        rootView.refreshTable();
    }

    public void charge(ComputerClient rootView) {
        BillDialog bill = new BillDialog(rootView, this);
        bill.setVisible(true);
    }

    public void addTransactionTransfer(String fromUserName, int timeFee, int serviceFee) {
        listTransactionsTransfer.add(new TransactionTransfer(fromUserName, timeFee, serviceFee));
    }

    public String getServiceFeeDescriptionForTransferTransaction() {
        int totalAmount = 0;
        String usersName = "";
        for (int i = 0; i < listTransactionsTransfer.size(); i++) {
            totalAmount += listTransactionsTransfer.get(i).getServiceFee();
            if (i != 0) {
                usersName += ", ";
            }
            usersName += ("Máy: " + listTransactionsTransfer.get(i).getFromUser());
        }
        return this.getUserUsing().getUserGroupName() + " " + this.getUserUsing().getUserName()
                + " đã trả " + NumberFormat.getNumberInstance().format(totalAmount) + " đồng"
                + " phí dịch vụ được chuyển đến từ " + "[ " + usersName + " ]";
    }

    public String getTimeFeeDescriptionForTransferTransaction() {
        int totalAmount = 0;
        String usersName = "";
        for (int i = 0; i < listTransactionsTransfer.size(); i++) {
            totalAmount += listTransactionsTransfer.get(i).getTimeFee();
            if (i != 0) {
                usersName += ", ";
            }
            usersName += ("Máy: " + listTransactionsTransfer.get(i).getFromUser());
        }
        return this.getUserUsing().getUserGroupName() + " " + this.getUserUsing().getUserName()
                + " đã trả " + NumberFormat.getNumberInstance().format(totalAmount) + " đồng"
                + " phí thời gian được chuyển đến từ " + "[ " + usersName + " ]";
    }
    
    public int getServiceFeeTransferred() {
        int total = 0;
        for (int i = 0; i < listTransactionsTransfer.size(); i++) {
            total += listTransactionsTransfer.get(i).getServiceFee();
        }
        return total;
    }

    public int getTimeFeeTransferred() {
        int total = 0;
        for (int i = 0; i < listTransactionsTransfer.size(); i++) {
            total += listTransactionsTransfer.get(i).getTimeFee();
        }
        return total;
    }

    public int getServiceFee() {
        int total = 0;
        for (int i = 0; i < listServicesOrdered.size(); i++) {
            total += listServicesOrdered.get(i).getTotalFee();
        }
        return total;
    }
    
    public int getTimeFee() {
        return Math.round((convertTimeRemainingToMoney(usedBySecond)) / 1000.0f) * 1000;
    }

    public int getTotalCharge() {
        int total = 0;
        total += convertTimeRemainingToMoney(usedBySecond);
        total += getServiceFee();
        for (int i = 0; i < listTransactionsTransfer.size(); i++) {
            total += listTransactionsTransfer.get(i).getTimeFee();
            total += listTransactionsTransfer.get(i).getServiceFee();
        }
        return total;
    }

    public int convertMoneyToTimeRemaining(int remainingAmount) {
        return Math.round((remainingAmount / (float) price) * 3600);
    }

    public int convertTimeRemainingToMoney(int remainingBySecond) {
        int total = (int) ((remainingBySecond / 3600.0) * price);
        if(total < 1000){
            return 1000;
        }
        return total;
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

    public ArrayList<ServiceCanBeOrdered> getListServicesOrdered() {
        return listServicesOrdered;
    }

    public void setListServicesOrdered(ArrayList<ServiceCanBeOrdered> listServicesOrdered) {
        this.listServicesOrdered = listServicesOrdered;
    }

    public ArrayList<TransactionTransfer> getListTransactionsTransfer() {
        return listTransactionsTransfer;
    }

    public void setListTransactionsTransfer(ArrayList<TransactionTransfer> listTransactionsTransfer) {
        this.listTransactionsTransfer = listTransactionsTransfer;
    }

}
