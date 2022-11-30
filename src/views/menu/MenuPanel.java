package views.menu;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class MenuPanel extends JPanel implements ActionListener{
    
    private JMenuItem btnFunction, btnSystem, btnReport, btnLanguage, btnHelper;
    
    public MenuPanel(){
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        btnFunction = new JMenuItem("Chức năng");
        initButton(btnFunction, "btnFunction");
        
        btnSystem = new JMenuItem("Hệ thống");
        initButton(btnSystem,"btnSystem");
        
        btnReport = new JMenuItem("Báo cáo");
        initButton(btnReport, "btnReport");
        
        btnLanguage = new JMenuItem("Ngôn ngữ");
        initButton(btnLanguage, "btnLanguage");
        
        btnHelper = new JMenuItem("Trợ giúp");
        initButton(btnHelper, "btnHelper");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        resetButtonColor();
        switch(e.getActionCommand()){
            case "btnFunction": {
                setColorForButton(btnFunction, Color.BLUE, Color.white);
                break;
            }
            case "btnSystem": {
                setColorForButton(btnSystem, Color.BLUE, Color.white);
                break;
            }
            case "btnReport": {
                setColorForButton(btnReport, Color.BLUE, Color.white);
                break;
            }
            case "btnLanguage": {
                setColorForButton(btnLanguage, Color.BLUE, Color.white);
                break;
            }
            case "btnHelper": {
                setColorForButton(btnHelper, Color.BLUE, Color.white);
                break;
            }
        }
    }
    
    private void resetButtonColor(){
        setColorForButton(btnFunction, Color.getColor("#00ffffff"), Color.black);
        setColorForButton(btnSystem, Color.getColor("#00ffffff"), Color.black);
        setColorForButton(btnReport, Color.getColor("#00ffffff"), Color.black);
        setColorForButton(btnLanguage, Color.getColor("#00ffffff"), Color.black);
        setColorForButton(btnHelper, Color.getColor("#00ffffff"), Color.black);
    }
    
    private void initButton(JMenuItem menuItem, String actionCommand){
        menuItem.setActionCommand(actionCommand);
        menuItem.addActionListener(this);
        this.add(menuItem);
    }
    
    private void setColorForButton(JMenuItem menuItem, Color backgroundColor, Color foregroundColor){
        menuItem.setBackground(backgroundColor);
        menuItem.setForeground(foregroundColor);
    }
}
