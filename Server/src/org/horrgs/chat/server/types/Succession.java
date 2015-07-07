package org.horrgs.chat.server.types;

import org.horrgs.chat.server.usertypes.Rank;

/**
 * Created by Horrgs on 7/2/2015.
 */
public class Succession {
    private RequestType requestType, succession;
    private String jsonFormat;
    private Rank rank;
    public Succession(RequestType requestType, RequestType succession, Rank rank) {
        this.requestType = requestType;
        this.succession = succession;
        this.rank = rank;
        this.jsonFormat = "{\"type\":\"" + requestType.getName() + "\",\"succession\":\""+succession.getName()+"\",\"rank\":\"" + rank.getName() + "\"}";
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public RequestType getSuccession() {
        return succession;
    }

    public Rank getRank() { return rank; }

    public String getJsonFormat() { return jsonFormat; }
}
