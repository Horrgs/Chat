package org.horrgs.chat.server.sockets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.horrgs.chat.server.types.RequestType;
import org.horrgs.chat.server.types.incoming.LoginFormat;
import org.horrgs.chat.server.types.MessageFormat;
import org.horrgs.chat.server.types.outgoing.ErrorFormat;
import org.horrgs.chat.server.usertypes.User;
import org.horrgs.chat.server.usertypes.UserManager;
import sun.plugin2.message.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Horrgs on 5/16/2015.
 */
public class ConnectionHandle implements Runnable {
    private BufferedReader bufferedReader;
    private Socket clientSocket;
    ArrayList clientOutputStreams;

    public ConnectionHandle() {
        super();
    }

    public ConnectionHandle(Socket clientSocket) {
        try {
            this.clientSocket = clientSocket;
            InputStreamReader inputStreamReader = new InputStreamReader(clientSocket.getInputStream()); //This recieves the messsages sent to client.
            bufferedReader = new BufferedReader(inputStreamReader);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        String receivingMessage;
        try {
            while((receivingMessage = bufferedReader.readLine()) != null) {
                Gson gson = new Gson();
                if (receivingMessage.startsWith("{\"type\":\"SEND_MESSAGE")) {
                    MessageFormat messageFormat = gson.fromJson(receivingMessage, MessageFormat.class);
                    User sender = UserManager.getInstance().getUser(messageFormat.getSender());
                    if(sender != null && sender.isAuthoized()) {
                        sendToAll(messageFormat.getSender(), messageFormat.getMessage());
                    }
                } else if(receivingMessage.startsWith("{\"type\":\"LOGIN")) {
                    LoginFormat loginFormat = gson.fromJson(receivingMessage, LoginFormat.class);
                    JsonParser jsonParser = new JsonParser();
                    JsonObject jsonObject = null;
                    try {
                        Object obj = jsonParser.parse(new FileReader("users.json"));
                        jsonObject = (JsonObject) obj;
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    if(jsonObject == null) {
                        return;
                    }
                    if(jsonObject.get(loginFormat.getUsername()).getAsJsonObject() != null) {
                        if (jsonObject.get(loginFormat.getUsername()).getAsJsonObject().get("password").equals(loginFormat.getPassword())) {
                            //TODO: authorize.
                        } else {
                            ErrorFormat errorFormat = new ErrorFormat(RequestType.ERROR, "Incorrect email, username or password.");
                            //TODO: write back "incorrect email, username or password."
                        }
                    } else {
                        ErrorFormat errorFormat = new ErrorFormat(RequestType.ERROR, "Incorrect email, username or password.");
                        //TODO: write back "incorrect email, username or password."
                    }
                } else if(receivingMessage.startsWith("{\"type\":\"CREATE_ACCOUNT")) {
                    //TODO: create account.
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void start() {
        clientOutputStreams = new ArrayList();
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            while(true) {
                Socket client = serverSocket.accept();
                PrintWriter printWriter = new PrintWriter(client.getOutputStream());
                clientOutputStreams.add(printWriter);

                Thread t = new Thread(new ConnectionHandle(client));
                t.start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void sendToAll(String sender, String message) {
        MessageFormat messageFormat = new MessageFormat(RequestType.SEND_MESSAGE, sender, message);
        sendToAll(messageFormat);
    }

    public void sendToAll(User sender, String message) {
        MessageFormat messageFormat = new MessageFormat(RequestType.SEND_MESSAGE, sender.getUsername(), message);
        sendToAll(messageFormat);
    }

    public void sendToAll(MessageFormat messageFormat) {
        Iterator it = clientOutputStreams.iterator();
        while(it.hasNext()) {
            try {
                PrintWriter printWriter = (PrintWriter) it.next();
                printWriter.println("{\"type\":\""+messageFormat.getType().getName() + "\",\"sender\":\""+messageFormat.getSender()+"\",\"message\":\""+messageFormat.getMessage()+"\"}");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
