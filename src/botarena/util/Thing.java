/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package botarena.util;

import java.awt.Point;
import java.util.LinkedList;

/**
 *
 * @author Lucas Hereld <duckman@piratehook.com>
 */
public abstract class Thing
{
    private LinkedList<Packet> packets = null;
    private Point position = null;
    
    public Thing()
    {
        packets = new LinkedList<Packet>();
    }

    abstract public String getName();

    abstract public Type getType();

    public Point getPosition()
    {
        return position;
    }

    public void setPosition(Point position)
    {
        this.position = position;
    }
    
    public void addPacket(Packet pkt)
    {
        packets.add(pkt);
    }

    public void step()
    {
        step(packets.poll());
    }
    
    abstract protected void step(Packet pkt);

    abstract public boolean collideWith(Thing thing);

    public int damage(int dmg)
    {
        return 0;
    }

    public void stop()
    {
        
    }

    @Override
    public String toString()
    {
        return getName()+":"+getType()+":"+getPosition();
    }
}
