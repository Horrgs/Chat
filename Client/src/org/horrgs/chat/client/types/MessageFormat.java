package org.horrgs.chat.client.types;

import org.horrgs.chat.client.users.RankManager;
import org.horrgs.chat.client.windows.MessageWindow;

/**
 * Created by Horrgs on 6/17/2015.
 *
 * This is both outgoing and incoming. It can read messages coming in using this and
 * can send them using this. when ConnectionHandle calls sendToAll all it requires is
 * an instance of MessageFormat as it contains the data for it to send to all others.
 */
public class MessageFormat {
    private RequestType type;
    private RankManager.Rank rank;
    private String sender;
    private String message;
    private String jsonFormat;
    private String color;

    public MessageFormat(RequestType type, RankManager.Rank rank, String sender, String message, String color) {
        this.type = type;
        this.rank = rank;
        this.sender = sender;
        this.message = message;
        this.jsonFormat = "{\"type\":\"" + type.getName() + "\",\"sender\":\"" + sender + "\",\"message\":\""+message+"\",\"color\":\"" + color + "\"}";
    }


    public String getMessage() {
        if(type == RequestType.SEND_MESSAGE) {
            return message;
        }
        return "";
    }

    public RankManager.Rank getRank() {
        return rank;
    }

    public String getSender() {
        return sender;
    }

    public RequestType getType() {
        return type;
    }

    public String getJsonFormat() {
        return jsonFormat;
    }

    public String getColor() {
        return color;
    }
}
