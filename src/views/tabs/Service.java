package views.tabs;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import data.Data;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
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
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import models.Computer;
import models.PlaceholderTextField;
import models.ServiceCategory;
import models.ServiceItem;
import models.User;
import screens.MainFrame;
import views.dialog.ServiceCategoryDialog;
import views.popup.AccountPopup;
import views.popup.ComputerClientPopUp;

public class Service extends JPanel implements ActionListener{
    
    private JTable table;
    private JPanel controller, tableWrapper;
    private JButton btnAdd, btnEdit, btnRemove, btnAddCategory;
    private AbstractTableModel tableModel;
    
    private int tableSelectedRow = -1;
    private int buttonSize = 25;
        
    public Service(){
        
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
                
        Border border = BorderFactory.createLineBorder(new Color(50,50,150, 50), 1, true);
        
        controller = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnAdd = new JButton();
        btnAdd.setActionCommand("btnAdd");
        btnAdd.addActionListener(this);
        btnAdd.setPreferredSize(new Dimension(buttonSize,buttonSize));
        btnAdd.setIcon(new FlatSVGIcon("icons/ic_add.svg", buttonSize, buttonSize));
        btnAdd.setBorder(border);
        btnAdd.setFocusPainted(false);
        btnAdd.setContentAreaFilled(false);
        
        btnEdit = new JButton();
        btnEdit.setActionCommand("btnEdit");
        btnEdit.addActionListener(this);
        btnEdit.setPreferredSize(new Dimension(buttonSize,buttonSize));
        btnEdit.setIcon(new FlatSVGIcon("icons/ic_edit.svg", buttonSize, buttonSize));
        btnEdit.setBorder(border);
        btnEdit.setFocusPainted(false);
        btnEdit.setContentAreaFilled(false);
        
        btnRemove = new JButton();
        btnRemove.setActionCommand("btnRemove");
        btnRemove.addActionListener(this);
        btnRemove.setPreferredSize(new Dimension(buttonSize,buttonSize));
        btnRemove.setIcon(new FlatSVGIcon("icons/ic_remove.svg", buttonSize, buttonSize));
        btnRemove.setBorder(border);
        btnRemove.setFocusPainted(false);
        btnRemove.setContentAreaFilled(false);
        
        btnAddCategory = new JButton("Loại dịch vụ");
        btnAddCategory.setActionCommand("btnAddCategory");
        btnAddCategory.addActionListener(this);
        btnAddCategory.setPreferredSize(new Dimension(buttonSize * 3,buttonSize));
        btnAddCategory.setBackground(Color.blue);
        btnAddCategory.setBorder(border);
        btnAddCategory.setFocusPainted(false);
        btnAddCategory.setContentAreaFilled(false);
        
        controller.add(btnAdd);
        controller.add(btnEdit);
        controller.add(btnRemove);
        controller.add(new JPanel(){
            @Override
            public void setPreferredSize(Dimension preferredSize) {
                new Dimension(buttonSize, buttonSize);
            }
        });
        controller.add(btnAddCategory);

       
        controller.add(new JPanel(){ // adding space
            @Override
            public Dimension preferredSize() {
                return new Dimension(buttonSize,0);
            }
        });
    }
    
    private void setupTable(){
       
        refreshTable();
        table = new JTable(tableModel);
        table.setRowHeight(20);
        table.addMouseListener(new MouseAdapter() {
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
    
    public void refreshTable(){
        Vector<String> columnNames = new Vector<String>();
        columnNames.add("Loại");
        columnNames.add("Tên");
        columnNames.add("Giá");
        columnNames.add("Đơn vị");
        columnNames.add("Tồn kho");
        
        Vector<Vector<String>> data = new Vector<Vector<String>>();
        for(int i = 0; i < Data.listServiceCategories.size();i++){
            ServiceCategory category = Data.listServiceCategories.get(i);
            Vector<String> row = new Vector<String>();
            row.add(category.getName());
            row.add("");
            row.add("");
            row.add("");
            row.add("");
            data.add(row);
            for(int j = 0; j < category.getListServiceItems().size(); j++){
                ServiceItem item = category.getListServiceItems().get(j);
                Vector<String> nextRow = new Vector<String>();
                nextRow.add("");
                nextRow.add(item.getName());
                nextRow.add(NumberFormat.getNumberInstance().format(item.getPrice()));
                nextRow.add(item.getUnit());
                nextRow.add(item.getStock() < 0 ? "(Không giới hạn)" : String.valueOf(item.getStock()));
                data.add(nextRow);
            }
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
                break;
            }
            case "btnEdit": {
                if(tableSelectedRow > -1){
                }
                break;
            }
            case "btnRemove": {
                if(tableSelectedRow > -1){
                }
                break;
            }
            case "btnAddCategory": {
                new ServiceCategoryDialog().setVisible(true);
                break;
            }
        }
    }
}
