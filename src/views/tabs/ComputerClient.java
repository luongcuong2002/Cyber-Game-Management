package views.tabs;

import data.Data;
import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import models.Computer;
import views.dialog.ComputerClientPopUp;

public class ComputerClient extends JPanel{
    
    private JTable table;
    private AbstractTableModel tableModel;
    
    private int tableSelectedRow = -1;
    
    public ComputerClient(){
        
        setupTable();
        
        this.setLayout(new BorderLayout());
        this.add(table, BorderLayout.CENTER);
    }
    
    private void setupTable(){
       
        refeshTable();
        table = new JTable(tableModel);
        
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
                    }

                    int rowindex = table.getSelectedRow();
                    if (rowindex < 0)
                        return;
                    if (event.getComponent() instanceof JTable ) {
                        ComputerClientPopUp menu = new ComputerClientPopUp(ComputerClient.this, table.getValueAt(rowindex, 0).toString());
                        menu.show(event.getComponent(), event.getX(), event.getY());
                    }
                } 
            }

            @Override
            public void mouseClicked(MouseEvent event) {
                if(event.getButton() != MouseEvent.BUTTON1){
                    return;
                }
                if (event.getClickCount() == 2) {
                    System.out.println("double clicked");
                    Point pnt = event.getPoint();
                    int row = table.rowAtPoint(pnt);
                    tableSelectedRow = row;
                    return;
                }
                if (event.getClickCount() == 1) {
                    System.out.println("clicked");
                    Point pnt = event.getPoint();
                    int row = table.rowAtPoint(pnt);
                    tableSelectedRow = row;
                    return;
                }
            }
        });
    }
    
    public void refeshTable(){
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Tên");
        columnNames.add("Tình trạng");
        columnNames.add("Người sử dụng");
        columnNames.add("Bắt đầu");
        columnNames.add("Sử dụng");
        columnNames.add("Còn lại");
        columnNames.add("Tiền");
        columnNames.add("Ngày");
        columnNames.add("Nhóm");
        
        SimpleDateFormat formater = new SimpleDateFormat("HH:mm:ss");
        
        Vector<Vector<String>> data = new Vector<>();
        for(int i = 0; i < Data.listComputers.size();i++){
            Vector<String> row = new Vector<>();
            Computer computer = Data.listComputers.get(i);
            row.add(computer.getComputerName());
            row.add(computer.getStatus());
            if(computer.getUserUsing() != null){
                row.add(computer.getUserUsing().getUserName());
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
                row.add(String.valueOf((int) (computer.getUsedBySecond()/ 3600.0) * computer.getPrice()));
                row.add(computer.getComputerGroup().getGroupName());
            }else{
                row.add("");
                row.add("");
                row.add("");
                row.add("");
                row.add("");
                row.add("");
            }
            data.add(row);
        }
        
        tableModel = new DefaultTableModel(data, columnNames){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        if(table != null){
            table.setModel(tableModel);
            if(tableSelectedRow > -1) table.setRowSelectionInterval(tableSelectedRow, tableSelectedRow);
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
