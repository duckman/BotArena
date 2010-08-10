/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package botarena;

import botarena.util.Packet;
import botarena.util.Command;
import botarena.util.Debug;
import botarena.util.Direction;
import botarena.util.Type;
import botarena.util.Thing;
import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * THE BOT! Represents a player created bot that roams our little world.
 *
 * @author Lucas Hereld <duckman@piratehook.com>
 */
public class Bot extends Thing implements Runnable
{
    private BotArena master = null;
    private ObjectOutputStream out = null;
    private ObjectInputStream in = null;
    private boolean authenticated = false;
    private boolean running = false;
    private String name = null;
    private int radius = 5;
    private int hp = 100;

    /**
     * Constructor...
     *
     * @param master The master BotArena
     * @param socket The ClientSocket responsible for the Bot
     * @param name The name of the Bot
     * @param x x coordinate of initial position
     * @param y y coordinate of initial position
     */
    public Bot(BotArena master,Socket clientSocket)
    {
        super();
        this.master = master;
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
                        if(!authenticated && perams.size() == 2)
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
                        if(authenticated && perams.size() == 1)
                        {
                            String bot = master.getDB().loadBot(perams.get(0));
                            master.addThing(this);
                        }
                        break;
                    default:
                        if(authenticated)
                        {
                            addPacket(pkt);
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
    @Override
    public void stop()
    {
        running = false;
        master.removeThing(this);
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

    /**
     * Sends a Packet to the client
     * @param cmd Command to be sent
     * @param params Parameters to be sent
     */
    public void send(Command cmd,ArrayList<String> params)
    {
        if(running && authenticated)
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

    /**
     * Getter function to retrieve the Bot's name
     *
     * @return The Bot's name
     */
    @Override
    public String getName()
    {
        return name;
    }

    /**
     * Getter function to retrieve the Bot's Type... which is BOT
     *
     * @return Type.BOT
     */
    @Override
    public Type getType()
    {
        return Type.BOT;
    }

    /**
     * Makes the Bot do what it does.
     *
     * @param pkt The Packet to be processed
     */
    @Override
    protected void step(Packet pkt)
    {
        if(pkt != null)
        {
            switch(pkt.getCommand())
            {
                // 0 - direction
                case MOVE:
                    Point position = master.getMap().getPosition(this);
                    switch(Enum.valueOf(Direction.class, pkt.getParameter().get(0)))
                    {
                        case UP:
                            position.translate(0, 1);
                            break;
                        case DOWN:
                            position.translate(0, -1);
                            break;
                        case LEFT:
                            position.translate(-1, 0);
                            break;
                        case RIGHT:
                            position.translate(1, 0);
                            break;
                    }
                    master.moveThing(this, position);
                    break;
                // 0 - spell
                // 1 - direction
                case FIRE:
                    Direction direction = Enum.valueOf(Direction.class, pkt.getParameter().get(1));

                    master.addThing(this,new Projectile(master,"Bullet",10,direction),direction);
                    break;
            }
        }

        ArrayList<Thing> things = master.getMap().getThings(master.getMap().getPosition(this), radius);
        ArrayList<String> perams = new ArrayList<String>();

        for(int x=0;x<things.size();x++)
        {
            perams.add(things.get(x).toString());
        }

        send(Command.VIEW, perams);
    }

    /**
     * handle when the Bot runs into something
     *
     * @param thing The Thing it's running into
     * @return returns true if it still can move, otherwise false
     */
    @Override
    public boolean collideWith(Thing thing)
    {
        switch(thing.getType())
        {
            case BOT:
            case OBSTACLE:
                return false;
            case PROJECTILE:
                hp -= thing.damage(0);
                if(hp <= 0)
                {
                    master.removeThing(this);
                    master.respawnThing(this);
                }
                break;
        }

        return true;
    }

    private void load(String bot)
    {
        String[] params = bot.split(":");

        name = params[0];
    }
}
