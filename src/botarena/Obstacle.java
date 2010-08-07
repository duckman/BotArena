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
    private BotArena master;
    private String name;
    private int hp;

    public Obstacle(BotArena master,String name,int hp)
    {
        this.master = master;
        this.name = name;
        this.hp = hp;
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

    /**
     * This function is called to damage this Obstacle
     * @param dmg The amount of damage being dealt
     * @return the amount of damage to do back
     */
    @Override
    public int damage(int dmg)
    {
        if(hp > 0)
        {
            hp -= dmg;
            if(hp < 1)
            {
                master.removeThing(this);
            }
        }
        return 0;
    }
}
