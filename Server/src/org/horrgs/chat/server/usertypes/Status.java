package org.horrgs.chat.server.usertypes;

/**
 * Created by Horrgs on 5/15/2015.
 */
public enum Status {
    ONLINE("ONLINE"),
    BUSY("BUSY"),
    OFFLINE("OFFLINE");

    private String status;

    private Status(String status) { this.status = status; }

    public String getId() { return status; }

    public Status getById(String id) {
        for(Status status : Status.values()) {
            if(status.getId().equals(id)) {
                return status;
            }
        }
        return null;
    }
}
