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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.NumberFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import models.Computer;
import models.ServiceCanBeOrdered;
import models.ServiceCategory;
import models.ServiceItem;
import models.TableCellListener;
import models.TransactionHistoryItem;
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

    private ArrayList<Boolean> checkList = new ArrayList<>();

    private JCheckBox checkAll;

    public ServiceRequestDialog(Computer computer) {
        this.computer = computer;
        seupCheckList();
        addServices();
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
        Border border2 = BorderFactory.createTitledBorder("Thực hiện");
        providedPanel.setBorder(border2);
        providedPanel.setLayout(new BoxLayout(providedPanel, BoxLayout.Y_AXIS));

        providedPanel.add(renderWrapperJCheckBoxAll());

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

        tableServiceProvided = new JTable();
        setupTableServiceProvided();
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(tableServiceProvided);

        providedPanel.add(scrollPane);
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

    private void seupCheckList() {
        for (int i = 0; i < computer.getListServicesOrdered().size(); i++) {
            checkList.add(false);
        }
    }

    private JPanel renderWrapperJCheckBoxAll() {
        JPanel container = new JPanel(new FlowLayout(FlowLayout.LEFT));
        checkAll = new JCheckBox("Chọn / bỏ chọn tất cả");
        checkAll.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                for (int i = 0; i < checkList.size(); i++) {
                    checkList.set(i, e.getStateChange() == 1 ? true : false);
                };
                refreshTableServiceProvided();
            }
        });
        container.add(checkAll);
        return container;
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
        tableAllOfServices.setRowHeight(35);
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
        tableServiceProvided.setRowHeight(35);
        tableServiceProvided.setDefaultRenderer(Object.class, new BoardTableServiceProvidedCellRenderer(this));
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
            if (item.getNumber() * item.getServiceItem().getPrice() > 0) {
                totalAmount += item.getNumber() * item.getServiceItem().getPrice();
                row.add(NumberFormat.getNumberInstance().format(item.getNumber() * item.getServiceItem().getPrice()));
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
                edtPriceOfServiceWillAdd.setText(NumberFormat.getNumberInstance().format(totalAmount));
            } else {
                edtPriceOfServiceWillAdd.setText("");
            }
        }
    }

    public void refreshTableServiceProvided() {
        Vector<Object> columnNames = new Vector<>();
        columnNames.add("");
        columnNames.add("Sản phẩm");
        columnNames.add("Số lượng");
        columnNames.add("Tình trạng");
        columnNames.add("Đơn giá");
        columnNames.add("Tổng giá tiền");

        int totalAmount = 0;
        boolean checkIfAnyServiceProvided = false;
        boolean checkIfAnyServiceChecked = false;

        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        for (int i = 0; i < computer.getListServicesOrdered().size(); i++) {
            ServiceCanBeOrdered item = computer.getListServicesOrdered().get(i);
            Vector<Object> row = new Vector<Object>();
            if (i < checkList.size()) {
                row.add(checkList.get(i));
            } else {
                row.add(true);
            }
            row.add(item.getServiceItem().getName());
            row.add(String.valueOf(item.getNumber()));
            row.add(item.isIsProvided() ? "Đã cung cấp" : "");
            row.add(String.valueOf(item.getServiceItem().getPrice()));
            row.add(NumberFormat.getNumberInstance().format(item.getNumber() * item.getServiceItem().getPrice()));
            data.add(row);

            if (i < checkList.size()) {
                if (checkList.get(i)) {
                    checkIfAnyServiceChecked = true;
                    if (item.isIsProvided()) {
                        checkIfAnyServiceProvided = true;
                    }
                    totalAmount += item.getNumber() * item.getServiceItem().getPrice();
                }
            }
        }

        tableModelServiceProvided = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0) {
                    return true;
                }
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return Boolean.class;
                }
                return String.class;
            }

            @Override
            public void setValueAt(Object aValue, int row, int column) {
                super.setValueAt(aValue, row, column);
                if (column == 0) {
                    if ((Boolean) this.getValueAt(row, column) == true) {
                        if (row < checkList.size()) {
                            checkList.set(row, true);
                        }
                    } else if ((Boolean) this.getValueAt(row, column) == false) {
                        if (row < checkList.size()) {
                            checkList.set(row, false);
                        }
                    }
                    ServiceRequestDialog.this.refreshTableServiceProvided();
                }
            }
        };

        if (tableServiceProvided != null) {
            tableServiceProvided.setModel(tableModelServiceProvided);
            if (rowSelectedTableServiceProvided > -1) {
                tableServiceProvided.setRowSelectionInterval(rowSelectedTableServiceProvided, rowSelectedTableServiceProvided);
            }
        };

        if (edtPriceOfServiceProvided != null) {
            if (totalAmount > 0) {
                edtPriceOfServiceProvided.setText(NumberFormat.getNumberInstance().format(totalAmount));
            } else {
                edtPriceOfServiceProvided.setText("");
            }
        }

        if (!checkIfAnyServiceChecked) {
            btnCash.setEnabled(false);
            btnProvided.setEnabled(false);
        } else {
            btnCash.setEnabled(true);
        }

        if (checkIfAnyServiceProvided) {
            btnProvided.setEnabled(false);
        } else if (checkIfAnyServiceChecked) {
            btnProvided.setEnabled(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "btnProvided": {
                for (int i = 0; i < computer.getListServicesOrdered().size(); i++) {
                    if (i < checkList.size() && checkList.get(i)) {
                        ServiceCanBeOrdered item = computer.getListServicesOrdered().get(i);
                        item.setIsProvided(true);
                    }
                }
                ServiceRequestDialog.this.setVisible(false);
                break;
            }
            case "btnCash": {
                for (int i = 0; i < computer.getListServicesOrdered().size(); i++) {
                    if (i < checkList.size() && checkList.get(i)) {
                        ServiceCanBeOrdered item = computer.getListServicesOrdered().get(i);
                        int amount = item.getNumber() * item.getServiceItem().getPrice();
                        Data.listTransactionHistoryItems.add(new TransactionHistoryItem(
                                computer.getUserUsing().getUserName(),
                                null,
                                Calendar.getInstance().getTime(),
                                amount,
                                0,
                                computer.getUserUsing().getUserGroupName() + " " + computer.getUserUsing().getUserName()
                                + " đã trả " + NumberFormat.getNumberInstance().format(amount) + " đồng" + " phí dịch vụ bằng tiền mặt."
                        ));
                        checkList.remove(i);
                        computer.getListServicesOrdered().remove(i);
                        i--;
                    }
                }
                ServiceRequestDialog.this.setVisible(false);
                break;
            }
            case "btnAdd": {
                for (int i = 0; i < listAllOfServices.size(); i++) {
                    if (listAllOfServices.get(i).getNumber() > 0) {
                        checkList.add(true);
                        computer.getListServicesOrdered().add(
                                new ServiceCanBeOrdered(
                                        listAllOfServices.get(i).getServiceItem().getCloneService(),
                                        listAllOfServices.get(i).getNumber(),
                                        false
                                ));
                        listAllOfServices.get(i).setNumber(0);
                    }
                }
                ServiceRequestDialog.this.refreshTableAllOfServices();
                ServiceRequestDialog.this.refreshTableServiceProvided();
                break;
            }
        }
    }

    public ArrayList<Boolean> getCheckList() {
        return checkList;
    }

    public void setCheckList(ArrayList<Boolean> checkList) {
        this.checkList = checkList;
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
                JTextPane textPane = new JTextPane();
                Font newLabelFont = new Font(textPane.getFont().getName(), Font.PLAIN, fontSize);
                textPane.setFont(newLabelFont);
                StyledDocument doc = textPane.getStyledDocument();
                SimpleAttributeSet center = new SimpleAttributeSet();
                StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
                doc.setParagraphAttributes(0, doc.getLength(), center, false);
                try {
                    doc.insertString(doc.getLength(), String.valueOf(value).toUpperCase(), doc.getStyle("regular"));
                } catch (BadLocationException ex) {
                    Logger.getLogger(BoardTableServiceProvidedCellRenderer.class.getName()).log(Level.SEVERE, null, ex);
                }
                container.add(textPane, BorderLayout.CENTER);
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

class BoardTableServiceProvidedCellRenderer extends DefaultTableCellRenderer {

    private int fontSize = 10;
    private Color colorSelectedText = Color.white;
    private Color colorUnselectedText = Color.BLACK;

    private ServiceRequestDialog parent;

    public BoardTableServiceProvidedCellRenderer(ServiceRequestDialog parent) {
        this.parent = parent;
    }

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
            case 0: {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
            case 1: {
                JTextPane textPane = new JTextPane();
                Font newLabelFont = new Font(textPane.getFont().getName(), Font.PLAIN, fontSize);
                textPane.setFont(newLabelFont);
                StyledDocument doc = textPane.getStyledDocument();
                SimpleAttributeSet center = new SimpleAttributeSet();
                StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
                doc.setParagraphAttributes(0, doc.getLength(), center, false);
                try {
                    doc.insertString(doc.getLength(), String.valueOf(value), doc.getStyle("regular"));
                } catch (BadLocationException ex) {
                    Logger.getLogger(BoardTableServiceProvidedCellRenderer.class.getName()).log(Level.SEVERE, null, ex);
                }
                container.add(textPane, BorderLayout.CENTER);
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
