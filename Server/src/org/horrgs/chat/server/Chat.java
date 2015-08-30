package org.horrgs.chat.server;

import org.horrgs.chat.server.sockets.ConnectionHandle;
import org.horrgs.chat.server.usertypes.Rank;
import org.horrgs.chat.server.windows.Console;
import org.horrgs.chat.server.windows.Window;

import java.util.HashMap;

/**
 * Created by Horrgs on 5/14/2015.
 */
public class Chat {
    //TODO: fix, color is null when being sent to Client.
    public static HashMap<Rank, String> rankColors = new HashMap<>();
    public static void main(String[] args) throws InterruptedException {
        new FileManager().setup();
        Console.getInstance().openWindow();
        new ConnectionHandle().start();
        new UsernameParser().parse();
        rankColors.put(Rank.USER, "#000000");
        rankColors.put(Rank.MODERATOR, "#47D147");
        rankColors.put(Rank.ADMINISTRATOR, "#B20000");
    }
}
