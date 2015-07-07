package org.horrgs.chat.server.usertypes;

/**
 * Created by Horrgs on 7/6/2015.
 */
public enum Rank {
    USER("USER"),
    MODERATOR("MODERATOR"),
    ADMINISTRATOR("ADMINISTRATOR");

    private String name;

    private Rank(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Rank getByName(String name) {
        for(Rank rank : Rank.values()) {
            if(rank.getName().equals(name)) {
                return rank;
            }
        }
        return null;
    }
}
