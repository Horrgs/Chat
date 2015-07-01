package org.horrgs.chat.client.windows;

import com.sun.javafx.scene.layout.region.BorderImage;
import org.horrgs.chat.client.ClientSocket;
import org.horrgs.chat.client.types.RequestType;
import org.horrgs.chat.client.types.outgoing.LoginFormat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Horrgs on 7/1/2015.
 */
public class Login extends JFrame {
    public JTextField hintEmail, hintUsername, hintPassword, email, username;
    public JPasswordField password;
    public JButton login, returnB;
    
    public Login() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chat - Login");
        //TODO: for future, make a method for hints that set all the info for it like not editable, etc,. Did this before in some project, look back.
        //https://github.com/Horrgs/Agenda/blob/master/src/org/horrgs/agenda/windows/SignUp.java#L50-L65
        hintEmail = new JTextField("Email: ");
        setSize(400, 400);
        hintEmail.setEditable(false);
        hintEmail.setBackground(getBackground());
        hintEmail.setBorder(BorderFactory.createLineBorder(getBackground()));
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(hintEmail, gbc);
        
        email = new JTextField("", 15);
        gbc.gridx = 1;
        add(email, gbc);
        
        hintUsername = new JTextField("Username: ");
        hintUsername.setEditable(false);
        hintUsername.setBackground(getBackground());
        hintUsername.setBorder(BorderFactory.createLineBorder(getBackground()));
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(hintUsername, gbc);
        
        username = new JTextField("", 15);
        gbc.gridx = 1;
        add(username, gbc);

        hintPassword = new JTextField("Password: ");
        hintPassword.setEditable(false);
        hintPassword.setBackground(getBackground());
        hintPassword.setBorder(BorderFactory.createLineBorder(getBackground()));
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(hintPassword, gbc);

        password = new JPasswordField("", 15);
        gbc.gridx = 1;
        add(password, gbc);

        login = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(10, 0, 0, 0);
        add(login, gbc);

        returnB = new JButton("Return");
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 0, 0, 0);
        add(returnB, gbc);
    }

    private class LoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ev) {
            if(ev.getSource() == login) {
                char[] charPass = password.getPassword();
                String pass = "";
                for(int x = 0; x < charPass.length; x++) {
                    pass = pass + charPass[x];
                }
                LoginFormat loginFormat = new LoginFormat(RequestType.LOGIN, email.getText(), username.getText(), pass);
                new ClientSocket().getWriterToServer().println("{\"type\":\""+loginFormat.getType().getName() + "\",\"email\":\""+loginFormat.getEmail()+"\",\"username\":\""+loginFormat.getUsername()+"\",\"password\":\""+loginFormat.getPassword() + "\"}");
            }
        }
    }
}
