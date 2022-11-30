package views.tabs;

import data.Data;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import models.Computer;
import models.User;
import screens.MainFrame;
import views.popup.AccountPopup;
import views.popup.ComputerClientPopUp;

public class Account extends JPanel implements ActionListener{
    
    private JTable table;
    private JPanel controller, tableWrapper;
    private JButton btnAdd, btnEdit, btnRemove;
    private AbstractTableModel tableModel;
    
    private int tableSelectedRow = -1;
    private int buttonSize = 25;
    
    private final String mainUri = System.getProperty("user.dir") + File.separator + "res" + File.separator + "icons" + File.separator;
    
    public Account(){
        
        this.setLayout(new BorderLayout());
        
        setupController();
        setupTable();
        this.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(table);
        
        tableWrapper = new JPanel(new BorderLayout());
        tableWrapper.add(scrollPane, BorderLayout.CENTER);
        this.add(tableWrapper, BorderLayout.CENTER);
        
        this.add(controller, BorderLayout.NORTH);
    }
    
    private void setupController(){
        controller = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnAdd = new JButton();
        btnAdd.setActionCommand("btnAdd");
        btnAdd.addActionListener(this);
        btnAdd.setPreferredSize(new Dimension(buttonSize,buttonSize));
        try{
            Image image = ImageIO.read(new File( mainUri + "add_icon.png")).getScaledInstance(buttonSize, buttonSize, Image.SCALE_DEFAULT);
            btnAdd.setIcon(new ImageIcon(image));
        } 
        catch (Exception e) {}
        
        btnEdit = new JButton();
        btnEdit.setActionCommand("btnEdit");
        btnEdit.addActionListener(this);
        btnEdit.setPreferredSize(new Dimension(buttonSize,buttonSize));
        try{
            Image image = ImageIO.read(new File( mainUri + "ic_edit.png")).getScaledInstance(buttonSize, buttonSize, Image.SCALE_DEFAULT);
            btnEdit.setIcon(new ImageIcon(image));
        } 
        catch (Exception e) {}
        
        
        btnRemove = new JButton();
        btnRemove.setActionCommand("btnRemove");
        btnRemove.addActionListener(this);
        btnRemove.setPreferredSize(new Dimension(buttonSize,buttonSize));
        try{
            Image image = ImageIO.read(new File( mainUri + "remove_ic.png")).getScaledInstance(buttonSize, buttonSize, Image.SCALE_DEFAULT);
            btnRemove.setIcon(new ImageIcon(image));
        } 
        catch (Exception e) {}
        
        controller.add(btnAdd);
        controller.add(btnEdit);
        controller.add(btnRemove);
    }
    
    private void setupTable(){
       
        refeshTable();
        table = new JTable(tableModel);
        
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                if (event.getButton() == MouseEvent.BUTTON3) {
                    int r = table.rowAtPoint(event.getPoint());
                    if (r >= 0 && r < table.getRowCount()) {
                        table.setRowSelectionInterval(r, r);
                        tableSelectedRow = r;
                    } else {
                        table.clearSelection();
                        tableSelectedRow = -1;
                    }

                    int rowindex = table.getSelectedRow();
                    if (rowindex < 0)
                        return;
                    if (event.getComponent() instanceof JTable ) {
                        AccountPopup menu = new AccountPopup(Account.this, table.getValueAt(rowindex, 0).toString());
                        menu.show(event.getComponent(), event.getX(), event.getY());
                    }
                } 
            }

            @Override
            public void mouseClicked(MouseEvent event) {
                if(event.getButton() != MouseEvent.BUTTON1){
                    return;
                }
                if (event.getClickCount() == 2) {
                    System.out.println("double clicked");
                    Point pnt = event.getPoint();
                    int row = table.rowAtPoint(pnt);
                    tableSelectedRow = row;
                    return;
                }
                if (event.getClickCount() == 1) {
                    System.out.println("clicked");
                    Point pnt = event.getPoint();
                    int row = table.rowAtPoint(pnt);
                    tableSelectedRow = row;
                    return;
                }
            }
        });
    }
    
    public void refeshTable(){
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Tên người sử dụng");
        columnNames.add("Tiền còn lại");
        columnNames.add("Nhóm người dùng");
        columnNames.add("Tình trạng");
        
        Vector<Vector<String>> data = new Vector<>();
        for(int i = 0; i < Data.listUsers.size();i++){
            Vector<String> row = new Vector<>();
            User user = Data.listUsers.get(i);
            row.add(user.getUserName());
            row.add(user.getRemainingAmountToString());
            row.add(user.getUserGroupName());
            row.add("Cho phép");
            data.add(row);
        }
        
        tableModel = new DefaultTableModel(data, columnNames){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        if(table != null){
            table.setModel(tableModel);
            if(tableSelectedRow > -1) table.setRowSelectionInterval(tableSelectedRow, tableSelectedRow);
        };
    }

    public AbstractTableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(AbstractTableModel tableModel) {
        this.tableModel = tableModel;
    }
    
    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "btnAdd": {
                JTextField userName = new JTextField();
                JPasswordField password = new JPasswordField();
                final JComponent[] inputs = new JComponent[] {
                    new JLabel("Nhập tên tài khoản"), userName,
                    new JLabel("Nhập mật khẩu"), password
                };  
                int result = JOptionPane.showConfirmDialog(null, inputs, "Tạo tài khoản hội viên", JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    if(userName.getText().trim().isEmpty()){
                        JOptionPane.showMessageDialog(this,
                        "Tên tài khoản không được để trống!",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    if(String.valueOf(password.getPassword()).trim().isEmpty()){
                        JOptionPane.showMessageDialog(this,
                        "Mật khẩu không được để trống!",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    for(int i = 0; i < Data.listUsers.size(); i++){
                        User user = Data.listUsers.get(i);
                        if(user.getUserName().equals(userName.getText().trim().toUpperCase())){
                            JOptionPane.showMessageDialog(this,
                            "Tài khoản này đã tồn tại!",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    }
                    Data.listUsers.add(new User(userName.getText().trim(), String.valueOf(password.getPassword()).trim(), "Member"));
                    refeshTable();
                }
                break;
            }
            case "btnEdit": {
                if(tableSelectedRow > -1){
                    String userName = (String) table.getValueAt(tableSelectedRow, 0);
                    for(int i = 0; i < Data.listUsers.size(); i++){
                        if(userName.equals(Data.listUsers.get(i).getUserName())){
                            JTextField edtUserName = new JTextField(userName);
                            edtUserName.setEnabled(false);
                            JPasswordField edtPassword = new JPasswordField();
                            final JComponent[] inputs = new JComponent[] {
                                new JLabel("Nhập tên tài khoản"), edtUserName,
                                new JLabel("Nhập mật khẩu"), edtPassword
                            };  
                            int result = JOptionPane.showConfirmDialog(null, inputs, "Tạo tài khoản hội viên", JOptionPane.PLAIN_MESSAGE);
                            if (result == JOptionPane.OK_OPTION) {
                                if(edtUserName.getText().trim().isEmpty()){
                                    JOptionPane.showMessageDialog(this,
                                    "Tên tài khoản không được để trống!",
                                    "Warning",
                                    JOptionPane.WARNING_MESSAGE);
                                    return;
                                }
                                if(String.valueOf(edtPassword.getPassword()).isEmpty()){
                                    JOptionPane.showMessageDialog(this,
                                    "Mật khẩu không được để trống!",
                                    "Warning",
                                    JOptionPane.WARNING_MESSAGE);
                                    return;
                                }
                                for(i = 0; i < Data.listUsers.size(); i++){
                                    User user = Data.listUsers.get(i);
                                    if(user.getUserName().equals(edtUserName.getText().trim().toUpperCase())){
                                        user.setPassword(String.valueOf(edtPassword.getPassword()));
                                        return;
                                    }
                                }
                                refeshTable();
                            }
                        }
                    }
                }
                break;
            }
            case "btnRemove": {
                if(tableSelectedRow > -1){
                    String userName = (String) table.getValueAt(tableSelectedRow, 0);
                    for(int i = 0; i < Data.listUsers.size(); i++){
                        User user = Data.listUsers.get(i);
                        if(user.getUserName().equals(userName)){
                            if(user.getRemainingAmount() > 0){
                                JOptionPane.showMessageDialog(this,
                                "Chỉ được xóa những tài khoản đã hết tiền!",
                                "Warning",
                                JOptionPane.WARNING_MESSAGE);
                                return;
                            }
                            Data.listUsers.remove(user);
                            refeshTable();
                            return;
                        }
                    }
                    refeshTable();
                }
                break;
            }
        }
    }
}
