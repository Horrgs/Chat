package org.horrgs.chat.server.types.outgoing;

import org.horrgs.chat.server.types.RequestType;

/**
 * Created by Horrgs on 6/22/2015.
 *
 * The use of this class is for sending an error message to the client.
 * If the email, username or password is wrong this is the class to use.
 */
public class ErrorFormat {
    private RequestType requestType;
    private String message;

    public ErrorFormat(RequestType requestType, String message) {
        this.requestType = requestType;
        this.message = message;
    }

    public ErrorFormat(String requestType, String message) {
        RequestType requestType1 = RequestType.CREATE_ACCOUNT;
        requestType1 = requestType1.getByType(requestType);
        this.requestType = requestType1;
        this.message = message;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public String getMessage() {
        return message;
    }
}
