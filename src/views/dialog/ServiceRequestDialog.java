/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.dialog;

import data.Data;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import models.Computer;
import models.ServiceCanBeOrdered;
import models.ServiceCategory;
import models.ServiceItem;
import models.TableCellListener;
import models.User;

/**
 *
 * @author ADMIN
 */
public class ServiceRequestDialog extends JDialog implements ActionListener {

    private int WIDTH = 900, HEIGHT = 430;
    private JPanel container;
    private int fontSize = 12;

    private Computer computer;

    private JTable tableAllOfServices, tableServiceProvided;
    private DefaultTableModel tableModelAllOfServices, tableModelServiceProvided;

    private JButton btnProvided, btnCash, btnAdd;
    private JTextField edtPriceOfServiceProvided, edtPriceOfServiceWillAdd;
    private JLabel txtPriceOfServiceProvided, txtPriceOfServiceWillAdd;

    private int rowSelectedTableAllOfServices = -1;
    private int rowSelectedTableServiceProvided = -1;

    private ArrayList<ServiceCanBeOrdered> listAllOfServices = new ArrayList<ServiceCanBeOrdered>();

    public ServiceRequestDialog(Computer computer) {
        addServices();
        this.computer = computer;
        Toolkit toolkit = this.getToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width / 2 - WIDTH / 2, dimension.height / 2 - HEIGHT / 2, WIDTH, HEIGHT);
        this.setModal(true);
        this.setTitle("Yêu cầu dịch vụ");
        this.setResizable(false);
        this.setLayout(new BorderLayout());

        container = new JPanel();
        container.setLayout(new FlowLayout());
        container.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.add(container, BorderLayout.CENTER);

        JPanel providedPanel = new JPanel();
        providedPanel.setPreferredSize(new Dimension(WIDTH / 2 - 20, HEIGHT - 55));
        Border border2 = BorderFactory.createTitledBorder("Đã cung cấp");
        providedPanel.setBorder(border2);
        providedPanel.setLayout(new BoxLayout(providedPanel, BoxLayout.Y_AXIS));
        tableServiceProvided = new JTable();
        setupTableServiceProvided();
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(tableServiceProvided);
        providedPanel.add(scrollPane);

        JPanel bottomProvidedPanel = new JPanel(new BorderLayout());
        JPanel bottomLeftProvidedPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        setupButtonProvided();
        bottomLeftProvidedPanel.add(btnProvided);
        setupButtonCash();
        bottomLeftProvidedPanel.add(btnCash);
        bottomProvidedPanel.add(bottomLeftProvidedPanel, BorderLayout.WEST);
        JPanel bottomRightProvidedPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 10));
        setupPriceLabelContentForProvided(bottomRightProvidedPanel);
        bottomProvidedPanel.add(bottomRightProvidedPanel, BorderLayout.EAST);

        providedPanel.add(bottomProvidedPanel);

        JPanel addingServicePanel = new JPanel();
        addingServicePanel.setPreferredSize(new Dimension(WIDTH / 2 - 20, HEIGHT - 55));
        Border border3 = BorderFactory.createTitledBorder("Thêm dịch vụ");
        addingServicePanel.setBorder(border3);
        addingServicePanel.setLayout(new BoxLayout(addingServicePanel, BoxLayout.Y_AXIS));
        tableAllOfServices = new JTable();
        setupTableAllOfServices();
        JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setViewportView(tableAllOfServices);
        addingServicePanel.add(scrollPane1);

        JPanel bottomAddingServicePanel = new JPanel(new BorderLayout());
        JPanel bottomLeftAddingServicePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        setupButtonAdd();
        bottomLeftAddingServicePanel.add(btnAdd);
        bottomAddingServicePanel.add(bottomLeftAddingServicePanel, BorderLayout.WEST);
        JPanel bottomRightAddingServicePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 10));
        setupPriceLabelContentForListServices(bottomRightAddingServicePanel);
        bottomAddingServicePanel.add(bottomRightAddingServicePanel, BorderLayout.EAST);

        addingServicePanel.add(bottomAddingServicePanel);

        container.add(providedPanel);
        container.add(addingServicePanel);
    }

    private void addServices() {
        for (int i = 0; i < Data.listServiceCategories.size(); i++) {
            ServiceCategory category = Data.listServiceCategories.get(i);
            for (int j = 0; j < category.getListServiceItems().size(); j++) {
                ServiceItem item = category.getListServiceItems().get(j);
                listAllOfServices.add(new ServiceCanBeOrdered(item.getCloneService(), 0));
            }
        }
    }

    private void setupButtonProvided() {
        btnProvided = new JButton("Cung cấp");
        btnProvided.setActionCommand("btnProvided");
        btnProvided.addActionListener(this);
    }

    private void setupButtonCash() {
        btnCash = new JButton("Tiền mặt");
        btnCash.setActionCommand("btnCash");
        btnCash.addActionListener(this);
    }

    private void setupButtonAdd() {
        btnAdd = new JButton("Thêm");
        btnAdd.setActionCommand("btnAdd");
        btnAdd.addActionListener(this);
    }

    private void setupPriceLabelContentForListServices(JPanel parent) {
        txtPriceOfServiceWillAdd = new JLabel("Giá");
        edtPriceOfServiceWillAdd = new JTextField("");
        edtPriceOfServiceWillAdd.setPreferredSize(new Dimension(80, 20));
        edtPriceOfServiceWillAdd.setEditable(false);
        parent.add(txtPriceOfServiceWillAdd);
        parent.add(edtPriceOfServiceWillAdd);
    }
    
    private void setupPriceLabelContentForProvided(JPanel parent) {
        txtPriceOfServiceProvided = new JLabel("Giá");
        edtPriceOfServiceProvided = new JTextField("");
        edtPriceOfServiceProvided.setPreferredSize(new Dimension(80, 20));
        edtPriceOfServiceProvided.setEditable(false);
        parent.add(txtPriceOfServiceProvided);
        parent.add(edtPriceOfServiceProvided);
    }

    private void setupTableAllOfServices() {
        refreshTableAllOfServices();
        tableAllOfServices = new JTable(tableModelAllOfServices);
        tableAllOfServices.setRowHeight(30);
        tableAllOfServices.setDefaultRenderer(Object.class, new BoardTableAllOfServiceCellRenderer());
        Action action = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                TableCellListener tableCellListener = (TableCellListener) e.getSource();
                if (tableCellListener.getColumn() == 1) {
                    Pattern regEx = Pattern.compile("\\d+");
                    Matcher matcher = regEx.matcher((CharSequence) tableCellListener.getNewValue());
                    String value;
                    if (!matcher.matches()) {
                        value = (String) tableCellListener.getOldValue();
                    } else {
                        int number = Integer.parseInt((String) tableCellListener.getNewValue());
                        value = String.valueOf(number);
                        listAllOfServices.get(tableCellListener.getRow()).setNumber(number);
                        refreshTableAllOfServices();
                    }
                    tableAllOfServices.setValueAt(value, tableCellListener.getRow(), tableCellListener.getColumn());
                }
            }
        };
        TableCellListener tcl = new TableCellListener(tableAllOfServices, action);
    }

    private void setupTableServiceProvided() {
        refreshTableServiceProvided();
        tableServiceProvided = new JTable(tableModelServiceProvided);
        tableServiceProvided.setRowHeight(30);
    }

    public void refreshTableAllOfServices() {
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Sản phẩm");
        columnNames.add("Số lượng");
        columnNames.add("Hàng tồn");
        columnNames.add("Đơn giá");
        columnNames.add("Tổng giá tiền");

        int totalAmount = 0;

        Vector<Vector<String>> data = new Vector<Vector<String>>();
        for (int i = 0; i < listAllOfServices.size(); i++) {
            ServiceCanBeOrdered item = listAllOfServices.get(i);
            Vector<String> row = new Vector<String>();
            row.add(item.getServiceItem().getName());
            row.add(String.valueOf(item.getNumber()));
            row.add(item.getServiceItem().getStock() < 0 ? "(Không giới hạn)" : String.valueOf(item.getServiceItem().getStock()));
            row.add(String.valueOf(item.getServiceItem().getPrice()));
            if (item.getTotalFee() > 0) {
                totalAmount += item.getTotalFee();
                row.add(NumberFormat.getNumberInstance().format(item.getTotalFee()));
            } else {
                row.add("");
            }
            data.add(row);
        }

        tableModelAllOfServices = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 1) {
                    return true;
                }
                return false;
            }
        };

        if (tableAllOfServices != null) {
            tableAllOfServices.setModel(tableModelAllOfServices);
            if (rowSelectedTableAllOfServices > -1) {
                tableAllOfServices.setRowSelectionInterval(rowSelectedTableAllOfServices, rowSelectedTableAllOfServices);
            }
        };

        if (edtPriceOfServiceWillAdd != null) {
            if (totalAmount > 0) {
                System.out.println("1");
                edtPriceOfServiceWillAdd.setText(NumberFormat.getNumberInstance().format(totalAmount));
            } else {
                edtPriceOfServiceWillAdd.setText("");
            }
        }
    }

    public void refreshTableServiceProvided() {
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Sản phẩm");
        columnNames.add("Số lượng");
        columnNames.add("Hàng tồn");
        columnNames.add("Đơn giá");
        columnNames.add("Tổng giá tiền");

        Vector<Vector<String>> data = new Vector<Vector<String>>();

        tableModelServiceProvided = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        if (tableServiceProvided != null) {
            tableServiceProvided.setModel(tableModelServiceProvided);
            if (rowSelectedTableServiceProvided > -1) {
                tableServiceProvided.setRowSelectionInterval(rowSelectedTableServiceProvided, rowSelectedTableServiceProvided);
            }
        };
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "btnProvided": {
                break;
            }
            case "btnCash": {
                break;
            }
            case "btnAdd": {
                break;
            }
        }
    }
}

class BoardTableAllOfServiceCellRenderer extends DefaultTableCellRenderer {

    private int fontSize = 10;
    private Color colorSelectedText = Color.white;
    private Color colorUnselectedText = Color.BLACK;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        JPanel container = new JPanel(new BorderLayout());
        container.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        if (row % 2 == 0) {
            if (column % 2 == 0) {
                container.setBackground(Color.white);
            } else {
                container.setBackground(new Color(247, 247, 247));
            }
        } else {
            if (column % 2 == 1) {
                container.setBackground(Color.white);
            } else {
                container.setBackground(new Color(247, 247, 247));
            }
        }
        Border border = BorderFactory.createLineBorder(new Color(50, 50, 150, 50), 1, true);
        container.setBorder(border);
        switch (column) {
            case 0,2: {
                JTextArea jLabel = new JTextArea(String.valueOf(value).toUpperCase());
                jLabel.setLineWrap(true);
                Font newLabelFont = new Font(jLabel.getFont().getName(), Font.PLAIN, fontSize);
                jLabel.setFont(newLabelFont);
                container.add(jLabel, BorderLayout.CENTER);
                return container;
            }
            case 1: {
                JLabel jLabel = new JLabel(String.valueOf(value).toUpperCase(), SwingConstants.CENTER);
                Font newLabelFont = new Font(jLabel.getFont().getName(), Font.PLAIN, fontSize);
                jLabel.setFont(newLabelFont);
                container.add(jLabel, BorderLayout.CENTER);
                return container;
            }
            default: {
                JLabel jLabel = new JLabel(String.valueOf(value).toUpperCase(), SwingConstants.CENTER);
                Font newLabelFont = new Font(jLabel.getFont().getName(), Font.PLAIN, fontSize);
                jLabel.setFont(newLabelFont);
                container.add(jLabel, BorderLayout.CENTER);
                return container;
            }
        }
    }
}
