
package views.tabs;

import data.Data;
import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ParentFrame extends JTabbedPane{
    
    private ComputerClient computerClientTab = new ComputerClient();
    private Account accountTab = new Account();
    private SystemHistory systemHistoryTab = new SystemHistory();
    private TransactionHistory transactionHistoryTab = new TransactionHistory();
    
    public ParentFrame(){
        this.setBackground(Color.white);
        this.addTab("Máy trạm", computerClientTab);
        this.addTab("Tài khoản", accountTab);
        this.addTab("Nhật ký hệ thống", systemHistoryTab);
        this.addTab("Nhật ký giao dịch", transactionHistoryTab);
        
        this.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(ParentFrame.this.getSelectedIndex() == 1){ // account
                    accountTab.refreshTable(Data.listUsers);
                }
            }
        });
    }
}
