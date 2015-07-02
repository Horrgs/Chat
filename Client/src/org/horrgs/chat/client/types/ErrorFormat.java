package org.horrgs.chat.client.types;

/**
 * Created by Horrgs on 6/22/2015.
 *
 * The use of this class is for sending an error message to the client.
 * If the email, username or password is wrong this is the class to use.
 */
public class ErrorFormat {
    /*
    The reason this isn't in a PACKAGE called 'incoming' as if an error is thrown we can send the
    stack trace to the server with it and other information.
     */
    private RequestType requestType;
    private String message;
    private String jsonFormat;

    public ErrorFormat(RequestType requestType, String message) {
        this.requestType = requestType;
        this.message = message;
        this.jsonFormat = "{\"type\":\"" + requestType.getName() + "\",\"message\":\"" + message+"\"}";
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public String getMessage() {
        return message;
    }

    public String getJsonFormat() { return jsonFormat; }
}
