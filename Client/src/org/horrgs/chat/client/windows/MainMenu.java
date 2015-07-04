package org.horrgs.chat.client.windows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Horrgs on 6/17/2015.
 */
public class MainMenu extends JFrame {
    private JButton createAccount;
    private JButton login;
    public MainMenu() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        setTitle("Chat - Main Menu");
        setSize(600, 600);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        createAccount = new JButton("Create Account");
        createAccount.addActionListener(new MainMenuListener());
        add(createAccount, gbc);
        login = new JButton("Login");
        login.addActionListener(new MainMenuListener());
        gbc.gridy = 1;
        add(login, gbc);

        setVisible(true);
    }

    private class MainMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ev) {
            if(ev.getSource() == createAccount) {
                setVisible(false);
                new CreateAccount();
            } else if(ev.getSource() == login) {
                setVisible(false);
                new Login().openWindow();
            }
        }
    }
}
