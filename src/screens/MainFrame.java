package screens;

import data.Data;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import views.component.Footer;
import views.tabs.ParentFrame;

public class MainFrame extends JFrame {

    public static final int SCREEN_WIDTH = (int) (1920 * 0.6);
    public static final int SCREEN_HEIGHT = (int) (1080 * 0.6);

    private ParentFrame parentFrame = new ParentFrame();
    private Footer footer = new Footer();

    public MainFrame() throws IOException {
        Toolkit toolkit = this.getToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width / 2 - SCREEN_WIDTH / 2, dimension.height / 2 - SCREEN_HEIGHT / 2, SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Border border = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        
//        URL url = new URL("https://i-imgur-com.cdn.ampproject.org/i/s/s2.fastlycdn.xyz/2022/11/934457155OGE.gif");
//        Icon icon = new ImageIcon(url);
//        JLabel label = new JLabel(icon);
//        JPanel jpn = new JPanel();
//        jpn.setBorder(border);
//        jpn.add(label);
//        this.add(jpn, BorderLayout.NORTH);
//        this.add(footer, BorderLayout.SOUTH);


        JPanel container = new JPanel(new BorderLayout());
        container.setBorder(border);
        container.add(parentFrame, BorderLayout.CENTER);
        this.add(container, BorderLayout.CENTER);

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
