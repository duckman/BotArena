/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package botarena;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;

/**
 *
 * @author lucashereld
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
                new Thread(new ClientSocket(master,serverSocket.accept())).start();
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
