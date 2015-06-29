package org.horrgs.chat.client.types.outgoing;

import org.horrgs.chat.client.types.RequestType;

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

    public CreateAccountFormat(RequestType requestType, String email, String username, String password) {
        this.email = email;
        this.requestType = requestType;
        this.username = username;
        this.password = password;
    }

    public RequestType getType() {
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
}
