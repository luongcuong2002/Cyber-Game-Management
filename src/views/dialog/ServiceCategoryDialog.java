/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.dialog;

import data.Data;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import models.Computer;
import models.ServiceCategory;
import views.tabs.ComputerClient;

/**
 *
 * @author ADMIN
 */
public class ServiceCategoryDialog extends JDialog{

    private int WIDTH = 350, HEIGHT = 400;
    private JPanel container;
    private int fontSize = 12;

    private JTable table;
    private JScrollPane scrollPane;
    private DefaultTableModel tableModel;
    private JButton btnAdd, btnEdit, btnDelete;

    private int tableSelectedRow = -1;

    public ServiceCategoryDialog() {
        Toolkit toolkit = this.getToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width / 2 - WIDTH / 2, dimension.height / 2 - HEIGHT / 2, WIDTH, HEIGHT);
        this.setModal(true);
        this.setTitle("Loại dịch vụ");
        //this.setResizable(false);
        this.setLayout(new BorderLayout());
        container = new JPanel();
        container.setLayout(new BorderLayout());
        container.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.add(container, BorderLayout.CENTER);

        setupScrollPane();
        setupTable();
        table = new JTable(tableModel);
        table.setRowHeight(20);
        scrollPane.setViewportView(table);
        container.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonsWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 5));
        setupAddButton();
        setupEditButton();
        setupDeleteButton();
        buttonsWrapper.add(btnAdd);
        buttonsWrapper.add(btnEdit);
        buttonsWrapper.add(btnDelete);

        container.add(buttonsWrapper, BorderLayout.SOUTH);
    }

    private void setupScrollPane() {
        scrollPane = new JScrollPane();
        scrollPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                table.clearSelection();
            }
        });
    }

    private void setupAddButton() {
        btnAdd = new JButton("Thêm");
        btnAdd.setBackground(new Color(44, 182, 109));
        btnAdd.setForeground(Color.white);
        btnAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTextField category = new JTextField();
                final JComponent[] inputs = new JComponent[] {
                    new JLabel("Nhập tên loại dịch vụ"), category,
                };
                int result = JOptionPane.showConfirmDialog(null, inputs, "Thêm loại", JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    if(category.getText().trim().isEmpty()){
                        JOptionPane.showMessageDialog(null,
                        "Tên không được để trống!",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    for(int i = 0; i < Data.listServiceCategories.size(); i++){
                        if(category.getText().trim().equals(Data.listServiceCategories.get(i).getName())){
                            JOptionPane.showMessageDialog(null,
                            "Loại dịch vụ này đã tồn tại!",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    }
                    String categoryString = category.getText().trim();
                    Data.listServiceCategories.add(new ServiceCategory(categoryString.toUpperCase()));
                    setupTable();
                }
            }
        });
    }

    private void setupEditButton() {
        btnEdit = new JButton("Chỉnh sửa");
        btnEdit.setBackground(Color.blue);
        btnEdit.setForeground(Color.white);
        btnEdit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
            }
        });
    }
    
    private void setupDeleteButton(){
        btnDelete = new JButton("Xóa");
        btnDelete.setBackground(Color.red);
        btnDelete.setForeground(Color.white);
        btnDelete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int[] rowIndexs = table.getSelectedRows();
                for(int i = 0; i < rowIndexs.length; i++){
                    Data.listServiceCategories.remove(rowIndexs[i]);
                }
                setupTable();
            }
        });
    }

    private void setupTable() {
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Tên");
        
        Vector<Vector<String>> data = new Vector<>();
        for (int i = 0; i < Data.listServiceCategories.size(); i++) {
            Vector<String> row = new Vector<>();
            row.add(Data.listServiceCategories.get(i).getName());
            data.add(row);
        }

        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        if(table != null){
            table.setModel(tableModel);
        }
    }
}
