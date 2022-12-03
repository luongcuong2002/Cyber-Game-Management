package data;

import java.util.ArrayList;
import models.Computer;
import models.ComputerGroup;
import models.ServiceCategory;
import models.ServiceItem;
import models.User;
import models.UserGroup;

public abstract class Data {
    public static ArrayList<ComputerGroup> computerGroups = new ArrayList<>();
    public static ArrayList<Computer> listComputers = new ArrayList<>();
    public static ArrayList<User> listUsers = new ArrayList<>();
    public static ArrayList<ServiceCategory> listServiceCategories = new ArrayList<>();
    public static ArrayList<ServiceCategory> listServiceItem = new ArrayList<>();
    
    public static void getData(){
        
        // read data from database here
        
        setUpComputerGroups();
        setUpListComputers();
        setUpListUsers();
        setUpListServiceCategories();
    }
    
    public static void saveData(){
        
    }
    
    private static void setUpComputerGroups(){
        ArrayList<UserGroup> userGroups = new ArrayList<>();
        userGroups.add(new UserGroup("Guest", 6000));
        userGroups.add(new UserGroup("Member", 5500));
        computerGroups.add(new ComputerGroup("NEW", userGroups));
        
        ArrayList<UserGroup> userGroups1 = new ArrayList<>();
        userGroups.add(new UserGroup("Guest", 4500));
        userGroups.add(new UserGroup("Member", 4000));
        computerGroups.add(new ComputerGroup("OLD", userGroups));
    }
    
    private static void setUpListComputers(){
        listComputers.add(new Computer("MAY01", computerGroups.get(0)));
        listComputers.add(new Computer("MAY02", computerGroups.get(0)));
        listComputers.add(new Computer("MAY03", computerGroups.get(0)));
        listComputers.add(new Computer("MAY04", computerGroups.get(0)));
        listComputers.add(new Computer("MAY05", computerGroups.get(0)));
        listComputers.add(new Computer("MAY06", computerGroups.get(0)));
        listComputers.add(new Computer("MAY07", computerGroups.get(0)));
        listComputers.add(new Computer("MAY08", computerGroups.get(0)));
        listComputers.add(new Computer("MAY09", computerGroups.get(1)));
        listComputers.add(new Computer("MAY10", computerGroups.get(1)));
        listComputers.add(new Computer("MAY11", computerGroups.get(1)));
        listComputers.add(new Computer("MAY12", computerGroups.get(1)));
        listComputers.add(new Computer("MAY13", computerGroups.get(1)));
        listComputers.add(new Computer("MAY14", computerGroups.get(1)));
    }
    
    private static void setUpListUsers(){
        listUsers.add(new User("NLcuong", "1", "Member"));
        listUsers.get(listUsers.size() - 1).setRemainingAmount(10000);
        listUsers.add(new User("User1", "1", "Member"));
        listUsers.get(listUsers.size() - 1).setRemainingAmount(15000);
        listUsers.add(new User("User2", "1", "Member"));
        listUsers.add(new User("User3", "1", "Member"));
        listUsers.add(new User("User4", "1", "Member"));
        listUsers.add(new User("User5", "1", "Member"));
        listUsers.add(new User("User6", "1", "Member"));
        listUsers.add(new User("User7", "1", "Member"));
    }
    
    private static void setUpListServiceCategories(){
        listServiceCategories.add(new ServiceCategory("ĐỒ VẶT"));
        listServiceCategories.get(0).addServiceItem(new ServiceItem("BIM THƯỜNG", 5000, "VNĐ", "ĐỒ VẶT"));
        listServiceCategories.get(0).addServiceItem(new ServiceItem("BIM POCA", 7000, "VNĐ", "ĐỒ VẶT"));
        listServiceCategories.get(0).addServiceItem(new ServiceItem("BIM MIX", 12000, "VNĐ", "ĐỒ VẶT"));
        listServiceCategories.get(0).addServiceItem(new ServiceItem("ĐẬU PHỘNG", 15000, "VNĐ", "ĐỒ VẶT"));
        
        listServiceCategories.add(new ServiceCategory("MÌ TÔM"));
        listServiceCategories.get(1).addServiceItem(new ServiceItem("1 MÌ 1 TRỨNG 1 XÚC XÍCH", 30000, "VNĐ", "MÌ TÔM"));
        listServiceCategories.get(1).addServiceItem(new ServiceItem("2 MÌ 1 TRỨNG 1 XÚC XÍCH", 35000, "VNĐ", "MÌ TÔM"));
        listServiceCategories.get(1).addServiceItem(new ServiceItem("XÚC XÍCH RÁN", 10000, "VNĐ", "MÌ TÔM"));
        listServiceCategories.get(1).addServiceItem(new ServiceItem("MÌ TRỨNG XÚC XÍCH RAU", 30000, "VNĐ", "MÌ TÔM"));
        
        listServiceCategories.add(new ServiceCategory("NƯỚC"));
        listServiceCategories.get(2).addServiceItem(new ServiceItem("BÒ HÚC", 15000, "VNĐ", "NƯỚC"));
        listServiceCategories.get(2).addServiceItem(new ServiceItem("NƯỚC C2", 10000, "VNĐ", "NƯỚC"));
        listServiceCategories.get(2).addServiceItem(new ServiceItem("NƯỚC DỪA", 10000, "VNĐ", "NƯỚC"));
        listServiceCategories.get(2).addServiceItem(new ServiceItem("NƯỚC STING ĐỎ", 10000, "VNĐ", "NƯỚC"));
        listServiceCategories.get(2).addServiceItem(new ServiceItem("NƯỚC STING ĐEN", 10000, "VNĐ", "NƯỚC"));
        listServiceCategories.get(2).addServiceItem(new ServiceItem("NƯỚC STING VÀNG", 10000, "VNĐ", "NƯỚC"));
        listServiceCategories.get(2).addServiceItem(new ServiceItem("NƯỚC LỌC", 5000, "VNĐ", "NƯỚC"));
        listServiceCategories.get(2).addServiceItem(new ServiceItem("NƯỚC TRÀ ĐÀO", 10000, "VNĐ", "NƯỚC"));
        listServiceCategories.get(2).addServiceItem(new ServiceItem("NƯỚC YẾN", 10000, "VNĐ", "NƯỚC"));
        listServiceCategories.get(2).addServiceItem(new ServiceItem("NƯỚC Ô LONG", 10000, "VNĐ", "NƯỚC"));
        listServiceCategories.get(2).addServiceItem(new ServiceItem("NƯỚC COCA", 10000, "VNĐ", "NƯỚC"));
        listServiceCategories.get(2).addServiceItem(new ServiceItem("NƯỚC PEPSI", 10000, "VNĐ", "NƯỚC"));
        listServiceCategories.get(2).addServiceItem(new ServiceItem("NƯỚC 7 UP", 10000, "VNĐ", "NƯỚC"));
    }
    
    public static User getUserByUserName(String userName){
        for(int i = 0; i < Data.listUsers.size(); i++){
            if(Data.listUsers.get(i).getUserName().equals(userName)){
                return Data.listUsers.get(i);
            }
        }
        return null;
    }
    
    public static User getUserPrePaidByUserName(String userName){
        for(int i = 0; i < Data.listUsers.size(); i++){
            User user = Data.listUsers.get(i);
            if(user.getUserGroupName().equals("Guest") && user.isIsPrepaid()){
                return user;
            }
        }
        return null;
    }
    
    public static Computer getComputerByComputerName(String computerName){
        for(int i = 0; i < Data.listComputers.size(); i++){
            if(Data.listComputers.get(i).getComputerName().equals(computerName)){
                return Data.listComputers.get(i);
            }
        }
        return null;
    }
}
