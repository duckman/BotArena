/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package botarena;

import botarena.util.Packet;
import botarena.util.Thing;
import botarena.util.Type;

/**
 *
 * @author Lucas Hereld <duckman@piratehook.com>
 */
public class Obstacle extends Thing
{
    private String name;

    public Obstacle(String name)
    {
        this.name = name;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public Type getType()
    {
        return Type.OBSTACLE;
    }

    @Override
    protected void step(Packet pkt)
    {
        // do nothing...
    }

}
