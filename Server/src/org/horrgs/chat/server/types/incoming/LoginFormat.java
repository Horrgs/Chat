package org.horrgs.chat.server.types.incoming;

import org.horrgs.chat.server.types.RequestType;

/**
 * Created by Horrgs on 6/18/2015.
 */
public class LoginFormat {
    private RequestType requestType;
    private String username;
    private String password;
    
    public LoginFormat(RequestType requestType, String username, String password) {
        this.requestType = requestType;
        this.username = username;
        this.password = password;
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
}
