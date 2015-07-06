package org.horrgs.chat.server;

import org.horrgs.chat.server.sockets.ConnectionHandle;
import org.horrgs.chat.server.windows.Console;
import org.horrgs.chat.server.windows.Window;

/**
 * Created by Horrgs on 5/14/2015.
 */
public class Chat {
    public static void main(String[] args) throws InterruptedException {
        new FileManager().setup();
        Console.getInstance().openWindow();
        new ConnectionHandle().start();
        new UsernameParser().parse();
    }
}
