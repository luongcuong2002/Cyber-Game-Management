package views.dialog;

import data.Data;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import models.ComputerGroup;
import models.UserGroup;

public class ComputerGroupManagerDialog extends JDialog {

    private int WIDTH = 350, HEIGHT = 400;
    private JPanel container;
    private int fontSize = 12;

    private JTable table;
    private JButton btnAdd, btnCancel;
    private JScrollPane scrollPane;
    private DefaultTableModel tableModel;
    private JTextField edtGroupName, edtDecription;

    private views.tabs.ComputerGroup parentView;

    private ComputerGroup computerGroup;

    private boolean isEditAction;
    private int tableSelectedRow = -1;

    public ComputerGroupManagerDialog(views.tabs.ComputerGroup parentView, ComputerGroup computerGroup, boolean isEditAction, boolean hasEditNamePermission) {
        this.isEditAction = isEditAction;
        this.parentView = parentView;
        if (computerGroup != null) {
            this.computerGroup = computerGroup;
        } else {
            ArrayList<UserGroup> list = new ArrayList<UserGroup>();
            for (int i = 0; i < Data.computerGroups.get(0).getPriceForEachUserGroups().size(); i++) {
                UserGroup item = Data.computerGroups.get(0).getPriceForEachUserGroups().get(i);
                list.add(new UserGroup(item.getUserGroupName(), 0));
            }
            this.computerGroup = computerGroup = new ComputerGroup("", "", list);
        }
        Toolkit toolkit = this.getToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width / 2 - WIDTH / 2, dimension.height / 2 - HEIGHT / 2, WIDTH, HEIGHT);
        this.setModal(true);
        this.setTitle("Nhóm máy");
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.add(container, BorderLayout.CENTER);

        JPanel groupNamePanel = new JPanel();
        groupNamePanel.setLayout(new BoxLayout(groupNamePanel, BoxLayout.X_AXIS));
        JLabel label2 = new JLabel("Tên nhóm: ");
        label2.setPreferredSize(new Dimension(70, 30));
        label2.setFont(label2.getFont().deriveFont(fontSize));
        groupNamePanel.add(label2);

        edtGroupName = new JTextField(computerGroup.getGroupName());
        edtGroupName.setFont(edtGroupName.getFont().deriveFont(fontSize));
        edtGroupName.setEnabled(hasEditNamePermission);
        groupNamePanel.add(edtGroupName);

        JPanel descriptionPanel = new JPanel(new GridLayout(1, 2, 0, 0));
        descriptionPanel.setLayout(new BoxLayout(descriptionPanel, BoxLayout.X_AXIS));
        JLabel label1 = new JLabel("Mô tả: ");
        label1.setPreferredSize(new Dimension(70, 30));
        label1.setFont(label1.getFont().deriveFont(fontSize));
        descriptionPanel.add(label1);

        edtDecription = edtDecription = new JTextField(computerGroup.getDescription());;
        edtDecription.setFont(edtDecription.getFont().deriveFont(fontSize));
        descriptionPanel.add(edtDecription);

        setupTable();
        setupScrollPane();
        scrollPane.setViewportView(table);

        JPanel buttonsWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 5));
        setupAddButton();
        setupCancelButton();
        buttonsWrapper.add(btnAdd);
        buttonsWrapper.add(btnCancel);

        container.add(groupNamePanel);
        container.add(descriptionPanel);
        container.add(scrollPane);
        container.add(buttonsWrapper);
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
        btnAdd = new JButton(isEditAction ? "Cập nhật" : "Thêm");
        btnAdd.setBackground(new Color(44, 182, 109));
        btnAdd.setForeground(Color.white);
        btnAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String txtGroupName = edtGroupName.getText().trim();
                if (txtGroupName.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Tên không được để trống!",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (!isEditAction) {
                    for (int i = 0; i < Data.computerGroups.size(); i++) {
                        if (txtGroupName.equals(Data.computerGroups.get(i).getGroupName())) {
                            JOptionPane.showMessageDialog(null,
                                    "Tên đã tồn tại!",
                                    "Warning",
                                    JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    }
                }
                for (int i = 0; i < table.getRowCount(); i++) {
                    try {
                        int rate = Integer.parseInt(String.valueOf(table.getValueAt(i, 1)));
                        if (rate <= 0) {
                            JOptionPane.showMessageDialog(null,
                                    "Số tiền phải lớn hơn 0!",
                                    "Warning",
                                    JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        ComputerGroupManagerDialog.this.computerGroup.getPriceForEachUserGroups().get(i).setPrice(rate); // bad code
                    } catch (NumberFormatException error) {
                        JOptionPane.showMessageDialog(null,
                                "Không đọc được số!",
                                "Warning",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }
                ComputerGroupManagerDialog.this.computerGroup.setGroupName(txtGroupName.toUpperCase());
                ComputerGroupManagerDialog.this.computerGroup.setDescription(edtDecription.getText().trim());
                if (isEditAction == false) {
                    Data.computerGroups.add(ComputerGroupManagerDialog.this.computerGroup);
                }
                ComputerGroupManagerDialog.this.setVisible(false);
                parentView.refreshTable();
            }
        });
    }

    private void setupCancelButton() {
        btnCancel = new JButton("Hủy bỏ");
        btnCancel.setBackground(Color.blue);
        btnCancel.setForeground(Color.white);
        btnCancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ComputerGroupManagerDialog.this.setVisible(false);
            }
        });
    }

    private void setupTable() {
        Vector<Object> columnNames = new Vector<>();
        columnNames.add("Nhóm người dùng");
        columnNames.add("Giá");

        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        if (computerGroup != null) {
            for (int i = 0; i < computerGroup.getPriceForEachUserGroups().size(); i++) {
                UserGroup userGroup = computerGroup.getPriceForEachUserGroups().get(i);
                Vector<Object> row = new Vector<Object>();
                row.add(userGroup.getUserGroupName());
                row.add(userGroup.getPrice());
                data.add(row);
            }
        }

        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 1) {
                    return true;
                }
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(20);
    }
}
