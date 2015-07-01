package org.horrgs.chat.server.types.incoming;

import org.horrgs.chat.server.types.RequestType;

/**
 * Created by Horrgs on 6/18/2015.
 *
 * This class is used for when a message is receive
 * from a client requesting to make an account.
 */
public class CreateAccountFormat {
    private RequestType requestType;
    private String email;
    private String username;
    private String password;
    private String jsonFormat;

    public CreateAccountFormat(RequestType requestType, String email, String username, String password) {
        this.email = email;
        this.requestType = requestType;
        this.username = username;
        this.password = password;
        this.jsonFormat = "{\"type\":\"" + requestType.getName() + "\",\"email\":\"" + email + "\",\"username\":\""+username+"\",\"password\":"+password+"\"}";
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJsonFormat() { return jsonFormat; }
}
