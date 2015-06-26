package org.horrgs.chat.server.sockets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.horrgs.chat.server.types.incoming.LoginFormat;
import org.horrgs.chat.server.types.MessageFormat;
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
                if (receivingMessage.startsWith("\"type\":message\",")) {
                    //TODO: check if account is authorized
                    MessageFormat messageFormat = gson.fromJson(receivingMessage, MessageFormat.class);
                    sendToAll(messageFormat.getSender(), messageFormat.getMessage());
                } else if(receivingMessage.startsWith("\"type\":login")) {
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
                            //TODO: write back "incorrect username or password."
                        }
                    } else {
                        //TODO: write back "incorrect username or password."
                    }
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
        Iterator it = clientOutputStreams.iterator();
        while(it.hasNext()) {
            try {
                PrintWriter printWriter = (PrintWriter) it.next();
                //TODO: I believe MessageFormat works for this too.
                printWriter.println("{\"type\":message\",\"sender\":"+ sender +"\",\"message\":\"" + message +"\"}");
                printWriter.flush();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
