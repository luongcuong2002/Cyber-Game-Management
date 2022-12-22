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
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import models.ProductCategory;
import models.ProductItem;
import views.tabs.Service;

/**
 *
 * @author ADMIN
 */
public class ProductEditingDialog extends JDialog {

    private int WIDTH = 260, HEIGHT = 240;
    private JPanel container;
    private int fontSize = 12;

    private JComboBox jCBCategory;
    private JTextField edtName, edtPrice;
    private JButton btnConfirm, btnCancel;

    private ProductItem productItem;
    private Service serviceTab;

    public ProductEditingDialog(Service serviceTab, ProductItem productItem) {
        this.serviceTab = serviceTab;
        this.productItem = productItem;
        Toolkit toolkit = this.getToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width / 2 - WIDTH / 2, dimension.height / 2 - HEIGHT / 2, WIDTH, HEIGHT);
        this.setModal(true);
        this.setTitle("Sửa");
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
        this.add(container, BorderLayout.CENTER);

        JPanel jPanelProductName = new JPanel();
        JLabel label1 = new JLabel("Tên");
        label1.setFont(label1.getFont().deriveFont(Font.BOLD, fontSize));
        label1.setPreferredSize(new Dimension(25, 25));
        edtName = new JTextField(productItem != null ? productItem.getName() : "");
        edtName.setFont(edtName.getFont().deriveFont(fontSize));
        edtName.setPreferredSize(new Dimension(200, 25));
        jPanelProductName.add(label1);
        jPanelProductName.add(edtName);

        JPanel jPanelProductPrice = new JPanel();
        JLabel label2 = new JLabel("Giá");
        label2.setFont(label1.getFont().deriveFont(Font.BOLD, fontSize));
        label2.setPreferredSize(new Dimension(25, 25));
        edtPrice = new JTextField(productItem != null ? String.valueOf(productItem.getPrice()) : "");
        edtPrice.setFont(edtPrice.getFont().deriveFont(fontSize));
        edtPrice.setPreferredSize(new Dimension(200, 25));
        jPanelProductPrice.add(label2);
        jPanelProductPrice.add(edtPrice);

        JPanel jPanelCategory = new JPanel();
        Border titleBorder = BorderFactory.createTitledBorder("Loại");
        jPanelCategory.setBorder(titleBorder);
        setupJComBoxCategory();
        jPanelCategory.add(jCBCategory);

        JPanel jPanelButtonContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        setupConfirmButton();
        setupCancelButton();
        jPanelButtonContainer.add(btnConfirm);
        jPanelButtonContainer.add(btnCancel);

        container.add(jPanelProductName);
        container.add(jPanelProductPrice);
        container.add(jPanelCategory);
        container.add(jPanelButtonContainer);
    }

    private void setupJComBoxCategory() {
        Vector<String> list = new Vector<String>();
        for (int i = 0; i < Data.listProductCategories.size(); i++) {
            list.add(Data.listProductCategories.get(i).getName());
        }

        jCBCategory = new JComboBox(list);
        jCBCategory.setPreferredSize(new Dimension(200, 25));
        jCBCategory.setFont(jCBCategory.getFont().deriveFont(Font.PLAIN, fontSize));
        if(productItem != null){
            jCBCategory.setSelectedItem(productItem.getCategory());
        }
        jCBCategory.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
            }
        });
    }

    private void setupConfirmButton() {
        btnConfirm = new JButton("Xong");
        btnConfirm.setPreferredSize(new Dimension(70, 25));
        btnConfirm.setBackground(Color.green);
        btnConfirm.setForeground(Color.white);
        btnConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String name = edtName.getText().trim().toString();
                String priceString = edtPrice.getText().trim().toString();
                String categoryName = jCBCategory.getItemAt(jCBCategory.getSelectedIndex()).toString();

                if (name.isEmpty() || priceString.isEmpty()) {
                    JOptionPane.showMessageDialog(ProductEditingDialog.this,
                            "Không được bỏ trống!",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int price;
                try {
                    price = Integer.parseInt(priceString);
                    if (price <= 0) {
                        JOptionPane.showMessageDialog(ProductEditingDialog.this,
                                "Giá phải lớn hơn 0",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException err) {
                    JOptionPane.showMessageDialog(ProductEditingDialog.this,
                            err.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                for (int i = 0; i < Data.listProductCategories.size(); i++) {
                    for (int j = 0; j < Data.listProductCategories.get(i).getListProductItems().size(); j++) {
                        ProductItem item = Data.listProductCategories.get(i).getListProductItems().get(j);
                        if (item.getName().equals(name)) {
                            if (productItem != null && productItem.equals(item)) {
                                continue;
                            }

                            JOptionPane.showMessageDialog(ProductEditingDialog.this,
                                    "Sản phẩm đã tồn tại!",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                }

                if (productItem != null) {
                    if (productItem.getCategory().equals(categoryName) == false) {
                        // remove from current category
                        for (int i = 0; i < Data.listProductCategories.size(); i++) {
                            ProductCategory category = Data.listProductCategories.get(i);
                            if (productItem.getCategory().equals(category.getName())) {
                                category.getListProductItems().remove(productItem);
                            }
                            if (categoryName.equals(category.getName())) { // add to new category
                                category.getListProductItems().add(productItem);
                            }
                        }
                    }
                    productItem.setName(name);
                    productItem.setPrice(price);
                    productItem.setCategory(categoryName);
                } else { // create new
                    productItem = new ProductItem(name, price, "VNĐ", categoryName);
                    for (int i = 0; i < Data.listProductCategories.size(); i++) {
                        ProductCategory category = Data.listProductCategories.get(i);
                        if(category.getName().equals(categoryName)){
                            category.getListProductItems().add(productItem);
                        }
                    }
                }
                serviceTab.refreshTable();
                
                ProductEditingDialog.this.setVisible(false);
            }
        });
    }

    private void setupCancelButton() {
        btnCancel = new JButton("Hủy");
        btnCancel.setPreferredSize(new Dimension(70, 25));
        btnCancel.setBackground(Color.gray);
        btnCancel.setForeground(Color.white);
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductEditingDialog.this.setVisible(false);
            }
        });
    }
}
