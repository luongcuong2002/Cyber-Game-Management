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
        
        initButton(btnFunction, "Chức năng", "bntFunction");
        initButton(btnSystem, "Hệ thống", "btnSystem");
        initButton(btnReport, "Báo cáo", "btnReport");
        initButton(btnLanguage, "Ngôn ngữ", "btnLanguage");
        initButton(btnHelper, "Trợ giúp", "btnHelper");
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
    
    private void initButton(JMenuItem jButton, String textString, String actionCommand){
        jButton = new JMenuItem(textString);
        jButton.setActionCommand(actionCommand);
        jButton.addActionListener(this);
        this.add(jButton);
    }
    
    private void setColorForButton(JMenuItem button, Color backgroundColor, Color foregroundColor){
        button.setBackground(backgroundColor);
        button.setForeground(foregroundColor);
    }
}
