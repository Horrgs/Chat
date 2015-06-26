package org.horrgs.chat.server.usertypes;

/**
 * Created by Horrgs on 5/14/2015.
 */
public class RankManager {

    public enum Rank {
        USER("USER"),
        MODERATOR("MODERATOR"),
        ADMINISTRATOR("ADMINISTRATOR");
        private String id;

        private Rank(String id) { this.id = id; }

        public String getId() { return id; }

        public Rank getById(String id) {
            for(Rank rank : Rank.values()) {
                if(rank.getId().equals(id)) {
                    return rank;
                }
            }
            return null;
        }
    }
}
