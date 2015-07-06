package org.horrgs.chat.server.types;

import org.horrgs.chat.server.usertypes.RankManager;

/**
 * Created by Horrgs on 7/2/2015.
 */
public class Succession {
    private RequestType requestType, succession;
    private String jsonFormat;
    private RankManager.Rank rank;
    public Succession(RequestType requestType, RequestType succession, RankManager.Rank rank) {
        this.requestType = requestType;
        this.succession = succession;
        this.rank = rank;
        this.jsonFormat = "{\"type\":\"" + requestType.getName() + "\",\"succession\":\""+succession.getName()+"\",\"rank\":\"" + rank.getId() + "\"}";
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public RequestType getSuccession() {
        return succession;
    }

    public RankManager.Rank getRank() { return rank; }

    public String getJsonFormat() { return jsonFormat; }
}
