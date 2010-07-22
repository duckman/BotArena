/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package botarena;

import java.awt.Point;
import java.util.LinkedList;

/**
 *
 * @author lucashereld
 */
public abstract class Thing
{
    protected LinkedList<Packet> packets = null;
    
    Thing()
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

    public String toString()
    {
        return getName()+":"+getType()+":"+getPosition();
    }
}
