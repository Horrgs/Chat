package org.horrgs.chat.client.types;

/**
 * Created by Horrgs on 7/2/2015.
 */
public class Succession {
    private RequestType requestType, succession;
    private String jsonFormat;
    public Succession(RequestType requestType, RequestType succession) {
        this.requestType = requestType;
        this.succession = succession;
        this.jsonFormat = "{\"type\":\"" + requestType.getName() + "\",\"succession\":\""+succession.getName()+"\"}";
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public RequestType getSuccession() {
        return succession;
    }
}
