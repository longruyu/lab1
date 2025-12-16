import game.GameControl;
import gameui.GamePanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Dto on 9/8/2016.
 */
public class MainApp extends JFrame{
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainApp frame = new MainApp();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public MainApp() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 960, 540);
		GamePanel gamePanel = new GamePanel();
        gamePanel.setBounds(0,0,960,540);
        GamePanel gamePanel = new GamePanel();
        GameControl gameControl = new GameControl(gamePanel);
        //gamePanel.setSize(getSize());
        setContentPane(gamePanel);
        gameControl.start();
    }
}
