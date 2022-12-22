
package views.tabs;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import data.Data;
import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ParentFrame extends JTabbedPane{
    
    private ComputerClient computerClientTab = new ComputerClient();
    private Account accountTab = new Account();
    private TransactionHistory transactionHistoryTab = new TransactionHistory();
    private ComputerGroup computerGroupTab = new ComputerGroup();
    private UserGroup userGroupTab = new UserGroup();
    private Service serviceTab = new Service();
    
    public ParentFrame(){
        this.setBackground(Color.white);
        this.addTab("Máy trạm", computerClientTab);
        this.setIconAt(0, new FlatSVGIcon("icons/ic_computer.svg", 25, 25));
        
        this.addTab("Tài khoản", accountTab);
        this.setIconAt(1, new FlatSVGIcon("icons/ic_user.svg", 25, 25));
        
        this.addTab("Nhật ký giao dịch", transactionHistoryTab);
        this.setIconAt(2, new FlatSVGIcon("icons/ic_trans_history.svg", 25, 25));
        
        this.addTab("Nhóm máy", computerGroupTab);
        this.setIconAt(3, new FlatSVGIcon("icons/ic_group_computer.svg", 25, 25));
        
        this.addTab("Nhóm người dùng", userGroupTab);
        this.setIconAt(4, new FlatSVGIcon("icons/ic_group_user.svg", 25, 25));
        
        this.addTab("Dịch vụ", serviceTab);
        this.setIconAt(5, new FlatSVGIcon("icons/ic_service.svg", 25, 25));
        
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
                    transactionHistoryTab.refreshTable(Data.listTransactionHistoryItems);
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
