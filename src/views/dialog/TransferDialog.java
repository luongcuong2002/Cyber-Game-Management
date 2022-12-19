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
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import models.Computer;
import views.tabs.ComputerClient;

/**
 *
 * @author ADMIN
 */
public class TransferDialog extends JDialog{

    private int WIDTH = 300, HEIGHT = 400;
    private JPanel container;
    private int fontSize = 12;

    private JTable table;
    private JScrollPane scrollPane;
    private DefaultTableModel tableModel;
    private JButton btnConfirm, btnCancel;

    private int tableSelectedRow = -1;

    private Computer computer;
    private BillDialog parentView;

    public TransferDialog(BillDialog parentView, Computer computer) {
        this.computer = computer;
        this.parentView = parentView;
        Toolkit toolkit = this.getToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width / 2 - WIDTH / 2, dimension.height / 2 - HEIGHT / 2, WIDTH, HEIGHT);
        this.setModal(true);
        this.setTitle("Chuyển tiền");
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        container = new JPanel();
        container.setLayout(new BorderLayout());
        container.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.add(container, BorderLayout.CENTER);

        setupScrollPane();
        setupTable();
        scrollPane.setViewportView(table);
        container.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonsWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 5));
        setupConfirmButton();
        setupCancelButton();
        buttonsWrapper.add(btnConfirm);
        buttonsWrapper.add(btnCancel);

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

    private void setupConfirmButton() {
        btnConfirm = new JButton("Đồng ý");
        btnConfirm.setBackground(new Color(44, 182, 109));
        btnConfirm.setForeground(Color.white);
        btnConfirm.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(table.getSelectedRow());
                if (table.getSelectedRow() >= 0) {
                    Computer computerTarget = Data.getComputerByComputerName((String) table.getValueAt(table.getSelectedRow(), 0));
                    if(computerTarget != null){
                        computerTarget.addTransactionTransfer(
                                computer.getUserUsing().getUserName(), 
                                computer.getTimeFee(),
                                computer.getServiceFee());
                        for(int i = 0; i < computer.getListTransactionsTransfer().size(); i++){
                            computerTarget.addTransactionTransfer(
                                    computer.getListTransactionsTransfer().get(i).getFromUser(), 
                                    computer.getListTransactionsTransfer().get(i).getTimeFee(), 
                                    computer.getListTransactionsTransfer().get(i).getServiceFee());
                        }
                        parentView.paid();
                        TransferDialog.this.setVisible(false);
                    }
                }
            }
        });
    }

    private void setupCancelButton() {
        btnCancel = new JButton("Hủy bỏ");
        btnCancel.setBackground(Color.gray);
        btnCancel.setForeground(Color.white);
        btnCancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                TransferDialog.this.setVisible(false);
            }
        });
    }

    private void setupTable() {
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Máy");
        columnNames.add("Người sử dụng");
        columnNames.add("Loại máy");

        Vector<Vector<String>> data = new Vector<>();
        for (int i = 0; i < Data.listComputers.size(); i++) {
            Vector<String> row = new Vector<>();
            Computer computer = Data.listComputers.get(i);
            if (computer.getUserUsing() != null && computer.getUserUsing().getUserGroupName().equals("Guest") && computer.getComputerName() != this.computer.getComputerName()) {
                row.add(computer.getComputerName());
                row.add(computer.getUserUsing().getUserName());
                row.add(computer.getUserUsing().getUserGroupName());
                data.add(row);
            }
        }

        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(20);
    }
}
