package screens;

import data.Data;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.JFrame;
import views.menu.MenuPanel;
import views.tabs.ParentFrame;

public class MainFrame extends JFrame{
    public static final int SCREEN_WIDTH = (int) (1920 * 0.6);
    public static final int SCREEN_HEIGHT = (int) (1080 * 0.6);
    
    private MenuPanel menuPanel = new MenuPanel();
    private ParentFrame parentFrame = new ParentFrame();
    
    public MainFrame() {
        Toolkit toolkit = this.getToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width / 2 - SCREEN_WIDTH / 2, dimension.height / 2 - SCREEN_HEIGHT / 2, SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       
        
        this.setBackground(Color.white);
        this.add(menuPanel, BorderLayout.NORTH);
        this.add(parentFrame, BorderLayout.CENTER);
        
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                Data.saveData();
            }
        });
    }
    
    public static void main(String[] args) throws IOException {
        
        Data.getData();
        
        MainFrame mainFrame = new MainFrame();
        mainFrame.setTitle("Quản lý quán game");
        mainFrame.setVisible(true);
    }
}   
