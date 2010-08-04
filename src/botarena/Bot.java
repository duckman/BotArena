/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package botarena;

import botarena.util.Packet;
import botarena.util.Command;
import botarena.util.Direction;
import botarena.util.Type;
import botarena.util.Thing;
import java.awt.Point;
import java.util.ArrayList;

/**
 * THE BOT! Represents a player created bot that roams our little world.
 *
 * @author Lucas Hereld <duckman@piratehook.com>
 */
public class Bot extends Thing
{
    private BotArena master = null;
    private ClientSocket socket = null;
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
    public Bot(BotArena master,ClientSocket socket,String name,int x,int y)
    {
        super();
        this.master = master;
        this.socket = socket;
        this.name = name;
        setPosition(new Point(x,y));
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
        switch(pkt.getCommand())
        {
            // 0 - direction
            case MOVE:
                switch(Enum.valueOf(Direction.class, pkt.getParameter().get(0)))
                {
                    case UP:
                        master.moveThing(this, getPosition().x, getPosition().y+1);
                        break;
                    case DOWN:
                        master.moveThing(this, getPosition().x, getPosition().y-1);
                        break;
                    case LEFT:
                        master.moveThing(this, getPosition().x-1, getPosition().y);
                        break;
                    case RIGHT:
                        master.moveThing(this, getPosition().x+1, getPosition().y);
                        break;
                }
                break;
            // 0 - spell
            // 1 - direction
            case FIRE:
                switch(Enum.valueOf(Direction.class, pkt.getParameter().get(1)))
                {
                    case UP:
                        master.addThing(new Projectile(master,"Bullet",10,Direction.UP,getPosition().x, getPosition().y+1));
                        break;
                    case DOWN:
                        master.addThing(new Projectile(master,"Bullet",10,Direction.DOWN,getPosition().x, getPosition().y-1));
                        break;
                    case LEFT:
                        master.addThing(new Projectile(master,"Bullet",10,Direction.LEFT,getPosition().x-1, getPosition().y));
                        break;
                    case RIGHT:
                        master.addThing(new Projectile(master,"Bullet",10,Direction.RIGHT,getPosition().x+1, getPosition().y));
                        break;
                }
                break;
        }

        ArrayList<Thing> things = master.getMap().getThings(getPosition().x, getPosition().y, radius);
        ArrayList<String> perams = new ArrayList<String>();

        for(int x=0;x<things.size();x++)
        {
            perams.add(things.get(x).toString());
        }

        socket.send(Command.VIEW, perams);
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

    /**
     * Stops the client socket for a clean shutdown
     */
    @Override
    public void stop()
    {
        socket.stop();
    }
}
