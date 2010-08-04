/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package botarena;

import botarena.util.Packet;
import botarena.util.Command;
import botarena.util.Debug;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * This is the network Thread for each connection from the clients. Should be
 * for each bot.
 *
 * @author Lucas Hereld <duckman@piratehook.com>
 */
public class ClientSocket implements Runnable
{
    private BotArena master = null;
    private boolean running = false;
    private Socket clientSocket = null;
    private ObjectOutputStream out = null;
    private ObjectInputStream in = null;
    private boolean authenticated = false;
    private Bot bot = null;
    
    ClientSocket(BotArena master,Socket clientSocket)
    {
        this.master = master;
        this.clientSocket = clientSocket;
        try
        {
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
        }
        catch(IOException ex)
        {
            System.out.println(ex.toString());
            ex.printStackTrace(System.out);
        }
    }

    /**
     * Handles the authentication
     *
     * @param username The username
     * @param password The password
     * @return An ArrayList of Bot names if authentication worked,
     * otherwise null
     */
    public ArrayList<String> authenticate(String username, String password)
    {
        if(password.compareToIgnoreCase(master.getDB().getPassword(username)) == 0)
        {
            return master.getDB().getBotList(username);
        }

        return null;
    }
    
    @Override
    public void run()
    {
        running = true;
        while(running)
        {
            try
            {
                Packet pkt = (Packet)in.readObject();
                ArrayList<String> perams = pkt.getParameter();
                switch(pkt.getCommand())
                {
                    case AUTHENTICATE:
                        if(!authenticated && bot == null && perams.size() == 2)
                        {
                            ArrayList<String> bots = authenticate(perams.get(0),perams.get(1));
                            if(bots != null && bots.size() > 0)
                            {
                                out.writeObject(new Packet(Command.AUTHENTICATE,bots));
                                authenticated = true;
                            }
                        }
                        break;
                    case LOGIN:
                        if(authenticated && bot == null && perams.size() == 1)
                        {
                            bot = master.getDB().loadBot(master,this,perams.get(0));
                            master.addThing(bot);
                        }
                        break;
                    default:
                        if(authenticated && bot != null)
                        {
                            bot.addPacket(pkt);
                        }
                }
            }
            catch(Exception ex)
            {
                Debug.printex(ex);
                stop();
            }
        }
    }

    /**
     * Properly stop the Thread for a clean shutdown
     */
    public void stop()
    {
        running = false;
        master.removeThing(bot);
    }

    /**
     * Sends a Packet to the client
     * @param cmd Command to be sent
     * @param params Parameters to be sent
     */
    public void send(Command cmd,ArrayList<String> params)
    {
        if(running && authenticated && bot == null)
        {
            try
            {
                out.writeObject(new Packet(cmd,params));
            }
            catch(IOException ex)
            {
                Debug.printex(ex);
            }
        }
    }
}
