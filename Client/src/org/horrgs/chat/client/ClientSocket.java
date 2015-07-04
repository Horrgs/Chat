package org.horrgs.chat.client;

import com.google.gson.Gson;
import org.horrgs.chat.client.types.RequestType;
import org.horrgs.chat.client.types.Succession;
import org.horrgs.chat.client.types.outgoing.CreateAccountFormat;
import org.horrgs.chat.client.types.outgoing.LoginFormat;
import org.horrgs.chat.client.windows.*;
import org.horrgs.chat.client.types.ErrorFormat;
import org.horrgs.chat.client.types.MessageFormat;
import org.horrgs.chat.client.windows.Error;

import javax.jws.soap.SOAPBinding;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;

/**
 * Created by Horrgs on 5/16/2015.
 */
public class ClientSocket implements Runnable {
    private Socket socket;
    public BufferedReader bufferedReader;
    public PrintWriter printWriter;
    private JFrame jFrame;

    public MessageWindow messageWindow;

    public void setMessageWindow(MessageWindow messageWindow) {
        this.messageWindow = messageWindow;
    }

    public MessageWindow getMessageWindow() {
        return messageWindow;
    }

    public String getMostRecentIncomingMessage() {
        return mostRecentIncomingMessage;
    }

    public void setMostRecentIncomingMessage(String mostRecentIncomingMessage) {
        this.mostRecentIncomingMessage = mostRecentIncomingMessage;
    }

    public String getMostRecentOutgoingMessage() {

        return mostRecentOutgoingMessage;
    }

    public void setMostRecentOutgoingMessage(String mostRecentOutgoingMessage) {
        this.mostRecentOutgoingMessage = mostRecentOutgoingMessage;
    }

    private String mostRecentOutgoingMessage, mostRecentIncomingMessage;

    public void giveWindow(JFrame jFrame) {
        this.jFrame = jFrame;
    }

    public PrintWriter getWriterToServer() {
        return printWriter;
    }

    public BufferedReader getReaderFromServer() {
        return bufferedReader;
    }

    public void connect() {
        try {
            socket = new Socket("127.0.0.1", 5000);
            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);
            printWriter = new PrintWriter(socket.getOutputStream());
            System.out.println("Connection established.");
            Thread readerAndRunner = new Thread(this);
            readerAndRunner.start();
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Issues connecting to 127.0.0.1 on port 5000");
        }
    }

    @Override
    public void run() {
        String incomingMessage;
        try {
            while((incomingMessage = bufferedReader.readLine()) != null) {
                System.out.println(incomingMessage);
                Gson gson = new Gson();
                if (incomingMessage.startsWith("{\"type\":\"SEND_MESSAGE")) {
                    MessageFormat messageFormat = gson.fromJson(incomingMessage, MessageFormat.class);
                    //String html = "<html><font color='"+messageFormat.getColor()+"'>"+messageFormat.getSender() + "</font>";
                    //String backToBlack = "<font color='black'>: </font></html>";
                    //String format = html + backToBlack;
                    String format = "<html>Text color: <font color='red'>red</font></html>";
                    String total = format + messageFormat.getMessage() + "\n";
                    getMessageWindow().appendText(total);
                    //TODO:textAreaofChat.append(messageFormat().getSender(), messageFormat.getMessage() + "\n");
                } else if(incomingMessage.startsWith("{\"type\":\"SUCCESSION")) {
                    Succession succession = gson.fromJson(incomingMessage, Succession.class);
                    jFrame.setVisible(false);
                    if(succession.getSuccession() == RequestType.LOGIN) {
                        User user = new User();
                        LoginFormat loginFormat = gson.fromJson(getMostRecentOutgoingMessage(), LoginFormat.class);
                        user.setEmail(loginFormat.getEmail());
                        user.setUsername(loginFormat.getUsername());
                        user.setPassword(loginFormat.getPassword());
                        MessageWindow messageWindow = new MessageWindow();
                        setMessageWindow(messageWindow);
                        messageWindow.openWindow();
                        messageWindow.setPrintWriter(printWriter);
                        messageWindow.setUser(user);
                        System.out.println(loginFormat.getEmail() + "\t" + loginFormat.getUsername() + "\t" + loginFormat.getPassword());
                    } else if(succession.getSuccession() == RequestType.CREATE_ACCOUNT) {
                        MessageWindow messageWindow = new MessageWindow();
                        messageWindow.openWindow();
                        User user = new User();
                        CreateAccountFormat loginFormat = gson.fromJson(getMostRecentOutgoingMessage(), CreateAccountFormat.class);
                        user.setEmail(loginFormat.getEmail());
                        user.setUsername(loginFormat.getUsername());
                        user.setPassword(loginFormat.getPassword());
                        messageWindow.setUser(user);
                        setMessageWindow(messageWindow);
                        System.out.println(loginFormat.getEmail() + "\t" + loginFormat.getUsername() + "\t" + loginFormat.getPassword());
                        messageWindow.setPrintWriter(printWriter);
                    } else {
                        System.out.println(succession.getSuccession());
                        ErrorFormat errorFormat1 = new ErrorFormat(RequestType.ERROR, "You had an invalid succession type: " + succession.getSuccession().getName());
                        getWriterToServer().println(errorFormat1.getJsonFormat());
                    }

                } else if(incomingMessage.startsWith("{\"type\":\"ERROR")) {
                    ErrorFormat errorFormat = gson.fromJson(incomingMessage, ErrorFormat.class);
                    new Error(errorFormat.getMessage(), new Dimension(400, 400));
                } else {
                    ErrorFormat errorFormat = new ErrorFormat(RequestType.ERROR, "A unknown REQUEST_TYPE has been sent to a client. Here is the message: " + incomingMessage);
                    getWriterToServer().println("{\"type\":\""+errorFormat.getRequestType() + "\",\"message\":\""+incomingMessage+"\"}");
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}
