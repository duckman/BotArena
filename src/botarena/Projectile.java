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
 * Represents a Projectile fired from a Bot or anything else that can fire.
 *
 * @author Lucas Hereld <duckman@piratehook.com>
 */
public class Projectile extends Thing
{
    private BotArena master = null;
    private String name = null;
    private int dmg = 0;
    private Direction direction;

    /**
     * Constructor...
     *
     * @param master The master BotArena
     * @param name The name of the Bot
     * @param dmg The amount of damage done by the projectile
     * @param direction The Direction the projectile is going
     * @param x x coordinate of initial position
     * @param y y coordinate of initial position
     */
    public Projectile(BotArena master,String name,int dmg,Direction direction)
    {
        this.master = master;
        this.name = name;
        this.dmg = dmg;
        this.direction = direction;
    }

    /**
     * handle when the Projectile runs into something
     *
     * @param thing The Thing it's running into
     * @return returns true if it still can move, otherwise false
     */
    @Override
    public boolean collideWith(Thing thing)
    {
        thing.damage(dmg);
        master.removeThing(this);

        return false;
    }

    /**
     * Getter function to retrieve the Projectile's name
     *
     * @return The Projectile's name
     */
    @Override
    public String getName()
    {
        return name;
    }

    /**
     * Getter function to retrieve the Projectile's Type... which is BOT
     *
     * @return Type.PROJCTILE
     */
    @Override
    public Type getType()
    {
        return Type.PROJECTILE;
    }

    /**
     * Makes the Projectile do what it does, which is just move in its Direction
     *
     * @param pkt The Packet to be processed
     */
    @Override
    protected void step(Packet pkt)
    {
        Point position = master.getMap().getPosition(this);
        switch(direction)
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
    }


}
