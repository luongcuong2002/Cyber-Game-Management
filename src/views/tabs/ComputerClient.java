package views.tabs;

import data.Data;
import java.awt.BorderLayout;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import models.Computer;

public class ComputerClient extends JPanel{
    
    private JTable table;
    
    public ComputerClient(){
        
        setupTable();
        
        this.setLayout(new BorderLayout());
        this.add(table, BorderLayout.CENTER);
    }
    
    private void setupTable(){
       
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
                String timeUsed = computer.getUsedByMinute() / 60 + "h " + computer.getUsedByMinute() % 60 + "m";
                row.add(timeUsed);
                String timeRemaining = computer.getRemainingByMinute()/ 60 + "h " + computer.getRemainingByMinute() % 60 + "m";
                row.add(timeRemaining);
                row.add(String.valueOf((int) (computer.getUsedByMinute() / 60.0) * computer.getPrice()));
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
        
        table = new JTable(data, columnNames);
    }
}
