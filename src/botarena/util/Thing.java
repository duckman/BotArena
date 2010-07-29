/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package botarena.util;

import java.awt.Point;
import java.util.LinkedList;

/**
 *
 * @author lucashereld
 */
public abstract class Thing
{
    private LinkedList<Packet> packets = null;
    
    public Thing()
    {
        packets = new LinkedList<Packet>();
    }

    abstract public String getName();

    abstract public Type getType();

    abstract public Point getPosition();

    abstract public void setPosition(Point position);
    
    public void addPacket(Packet pkt)
    {
        packets.add(pkt);
    }

    public void step()
    {
        step(packets.poll());
    }
    
    abstract protected void step(Packet pkt);

    public void stop()
    {
        
    }

    @Override
    public String toString()
    {
        return getName()+":"+getType()+":"+getPosition();
    }
}
