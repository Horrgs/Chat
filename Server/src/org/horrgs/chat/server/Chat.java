package org.horrgs.chat.server;

import org.horrgs.chat.server.usertypes.Status;
import org.horrgs.chat.server.usertypes.User;
import org.horrgs.chat.server.usertypes.UserManager;

/**
 * Created by Horrgs on 5/14/2015.
 */
public class Chat {

    public static void main(String[] args) throws InterruptedException {
        //new ConnectionHandle().start();
        String sender = "Horrgs", message = "hello.";
        System.out.println("{\"type\":message\",\"sender\":"+ sender +"\",\"message\":\"" + message +"\"}");

        ///Hello.
    }
}
