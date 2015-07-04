package org.horrgs.chat.server.usertypes;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.horrgs.chat.server.types.incoming.CreateAccountFormat;

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
    private String email;
    private boolean authoized;
    private String color;

    public UserManager() {
        super();
    }

    public UserManager(String email) {
        JsonObject jsonObject = new JsonObject();
        JsonParser jsonParser = new JsonParser();
        try {
            Object obj = jsonParser.parse(new FileReader("users.json"));
            jsonObject = (JsonObject) obj;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        boolean existsAlready = false;
        if(jsonObject != null && email != null) {
            if(jsonObject.get(email) != null && jsonObject.get(email).getAsJsonObject() != null) {
                existsAlready = true;
            } else {
                System.out.println("T" + email);
                if(jsonObject.get(email) == null) System.out.println("jsonObject.get(email) == null");
                if(jsonObject.get(email).getAsJsonObject() == null) System.out.println("jsonObject.get(email).getAsJsonObject() == null");
            }
        } else {
            if(jsonObject == null) System.out.println("jsonObject == null");
            if(email == null) System.out.println("email == null");
        }
        if(existsAlready) {
            System.out.println("Ran 2");
            setUsername(jsonObject.get(email).getAsJsonObject().get("username").getAsString());
            setPassword(jsonObject.get(email).getAsJsonObject().get("password").getAsString());
            setAuthoized(true);
            setMostRecentMessage(jsonObject.get(email).getAsJsonObject().get("most_recent_message").getAsString());
            Status status = Status.OFFLINE;
            status = status.getById(jsonObject.get(email).getAsJsonObject().get("status").getAsString());
            setStatus(status);
            RankManager.Rank rank = RankManager.Rank.USER;
            rank = rank.getById(jsonObject.get(email).getAsJsonObject().get("rank").getAsString());
            setRank(rank);
            setEmail(jsonObject.get(email).getAsJsonObject().get("email").getAsString());
            if(rank == RankManager.Rank.USER) {
                setColoredName("black");
            } else if(rank == RankManager.Rank.MODERATOR) {
                //TODO: should be a light green
                setColoredName("green");
            } else if(rank == RankManager.Rank.ADMINISTRATOR) {
                setColoredName("red");
            }
        }
        System.out.println("Ran#284y33824y");
        UserManager.getInstance().getUsersOnline().add(this);
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

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getColoredName() {
        return color;
    }

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

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void setColoredName(String color) {
        this.color = color;
    }

    public void writeNewUser(CreateAccountFormat createAccountFormat) {
        //WORKS.
        JsonObject jsonObject = new JsonObject();
        JsonParser jsonParser = new JsonParser();
        try {
            Object obj = jsonParser.parse(new FileReader("users.json"));
            jsonObject = (JsonObject) obj;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            JsonObject email = new JsonObject();
            jsonObject.add(createAccountFormat.getEmail(), email);
            email.addProperty("email", createAccountFormat.getEmail());
            email.addProperty("username", createAccountFormat.getUsername());
            email.addProperty("password", createAccountFormat.getPassword());
            email.addProperty("most_recent_message", "Hello, I am " + createAccountFormat.getUsername() + "!");
            email.addProperty("status", Status.ONLINE.getId());
            email.addProperty("rank", RankManager.Rank.USER.getId());
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("users.json"));
            bufferedWriter.write(jsonObject.toString());
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        setRank(RankManager.Rank.USER);
        setMostRecentMessage("");
        setAuthoized(true);
        setStatus(Status.ONLINE);
        setPassword(createAccountFormat.getPassword());
        setUsername(createAccountFormat.getUsername());
        setEmail(createAccountFormat.getEmail());
        UserManager.getInstance().getUsersOnline().add(this);
        System.out.println("Rakn34oherkberjkeb");
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
