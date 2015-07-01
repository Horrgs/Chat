package org.horrgs.chat.client.windows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Horrgs on 6/30/2015.
 */
public class Error extends JFrame {
    private JTextArea messageArea;
    public JButton okay;
    public Error() {
        super();
    }

    public Error(String message, int width, int height) {
        new Error(message, new Dimension(width, height));
    }

    public Error(String message, Dimension dimension) {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Error");
        setSize(dimension);
        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        messageArea = new JTextArea(message);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        add(messageArea, gridBagConstraints);
        okay = new JButton("Okay, I understand.");
        okay.addActionListener(new ErrorListener());
        gridBagConstraints.gridy = 1;
        add(okay, gridBagConstraints);
        setVisible(true);
    }

    private class ErrorListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ev) {
            if(ev.getSource() == okay) {
                setVisible(false);
            }
        }
    }
}
