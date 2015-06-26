package org.horrgs.chat.server.types;

/**
 * Created by Horrgs on 6/18/2015.
 */
public enum RequestType {
    CREATE_ACCOUNT("create_account"),
    LOGIN("login"),
    ERROR("error"),
    SEND_MESSAGE("send_message");
    private String type;

    private RequestType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public RequestType getByType(String type) {
        for(RequestType requestTypes : RequestType.values()) {
            if(requestTypes.getType().equals(type)) {
                return requestTypes;
            }
        }
        return null;
    }
}
