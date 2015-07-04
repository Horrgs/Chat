package org.horrgs.chat.server.sockets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.horrgs.chat.server.UsernameParser;
import org.horrgs.chat.server.types.RequestType;
import org.horrgs.chat.server.types.Succession;
import org.horrgs.chat.server.types.incoming.CreateAccountFormat;
import org.horrgs.chat.server.types.incoming.LoginFormat;
import org.horrgs.chat.server.types.MessageFormat;
import org.horrgs.chat.server.types.outgoing.ErrorFormat;
import org.horrgs.chat.server.usertypes.User;
import org.horrgs.chat.server.usertypes.UserManager;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;

/**
 * Created by Horrgs on 5/16/2015.
 */
public class ConnectionHandle implements Runnable {
    private BufferedReader bufferedReader;
    private Socket clientSocket;
    private static ConnectionHandle instance = new ConnectionHandle();
    public static ConnectionHandle getInstance() {
        return instance;
    }
    ArrayList<PrintWriter> clientOutputStreams = new ArrayList<>();

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
                System.out.println(receivingMessage);
                Gson gson = new Gson();
                if (receivingMessage.startsWith("{\"type\":\"SEND_MESSAGE")) {
                    MessageFormat messageFormat = gson.fromJson(receivingMessage, MessageFormat.class);
                    User sender = UserManager.getInstance().getUser(messageFormat.getSender());
                    if(sender != null && sender.isAuthoized()) {
                        sendToAll(messageFormat.getSender(), messageFormat.getMessage(), sender.getColoredName());
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

                    if(jsonObject != null) {
                        if(jsonObject.get(loginFormat.getEmail()).getAsJsonObject() != null) {
                            if (jsonObject.get(loginFormat.getEmail()).getAsJsonObject().get("username").getAsString().equals(loginFormat.getUsername()) && jsonObject.get(loginFormat.getEmail()).getAsJsonObject().get("password").getAsString().equals(loginFormat.getPassword())) {
                                new UserManager(loginFormat.getEmail());
                                Succession succession = new Succession(RequestType.SUCCESSION, RequestType.LOGIN);
                                PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream());
                                printWriter.println(succession.getJsonFormat());
                                printWriter.flush();
                            } else {
                                ErrorFormat errorFormat = new ErrorFormat(RequestType.ERROR, "Incorrect email, username or password.");
                                PrintWriter clientStream = new PrintWriter(clientSocket.getOutputStream());
                                clientStream.println("{\"type\":\""+errorFormat.getRequestType().getName() + "\",\"message\":\""+errorFormat.getMessage()+"\"}");
                                clientStream.flush();
                            }
                        } else {
                            ErrorFormat errorFormat = new ErrorFormat(RequestType.ERROR, "Incorrect email, username or password.");
                            PrintWriter clientStream = new PrintWriter(clientSocket.getOutputStream());
                            clientStream.println(errorFormat.getJsonFormat());
                            clientStream.flush();
                        }
                    }
                } else if(receivingMessage.startsWith("{\"type\":\"CREATE_ACCOUNT")) {
                    CreateAccountFormat createAccountFormat = gson.fromJson(receivingMessage, CreateAccountFormat.class);
                    if(new UsernameParser().isUsernameTaken(createAccountFormat.getUsername())) {
                        ErrorFormat errorFormat = new ErrorFormat(RequestType.ERROR, "There is already an account with that username.");
                        PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream());
                        printWriter.println(errorFormat.getJsonFormat());
                        printWriter.flush();
                    }
                    JsonParser jsonParser = new JsonParser();
                    JsonObject jsonObject = null;
                    try {
                        Object obj = jsonParser.parse(new FileReader("users.json"));
                        jsonObject = (JsonObject) obj;
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    if(jsonObject != null) {
                        if(jsonObject.get(createAccountFormat.getEmail()) == null) {
                            UserManager.getInstance().writeNewUser(createAccountFormat);
                            Succession succession = new Succession(RequestType.SUCCESSION, RequestType.CREATE_ACCOUNT);
                            PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream());

                            printWriter.println(succession.getJsonFormat());
                            printWriter.flush();
                        } else {
                            if(clientSocket != null) {
                                if(clientSocket.getOutputStream() != null) {
                                    PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream());
                                    ErrorFormat errorFormat = new ErrorFormat(RequestType.ERROR, "There is already an account with that email.");
                                    printWriter.println(errorFormat.getJsonFormat());
                                    printWriter.flush();
                                    //TODO: I'd assume this would have to "flush" and "close".
                                } else {
                                    System.out.println("clientSocket.getOutputStream == null");
                                }
                            } else {
                                System.out.println("clientSocket == null");
                            }
                        }
                    } else {
                        System.out.println("jsonObject == null");
                    }
                } else if(receivingMessage.startsWith("{\"type\":\"ERROR")) {
                    ErrorFormat errorFormat = gson.fromJson(receivingMessage, ErrorFormat.class);
                    Logger log = Logger.getLogger(ConnectionHandle.class.getName());
                    log.severe("An error has occurred. Message: " + errorFormat.getMessage());
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            while(true) {
                Socket client = serverSocket.accept();
                PrintWriter printWriter = new PrintWriter(client.getOutputStream());
                ConnectionHandle.getInstance().clientOutputStreams.add(printWriter);
                Thread t = new Thread(new ConnectionHandle(client));
                t.start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void sendToAll(String sender, String message, String color) {
        MessageFormat messageFormat = new MessageFormat(RequestType.SEND_MESSAGE, sender, message, UserManager.getInstance().getUser(sender).getColoredName());
        sendToAll(messageFormat);
    }

    public void sendToAll(User sender, String message) {
        MessageFormat messageFormat = new MessageFormat(RequestType.SEND_MESSAGE, sender.getUsername(), message, sender.getColoredName());
        sendToAll(messageFormat);
    }

    public void sendToAll(MessageFormat messageFormat) {
        Iterator it = ConnectionHandle.getInstance().clientOutputStreams.iterator();
        System.out.println(ConnectionHandle.getInstance().clientOutputStreams.size());
        while(it.hasNext()) {
            try {
                PrintWriter printWriter = (PrintWriter) it.next();
                printWriter.println(messageFormat.getJsonFormat());
                printWriter.flush();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
