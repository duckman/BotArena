/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package botarena.util;

import botarena.Bot;
import botarena.BotArena;
import botarena.ClientSocket;
import java.util.ArrayList;

/**
 *
 * @author Lucas Hereld <duckman@piratehook.com>
 */
public interface Database
{
    public String getPassword(String username);
    
    public ArrayList<String> getBotList(String username);

    public ArrayList<Thing> loadMap();

    public Bot loadBot(BotArena master,ClientSocket socket,String name);
}
