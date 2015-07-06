package org.horrgs.chat.server.windows;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Horrgs on 7/5/2015.
 */
public class Console {
    private static Console instance = new Console();
    public static Console getInstance() { return instance; }
    public JFrame jFrame;
    public JTextArea jTextArea = new JTextArea();

    public void appendConsole(String message) {
        jTextArea.append(message + "\n");
    }

    public void openWindow() {
        jFrame = new JFrame();
        jFrame.setSize(600, 600);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setLayout(new GridBagLayout());
        jFrame.setTitle("Console");
        jTextArea = new JTextArea();
        jTextArea.setPreferredSize(new Dimension(550, 550));
        jTextArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        jTextArea.setEditable(false);
        jFrame.add(jTextArea);
        jFrame.setVisible(true);
    }
}
