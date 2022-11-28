
package views.tabs;

import javax.swing.JTabbedPane;

public class ParentFrame extends JTabbedPane{
    
    private ComputerClient computerClientTab = new ComputerClient();
    private Account accountTab = new Account();
    private SystemHistory systemHistoryTab = new SystemHistory();
    private TransactionHistory transactionHistoryTab = new TransactionHistory();
    
    public ParentFrame(){
        this.addTab("Máy trạm", computerClientTab);
        this.addTab("Tài khoản", accountTab);
        this.addTab("Nhật ký hệ thống", systemHistoryTab);
        this.addTab("Nhật ký giao dịch", transactionHistoryTab);
    }
}
