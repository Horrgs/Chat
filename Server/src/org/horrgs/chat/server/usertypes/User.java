package org.horrgs.chat.server.usertypes;

/**
 * Created by Horrgs on 5/14/2015.
 */
public interface User {

    public String getUsername();

    public Rank getRank();

    public Status getStatus();

    public String getMostRecentMessage();

    public String getPassword();

    public boolean isAuthoized();

    public String getEmail();

    public String getColoredName();

    public void setUsername(String string);

    public void setRank(Rank rank);

    public void setStatus(Status status);

    public void setPassword(String password);

    public void setMostRecentMessage(String message);

    public void sendMessage(String message);

    public void setAuthoized(boolean authorized);

    public void setEmail(String email);

    public void setColoredName(String color);
}
