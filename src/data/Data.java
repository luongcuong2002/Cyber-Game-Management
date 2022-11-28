package data;

import java.util.ArrayList;
import models.Computer;
import models.ComputerGroup;
import models.UserGroup;

public abstract class Data {
    public static ArrayList<ComputerGroup> computerGroups = new ArrayList<>();
    public static ArrayList<Computer> listComputers = new ArrayList<>();
    
    public static void getData(){
        initComputerGroups();
        initListComputers();
    }
    
    public static void saveData(){
        
    }
    
    private static void initComputerGroups(){
        ArrayList<UserGroup> userGroups = new ArrayList<>();
        userGroups.add(new UserGroup("Guest", 6000));
        userGroups.add(new UserGroup("Member", 5500));
        computerGroups.add(new ComputerGroup("NEW", userGroups));
        
        ArrayList<UserGroup> userGroups1 = new ArrayList<>();
        userGroups.add(new UserGroup("Guest", 4500));
        userGroups.add(new UserGroup("Member", 4000));
        computerGroups.add(new ComputerGroup("OLD", userGroups));
    }
    
    private static void initListComputers(){
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
}
