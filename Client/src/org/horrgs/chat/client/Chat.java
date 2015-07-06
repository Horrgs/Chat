package org.horrgs.chat.client;

import org.horrgs.chat.client.windows.Console;
import org.horrgs.chat.client.windows.MainMenu;

/**
 * Created by Horrgs on 5/15/2015.
 */
public class Chat {

    public static void main(String[] args) {
        new Console().getInstance().openWindow();
        new MainMenu();
    }
}
