package org.horrgs.chat.server;

import com.google.gson.JsonObject;

import java.io.*;

/**
 * Created by Horrgs on 7/5/2015.
 */
public class FileManager {
    private File usernames,users;
    public void setup() {
        usernames = new File("usernames.txt");
        if(!usernames.exists()) {
            try {
                usernames.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        users = new File("users.json");
        if(!users.exists()) {
            try {
                users.createNewFile();
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(users));
                JsonObject jsonObject = new JsonObject();
                bufferedWriter.write(jsonObject.toString());
                bufferedWriter.flush();
                bufferedWriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
