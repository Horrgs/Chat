package org.horrgs.chat.server.usertypes;

/**
 * Created by Horrgs on 5/14/2015.
 */
public interface User {

    public String getUsername();

    public RankManager.Rank getRank();

    public Status getStatus();

    public String getMostRecentMessage();

    public String getPassword();

    public boolean isAuthoized();

    public void setUsername(String string);

    public void setRank(RankManager.Rank rank);

    public void setStatus(Status status);

    public void setPassword(String password);

    public void setMostRecentMessage(String message);

    public void sendMessage(String message);

    public void setAuthoized(boolean authorized);
}
