/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.component;

import data.Data;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author ADMIN
 */
public class Footer extends JPanel{
    
    private static JLabel txtTotalMembers, txtComputerBeingUsed, txtComputerLostConnection, txtRatio;
    
    public Footer(){
        this.setLayout(new GridLayout(1, 5, 0, 0));
        
        JPanel jPanelTotalMembers = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jPanelTotalMembers.add(new JLabel("Tổng thành viên: "));
        txtTotalMembers = new JLabel(String.valueOf(Data.listUsers.size()));
        jPanelTotalMembers.add(txtTotalMembers);
        
        JPanel jPanelTotalComputers = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jPanelTotalComputers.add(new JLabel("Số máy trạm: "));
        jPanelTotalComputers.add(new JLabel(String.valueOf(Data.listComputers.size())));
        
        JPanel jPanelComputerBeingUsed = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jPanelComputerBeingUsed.add(new JLabel("Đang sử dụng: "));
        txtComputerBeingUsed = new JLabel("10");
        jPanelComputerBeingUsed.add(txtComputerBeingUsed);
        
        JPanel jPanelComputerLostConnection = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jPanelComputerLostConnection.add(new JLabel("Mất kết nối: "));
        txtComputerLostConnection = new JLabel("10");
        jPanelComputerLostConnection.add(txtComputerLostConnection);
        
        JPanel jPanelRatio = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jPanelRatio.add(new JLabel("Tỷ lệ: "));
        txtRatio = new JLabel("50%");
        jPanelRatio.add(txtRatio);
        
        this.add(jPanelTotalMembers);
        this.add(jPanelTotalComputers);
        this.add(jPanelComputerBeingUsed);
        this.add(jPanelComputerLostConnection);
        this.add(jPanelRatio);
        
        updateTotalMember();
        updateNumberOfComputerBeingUsed();
    }
    
    public static void updateTotalMember(){
        txtTotalMembers.setText(String.valueOf(Data.listUsers.size()));
    }
    
    public static void updateNumberOfComputerBeingUsed(){
        int count = 0;
        for(int i = 0; i < Data.listComputers.size(); i++){
            if(Data.listComputers.get(i).getUserUsing() != null){
                count++;
            }
        }
        txtComputerBeingUsed.setText(String.valueOf(count));
        txtComputerLostConnection.setText(String.valueOf(Data.listComputers.size() - count));
        txtRatio.setText(Math.round(((float) count) / (Data.listComputers.size()) * 100) + "%");
    }
}
