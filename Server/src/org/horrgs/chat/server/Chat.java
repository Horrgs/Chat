package org.horrgs.chat.server;

import org.horrgs.chat.server.windows.Console;
import org.horrgs.chat.server.windows.Window;

/**
 * Created by Horrgs on 5/14/2015.
 */
public class Chat {
    public static void main(String[] args) throws InterruptedException {
        new FileManager().setup();
        new Window();
        new Console().openWindow();
        new UsernameParser().parse();
    }
}
