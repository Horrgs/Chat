package org.horrgs.chat.server.windows;

import org.horrgs.chat.server.sockets.ConnectionHandle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Horrgs on 7/5/2015.
 */
public class Window extends JFrame {
    public JButton start,end;
    public Window() {
        setSize(400, 400);
        setTitle("Server");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        start = new JButton("Start");
        start.addActionListener(new WindowListener());
        add(start, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        end = new JButton("End");
        add(end, gridBagConstraints);

        setVisible(true);
    }

    public class WindowListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ev) {
            ConnectionHandle connectionHandle = new ConnectionHandle();
            if(ev.getSource() == start) {
                connectionHandle.start();
            } else {
                //TODO: end.
            }
        }
    }
}
