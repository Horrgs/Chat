package org.horrgs.chat.server.types;

import org.horrgs.chat.server.usertypes.User;

import java.awt.*;

/**
 * Created by Horrgs on 6/17/2015.
 *
 * This is both outgoing and incoming. It can read messages coming in using this and
 * can send them using this. when ConnectionHandle calls sendToAll all it requires is
 * an instance of MessageFormat as it contains the data for it to send to all others.
 */
public class MessageFormat {
    private RequestType type;
    private String sender;
    private String message;
    private String jsonFormat;
    //private String color;
    public MessageFormat(RequestType type, String sender, String message, String color) {
        this.type = type;
        this.sender = sender;
        this.message = message;
        this.jsonFormat = "{\"type\":\"" + type.getName() + "\",\"sender\":\"" + sender + "\",\"message\":\""+message+"\",\"color\":\"" + color + "\"}";
    }

    public MessageFormat(RequestType type, User user, String message) {
        this.type = type;
        this.sender = user.getUsername();
        this.message = message;
    }

    public String getMessage() {
        if(type == RequestType.SEND_MESSAGE) {
            return message;
        }
        return "";
    }

    public String getSender() {
        return sender;
    }

    public RequestType getType() {
        return type;
    }

    public String getJsonFormat() { return jsonFormat; }

    //public String getColor() { return color; }
}
