package org.horrgs.chat.client.windows;

import org.horrgs.chat.client.ClientSocket;
import org.horrgs.chat.client.types.MessageFormat;
import org.horrgs.chat.client.types.RequestType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Horrgs on 7/1/2015.
 */
public class MessageWindow extends JFrame {
    private JButton sendMessage;
    public JTextArea messageArea, composeMessage;

    public MessageWindow() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        setSize(600, 600);
        GridBagConstraints gbc = new GridBagConstraints();
        setTitle("Chat - Messages");

        gbc.gridx = 0;
        gbc.gridy = 0;
        messageArea = new JTextArea(50, 50);
        gbc.ipady = 430;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(messageArea, gbc);

        gbc.gridy = 1;
        composeMessage = new JTextArea(1, 50);
        gbc.ipady = 100;
        gbc.insets = new Insets(0, 10, 10, 10);
        add(composeMessage, gbc);

        sendMessage = new JButton("Send Message");
        gbc.gridy = 3;
        gbc.ipady = 50;
        gbc.insets = new Insets(0, 10, 10, 10);
        sendMessage.addActionListener(new SendMessage());
        add(sendMessage, gbc);
    }

    private class SendMessage implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ev) {
            if(ev.getSource() == sendMessage) {
                User user = new User();
                MessageFormat messageFormat = new MessageFormat(RequestType.SEND_MESSAGE, user.getUsername(),  composeMessage.getText());
                ClientSocket clientSocket = new ClientSocket();
                clientSocket.getWriterToServer().println(messageFormat.getJsonFormat());
                clientSocket.getWriterToServer().flush();
            }
        }
    }
}
