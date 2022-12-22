package views.tabs;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import data.Data;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import models.ProductCategory;
import models.ProductItem;
import views.dialog.ProductCategoryDialog;
import views.dialog.ProductEditingDialog;

public class Service extends JPanel implements ActionListener {

    private JTable table;
    private JPanel controller, tableWrapper;
    private JButton btnAdd, btnEdit, btnRemove, btnAddCategory;
    private AbstractTableModel tableModel;

    private int buttonSize = 25;

    public Service() {

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

    private void setupController() {

        Border border = BorderFactory.createLineBorder(new Color(50, 50, 150, 50), 1, true);

        controller = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnAdd = new JButton();
        btnAdd.setActionCommand("btnAdd");
        btnAdd.addActionListener(this);
        btnAdd.setPreferredSize(new Dimension(buttonSize, buttonSize));
        btnAdd.setIcon(new FlatSVGIcon("icons/ic_add.svg", buttonSize, buttonSize));
        btnAdd.setBorder(border);
        btnAdd.setFocusPainted(false);
        btnAdd.setContentAreaFilled(false);

        btnEdit = new JButton();
        btnEdit.setActionCommand("btnEdit");
        btnEdit.addActionListener(this);
        btnEdit.setPreferredSize(new Dimension(buttonSize, buttonSize));
        btnEdit.setIcon(new FlatSVGIcon("icons/ic_edit.svg", buttonSize, buttonSize));
        btnEdit.setBorder(border);
        btnEdit.setFocusPainted(false);
        btnEdit.setContentAreaFilled(false);

        btnRemove = new JButton();
        btnRemove.setActionCommand("btnRemove");
        btnRemove.addActionListener(this);
        btnRemove.setPreferredSize(new Dimension(buttonSize, buttonSize));
        btnRemove.setIcon(new FlatSVGIcon("icons/ic_remove.svg", buttonSize, buttonSize));
        btnRemove.setBorder(border);
        btnRemove.setFocusPainted(false);
        btnRemove.setContentAreaFilled(false);

        btnAddCategory = new JButton("Loại dịch vụ");
        btnAddCategory.setActionCommand("btnAddCategory");
        btnAddCategory.addActionListener(this);
        btnAddCategory.setPreferredSize(new Dimension(buttonSize * 3, buttonSize));
        btnAddCategory.setBackground(Color.blue);
        btnAddCategory.setBorder(border);
        btnAddCategory.setFocusPainted(false);
        btnAddCategory.setContentAreaFilled(false);

        controller.add(btnAdd);
        controller.add(btnEdit);
        controller.add(btnRemove);
        controller.add(new JPanel() {
            @Override
            public void setPreferredSize(Dimension preferredSize) {
                new Dimension(buttonSize, buttonSize);
            }
        });
        controller.add(btnAddCategory);

        controller.add(new JPanel() { // adding space
            @Override
            public Dimension preferredSize() {
                return new Dimension(buttonSize, 0);
            }
        });
    }

    private void setupTable() {

        refreshTable();
        table = new JTable(tableModel);
        table.setRowHeight(20);
        
        JTableHeader jTableHeader = table.getTableHeader();
        jTableHeader.setReorderingAllowed(false);
        jTableHeader.setBackground(new Color(255, 184, 248));
        jTableHeader.setForeground(Color.black);
    }

    public void refreshTable() {
        Vector<String> columnNames = new Vector<String>();
        columnNames.add("Loại");
        columnNames.add("Tên");
        columnNames.add("Giá");
        columnNames.add("Đơn vị");

        Vector<Vector<String>> data = new Vector<Vector<String>>();
        for (int i = 0; i < Data.listProductCategories.size(); i++) {
            ProductCategory category = Data.listProductCategories.get(i);
            Vector<String> row = new Vector<String>();
            row.add(category.getName());
            row.add("");
            row.add("");
            row.add("");
            row.add("");
            data.add(row);
            for (int j = 0; j < category.getListProductItems().size(); j++) {
                ProductItem item = category.getListProductItems().get(j);
                Vector<String> nextRow = new Vector<String>();
                nextRow.add("");
                nextRow.add(item.getName());
                nextRow.add(NumberFormat.getNumberInstance().format(item.getPrice()));
                nextRow.add(item.getUnit());
                data.add(nextRow);
            }
        }

        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        if (table != null) {
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
        switch (e.getActionCommand()) {
            case "btnAdd": {
                new ProductEditingDialog(Service.this, null).setVisible(true);
                break;
            }
            case "btnEdit": {
                if (table.getSelectedRow() > -1) {
                    String productName = table.getValueAt(table.getSelectedRow(), 1).toString();
                    if (productName.isEmpty()) {
                        return;
                    }
                    for (int i = 0; i < Data.listProductCategories.size(); i++) {
                        for (int j = 0; j < Data.listProductCategories.get(i).getListProductItems().size(); j++) {
                            ProductItem productItem = Data.listProductCategories.get(i).getListProductItems().get(j);
                            if (productItem.getName().equals(productName)) {
                                new ProductEditingDialog(Service.this, productItem).setVisible(true);
                                return;
                            }
                        }
                    }
                }
                break;
            }
            case "btnRemove": {
                int[] rowsSelected = table.getSelectedRows();
                if (rowsSelected.length > 0) {
                    for (int r = 0; r < rowsSelected.length; r++) {
                        String productName = table.getValueAt(rowsSelected[r], 1).toString();
                        if (productName.isEmpty()) {
                            continue;
                        }
                        for (int i = 0; i < Data.listProductCategories.size(); i++) {
                            ProductCategory category = Data.listProductCategories.get(i);
                            for (int j = 0; j < category.getListProductItems().size(); j++) {
                                ProductItem productItem = Data.listProductCategories.get(i).getListProductItems().get(j);
                                if (productItem.getName().equals(productName)) {
                                    category.getListProductItems().remove(productItem);
                                    break;
                                }
                            }
                        }
                    }
                    refreshTable();
                }
                break;
            }
            case "btnAddCategory": {
                new ProductCategoryDialog(Service.this).setVisible(true);
                break;
            }
        }
    }
}
