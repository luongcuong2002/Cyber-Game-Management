/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author ADMIN
 */
public class ServiceCategory {

    @Expose(serialize = true, deserialize = true)
    private String name;
    
    @Expose(serialize = true, deserialize = true)
    private ArrayList<ServiceItem> listServiceItems = new ArrayList<>();

    public ServiceCategory(String name) {
        this.name = name;
    }

    public void addServiceItem(ServiceItem item) {
        for (int i = 0; i < listServiceItems.size(); i++) {
            if (listServiceItems.get(i).getName().equals(item.getName())) {
                JOptionPane.showMessageDialog(null,
                        "Dịch vụ này đã tồn tại!",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        listServiceItems.add(item);
    }
    
    public void removeServiceItem(String name){
        for (int i = 0; i < listServiceItems.size(); i++) {
            if (listServiceItems.get(i).getName().equals(name)) {
                listServiceItems.remove(listServiceItems.get(i));
                return;
            }
        }
        JOptionPane.showMessageDialog(null,
        "Dịch vụ không tồn tại!",
        "Warning",
        JOptionPane.WARNING_MESSAGE);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ServiceItem> getListServiceItems() {
        return listServiceItems;
    }

    public void setListServiceItems(ArrayList<ServiceItem> listServiceItems) {
        this.listServiceItems = listServiceItems;
    }
}
