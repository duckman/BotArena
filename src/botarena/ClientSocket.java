/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package botarena;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author lucashereld
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

    public ArrayList<String> authenticate(String username, String password)
    {
        if(password.compareToIgnoreCase(master.getDB().getPassword(username)) == 0)
        {
            return master.getDB().getBotList(username);
        }

        return null;
    }
    
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

    public void stop()
    {
        running = false;
        master.removeThing(bot);
    }

    public void send(Command cmd,ArrayList<String> perams)
    {
        if(running && authenticated && bot == null)
        {
            try
            {
                out.writeObject(new Packet(cmd,perams));
            }
            catch(IOException ex)
            {
                Debug.printex(ex);
            }
        }
    }
}
