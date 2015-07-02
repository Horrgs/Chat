package org.horrgs.chat.server.types;

/**
 * Created by Horrgs on 6/18/2015.
 */
public enum RequestType {
    CREATE_ACCOUNT("CREATE_ACCOUNT"),
    LOGIN("LOGIN"),
    ERROR("ERROR"),
    SUCCESSION("SUCCESSION"),
    SEND_MESSAGE("SEND_MESSAGE");
    private String name;

    private RequestType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public RequestType getByName(String name) {
        for(RequestType requestTypes : RequestType.values()) {
            if(requestTypes.getName().equals(name)) {
                return requestTypes;
            }
        }
        return null;
    }
}
