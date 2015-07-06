package org.horrgs.chat.client.users;

/**
 * Created by Horrgs on 7/1/2015.
 */
public class User {
    private String email,username,password;
    private RankManager.Rank rank;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RankManager.Rank getRank() {
        return rank;
    }

    public void setRank(RankManager.Rank rank) {
        this.rank = rank;
    }
}
