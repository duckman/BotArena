/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package botarena.util;

import java.awt.Point;
import java.util.LinkedList;

/**
 * Ahh the Think! This Class represents anything that can exist on the Map. It
 * is the heart and soul of everything that exists.
 *
 * @author Lucas Hereld <duckman@piratehook.com>
 */
public abstract class Thing
{
    private LinkedList<Packet> packets = null;
    private Point position = null;
    
    /**
     * Initializes the Thing so it is ready to do what it will do
     */
    public Thing()
    {
        packets = new LinkedList<Packet>();
    }

    /**
     * Getter function to retrieve the Thing's name
     *
     * @return The Thing's name
     */
    abstract public String getName();

    /**
     * Getter function to retrieve the Thing's Type
     *
     * @return The Thing's Type
     */
    abstract public Type getType();

    /**
     * Getter function to retrieve the Thing's current location in the Map
     *
     * @return The Thing's position
     */
    public Point getPosition()
    {
        return position;
    }

    /**
     * Setter for setting the Thing's current location in the Map
     *
     * @param position The new position
     */
    public void setPosition(Point position)
    {
        this.position = position;
    }
    
    /**
     * Add a new Packet to the Thing so it does something
     *
     * @param pkt The Packet
     */
    public void addPacket(Packet pkt)
    {
        packets.add(pkt);
    }

    /**
     * Tells the Thing to make it's next move. This function isn't meant to be
     * overwritten
     */
    public void step()
    {
        step(packets.poll());
    }
    
    /**
     * This function is meant to be overwritten and is what processes the actual
     * Packets
     *
     * @param pkt The Packet to be processed
     */
    abstract protected void step(Packet pkt);

    /**
     * When collisions are processed, this is the function that is called
     *
     * @param thing The thing colliding with this Thing
     * @return true if the collision still allows this Thing to move, false
     * otherwise
     */
    abstract public boolean collideWith(Thing thing);

    /**
     * This function is called to damage this Thing
     * @param dmg The amount of damage being dealt
     * @return the amount of damage to do back
     */
    public int damage(int dmg)
    {
        return 0;
    }

    /**
     * If your Thing needs to do something to stop properly, you should
     * overwrite this function.
     */
    public void stop()
    {
        
    }

    @Override
    public String toString()
    {
        return getName()+":"+getType()+":"+getPosition();
    }
}
