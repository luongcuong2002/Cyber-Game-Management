package models;

import com.google.gson.annotations.Expose;
import data.Data;
import java.text.NumberFormat;
import java.util.Locale;

public class User {
    
    @Expose(serialize = true, deserialize = true)
    private String userName;
    
    @Expose(serialize = true, deserialize = true)
    private String password;
    
    @Expose(serialize = true, deserialize = true)
    private int remainingAmount;
    
    @Expose(serialize = true, deserialize = true)
    private String userGroupName;
    
    @Expose(serialize = true, deserialize = true)
    private boolean isPrepaid = false;

    public User(String userName, String password, String userGroupName) { // for member
        this.userName = userName.toUpperCase();
        this.password = password;
        this.remainingAmount = 0;
        this.userGroupName = userGroupName;
    }
    
    public User(String userName, int remainingAmount, String userGroupName, boolean isPrepaid) { // for prepaid
        this.userName = userName.toUpperCase();
        this.userGroupName = userGroupName;
        this.remainingAmount = remainingAmount;
        this.isPrepaid = isPrepaid;
    }

    public User(String userName, String userGroupName, boolean isPrepaid) { // for postpaid
        this.userName = userName.toUpperCase();
        this.userGroupName = userGroupName;
        this.isPrepaid = isPrepaid;
    }
    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName.toUpperCase();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(int remainingAmount) {
        this.remainingAmount = remainingAmount;
    }
    
    public void topUp(int topUpAmount){
        this.remainingAmount += topUpAmount;
        if(this.remainingAmount == 0) this.remainingAmount = 0;
        for(int i = 0; i < Data.listComputers.size(); i++){
            User user = Data.listComputers.get(i).getUserUsing();
            if(user != null && user.getUserName().equals(this.getUserName())){
                Data.listComputers.get(i).setTotalMitute(
                        Data.listComputers.get(i).convertMoneyToTimeRemaining(remainingAmount));
            }
        }
    }

    public String getUserGroupName() {
        return userGroupName;
    }

    public void setUserGroupName(String userGroupName) {
        this.userGroupName = userGroupName;
    }
    
    public String getRemainingAmountToString(){
        NumberFormat formatter = NumberFormat.getNumberInstance();
        return formatter.format(this.remainingAmount);
    }

    public boolean isIsPrepaid() {
        return isPrepaid;
    }

    public void setIsPrepaid(boolean isPrepaid) {
        this.isPrepaid = isPrepaid;
    }
    
}
