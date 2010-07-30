/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package botarena;

import botarena.util.Direction;
import botarena.util.Packet;
import botarena.util.Thing;
import botarena.util.Type;
import java.awt.Point;

/**
 *
 * @author Lucas Hereld <duckman@piratehook.com>
 */
public class Projectile extends Thing
{
    private BotArena master = null;
    private String name = null;
    private int dmg = 0;
    private Direction direction;

    public Projectile(BotArena master,String name,int dmg,Direction direction,int x,int y)
    {
        this.master = master;
        this.name = name;
        this.dmg = dmg;
        this.direction = direction;
        setPosition(new Point(x,y));
    }

    @Override
    public boolean collideWith(Thing thing)
    {
        thing.damage(dmg);
        master.removeThing(this);

        return false;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public Type getType()
    {
        return Type.PROJECTILE;
    }

    @Override
    protected void step(Packet pkt)
    {
        switch(direction)
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
    }


}
