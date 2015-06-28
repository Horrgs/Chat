package org.horrgs.chat.server;

import org.horrgs.chat.server.sockets.ConnectionHandle;

/**
 * Created by Horrgs on 5/14/2015.
 */
public class Chat {
    //TODO: have a JSON ready string for each Format class so it's easier to send to the client for the server and easier for the client to send to the server.
    //TODO: we also need not to only check if there is an account with that email but already an account with that username.

    public static void main(String[] args) throws InterruptedException {
        new ConnectionHandle().start();
    }
}
