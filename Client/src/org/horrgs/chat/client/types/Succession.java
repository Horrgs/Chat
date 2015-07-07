package org.horrgs.chat.client.types;

import org.horrgs.chat.client.users.Rank;

/**
 * Created by Horrgs on 7/2/2015.
 */
public class Succession {
    private RequestType requestType, succession;
    private Rank rank;
    private String jsonFormat;
    public Succession(RequestType requestType, RequestType succession, Rank rank) {
        this.requestType = requestType;
        this.succession = succession;
        this.rank = rank;
        this.jsonFormat = "{\"type\":\"" + requestType.getName() + "\",\"succession\":\""+succession.getName()+"\"}";
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public Rank getRank() {
        return rank;
    }

    public RequestType getSuccession() {
        return succession;
    }
}
