/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package botarena.util;

/**
 * Used to keep track of a collision on the map.
 *
 * @author Lucas Hereld <duckman@piratehook.com>
 */
public class Collision
{
    private Thing thing = null;
    private int x;
    private int y;

    /**
     *
     *
     * @param thing the Thing that did the colliding
     * @param x the x coordinate it was trying to go to
     * @param y the y coordinate it was trying to go to
     */
    public Collision(Thing thing,int x,int y)
    {
        this.thing = thing;
        this.x = x;
        this.y = y;
    }

    /**
     * Getter for the Thing
     *
     * @return the Thing
     */
    public Thing getThing()
    {
        return thing;
    }

    /**
     * Getter for the x coordinate
     *
     * @return the x
     */
    public int getX()
    {
        return x;
    }

    /**
     * Getter for the y coordinate
     *
     * @return the y
     */
    public int getY()
    {
        return y;
    }
}
