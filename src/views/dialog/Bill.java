/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 *
 * @author ADMIN
 */
public class Bill extends JDialog{
   
    private int WIDTH = 350, HEIGHT = 430;
    private JPanel container;
    private int fontSize = 12;
    
    public Bill(){
        Toolkit toolkit = this.getToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width / 2 - WIDTH / 2, dimension.height / 2 - HEIGHT / 2, WIDTH, HEIGHT);
        this.setModal(true);
        this.setTitle("Kết thúc giao dịch");
        //this.setResizable(false);
        this.setLayout(new BorderLayout());
        container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        this.add(container, BorderLayout.CENTER);
        
        JPanel userNamePanel = new JPanel(new GridLayout(1,2,0,0));
        userNamePanel.add(renderJLabelWithFontSize("Tên người sử dụng: ", fontSize));
        userNamePanel.add(renderDisableInputWithFontSize("MAY10", fontSize));
        
        JPanel currentTransaction = new JPanel();
        Border border = BorderFactory.createTitledBorder("Giao dịch hiện tại");
        currentTransaction.setBorder(border);
        currentTransaction.setLayout(new GridLayout(4,2,5,5));
        currentTransaction.add(renderJLabelWithFontSize("Thời gian đã sử dụng: ", fontSize));
        currentTransaction.add(renderDisableInputWithFontSize("1 Giờ 0 Phút", fontSize));
        currentTransaction.add(renderJLabelWithFontSize("Phí thời gian: ", fontSize));
        currentTransaction.add(renderDisableInputWithFontSize("11,100", fontSize));
        currentTransaction.add(renderJLabelWithFontSize("Dịch vụ: ", fontSize));
        currentTransaction.add(renderDisableInputWithFontSize("18,000", fontSize));
        currentTransaction.add(renderJLabelWithFontSize("Làm tròn: ", fontSize));
        currentTransaction.add(renderDisableInputWithFontSize("29,000", fontSize));
        
        
        JPanel transferredTransaction = new JPanel();
        Border border1 = BorderFactory.createTitledBorder("Đã chuyển");
        transferredTransaction.setBorder(border1);
        transferredTransaction.setLayout(new GridLayout(2,2,5,5));
        transferredTransaction.add(renderJLabelWithFontSize("Phí thời gian: ", fontSize));
        transferredTransaction.add(renderDisableInputWithFontSize("11,100", fontSize));
        transferredTransaction.add(renderJLabelWithFontSize("Dịch vụ: ", fontSize));
        transferredTransaction.add(renderDisableInputWithFontSize("18,000", fontSize));
        
        JPanel totalCharge = new JPanel();
        Border border2 = BorderFactory.createTitledBorder("Tổng cộng");
        totalCharge.setBorder(border2);
        totalCharge.setLayout(new GridLayout(2,2,5,5));
        totalCharge.add(renderJLabelWithFontSize("Tiền: ", fontSize));
        totalCharge.add(renderDisableInputWithFontSize("11,100", fontSize));
        totalCharge.add(renderJLabelWithFontSize("Thu thập: ", fontSize));
        totalCharge.add(renderDisableInputWithFontSize("18,000", fontSize));
        
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel totalLabel = new JLabel("29,000");
        totalLabel.setFont(new Font(totalLabel.getFont().getName(), Font.BOLD, fontSize * 2));
        totalPanel.add(totalLabel);
        
        JPanel controllerButtons = new JPanel(new GridLayout(1,3,5,0));
        JButton confirmButton = new JButton("Chấp nhận");
        confirmButton.setBackground(new Color(17,148,189));
        confirmButton.setForeground(Color.white);
        confirmButton.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                
            }
        });
        controllerButtons.add(confirmButton);
        JButton tranferButton = new JButton("Chuyển tiền");
        tranferButton.setBackground(new Color(17,148,189));
        tranferButton.setForeground(Color.white);
        tranferButton.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                
            }
        });
        controllerButtons.add(tranferButton);
        JButton cancelButton = new JButton("Hủy bỏ");
        cancelButton.setBackground(new Color(76,76,76));
        cancelButton.setForeground(Color.white);
        cancelButton.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                Bill.this.setVisible(false);
            }
        });
        controllerButtons.add(cancelButton);
        
        container.add(userNamePanel);
        container.add(userNamePanel);
        container.add(currentTransaction);
        container.add(transferredTransaction);
        container.add(totalCharge);
        container.add(totalPanel);
        container.add(controllerButtons);
    }
    
    private JLabel renderJLabelWithFontSize(String title, int fontSize){
        JLabel label2 = new JLabel(title);
        label2.setFont(label2.getFont().deriveFont(fontSize));
        return label2;
    }
    
    private JTextField renderDisableInputWithFontSize(String title, int fontSize){
        JTextField edt3 = new JTextField(title);
        edt3.setFont(edt3.getFont().deriveFont(fontSize));
        edt3.setEditable(false);
        return edt3;
    }
}
