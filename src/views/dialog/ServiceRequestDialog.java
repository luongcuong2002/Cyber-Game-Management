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
import models.ProductCanBeOrdered;
import models.ProductCategory;
import models.ProductItem;
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

    private JTable tableAllOfProducts, tableProductProvided;
    private DefaultTableModel tableModelAllOfProducts, tableModelProductProvided;

    private JButton btnProvided, btnCash, btnAdd;
    private JTextField edtPriceOfProductProvided, edtPriceOfProductWillAdd;
    private JLabel txtPriceOfProductProvided, txtPriceOfProductWillAdd;

    private int rowSelectedTableAllOfProducts = -1;
    private int rowSelectedTableProductProvided = -1;

    private ArrayList<ProductCanBeOrdered> listAllOfProducts = new ArrayList<ProductCanBeOrdered>();

    private ArrayList<Boolean> checkList = new ArrayList<>();

    private JCheckBox checkAll;

    public ServiceRequestDialog(Computer computer) {
        this.computer = computer;
        seupCheckList();
        addProducts();
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

        tableProductProvided = new JTable();
        setupTableProductProvided();
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(tableProductProvided);

        providedPanel.add(scrollPane);
        providedPanel.add(bottomProvidedPanel);

        JPanel addingProductPanel = new JPanel();
        addingProductPanel.setPreferredSize(new Dimension(WIDTH / 2 - 20, HEIGHT - 55));
        Border border3 = BorderFactory.createTitledBorder("Thêm dịch vụ");
        addingProductPanel.setBorder(border3);
        addingProductPanel.setLayout(new BoxLayout(addingProductPanel, BoxLayout.Y_AXIS));
        tableAllOfProducts = new JTable();
        setupTableAllOfProducts();
        JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setViewportView(tableAllOfProducts);
        addingProductPanel.add(scrollPane1);

        JPanel bottomAddingProductPanel = new JPanel(new BorderLayout());
        JPanel bottomLeftAddingProductPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        setupButtonAdd();
        bottomLeftAddingProductPanel.add(btnAdd);
        bottomAddingProductPanel.add(bottomLeftAddingProductPanel, BorderLayout.WEST);
        JPanel bottomRightAddingProductPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 10));
        setupPriceLabelContentForListProducts(bottomRightAddingProductPanel);
        bottomAddingProductPanel.add(bottomRightAddingProductPanel, BorderLayout.EAST);

        addingProductPanel.add(bottomAddingProductPanel);

        container.add(providedPanel);
        container.add(addingProductPanel);
    }

    private void addProducts() {
        for (int i = 0; i < Data.listProductCategories.size(); i++) {
            ProductCategory category = Data.listProductCategories.get(i);
            for (int j = 0; j < category.getListProductItems().size(); j++) {
                ProductItem item = category.getListProductItems().get(j);
                listAllOfProducts.add(new ProductCanBeOrdered(item.getCloneProduct(), 0));
            }
        }
    }

    private void seupCheckList() {
        for (int i = 0; i < computer.getListProductsOrdered().size(); i++) {
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
                refreshTableProductProvided();
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

    private void setupPriceLabelContentForListProducts(JPanel parent) {
        txtPriceOfProductWillAdd = new JLabel("Giá");
        edtPriceOfProductWillAdd = new JTextField("");
        edtPriceOfProductWillAdd.setPreferredSize(new Dimension(80, 20));
        edtPriceOfProductWillAdd.setEditable(false);
        parent.add(txtPriceOfProductWillAdd);
        parent.add(edtPriceOfProductWillAdd);
    }

    private void setupPriceLabelContentForProvided(JPanel parent) {
        txtPriceOfProductProvided = new JLabel("Giá");
        edtPriceOfProductProvided = new JTextField("");
        edtPriceOfProductProvided.setPreferredSize(new Dimension(80, 20));
        edtPriceOfProductProvided.setEditable(false);
        parent.add(txtPriceOfProductProvided);
        parent.add(edtPriceOfProductProvided);
    }

    private void setupTableAllOfProducts() {
        refreshTableAllOfProducts();
        tableAllOfProducts = new JTable(tableModelAllOfProducts);
        tableAllOfProducts.setRowHeight(35);
        tableAllOfProducts.setDefaultRenderer(Object.class, new BoardTableAllOfProductCellRenderer());
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
                        listAllOfProducts.get(tableCellListener.getRow()).setNumber(number);
                        refreshTableAllOfProducts();
                    }
                    tableAllOfProducts.setValueAt(value, tableCellListener.getRow(), tableCellListener.getColumn());
                }
            }
        };
        TableCellListener tcl = new TableCellListener(tableAllOfProducts, action);
    }

    private void setupTableProductProvided() {
        refreshTableProductProvided();
        tableProductProvided = new JTable(tableModelProductProvided);
        tableProductProvided.setRowHeight(35);
        tableProductProvided.setDefaultRenderer(Object.class, new BoardTableProductProvidedCellRenderer(this));
    }

    public void refreshTableAllOfProducts() {
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Sản phẩm");
        columnNames.add("Số lượng");
        columnNames.add("Đơn giá");
        columnNames.add("Tổng giá tiền");

        int totalAmount = 0;

        Vector<Vector<String>> data = new Vector<Vector<String>>();
        for (int i = 0; i < listAllOfProducts.size(); i++) {
            ProductCanBeOrdered item = listAllOfProducts.get(i);
            Vector<String> row = new Vector<String>();
            row.add(item.getProductItem().getName());
            row.add(String.valueOf(item.getNumber()));
            row.add(String.valueOf(item.getProductItem().getPrice()));
            if (item.getNumber() * item.getProductItem().getPrice() > 0) {
                totalAmount += item.getNumber() * item.getProductItem().getPrice();
                row.add(NumberFormat.getNumberInstance().format(item.getNumber() * item.getProductItem().getPrice()));
            } else {
                row.add("");
            }
            data.add(row);
        }

        tableModelAllOfProducts = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 1) {
                    return true;
                }
                return false;
            }
        };

        if (tableAllOfProducts != null) {
            tableAllOfProducts.setModel(tableModelAllOfProducts);
            if (rowSelectedTableAllOfProducts > -1) {
                tableAllOfProducts.setRowSelectionInterval(rowSelectedTableAllOfProducts, rowSelectedTableAllOfProducts);
            }
        };

        if (edtPriceOfProductWillAdd != null) {
            if (totalAmount > 0) {
                edtPriceOfProductWillAdd.setText(NumberFormat.getNumberInstance().format(totalAmount));
            } else {
                edtPriceOfProductWillAdd.setText("");
            }
        }
    }

    public void refreshTableProductProvided() {
        Vector<Object> columnNames = new Vector<>();
        columnNames.add("");
        columnNames.add("Sản phẩm");
        columnNames.add("Số lượng");
        columnNames.add("Tình trạng");
        columnNames.add("Đơn giá");
        columnNames.add("Tổng giá tiền");

        int totalAmount = 0;
        boolean checkIfAnyProductProvided = false;
        boolean checkIfAnyProductChecked = false;

        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        for (int i = 0; i < computer.getListProductsOrdered().size(); i++) {
            ProductCanBeOrdered item = computer.getListProductsOrdered().get(i);
            Vector<Object> row = new Vector<Object>();
            if (i < checkList.size()) {
                row.add(checkList.get(i));
            } else {
                row.add(true);
            }
            row.add(item.getProductItem().getName());
            row.add(String.valueOf(item.getNumber()));
            row.add(item.isIsProvided() ? "Đã cung cấp" : "");
            row.add(String.valueOf(item.getProductItem().getPrice()));
            row.add(NumberFormat.getNumberInstance().format(item.getNumber() * item.getProductItem().getPrice()));
            data.add(row);

            if (i < checkList.size()) {
                if (checkList.get(i)) {
                    checkIfAnyProductChecked = true;
                    if (item.isIsProvided()) {
                        checkIfAnyProductProvided = true;
                    }
                    totalAmount += item.getNumber() * item.getProductItem().getPrice();
                }
            }
        }

        tableModelProductProvided = new DefaultTableModel(data, columnNames) {
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
                    ServiceRequestDialog.this.refreshTableProductProvided();
                }
            }
        };

        if (tableProductProvided != null) {
            tableProductProvided.setModel(tableModelProductProvided);
            if (rowSelectedTableProductProvided > -1) {
                tableProductProvided.setRowSelectionInterval(rowSelectedTableProductProvided, rowSelectedTableProductProvided);
            }
        };

        if (edtPriceOfProductProvided != null) {
            if (totalAmount > 0) {
                edtPriceOfProductProvided.setText(NumberFormat.getNumberInstance().format(totalAmount));
            } else {
                edtPriceOfProductProvided.setText("");
            }
        }

        if (!checkIfAnyProductChecked) {
            btnCash.setEnabled(false);
            btnProvided.setEnabled(false);
        } else {
            btnCash.setEnabled(true);
        }

        if (checkIfAnyProductProvided) {
            btnProvided.setEnabled(false);
        } else if (checkIfAnyProductChecked) {
            btnProvided.setEnabled(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "btnProvided": {
                for (int i = 0; i < computer.getListProductsOrdered().size(); i++) {
                    if (i < checkList.size() && checkList.get(i)) {
                        ProductCanBeOrdered item = computer.getListProductsOrdered().get(i);
                        item.setIsProvided(true);
                    }
                }
                ServiceRequestDialog.this.setVisible(false);
                break;
            }
            case "btnCash": {
                for (int i = 0; i < computer.getListProductsOrdered().size(); i++) {
                    if (i < checkList.size() && checkList.get(i)) {
                        ProductCanBeOrdered item = computer.getListProductsOrdered().get(i);
                        int amount = item.getNumber() * item.getProductItem().getPrice();
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
                        computer.getListProductsOrdered().remove(i);
                        i--;
                    }
                }
                ServiceRequestDialog.this.setVisible(false);
                break;
            }
            case "btnAdd": {
                for (int i = 0; i < listAllOfProducts.size(); i++) {
                    if (listAllOfProducts.get(i).getNumber() > 0) {
                        checkList.add(true);
                        computer.getListProductsOrdered().add(new ProductCanBeOrdered(
                                        listAllOfProducts.get(i).getProductItem().getCloneProduct(),
                                        listAllOfProducts.get(i).getNumber(),
                                        false
                                ));
                        listAllOfProducts.get(i).setNumber(0);
                    }
                }
                ServiceRequestDialog.this.refreshTableAllOfProducts();
                ServiceRequestDialog.this.refreshTableProductProvided();
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

class BoardTableAllOfProductCellRenderer extends DefaultTableCellRenderer {

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
                    Logger.getLogger(BoardTableProductProvidedCellRenderer.class.getName()).log(Level.SEVERE, null, ex);
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

class BoardTableProductProvidedCellRenderer extends DefaultTableCellRenderer {

    private int fontSize = 10;
    private Color colorSelectedText = Color.white;
    private Color colorUnselectedText = Color.BLACK;

    private ServiceRequestDialog parent;

    public BoardTableProductProvidedCellRenderer(ServiceRequestDialog parent) {
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
                    Logger.getLogger(BoardTableProductProvidedCellRenderer.class.getName()).log(Level.SEVERE, null, ex);
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
