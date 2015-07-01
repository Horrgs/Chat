package org.horrgs.chat.server;

import org.horrgs.chat.server.sockets.ConnectionHandle;

/**
 * Created by Horrgs on 5/14/2015.
 */
public class Chat {
    public static void main(String[] args) throws InterruptedException {
        new UsernameParser().parse();
        new ConnectionHandle().start();
    }
}
