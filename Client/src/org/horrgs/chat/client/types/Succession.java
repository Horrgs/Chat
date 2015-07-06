package org.horrgs.chat.client.types;

import org.horrgs.chat.client.users.RankManager;

/**
 * Created by Horrgs on 7/2/2015.
 */
public class Succession {
    private RequestType requestType, succession;
    private RankManager.Rank rank;
    private String jsonFormat;
    public Succession(RequestType requestType, RequestType succession, RankManager.Rank rank) {
        this.requestType = requestType;
        this.succession = succession;
        this.rank = rank;
        this.jsonFormat = "{\"type\":\"" + requestType.getName() + "\",\"succession\":\""+succession.getName()+"\"}";
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public RankManager.Rank getRank() {
        return rank;
    }

    public RequestType getSuccession() {
        return succession;
    }
}
