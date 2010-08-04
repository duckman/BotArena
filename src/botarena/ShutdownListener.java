/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package botarena;

/**
 * Listens for a shutdown and tells the BotArena to shutdown properly
 * 
 * @author Lucas Hereld <duckman@piratehook.com>
 */
public class ShutdownListener extends Thread
{
    private BotArena bots = null;

    ShutdownListener(BotArena bots)
    {
        this.bots = bots;
    }

    @Override
    public void run()
    {
        System.out.println("Caught Shutdown - shutting down now!");
        bots.stop();
    }
}
