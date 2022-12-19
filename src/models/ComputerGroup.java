package models;

import com.google.gson.annotations.Expose;
import java.util.ArrayList;
import java.util.Map;

public class ComputerGroup {
    
    @Expose(serialize = true, deserialize = true)
    private String groupName;
    
    @Expose(serialize = true, deserialize = true)
    private String description;
    
    @Expose(serialize = true, deserialize = true)
    private ArrayList<UserGroup> priceForEachUserGroups;

    public ComputerGroup(String groupName, ArrayList<UserGroup> priceForEachUserGroups) {
        this.groupName = groupName;
        this.description = "";
        this.priceForEachUserGroups = priceForEachUserGroups;
    }
    
    public ComputerGroup(String groupName, String description, ArrayList<UserGroup> priceForEachUserGroups) {
        this.groupName = groupName;
        this.description = description;
        this.priceForEachUserGroups = priceForEachUserGroups;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<UserGroup> getPriceForEachUserGroups() {
        return priceForEachUserGroups;
    }

    public void setPriceForEachUserGroups(ArrayList<UserGroup> priceForEachUserGroups) {
        this.priceForEachUserGroups = priceForEachUserGroups;
    }
}
