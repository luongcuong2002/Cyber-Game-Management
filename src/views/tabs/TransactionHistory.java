package views.tabs;

import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.optionalusertools.DateTimeChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;
import com.github.lgooddatepicker.zinternaltools.DateTimeChangeEvent;
import com.toedter.calendar.JDateChooser;
import data.Data;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import models.Computer;
import models.PlaceholderTextField;
import models.User;
import screens.MainFrame;
import views.popup.AccountPopup;
import views.popup.ComputerClientPopUp;

public class TransactionHistory extends JPanel implements ActionListener{
    
    private JTable table;
    private JPanel controller, tableWrapper;
    private JButton btnAdd, btnEdit, btnRemove, btnRefresh, btnSearch;
    private PlaceholderTextField edtInputToSearch;
    private AbstractTableModel tableModel;
    
    private int tableSelectedRow = -1;
    private int buttonSize = 25;
    
    private final String mainUri = System.getProperty("user.dir") + File.separator + "res" + File.separator + "icons" + File.separator;
    
    public TransactionHistory(){
        
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
        
        btnRefresh = new JButton();
        btnRefresh.setActionCommand("btnRefresh");
        btnRefresh.addActionListener(this);
        btnRefresh.setPreferredSize(new Dimension(buttonSize,buttonSize));
        try{
            Image image = ImageIO.read(new File( mainUri + "ic_refresh.png")).getScaledInstance(buttonSize, buttonSize, Image.SCALE_DEFAULT);
            btnRefresh.setIcon(new ImageIcon(image));
        } 
        catch (Exception e) {}

        edtInputToSearch = new PlaceholderTextField("");
        edtInputToSearch.setPlaceholder("Nhập tên tài khoản để tìm kiếm");
        edtInputToSearch.setPreferredSize(new Dimension(200, buttonSize));
        
        btnSearch = new JButton();
        btnSearch.setActionCommand("btnSearch");
        btnSearch.addActionListener(this);
        btnSearch.setPreferredSize(new Dimension(buttonSize,buttonSize));
        try{
            Image image = ImageIO.read(new File( mainUri + "ic_search.png")).getScaledInstance(buttonSize, buttonSize, Image.SCALE_DEFAULT);
            btnSearch.setIcon(new ImageIcon(image));
        } 
        catch (Exception e) {}
        
        DatePickerSettings dateSettingsForTimeStart = new DatePickerSettings();
        dateSettingsForTimeStart.setFormatForDatesCommonEra("dd-MM-yyyy");
        dateSettingsForTimeStart.setAllowEmptyDates(false);
        dateSettingsForTimeStart.setAllowKeyboardEditing(false);
        
        TimePickerSettings timeSettingsForTimeStart = new TimePickerSettings();
        timeSettingsForTimeStart.setFormatForDisplayTime("HH:mm");
        timeSettingsForTimeStart.setAllowEmptyTimes(false);
        timeSettingsForTimeStart.setAllowKeyboardEditing(false);
        
        DatePickerSettings dateSettingsForTimeEnd = new DatePickerSettings();
        dateSettingsForTimeEnd.setFormatForDatesCommonEra("dd-MM-yyyy");
        dateSettingsForTimeEnd.setAllowEmptyDates(false);
        dateSettingsForTimeEnd.setAllowKeyboardEditing(false);
        
        TimePickerSettings timeSettingsForTimeEnd = new TimePickerSettings();
        timeSettingsForTimeEnd.setFormatForDisplayTime("HH:mm");
        timeSettingsForTimeEnd.setAllowEmptyTimes(false);
        timeSettingsForTimeEnd.setAllowKeyboardEditing(false);
        
        DateTimePicker dateTimeStartPicker = new DateTimePicker(dateSettingsForTimeStart, timeSettingsForTimeStart);
        dateTimeStartPicker.addDateTimeChangeListener(new DateTimeChangeListener() {
            @Override
            public void dateOrTimeChanged(DateTimeChangeEvent event) {
                DateChangeEvent dateEvent = event.getDateChangeEvent();
                if (dateEvent != null) {
                    dateEvent.getNewDate(); // new date
                }
            }
        });
        
        DateTimePicker dateTimeEndPicker = new DateTimePicker(dateSettingsForTimeEnd, timeSettingsForTimeEnd);
        dateTimeEndPicker.addDateTimeChangeListener(new DateTimeChangeListener() {
            @Override
            public void dateOrTimeChanged(DateTimeChangeEvent event) {
                DateChangeEvent dateEvent = event.getDateChangeEvent();
                if (dateEvent != null) {
                    dateEvent.getNewDate(); // new date
                }
            }
        });
        
        controller.add(new JLabel("Bắt đầu:"));
        controller.add(dateTimeStartPicker);
        controller.add(new JLabel("Kết thúc:"));
        controller.add(dateTimeEndPicker);
        controller.add(btnRefresh);
        controller.add(edtInputToSearch);
        controller.add(btnSearch);
    }
    
    private void setupTable(){
       
        refreshTable(Data.listUsers);
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
                        //AccountPopup menu = new AccountPopup(Account.this, table.getValueAt(rowindex, 0).toString());
                        //menu.show(event.getComponent(), event.getX(), event.getY());
                    }
                } 
            }

            @Override
            public void mouseClicked(MouseEvent event) {
                if(event.getButton() != MouseEvent.BUTTON1){
                    return;
                }
                if (event.getClickCount() == 2) {
                    Point pnt = event.getPoint();
                    int row = table.rowAtPoint(pnt);
                    tableSelectedRow = row;
                    return;
                }
                if (event.getClickCount() == 1) {
                    Point pnt = event.getPoint();
                    int row = table.rowAtPoint(pnt);
                    tableSelectedRow = row;
                    return;
                }
            }
        });
    }
    
    public void refreshTable(ArrayList<User> list){
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Tên người sử dụng");
        columnNames.add("Tiền còn lại");
        columnNames.add("Nhóm người dùng");
        columnNames.add("Tình trạng");
        
        Vector<Vector<String>> data = new Vector<>();
        for(int i = 0; i < list.size();i++){
            Vector<String> row = new Vector<>();
            User user = list.get(i);
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
                    refreshTable(Data.listUsers);
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
                                refreshTable(Data.listUsers);
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
                            refreshTable(Data.listUsers);
                            return;
                        }
                    }
                    refreshTable(Data.listUsers);
                }
                break;
            }
            case "btnRefresh":{
                refreshTable(Data.listUsers);
                break;
            }
            case "btnSearch": {
                String text = edtInputToSearch.getText().trim().toUpperCase();
                edtInputToSearch.setText("");
                ArrayList<User> list = new ArrayList<>();
                for(int i = 0; i < Data.listUsers.size(); i++){
                    if(Data.listUsers.get(i).getUserName().startsWith(text)){
                        list.add(Data.listUsers.get(i));
                    }
                }
                refreshTable(list);
                break;
            }
        }
    }
}
