package org.horrgs.chat.client;

import com.google.gson.Gson;
import org.horrgs.chat.client.windows.Error;
import org.horrgs.chat.client.types.ErrorFormat;
import org.horrgs.chat.client.types.MessageFormat;

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

    public void connect() {
        //TODO: need to do a forever loop listening for incoming msgs.
        try {
            socket = new Socket("127.0.0.1", 5000);
            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);
            printWriter = new PrintWriter(socket.getOutputStream());
            System.out.println("Connection established.");
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
                Gson gson = new Gson();
                if (incomingMessage.startsWith("{\"type\":\"SEND_MESSAGE")) {
                    MessageFormat messageFormat = gson.fromJson(incomingMessage, MessageFormat.class);
                    //textAreaofChat.append(messageFormat().getSender(), messageFormat.getMessage() + "\n");
                    //READ Server MessageFormat.
                } else if(incomingMessage.startsWith("{\"type\":\"ERROR")) {
                    ErrorFormat errorFormat = gson.fromJson(incomingMessage, ErrorFormat.class);
                    new Error(errorFormat.getMessage(), new Dimension(400, 400));
                    //TODO: open error window / prompt (I think error will happen enough to have it's own class.)
                } else {
                    //TODO: send an error format to server for an unknown message type.
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}
