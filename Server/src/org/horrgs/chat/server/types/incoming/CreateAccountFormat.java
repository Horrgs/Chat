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
    private String username;
    private String password;

    public CreateAccountFormat(RequestType requestType, String username, String password) {
        this.requestType = requestType;
        this.username = username;
        this.password = password;
    }

    public CreateAccountFormat(String requestType, String username, String password) {
        RequestType requestType1 = RequestType.LOGIN;
        requestType1 = requestType1.getByType(requestType);
        this.requestType = requestType1;
        this.username = username;
        this.password = password;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public String getUsername() {
        return username;
    }

    protected String getPassword() {
        return password;
    }
}
