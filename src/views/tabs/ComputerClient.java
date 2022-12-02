package views.tabs;

import data.Data;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import models.Computer;
import models.User;
import views.popup.ComputerClientPopUp;

public class ComputerClient extends JPanel {

    private JTable table;
    private JScrollPane scrollPane;
    private AbstractTableModel tableModel;

    private int tableSelectedRow = -1;

    public ComputerClient() {
        this.setBackground(Color.white);
        setupScrollPane();
        setupTable();

        this.setLayout(new BorderLayout());

        scrollPane.setViewportView(table);

        this.add(scrollPane, BorderLayout.CENTER);
    }

    private void setupScrollPane() {
        scrollPane = new JScrollPane();
        scrollPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                int r = table.rowAtPoint(event.getPoint());
                if (r < 0 || r >= table.getRowCount()) {
                    table.clearSelection();
                    tableSelectedRow = -1;
                }
            }
        });
    }

    private void setupTable() {

        refreshTable();
        table = new JTable(tableModel);
        table.setDefaultRenderer(Object.class, new BoardTableCellRenderer());
        table.setRowHeight(22);
        table.setShowGrid(false);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                if (event.getButton() == MouseEvent.BUTTON3) {
                    int r = table.rowAtPoint(event.getPoint());
                    if (r >= 0 && r < table.getRowCount()) {
                        table.setRowSelectionInterval(r, r);
                        tableSelectedRow = r;
                    } else {
                        table.clearSelection();
                        tableSelectedRow = -1;
                    }

                    int rowindex = table.getSelectedRow();
                    if (rowindex < 0) {
                        return;
                    }
                    if (event.getComponent() instanceof JTable) {
                        ComputerClientPopUp menu = new ComputerClientPopUp(ComputerClient.this, table.getValueAt(rowindex, 0).toString());
                        menu.show(event.getComponent(), event.getX(), event.getY());
                    }
                }
            }

            @Override
            public void mouseClicked(MouseEvent event) {
                if (event.getButton() != MouseEvent.BUTTON1) {
                    return;
                }
                if (event.getClickCount() == 2) {
                    Point pnt = event.getPoint();
                    int row = table.rowAtPoint(pnt);
                    tableSelectedRow = row;
                    if (row < 0) {
                        return;
                    }

                    String computerName = (String) table.getValueAt(row, 0);
                    if (computerName.equals(Data.listComputers.get(row).getComputerName())) { // useful when table unsorted
                        User userUsing = Data.listComputers.get(row).getUserUsing();
                        if (userUsing != null && userUsing.getUserGroupName().equals("Guest") && userUsing.isIsPrepaid() == false) {
                            Data.listComputers.get(row).charge(ComputerClient.this);
                            return;
                        }
                    }
                    for (int i = 0; i < Data.listComputers.size(); i++) {
                        if (Data.listComputers.get(i).getComputerName().equals(computerName)) {
                            User userUsing = Data.listComputers.get(i).getUserUsing();
                            if (userUsing != null && userUsing.getUserGroupName().equals("Guest") && userUsing.isIsPrepaid() == false) {
                                Data.listComputers.get(i).charge(ComputerClient.this);
                            }
                            if (userUsing == null) {
                                Data.listComputers.get(i).turnOnComputer(new User(Data.listComputers.get(i).getComputerName(), "Guest", false), ComputerClient.this);
                            }
                            return;
                        }
                    }
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

    public void refreshTable() {
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Tên");
        columnNames.add("Tình trạng");
        columnNames.add("Người sử dụng");
        columnNames.add("Bắt đầu");
        columnNames.add("Sử dụng");
        columnNames.add("Còn lại");
        columnNames.add("Tiền đã dùng");
        columnNames.add("Ngày");
        columnNames.add("Nhóm máy");
        columnNames.add("Ghi chú");

        SimpleDateFormat formater;

        Vector<Vector<String>> data = new Vector<>();
        for (int i = 0; i < Data.listComputers.size(); i++) {
            Vector<String> row = new Vector<>();
            Computer computer = Data.listComputers.get(i);
            row.add(computer.getComputerName());
            row.add(computer.getStatus());
            if (computer.getUserUsing() != null) {
                row.add(computer.getUserUsing().getUserName());

                formater = new SimpleDateFormat("HH:mm:ss");
                row.add(formater.format(computer.getTimeStart()));

                int hour = computer.getUsedBySecond() / 3600;
                int minute = (computer.getUsedBySecond() % 3600) / 60;
                int second = (computer.getUsedBySecond() % 3600) % 60;
                String timeUsed = hour + "h " + minute + "m " + second + "s";
                row.add(timeUsed);
                hour = computer.getRemainingBySecond() / 3600;
                minute = (computer.getRemainingBySecond() % 3600) / 60;
                second = (computer.getRemainingBySecond() % 3600) % 60;
                String timeRemaining = hour + "h " + minute + "m " + second + "s";
                row.add(timeRemaining);
                row.add(NumberFormat.getNumberInstance().format((int) ((computer.getUsedBySecond() / 3600.0) * computer.getPrice())));

                formater = new SimpleDateFormat("dd-MM-yyyy");
                row.add(formater.format(computer.getCurrentDate()));
            } else {
                row.add("");
                row.add("");
                row.add("");
                row.add("");
                row.add("");
                row.add("");
            }
            row.add(computer.getComputerGroup().getGroupName());
            row.add(computer.getNote());
            data.add(row);
        }

        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        if (table != null) {
            table.setModel(tableModel);
            table.setDefaultRenderer(Object.class, new BoardTableCellRenderer());
            if (tableSelectedRow > -1) {
                table.setRowSelectionInterval(tableSelectedRow, tableSelectedRow);
            }
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
}

class BoardTableCellRenderer extends DefaultTableCellRenderer {

    private int fontSize = 15;
    private Color colorSelectedText = Color.white;
    private Color colorUnselectedText = Color.BLACK;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        JPanel container = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
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
        if (isSelected) {
            container.setBackground(new Color(49, 156, 255));
        }
        Border border = BorderFactory.createLineBorder(new Color(50, 50, 150, 50), 1, true);
        container.setBorder(border);
        switch (column) {
//            case 0: { // column of name computer
//                JLabel jLabel = new JLabel(String.valueOf(value).toUpperCase());
//                Font newLabelFont=new Font(jLabel.getFont().getName(),Font.BOLD, fontSize);
//                jLabel.setFont(newLabelFont);
//                jLabel.setForeground(isSelected ? colorSelectedText : colorUnselectedText);
//                container.add(jLabel);
//                return container;
//            }
            case 1: { // column of status computer
                JLabel jLabel = new JLabel(String.valueOf(value).toUpperCase());
                Font newLabelFont = new Font(jLabel.getFont().getName(), Font.BOLD, fontSize);
                jLabel.setFont(newLabelFont);

                Computer computer = Data.getComputerByComputerName(table.getValueAt(row, 0).toString());
                if (computer != null) {
                    if (computer.getUserUsing() == null) {
                        jLabel.setText("Đang tắt");
                        jLabel.setForeground(colorSelectedText);
                        container.setBackground(Color.red);
                    } else {
                        jLabel.setText("Đang sử dụng");
                        jLabel.setForeground(isSelected ? colorSelectedText : Color.BLUE);
                        container.setBackground(new Color(231, 247, 255));
                    }
                }
                if (isSelected) {
                    container.setBackground(new Color(49, 156, 255));
                }
                container.add(jLabel);
                return container;
            }
            case 2: { // column of user name
                JLabel jLabel = new JLabel(String.valueOf(value).toUpperCase());
                Font newLabelFont = new Font(jLabel.getFont().getName(), Font.BOLD, fontSize);
                jLabel.setFont(newLabelFont);
                jLabel.setForeground(isSelected ? colorSelectedText : colorUnselectedText);

                Computer computer = Data.getComputerByComputerName(table.getValueAt(row, 0).toString());
                if (computer != null) {
                    if (computer.getUserUsing() != null && computer.getUserUsing().getUserGroupName().equals("Admin")) {
                        container.setBackground(Color.yellow);
                    }
                }

                container.add(jLabel);
                return container;
            }
//            case 3: { // time start
//                if(String.valueOf(value).isEmpty()){
//                    break;
//                }
//                JLabel jLabel = new JLabel(String.valueOf(value).toUpperCase());
//                Font newLabelFont=new Font(jLabel.getFont().getName(),Font.BOLD, fontSize);
//                jLabel.setFont(newLabelFont);
//                jLabel.setForeground(isSelected ? colorSelectedText : colorUnselectedText);
//                container.add(jLabel);
//                return container;
//            }
            case 4: { // used
                JLabel jLabel = new JLabel(String.valueOf(value).toUpperCase());
                Font newLabelFont = new Font(jLabel.getFont().getName(), Font.BOLD, fontSize);
                jLabel.setFont(newLabelFont);

                Computer computer = Data.getComputerByComputerName(table.getValueAt(row, 0).toString());
                if (computer != null) {
                    if (computer.getUserUsing() != null) {
                        jLabel.setText(String.valueOf(value));
                        jLabel.setForeground(isSelected ? colorSelectedText : Color.red);
                        container.setBackground(new Color(232, 232, 232));
                    }
                }
                if (isSelected) {
                    container.setBackground(new Color(49, 156, 255));
                }
                container.add(jLabel);
                return container;
            }
            case 5: { // remaining
                JLabel jLabel = new JLabel(String.valueOf(value).toUpperCase());
                Font newLabelFont = new Font(jLabel.getFont().getName(), Font.BOLD, fontSize);
                jLabel.setFont(newLabelFont);

                Computer computer = Data.getComputerByComputerName(table.getValueAt(row, 0).toString());
                if (computer != null) {
                    if (computer.getUserUsing() != null) {
                        jLabel.setText(String.valueOf(value));
                        jLabel.setForeground(isSelected ? colorSelectedText : Color.blue);
                        container.setBackground(new Color(231, 247, 255));
                    }
                }
                if (isSelected) {
                    container.setBackground(new Color(49, 156, 255));
                }
                container.add(jLabel);
                return container;
            }
            default: {
                JLabel jLabel = new JLabel(String.valueOf(value).toUpperCase());
                Font newLabelFont = new Font(jLabel.getFont().getName(), Font.BOLD, fontSize);
                jLabel.setFont(newLabelFont);
                jLabel.setForeground(isSelected ? colorSelectedText : colorUnselectedText);
                container.add(jLabel);
                return container;
            }
        }
    }
}
