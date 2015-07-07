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
public class Login {
    public JFrame jFrame = new JFrame();
    public JTextField hintUsername, hintPassword, email, username;
    public JPasswordField password;
    public JButton login, returnB;
    
    public void openWindow() {
        jFrame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setTitle("Chat - Login");
        jFrame.setSize(400, 400);
        addComp(new JTextField("Email: "), 0, 0, false);
        addComp(email = new JTextField("", 15), 1, 0, true);
        addComp(new JTextField("Username: "), 0, 1, false);
        addComp(username = new JTextField("", 15), 1, 1, true);
        addComp(new JTextField("Password: "), 0, 2, false);
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

    public void addComp(JTextField jTextField, int x, int y, boolean editable) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        jTextField.setEditable(editable);
        if(!editable) {
            jTextField.setBackground(jFrame.getBackground());
            jTextField.setBorder(BorderFactory.createLineBorder(jFrame.getBackground()));
        }
        jFrame.add(jTextField, gbc);
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
