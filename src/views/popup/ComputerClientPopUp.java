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
import views.tabs.ComputerClient;

/**
 *
 * @author ADMIN
 */
public class ComputerClientPopUp extends JPopupMenu implements ActionListener{
    
    private ComputerClient parentView;
    private Computer computer;
    
    private JMenuItem btnTurnOnWithUserPermission, btnTurnOnWithAdminPermission;
    private JMenuItem btnPrePaidLogin, btnPostPaidLogin;
    private JMenuItem btnCharge, btnSignOut;
    
    public ComputerClientPopUp(ComputerClient parentView, String computerName) {
        
        this.parentView = parentView;
        for(int i = 0; i < Data.listComputers.size();i++){
            if(Data.listComputers.get(i).getComputerName().equals(computerName)){
                this.computer = Data.listComputers.get(i);
                break;
            }
        }
        if(computer == null) return;
        
        btnTurnOnWithAdminPermission = new JMenuItem("Đăng nhập bằng tài khoản admin");
        btnTurnOnWithAdminPermission.setActionCommand("btnTurnOnWithAdminPermission");
        btnTurnOnWithAdminPermission.addActionListener(this);
        
        btnTurnOnWithUserPermission = new JMenuItem("Đăng nhập bằng tài khoản hội viên");
        btnTurnOnWithUserPermission.setActionCommand("btnTurnOnWithUserPermission");
        btnTurnOnWithUserPermission.addActionListener(this);
        
        btnPrePaidLogin = new JMenuItem("Đăng nhập trả trước");
        btnPrePaidLogin.setActionCommand("btnPrePaidLogin");
        btnPrePaidLogin.addActionListener(this);
        
        btnPostPaidLogin = new JMenuItem("Đăng nhập trả sau");
        btnPostPaidLogin.setActionCommand("btnPostPaidLogin");
        btnPostPaidLogin.addActionListener(this);
        
        btnCharge = new JMenuItem("Tính tiền");
        btnCharge.setActionCommand("btnCharge");
        btnCharge.addActionListener(this);
        
        btnSignOut = new JMenuItem("Đăng xuất");
        btnSignOut.setActionCommand("btnSignOut");
        btnSignOut.addActionListener(this);
        
        if(computer.getUserUsing() == null){
            this.add(btnPrePaidLogin);
            this.add(btnPostPaidLogin);
            this.add(btnTurnOnWithUserPermission);
            this.add(btnTurnOnWithAdminPermission);
        }else if(computer.getUserUsing().getUserGroupName().equals("Member")){
            this.add(btnSignOut);
        }else if(computer.getUserUsing().getUserGroupName().equals("Guest")){
            if(computer.getUserUsing().isIsPrepaid()){
                this.add(btnSignOut);
            }else{
                this.add(btnCharge);
            }
        }else if(computer.getUserUsing().getUserGroupName().equals("Admin")){
            this.add(btnSignOut);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "btnPrePaidLogin":{
                JTextField amountOfMoney = new JTextField();
                
                ((AbstractDocument) amountOfMoney.getDocument()).setDocumentFilter(new DocumentFilter() {
                   Pattern regEx = Pattern.compile("\\d+");

                    @Override
                    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                        Matcher matcher = regEx.matcher(text);
                        if (!matcher.matches()) {
                            return;
                        }
                        super.replace(fb, offset, length, text, attrs);
                    }
                });
                
                final JComponent[] inputs = new JComponent[] {
                    new JLabel("Nhập số tiền người dùng trả trước"), amountOfMoney,
                };
                int result = JOptionPane.showConfirmDialog(null, inputs, "Đăng nhập trả tiền trước", JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    computer.turnOnComputer(new User(computer.getComputerName(), Integer.parseInt(amountOfMoney.getText().trim()),"Guest", true ), parentView);
                }
                break;
            }
            case "btnPostPaidLogin":{
                computer.turnOnComputer(new User(computer.getComputerName(), "Guest", false ), parentView);
                break;
            }
            case "btnCharge":{
                if(computer.getUserUsing().getUserGroupName().equals("Guest") && computer.getUserUsing().isIsPrepaid() == false){
                    computer.charge(parentView);
                    return;
                }
                break;
            }
            case "btnSignOut":{
                computer.turnOffComputer(parentView);
                break;
            }
            case "btnTurnOnWithAdminPermission":{
                computer.turnOnComputer(new User("ADMIN", "Admin", false ), parentView);
                break;
            }
            case "btnTurnOnWithUserPermission":{
                JTextField userName = new JTextField();
                JPasswordField password = new JPasswordField();
                final JComponent[] inputs = new JComponent[] {
                    new JLabel("Nhập tên tài khoản"), userName,
                    new JLabel("Nhập mật khẩu"), password
                };
                int result = JOptionPane.showConfirmDialog(null, inputs, "Đăng nhập bằng tài khoản hội viên", JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    User user = null;
                    for(int i = 0; i < Data.listUsers.size(); i++){
                        user = Data.listUsers.get(i);
                        if(user.getUserName().equals(userName.getText().trim().toUpperCase())){
                            break;
                        }
                    }
                    if(user == null){
                        JOptionPane.showMessageDialog(this,
                        "Tài khoản này không tồn tại!",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
                        return;
                    }else if(!user.getPassword().equals(String.valueOf(password.getPassword()))){
                        JOptionPane.showMessageDialog(this,
                        "Mật khẩu không khớp. Vui lòng thử lại!",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
                    }else if(user.getRemainingAmount() <= 0){
                        JOptionPane.showMessageDialog(this,
                        "Tài khoản " + user.getUserName() + " đã hết tiền! Vui lòng nạp thêm để sử dụng!",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
                    }else{
                        boolean userIsUsing = false;
                        for(int i = 0; i < Data.listComputers.size(); i++){
                            if(Data.listComputers.get(i).getUserUsing() != null && Data.listComputers.get(i).getUserUsing().getUserName().equals(user.getUserName())){
                                JOptionPane.showMessageDialog(this,
                                "Tài khoản hiện đang được sử dụng!",
                                "Warning",
                                JOptionPane.WARNING_MESSAGE);
                                userIsUsing = true;
                                break;
                            }
                        }
                        if(!userIsUsing){
                            computer.turnOnComputer(user, parentView);
                        }
                    }
                }
                break;
            }
        }
    }
}
