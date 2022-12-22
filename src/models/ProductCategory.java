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
public class ProductCategory {

    @Expose(serialize = true, deserialize = true)
    private String name;
    
    @Expose(serialize = true, deserialize = true)
    private ArrayList<ProductItem> listProductItems = new ArrayList<>();

    public ProductCategory(String name) {
        this.name = name;
    }

    public void addProductItem(ProductItem item) {
        for (int i = 0; i < listProductItems.size(); i++) {
            if (listProductItems.get(i).getName().equals(item.getName())) {
                JOptionPane.showMessageDialog(null,
                        "Dịch vụ này đã tồn tại!",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        listProductItems.add(item);
    }
    
    public void removeProductItem(String name){
        for (int i = 0; i < listProductItems.size(); i++) {
            if (listProductItems.get(i).getName().equals(name)) {
                listProductItems.remove(listProductItems.get(i));
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

    public ArrayList<ProductItem> getListProductItems() {
        return listProductItems;
    }

    public void setListProductItems(ArrayList<ProductItem> listProductItems) {
        this.listProductItems = listProductItems;
    }
}
