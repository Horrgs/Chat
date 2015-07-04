package org.horrgs.chat.client.windows;

import org.horrgs.chat.client.ClientSocket;
import org.horrgs.chat.client.types.MessageFormat;
import org.horrgs.chat.client.types.RequestType;

import javax.jws.soap.SOAPBinding;
import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

/**
 * Created by Horrgs on 7/1/2015.
 */
public class MessageWindow {
    String format = "<html>Text color: <font color='red'>red</font></html>";
    private JFrame jFrame = new JFrame();
    private JButton sendMessage;
    public JTextArea messageArea = new JTextArea(format), composeMessage;
    public JScrollPane scrollPane;
    public PrintWriter printWriter;
    public User user;

    public void appendText(String text) {
        messageArea.append("\n" + text);
    }

    public void setPrintWriter(PrintWriter printWriter) {
        this.printWriter = printWriter;
    }
    /*
    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    public User getUser() {
        return user;
    }  */

    public void setUser(User user) {
        this.user = user;
    }

    public void openWindow() {
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setLayout(new GridBagLayout());
        jFrame.setSize(865, 557);
        GridBagConstraints gbc = new GridBagConstraints();
        jFrame.setTitle("Chat - Messages");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        messageArea.setEditable(false);
        gbc.insets = new Insets(10, 10, 10, 10);
        scrollPane = new JScrollPane(messageArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(765, 320));

        jFrame.add(scrollPane, gbc);

        gbc.gridy = 1;
        composeMessage = new JTextArea(1, 50);
        composeMessage.setLineWrap(true);
        composeMessage.setWrapStyleWord(true);
        //TODO: scroll for composeMessage for long messages.
        composeMessage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        gbc.insets = new Insets(0, 10, 10, 10);
        jFrame.add(composeMessage, gbc);

        sendMessage = new JButton("Send Message");
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 10, 10, 10);
        sendMessage.addActionListener(new SendMessage());
        jFrame.add(sendMessage, gbc);
        jFrame.setVisible(true);
    }

    private class SendMessage implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ev) {
            if(ev.getSource() == sendMessage) {
                System.out.println(jFrame.getSize());
                MessageFormat messageFormat = new MessageFormat(RequestType.SEND_MESSAGE, user.getUsername(),  composeMessage.getText(), "null");
                System.out.println(messageFormat.getSender());
                printWriter.println(messageFormat.getJsonFormat());
                printWriter.flush();
                ClientSocket clientSocket = new ClientSocket();
                clientSocket.setMostRecentOutgoingMessage(messageFormat.getJsonFormat());
            }
        }
    }
}
