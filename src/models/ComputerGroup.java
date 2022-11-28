/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author ADMIN
 */
public class ComputerGroup {
    private String groupName;
    private String description;
    private ArrayList<UserGroup> priceForEachUserGroups;

    public ComputerGroup(String groupName, ArrayList<UserGroup> priceForEachUserGroups) {
        this.groupName = groupName;
        this.description = "";
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
