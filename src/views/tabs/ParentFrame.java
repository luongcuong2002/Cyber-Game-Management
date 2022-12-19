
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
    private ComputerGroup computerGroupTab = new ComputerGroup();
    private UserGroup userGroupTab = new UserGroup();
    private Service serviceTab = new Service();
    
    public ParentFrame(){
        this.setBackground(Color.white);
        this.addTab("Máy trạm", computerClientTab);
        this.addTab("Tài khoản", accountTab);
        this.addTab("Nhật ký giao dịch", transactionHistoryTab);
        this.addTab("Nhóm máy", computerGroupTab);
        this.addTab("Nhóm người dùng", userGroupTab);
        this.addTab("Dịch vụ", serviceTab);
        
        this.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(ParentFrame.this.getSelectedIndex() == 0){
                    computerClientTab.refreshTable();
                    return;
                }
                if(ParentFrame.this.getSelectedIndex() == 1){ // account
                    accountTab.refreshTable(Data.listUsers);
                    return;
                }
                if(ParentFrame.this.getSelectedIndex() == 2){
                    transactionHistoryTab.refreshTable();
                    return;
                }
                if(ParentFrame.this.getSelectedIndex() == 4){
                    userGroupTab.refreshTable();
                    return;
                }
            }
        });
    }
}
