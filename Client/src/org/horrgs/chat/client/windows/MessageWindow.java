package org.horrgs.chat.client.windows;

import org.horrgs.chat.client.ClientSocket;
import org.horrgs.chat.client.types.MessageFormat;
import org.horrgs.chat.client.types.RequestType;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

/**
 * Created by Horrgs on 7/1/2015.
 */
public class MessageWindow {
    private JFrame jFrame = new JFrame();
    private JButton sendMessage;
    public JTextArea composeMessage;
    public JTextPane messageArea;
    public JScrollPane scrollPane;
    public PrintWriter printWriter;
    public User user;

    public static Color hex2Rgb(String colorStr) {
        return new Color(
                Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
                Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
                Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
    }

    public void appendText(MessageFormat messageFormat) {
        StyledDocument styledDocument = messageArea.getStyledDocument();
        SimpleAttributeSet sender = new SimpleAttributeSet(), rest = new SimpleAttributeSet();
        /*
        Name
         */
        Color color = hex2Rgb(messageFormat.getColor());
        StyleConstants.setForeground(sender, color);


        /*
        Rest
         */
        StyleConstants.setForeground(rest, Color.BLACK);

        try {
            styledDocument.insertString(styledDocument.getLength(), messageFormat.getSender(), sender);
            styledDocument.insertString(styledDocument.getLength(), ": " + messageFormat.getMessage() + "\n", rest);
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }

    }

    public void setPrintWriter(PrintWriter printWriter) {
        this.printWriter = printWriter;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public void openWindow() {
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setLayout(new GridBagLayout());
        jFrame.setSize(865, 557);
        GridBagConstraints gbc = new GridBagConstraints();
        jFrame.setTitle("Chat - Messages");
        gbc.fill = GridBagConstraints.BOTH;


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;

        messageArea = new JTextPane();
        messageArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        messageArea.setEditable(false);
        gbc.insets = new Insets(10, 10, 10, 10);
        scrollPane = new JScrollPane(messageArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setViewportView(messageArea);
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
                MessageFormat messageFormat = new MessageFormat(RequestType.SEND_MESSAGE, user.getUsername(),  composeMessage.getText(), "null");
                printWriter.println(messageFormat.getJsonFormat());
                printWriter.flush();
                ClientSocket clientSocket = new ClientSocket();
                clientSocket.setMostRecentOutgoingMessage(messageFormat.getJsonFormat());
            }
        }
    }
}
