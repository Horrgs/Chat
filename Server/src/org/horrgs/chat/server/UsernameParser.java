package org.horrgs.chat.server;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Horrgs on 7/1/2015.
 */
public class UsernameParser {
    private ArrayList<String> usernames = new ArrayList<>();
    public void parse() {
        try {
            File f = new File("usernames.txt");
            Scanner scanner = new Scanner(new FileReader(f));
            for(int x = 0; x < countLines(f.getAbsolutePath()); x++) {
                usernames.add(scanner.nextLine());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
     }

    public boolean isUsernameTaken(String username) {
        return usernames.contains(username);
    }

    public void addUsername(String username) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("usernames.txt")));
            bufferedWriter.write(username);
            System.out.println(username + " has been written to the file.");
            usernames.add(username);
            System.out.println(username + " has been added to the ArrayList.");
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Deprecated
    public void removeUsername(String username) {
        File f = new File("usernames.txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(f));
            for(int x = 0; x < countLines(f.getAbsolutePath()); x++) {
                String line = reader.readLine();
                if(username.equals(line)) {
                    File tempFile = new File("tempUsernames.txt");
                    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

                    String lineToRemove = username;
                    String currentLine;

                    while((currentLine = reader.readLine()) != null) {
                        // trim newline when comparing with lineToRemove
                        String trimmedLine = currentLine.trim();
                        if(trimmedLine.equals(lineToRemove)) continue;
                        writer.write(currentLine + System.getProperty("line.separator"));
                    }
                    writer.close();
                    reader.close();
                    boolean successful = tempFile.renameTo(f);
                    if(successful) {
                        System.out.println("Removed from file.");
                        usernames.remove(username);
                        System.out.println("Removed from ArrayList.");

                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        usernames.remove(username);
    }

    public void addUsernames(String[] usernames) {
        File f = new File("usernames.txt");
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(f));
            for(int x = 0; x < usernames.length; x++) {
                bufferedWriter.write(usernames[x]);
                System.out.println(usernames[x] + " has been written to the file.");
                this.usernames.add(usernames[x]);
                System.out.println(usernames[x] + " has been added to the ArrayList.");
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     @author martinus
     */

    public static int countLines(String filename) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(filename));
        try {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            return (count == 0 && !empty) ? 1 : count;
        } finally {
            is.close();
        }
    }
}
