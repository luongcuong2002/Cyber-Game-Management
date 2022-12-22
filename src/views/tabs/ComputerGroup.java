/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.tabs;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import data.Data;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import views.dialog.ComputerGroupManagerDialog;

public class ComputerGroup extends JPanel implements ActionListener{
    
    private JTable table;
    private JPanel controller, tableWrapper;
    private JButton btnAdd, btnEdit, btnRemove;
    private AbstractTableModel tableModel;
    
    private int buttonSize = 25;
        
    public ComputerGroup(){
        
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
        
        controller.add(btnAdd);
        controller.add(btnEdit);
        controller.add(btnRemove);
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
                    return;
                }
                if (event.getClickCount() == 1) {
                    Point pnt = event.getPoint();
                    int row = table.rowAtPoint(pnt);
                    return;
                }
            }
        });
        
        JTableHeader jTableHeader = table.getTableHeader();
        jTableHeader.setReorderingAllowed(false);
        jTableHeader.setBackground(new Color(255, 255, 184));
        jTableHeader.setForeground(Color.black);
    }
    
    public void refreshTable(){
        Vector<String> columnNames = new Vector<String>();
        columnNames.add("Nhóm máy");
        columnNames.add("Mô tả");
        
        Vector<Vector<String>> data = new Vector<Vector<String>>();
        for(int i = 0; i < Data.computerGroups.size();i++){
            models.ComputerGroup item = Data.computerGroups.get(i);
            Vector<String> row = new Vector<String>();
            row.add(item.getGroupName());
            row.add(item.getDescription());
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
                new ComputerGroupManagerDialog(this, null, false, true).setVisible(true);
                break;
            }
            case "btnEdit": {
                if(table.getSelectedRow() > -1){
                    models.ComputerGroup computerGroup = Data.getComputerGroupByName((String) table.getValueAt(table.getSelectedRow(), 0));
                    if(computerGroup != null){
                        if(computerGroup.getGroupName().equals("Default")){
                            new ComputerGroupManagerDialog(this, computerGroup, true, false).setVisible(true);
                        }else{
                            new ComputerGroupManagerDialog(this, computerGroup, true, true).setVisible(true);
                        }
                    }
                }
                break;
            }
            case "btnRemove": {
                if(table.getSelectedRow() > -1){
                    models.ComputerGroup computerGroup = Data.getComputerGroupByName((String) table.getValueAt(table.getSelectedRow(), 0));
                    if(computerGroup == null) return;
                    if(computerGroup.getGroupName().equals("Default")){
                        JOptionPane.showMessageDialog(null,
                        "Bạn không đủ quyền để xóa nhóm mặc định!",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    models.ComputerGroup groupDefault = Data.getComputerGroupByName("Default");
                    if(groupDefault == null){
                        JOptionPane.showMessageDialog(null,
                        "Something went wrong!",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    for(int i = 0; i < Data.listComputers.size(); i++){
                        if(computerGroup.getGroupName().equals(Data.listComputers.get(i).getComputerGroup().getGroupName())){
                            Data.listComputers.get(i).setComputerGroup(groupDefault);
                        }
                    }
                    Data.computerGroups.remove(computerGroup);
                    refreshTable();
                }
                break;
            }
        }
    }
}

