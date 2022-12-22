package views.tabs;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.optionalusertools.DateTimeChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;
import com.github.lgooddatepicker.zinternaltools.DateTimeChangeEvent;
import com.github.lgooddatepicker.zinternaltools.TimeChangeEvent;
import com.toedter.calendar.JDateChooser;
import data.Data;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import models.Computer;
import models.PlaceholderTextField;
import models.TransactionHistoryItem;
import models.User;
import screens.MainFrame;
import views.popup.AccountPopup;
import views.popup.ComputerClientPopUp;

public class TransactionHistory extends JPanel implements ActionListener {

    private JTable table;
    private JPanel controller, tableWrapper;
    private JButton btnAdd, btnEdit, btnRemove, btnRefresh, btnSearch;
    private PlaceholderTextField edtInputToSearch;
    private AbstractTableModel tableModel;

    private int tableSelectedRow = -1;
    private int buttonSize = 25;

    private LocalDateTime timeStart, timeEnd;

    public TransactionHistory() {

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
        controller = new JPanel(new FlowLayout(FlowLayout.LEFT));

        Border border = BorderFactory.createLineBorder(new Color(50, 50, 150, 50), 1, true);

        btnRefresh = new JButton();
        btnRefresh.setActionCommand("btnRefresh");
        btnRefresh.addActionListener(this);
        btnRefresh.setPreferredSize(new Dimension(buttonSize, buttonSize));
        btnRefresh.setPreferredSize(new Dimension(buttonSize, buttonSize));
        btnRefresh.setIcon(new FlatSVGIcon("icons/ic_refresh.svg", buttonSize, buttonSize));
        btnRefresh.setBorder(border);
        btnRefresh.setFocusPainted(false);
        btnRefresh.setContentAreaFilled(false);

        edtInputToSearch = new PlaceholderTextField("");
        edtInputToSearch.setPlaceholder("Nhập tên tài khoản để tìm kiếm");
        edtInputToSearch.setPreferredSize(new Dimension(200, buttonSize));

        btnSearch = new JButton();
        btnSearch.setActionCommand("btnSearch");
        btnSearch.addActionListener(this);
        btnSearch.setPreferredSize(new Dimension(buttonSize, buttonSize));
        btnSearch.setPreferredSize(new Dimension(buttonSize, buttonSize));
        btnSearch.setIcon(new FlatSVGIcon("icons/ic_search.svg", buttonSize, buttonSize));
        btnSearch.setBorder(border);
        btnSearch.setFocusPainted(false);
        btnSearch.setContentAreaFilled(false);

        DatePickerSettings dateSettingsForTimeStart = new DatePickerSettings();
        dateSettingsForTimeStart.setFormatForDatesCommonEra("dd-MM-yyyy");
        dateSettingsForTimeStart.setAllowEmptyDates(false);
        dateSettingsForTimeStart.setAllowKeyboardEditing(false);

        TimePickerSettings timeSettingsForTimeStart = new TimePickerSettings();
        timeSettingsForTimeStart.setFormatForDisplayTime("HH:mm");
        timeSettingsForTimeStart.setAllowEmptyTimes(false);
        timeSettingsForTimeStart.setAllowKeyboardEditing(false);

        DatePickerSettings dateSettingsForTimeEnd = new DatePickerSettings();
        dateSettingsForTimeEnd.setFormatForDatesCommonEra("dd-MM-yyyy");
        dateSettingsForTimeEnd.setAllowEmptyDates(false);
        dateSettingsForTimeEnd.setAllowKeyboardEditing(false);

        TimePickerSettings timeSettingsForTimeEnd = new TimePickerSettings();
        timeSettingsForTimeEnd.setFormatForDisplayTime("HH:mm");
        timeSettingsForTimeEnd.setAllowEmptyTimes(false);
        timeSettingsForTimeEnd.setAllowKeyboardEditing(false);

        DateTimePicker dateTimeStartPicker = new DateTimePicker(dateSettingsForTimeStart, timeSettingsForTimeStart);
        dateTimeStartPicker.addDateTimeChangeListener(new DateTimeChangeListener() {
            @Override
            public void dateOrTimeChanged(DateTimeChangeEvent event) {
                timeStart = event.getNewDateTimeStrict();
            }
        });
        timeStart = dateTimeStartPicker.getDateTimeStrict();

        DateTimePicker dateTimeEndPicker = new DateTimePicker(dateSettingsForTimeEnd, timeSettingsForTimeEnd);
        dateTimeEndPicker.addDateTimeChangeListener(new DateTimeChangeListener() {
            @Override
            public void dateOrTimeChanged(DateTimeChangeEvent event) {
                timeEnd = event.getNewDateTimeStrict();
            }
        });
        timeEnd = dateTimeStartPicker.getDateTimeStrict();

        controller.add(new JLabel("Bắt đầu:"));
        controller.add(dateTimeStartPicker);
        controller.add(new JLabel("Kết thúc:"));
        controller.add(dateTimeEndPicker);
        controller.add(btnRefresh);
        controller.add(edtInputToSearch);
        controller.add(btnSearch);
    }

    private void setupTable() {

        refreshTable(Data.listTransactionHistoryItems);
        table = new JTable(tableModel);
        table.setRowHeight(20);
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
                        //AccountPopup menu = new AccountPopup(Account.this, table.getValueAt(rowindex, 0).toString());
                        //menu.show(event.getComponent(), event.getX(), event.getY());
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
        
        JTableHeader jTableHeader = table.getTableHeader();
        jTableHeader.setReorderingAllowed(false);
        jTableHeader.setBackground(new Color(184, 236, 255));
        jTableHeader.setForeground(Color.black);
    }

    public void refreshTable(List<TransactionHistoryItem> list) {
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Tên người sử dụng");
        columnNames.add("Ngày bắt đầu");
        columnNames.add("Giờ bắt đầu");
        columnNames.add("Ngày");
        columnNames.add("Thời gian");
        columnNames.add("Tiền");
        columnNames.add("Thời gian đã sử dụng");
        columnNames.add("Ghi chú");

        SimpleDateFormat formater;

        Vector<Vector<String>> data = new Vector<>();
        for (int i = list.size() - 1; i >= 0; i--) {
            Vector<String> row = new Vector<>();
            TransactionHistoryItem item = list.get(i);
            row.add(item.getUserName());
            if (item.getTimeStart() != null) {
                formater = new SimpleDateFormat("dd-MM-yyyy");
                row.add(formater.format(item.getTimeStart()));
                formater = new SimpleDateFormat("HH:mm:ss");
                row.add(formater.format(item.getTimeStart()));
            } else {
                row.add("");
                row.add("");
            }
            formater = new SimpleDateFormat("dd-MM-yyyy");
            row.add(formater.format(item.getTimeMadeTransaction()));
            formater = new SimpleDateFormat("HH:mm:ss");
            row.add(formater.format(item.getTimeMadeTransaction()));
            row.add(NumberFormat.getNumberInstance().format(item.getAmount()));
            String timeUsed;
            if (item.getTimeUsedByMinute() / 60 == 0) {
                timeUsed = String.valueOf(item.getTimeUsedByMinute()) + "Phút";
            } else {
                timeUsed = item.getTimeUsedByMinute() / 60 + "Giờ " + (item.getTimeUsedByMinute() % 60) + "Phút";
            }
            row.add(timeUsed);
            row.add(item.getDescription());
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
            table.getColumnModel().getColumn(0).setMaxWidth(120);
            table.getColumnModel().getColumn(1).setMaxWidth(100);
            table.getColumnModel().getColumn(2).setMaxWidth(100);
            table.getColumnModel().getColumn(3).setMaxWidth(100);
            table.getColumnModel().getColumn(4).setMaxWidth(100);
            table.getColumnModel().getColumn(5).setMaxWidth(100);
            table.getColumnModel().getColumn(6).setMaxWidth(130);
            table.getColumnModel().getColumn(0).setMinWidth(120);
            table.getColumnModel().getColumn(1).setMinWidth(100);
            table.getColumnModel().getColumn(2).setMinWidth(100);
            table.getColumnModel().getColumn(3).setMinWidth(100);
            table.getColumnModel().getColumn(4).setMinWidth(100);
            table.getColumnModel().getColumn(5).setMinWidth(100);
            table.getColumnModel().getColumn(6).setMinWidth(130);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
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
            case "btnRefresh": {
                refreshTable(Data.listTransactionHistoryItems);
                break;
            }
            case "btnSearch": {
                String text = edtInputToSearch.getText().trim().toUpperCase();
                ArrayList<TransactionHistoryItem> list = new ArrayList<>();
                for (int i = 0; i < Data.listTransactionHistoryItems.size(); i++) {
                    TransactionHistoryItem item = Data.listTransactionHistoryItems.get(i);
                    LocalDateTime timeMadeTrans = LocalDateTime.ofInstant(item.getTimeMadeTransaction().toInstant(), ZoneId.systemDefault());
                    if (item.getUserName().startsWith(text)
                            && timeMadeTrans.isBefore(timeEnd)
                            && timeMadeTrans.isAfter(timeStart)) {
                        list.add(Data.listTransactionHistoryItems.get(i));
                    }
                }
                refreshTable(list);
                break;
            }
        }
    }
}
