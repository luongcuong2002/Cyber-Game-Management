package models;

public class User {
    private String userName;
    private String password;
    private int remainingAmount;
    private String userGroupName;

    public User(String userName, String password, String userGroupName) {
        this.userName = userName.toUpperCase();
        this.password = password;
        this.remainingAmount = 0;
        this.userGroupName = userGroupName;
    }

    public User(String userName, String password, int remainingAmount, String userGroupName) {
        this.userName = userName.toUpperCase();
        this.password = password;
        this.remainingAmount = remainingAmount;
        this.userGroupName = userGroupName;
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

    public String getUserGroupName() {
        return userGroupName;
    }

    public void setUserGroupName(String userGroupName) {
        this.userGroupName = userGroupName;
    }
    
    
}
