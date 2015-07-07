package org.horrgs.chat.server.types;

import org.horrgs.chat.server.usertypes.Rank;
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
    private Rank rank;
    private String sender;
    private String message;
    private String jsonFormat;
    //private String color;
    //TODO: maybe include rank name for the messageArea on client.
    public MessageFormat(RequestType type, Rank rank, String sender, String message, String color) {
        this.type = type;
        this.rank = rank;
        this.sender = sender;
        this.message = message;
        //TODO: add rank to jsonFormat.
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

    public Rank getRank() { return rank; }

    public String getSender() {
        return sender;
    }

    public RequestType getType() {
        return type;
    }

    public String getJsonFormat() { return jsonFormat; }

    //public String getColor() { return color; }
}
