package org.horrgs.chat.server.usertypes;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Horrgs on 5/15/2015.
 */
public class UserManager implements User {
    private static UserManager instance = new UserManager();
    public static UserManager getInstance() { return instance; }
    private String username;
    private RankManager.Rank rank;
    private Status status;
    private String mostRecentMessage;
    private String password;
    private boolean authoized;

    public UserManager() {
        super();
    }

    public UserManager(String username) {
        //TODO: works. Although authorization will always return false when it's called so we must handle it without storing it in a file.
        //Account is authorized at the current line #46
        JsonObject jsonObject = new JsonObject();
        JsonParser jsonParser = new JsonParser();
        try {
            Object obj = jsonParser.parse(new FileReader("users.json"));
            jsonObject = (JsonObject) obj;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        boolean existsAlready = false;
        if(jsonObject.has(username)) {
            existsAlready = true;
        }
        if(existsAlready) {
            setUsername(jsonObject.get(username).getAsJsonObject().get("username").getAsString());
            setPassword(jsonObject.get(username).getAsJsonObject().get("password").getAsString());
            setAuthoized(true);
            setMostRecentMessage(jsonObject.get(username).getAsJsonObject().get("most_recent_message").getAsString());
            Status status = Status.OFFLINE;
            status = status.getById(jsonObject.get(username).getAsJsonObject().get("status").getAsString());
            setStatus(status);
            RankManager.Rank rank = RankManager.Rank.USER;
            rank = rank.getById(jsonObject.get(username).getAsJsonObject().get("rank").getAsString());
            setRank(rank);
        }
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public RankManager.Rank getRank() {
        return rank;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public String getMostRecentMessage() {
        return mostRecentMessage;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAuthoized() {
        return authoized;
    }

    //TODO: all of the sets need to write to the json file

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void setRank(RankManager.Rank rank) {
        this.rank = rank;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public void setMostRecentMessage(String mostRecentMessage) {
        this.mostRecentMessage = mostRecentMessage;
    }

    @Override
    public void sendMessage(String message) {
        this.mostRecentMessage = message;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public void setAuthoized(boolean authoized) {
        this.authoized = authoized;
    }
    
    public void writeNewUser(String username, String password, Status status) {
        //WORKS.
        JsonObject jsonObject = new JsonObject();
        JsonParser jsonParser = new JsonParser();
        try {
            Object obj = jsonParser.parse(new FileReader("users.json"));
            jsonObject = (JsonObject) obj;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        JsonObject name = new JsonObject();
        boolean existsAlready = false;
        if(jsonObject.has(username)) {
            existsAlready = true;
        }
        if(!existsAlready) {
            name.addProperty("username", username);
            name.addProperty("password", password);
            name.addProperty("status", status.getId());
            name.addProperty("most_recent_message", "Hello, my name is " + username + "!");
            name.addProperty("rank", RankManager.Rank.USER.getId());
            setUsername(username);
            setPassword(password);
            setStatus(status);
            setMostRecentMessage(name.get("most_recent_message").getAsString());
            RankManager.Rank rank = RankManager.Rank.USER;
            setRank(rank.getById(name.get("rank").getAsString()));
        } else {
            return;
        }

        jsonObject.add(username, name);
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("users.json"), false));
            bufferedWriter.write(jsonObject.toString());
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        setAuthoized(true);
    }

    public List<User> usersOnline = new ArrayList<>();

    public List<User> getUsersOnline() {
        return usersOnline;
    }

    public User getUser(String username) {
        //WORKS
        for(User user : getUsersOnline()) {
            if(user.getUsername() != null) {
                if (user.getUsername().equals(username)) {
                    return user;
                }
            } else {
                System.out.println("getUsername() == null.");
            }
        }
        return null;
    }
}
