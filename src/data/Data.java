package data;

import java.util.ArrayList;
import models.Computer;
import models.ComputerGroup;
import models.User;
import models.UserGroup;

public abstract class Data {
    public static ArrayList<ComputerGroup> computerGroups = new ArrayList<>();
    public static ArrayList<Computer> listComputers = new ArrayList<>();
    public static ArrayList<User> listUsers = new ArrayList<>();
    
    public static void getData(){
        
        // read data from database here
        
        setUpComputerGroups();
        setUpListComputers();
        setUpListUsers();
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
}
