/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.popup;

import data.Data;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import models.Computer;
import models.User;
import views.tabs.Account;
import views.tabs.ComputerClient;

/**
 *
 * @author ADMIN
 */
public class AccountPopup extends JPopupMenu implements ActionListener{
    
    private Account parentView;
    private User user;
    
    private JMenuItem btnTopup;
    
    public AccountPopup(Account parentView, String userName) {
        
        this.parentView = parentView;
        for(int i = 0; i < Data.listUsers.size();i++){
            if(Data.listUsers.get(i).getUserName().equals(userName)){
                this.user = Data.listUsers.get(i);
                break;
            }
        }
        if(user == null) return;
        
        btnTopup = new JMenuItem("Nạp tiền");
        btnTopup.setActionCommand("btnTopup");
        btnTopup.addActionListener(this);
        
        this.add(btnTopup);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "btnTopup":{
                JTextField topUpAmount = new JTextField();
//                ((AbstractDocument) topUpAmount.getDocument()).setDocumentFilter(new DocumentFilter() {
//                   Pattern regEx = Pattern.compile("\\d+");
//
//                    @Override
//                    public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
//                        Matcher matcher = regEx.matcher(text);
//                        if (!matcher.matches()) {
//                            return;
//                        }
//                        super.replace(fb, offset, length, text, attrs);
//                    }
//                });
                final JComponent[] inputs = new JComponent[] {
                    new JLabel("Số tiền nạp"), topUpAmount
                };
                int result = JOptionPane.showConfirmDialog(null, inputs, "Nạp tiền", JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    String text = topUpAmount.getText().trim();
                    if(text.isEmpty()){
                        JOptionPane.showMessageDialog(this,
                        "Số tiền không được để trống!",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    try {
                        int amount = Integer.parseInt(text);
                        user.topUp(amount);
                        if(user.getRemainingAmount() < 0){
                            user.setRemainingAmount(0);
                        }
                    } catch (NumberFormatException e1) {
                        JOptionPane.showMessageDialog(this,
                        "Input không hợp lệ!",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
                    }
                    parentView.refreshTable(Data.listUsers);
                }
                break;
            }
        }
    }
}
