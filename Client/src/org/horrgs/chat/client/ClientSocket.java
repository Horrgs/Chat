package org.horrgs.chat.client;

import com.google.gson.Gson;
import org.horrgs.chat.client.types.RequestType;
import org.horrgs.chat.client.types.Succession;
import org.horrgs.chat.client.windows.CreateAccount;
import org.horrgs.chat.client.windows.Error;
import org.horrgs.chat.client.types.ErrorFormat;
import org.horrgs.chat.client.types.MessageFormat;
import org.horrgs.chat.client.windows.Login;
import org.horrgs.chat.client.windows.MessageWindow;

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
                    //textAreaofChat.append(messageFormat().getSender(), messageFormat.getMessage() + "\n");
                    //READ Server MessageFormat.
                } else if(incomingMessage.startsWith("{\"type\":\"SUCCESSION")) {
                    Succession succession = gson.fromJson(incomingMessage, Succession.class);
                    System.out.println("1");
                    if(succession.getSuccession() == RequestType.LOGIN) {
                        System.out.println("2");
                        new Login(false);
                        new MessageWindow();
                    } else if(succession.getSuccession() == RequestType.CREATE_ACCOUNT) {
                        System.out.println("3");
                        new CreateAccount(false);
                        new MessageWindow();
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
