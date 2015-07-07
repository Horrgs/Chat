package org.horrgs.chat.client.windows;

import org.horrgs.chat.client.ClientSocket;
import org.horrgs.chat.client.types.MessageFormat;
import org.horrgs.chat.client.types.RequestType;
import org.horrgs.chat.client.users.Rank;
import org.horrgs.chat.client.users.Rank;
import org.horrgs.chat.client.users.User;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Horrgs on 7/1/2015.
 */
public class MessageWindow /*implements Runnable */ {
    private JFrame jFrame = new JFrame();
    private JButton sendMessage;
    public JTextArea composeMessage;
    public JTextPane messageArea;
    public JScrollPane scrollPane;
    public PrintWriter printWriter;
    public User user;

    public static Color hex2Rgb(String colorStr) {
        return new Color(
                Integer.valueOf(colorStr.substring(1, 3), 16),
                Integer.valueOf(colorStr.substring(3, 5), 16),
                Integer.valueOf(colorStr.substring(5, 7), 16));
    }

    public void appendText(MessageFormat messageFormat) {
        StyledDocument styledDocument = messageArea.getStyledDocument();
        SimpleAttributeSet brackets = new SimpleAttributeSet(), rank = new SimpleAttributeSet(), sender = new SimpleAttributeSet(), rest = new SimpleAttributeSet();
        /*
        Name
         */
        Color color = hex2Rgb(messageFormat.getColor());

        StyleConstants.setForeground(brackets, color.darker().darker());

        StyleConstants.setForeground(rank, color.darker());
        StyleConstants.setBold(rank, true);


        StyleConstants.setForeground(sender, color);
        StyleConstants.setBold(sender, false);


        /*
        Rest
         */
        StyleConstants.setForeground(rest, Color.BLACK);

        try {
            styledDocument.insertString(styledDocument.getLength(), "[", brackets);
            styledDocument.insertString(styledDocument.getLength(), messageFormat.getRank().getName(), rank);
            styledDocument.insertString(styledDocument.getLength(), "] ", brackets);
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
    /*
    int seconds = 5;
    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getSeconds() {
        return seconds;
    }

    @Override
    public void run() {
        if(getSeconds() <= 0) {
            setSeconds(getSeconds() - 1);
        }
    }  */

    private class SendMessage implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ev) {
            if (ev.getSource() == sendMessage) {
                System.out.println(user.getRank());
                /*if (getSeconds() != 0 && user.getRank() != Rank.ADMINISTRATOR) {   */
                if (composeMessage.getText().length() >= 10) {
                    MessageFormat messageFormat = new MessageFormat(RequestType.SEND_MESSAGE, Rank.USER, user.getUsername(), composeMessage.getText(), "null");
                    printWriter.println(messageFormat.getJsonFormat());
                    composeMessage.setText("");
                    printWriter.flush();
                    ClientSocket clientSocket = new ClientSocket();
                    clientSocket.setMostRecentOutgoingMessage(messageFormat.getJsonFormat());
                    //setSeconds(5);
                    //ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
                    //scheduledExecutorService.scheduleAtFixedRate(new MessageWindow(), 0, 1, TimeUnit.SECONDS);
                } else {
                    new Error("Message is too short, must be a total of 10 characters. You have " + composeMessage.getText().length() + ".", new Dimension(400, 400));
                }
            } else {
                //new Error("Your chat cooldown has not ended yet, you still have " + getSeconds() + " second(s) left.", new Dimension(400, 400));
            }
        }
    }
}
