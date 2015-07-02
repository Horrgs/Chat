package org.horrgs.chat.client.windows;

import org.horrgs.chat.client.ClientSocket;
import org.horrgs.chat.client.types.RequestType;
import org.horrgs.chat.client.types.outgoing.CreateAccountFormat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Horrgs on 5/15/2015.
 */
public class CreateAccount extends JFrame {
    private JTextField hintEmail, hintUsername,hintPassword,hintConfirmPassword;
    private JTextField email, username;
    private JPasswordField jPasswordField;
    private JPasswordField confirmJPasswordField;
    private JButton createAccount, exit;

    public CreateAccount(boolean window) {
        setVisible(window);
    }


    public CreateAccount() {
        setSize(600, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chat - Create Account");
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        hintEmail = new JTextField("Email: ");
        hintEmail.setBackground(getBackground());
        hintEmail.setEditable(false);
        hintEmail.setBorder(BorderFactory.createLineBorder(getBackground()));
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(hintEmail, gbc);

        gbc.gridx = 1;
        email = new JTextField("", 10);
        add(email, gbc);

        hintUsername = new JTextField("Username: ");
        hintUsername.setBackground(getBackground());
        hintUsername.setEditable(false);
        hintUsername.setBorder(BorderFactory.createLineBorder(getBackground()));
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(hintUsername, gbc);


        gbc.gridx = 1;
        username = new JTextField("", 10);
        add(username, gbc);


        gbc.gridx = 0;
        gbc.gridy = 2;
        hintPassword = new JTextField("Password: ");
        hintPassword.setBackground(getBackground());
        hintPassword.setEditable(false);
        hintPassword.setBorder(BorderFactory.createLineBorder(getBackground()));
        add(hintPassword, gbc);

        gbc.gridx = 1;
        jPasswordField = new JPasswordField("", 10);
        add(jPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        hintConfirmPassword = new JTextField("Confirm Password: ");
        hintConfirmPassword.setBackground(getBackground());
        hintConfirmPassword.setEditable(false);
        hintConfirmPassword.setBorder(BorderFactory.createLineBorder(getBackground()));
        add(hintConfirmPassword, gbc);

        gbc.gridx = 1;
        confirmJPasswordField = new JPasswordField("", 10);
        add(confirmJPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;

        createAccount = new JButton("Create Account");
        createAccount.addActionListener(new CreateAccountListener());
        add(createAccount, gbc);

        gbc.gridx = 1;
        exit = new JButton("Return (hover)");
        exit.setToolTipText("Clicking this will not create an account and remove all info in fields.");
        exit.addActionListener(new CreateAccountListener());
        add(exit, gbc);

        setVisible(true);
    }

    public class CreateAccountListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ev) {
            if(ev.getSource() == createAccount) {
                char[] password = jPasswordField.getPassword();
                String stringPass = "";
                for(int x = 0; x < password.length; x++) {
                    stringPass = stringPass + password[x];
                }

                char[] checkPass=  confirmJPasswordField.getPassword();
                String confirmStringPass = "";
                for(int x = 0; x < checkPass.length; x++) {
                    confirmStringPass = confirmStringPass + checkPass[x];
                }
                if(!stringPass.equals(confirmStringPass)) {
                    new Error("The password and the confirm password do not match. ", new Dimension(400, 400));
                    return;
                }
                ClientSocket clientSocket = new ClientSocket();
                clientSocket.connect();
                CreateAccountFormat createAccountFormat = new CreateAccountFormat(RequestType.CREATE_ACCOUNT, email.getText(), username.getText(), stringPass);
                clientSocket.printWriter.println("{\"type\":\""+createAccountFormat.getType().getName() + "\",\"email\":\""+createAccountFormat.getEmail()+"\",\"username\":\""+createAccountFormat.getUsername()+"\",\"password\":\""+createAccountFormat.getPassword() + "\"}");
                clientSocket.printWriter.flush();
                //TODO: check if all fields are "eligible" meaning email is proper format, password is good, etc,.
            } else if(ev.getSource() == exit) {
                setVisible(false);
                new MainMenu();
            }
        }
    }
}
