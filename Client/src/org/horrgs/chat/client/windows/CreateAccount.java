package org.horrgs.chat.client.windows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Horrgs on 5/15/2015.
 */
public class CreateAccount extends JFrame {
    //TODO: email.
    private JTextField hintUsername,hintPassword,hintConfirmPassword;
    private JTextField username;
    private JPasswordField jPasswordField;
    private JPasswordField confirmJPasswordField;
    //TODO: this will need to check if there is an account already with that name.
    private JButton connect;
    public CreateAccount() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chat - Create Account");
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        hintUsername = new JTextField("Username: ");
        hintUsername.setBackground(getBackground());
        hintUsername.setEditable(false);
        hintUsername.setBorder(BorderFactory.createLineBorder(getBackground()));
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(hintUsername, gbc);


        gbc.gridx = 1;
        username = new JTextField("", 10);
        add(username, gbc);


        gbc.gridx = 0;
        gbc.gridy = 1;
        hintPassword = new JTextField("Password: ");
        hintPassword.setBackground(getBackground());
        hintPassword.setEditable(false);
        hintPassword.setBorder(BorderFactory.createLineBorder(getBackground()));
        add(hintPassword, gbc);

        gbc.gridx = 1;
        jPasswordField = new JPasswordField("", 15);
        add(jPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        hintConfirmPassword = new JTextField("Confirm Password: ");
        hintConfirmPassword.setBackground(getBackground());
        hintConfirmPassword.setEditable(false);
        hintConfirmPassword.setBorder(BorderFactory.createLineBorder(getBackground()));
        add(hintConfirmPassword, gbc);

        gbc.gridx = 1;
        confirmJPasswordField = new JPasswordField("", 15);
        add(confirmJPasswordField, gbc);
    }

}
