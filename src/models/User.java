package models;

public class User {
    private String userName;
    private String password;
    private int remainingAmount;
    private String userGroupName;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.remainingAmount = 0;
    }

    public User(String userName, String password, int topUpAmount, int usedAmount, int remainingAmount) {
        this.userName = userName;
        this.password = password;
        this.remainingAmount = remainingAmount;
    }
    
    
    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getUserGroupName() {
        return userGroupName;
    }

    public void setUserGroupName(String userGroupName) {
        this.userGroupName = userGroupName;
    }
    
    
}
