/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.dialog;

import data.Data;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import models.Computer;
import models.User;
import views.tabs.ComputerClient;

/**
 *
 * @author ADMIN
 */
public class ComputerClientPopUp extends JPopupMenu implements ActionListener{
    
    private ComputerClient parentView;
    private Computer computer;
    
    private JMenuItem btnTurnOn;
    
    public ComputerClientPopUp(ComputerClient parentView, String computerName) {
        
        this.parentView = parentView;
        for(int i = 0; i < Data.listComputers.size();i++){
            if(Data.listComputers.get(i).getComputerName().equals(computerName)){
                this.computer = Data.listComputers.get(i);
                break;
            }
        }
        if(computer == null) return;
        
        btnTurnOn = new JMenuItem("Bật máy!");
        btnTurnOn.setActionCommand("btnTurnOn");
        btnTurnOn.addActionListener(this);
        
        add(btnTurnOn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "btnTurnOn":{
                computer.turnOnComputer(new User("NLcuong", "1", "Guest"), parentView);
                break;
            }
        }
    }
}
