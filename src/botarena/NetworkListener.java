/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package botarena;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;

/**
 * The main network thread that watches for new connections from clients and
 * creates the necessary ClientSocket when one come in.
 *
 * @author Lucas Hereld <duckman@piratehook.com>
 */
public class NetworkListener implements Runnable
{
    private BotArena master = null;
    private ServerSocket serverSocket = null;
    private boolean running = false;
   
    NetworkListener(BotArena master)
    {
        this.master = master;
        try
        {
            serverSocket = new ServerSocket(1337);
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
            ex.printStackTrace(System.out);
        }
    }
    
    @Override
    public void run()
    {
        running = true;
        
        while(running)
        {
            try
            {
                new Thread(new Bot(master,serverSocket.accept())).start();
            }
            catch(SocketException ex)
            {
                // do nothing - this means the socket was closed
            }
            catch (IOException ex)
            {
                System.out.println(ex.toString());
                ex.printStackTrace(System.out);
            }
        }
    }

    /**
     * Nicely stops the Thread for a clean shutdown
     */
    public void stop()
    {
        running = false;
        try
        {
            serverSocket.close();
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
            ex.printStackTrace(System.out);
        }
    }
}
