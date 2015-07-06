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
    public JFrame jFrame = new JFrame();
    public JTextField hintEmail, hintUsername, hintPassword, email, username;
    public JPasswordField password;
    public JButton login, returnB;
    
    public void openWindow() {
        jFrame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setTitle("Chat - Login");
        //TODO: for future, make a method for hints that set all the info for it like not editable, etc,. Did this before in some project, look back.
        //https://github.com/Horrgs/Agenda/blob/master/src/org/horrgs/agenda/windows/SignUp.java#L50-L65
        hintEmail = new JTextField("Email: ");
        jFrame.setSize(400, 400);
        hintEmail.setEditable(false);
        hintEmail.setBackground(jFrame.getBackground());
        hintEmail.setBorder(BorderFactory.createLineBorder(jFrame.getBackground()));
        gbc.gridx = 0;
        gbc.gridy = 0;
        jFrame.add(hintEmail, gbc);

        email = new JTextField("", 15);
        gbc.gridx = 1;
        jFrame.add(email, gbc);

        hintUsername = new JTextField("Username: ");
        hintUsername.setEditable(false);
        hintUsername.setBackground(jFrame.getBackground());
        hintUsername.setBorder(BorderFactory.createLineBorder(jFrame.getBackground()));
        gbc.gridx = 0;
        gbc.gridy = 1;
        jFrame.add(hintUsername, gbc);

        username = new JTextField("", 15);
        gbc.gridx = 1;
        jFrame.add(username, gbc);

        hintPassword = new JTextField("Password: ");
        hintPassword.setEditable(false);
        hintPassword.setBackground(jFrame.getBackground());
        hintPassword.setBorder(BorderFactory.createLineBorder(jFrame.getBackground()));
        gbc.gridx = 0;
        gbc.gridy = 2;
        jFrame.add(hintPassword, gbc);

        password = new JPasswordField("", 15);
        gbc.gridx = 1;
        jFrame.add(password, gbc);

        login = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(10, 0, 0, 0);
        login.addActionListener(new LoginListener());
        jFrame.add(login, gbc);

        returnB = new JButton("Return");
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 0, 0, 0);
        returnB.addActionListener(new LoginListener());
        jFrame.add(returnB, gbc);
        jFrame.setVisible(true);
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
                ClientSocket clientSocket = new ClientSocket();
                clientSocket.connect();
                LoginFormat loginFormat = new LoginFormat(RequestType.LOGIN, email.getText(), username.getText(), pass);
                clientSocket.printWriter.println(loginFormat.getJsonFormat());
                clientSocket.printWriter.flush();
                clientSocket.setMostRecentOutgoingMessage(loginFormat.getJsonFormat());
                clientSocket.giveWindow(jFrame);
            } else if(ev.getSource() == returnB) {
                jFrame.setVisible(false);
                new MainMenu();
            }
        }
    }
}
