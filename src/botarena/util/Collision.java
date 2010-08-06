/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package botarena.util;

import java.awt.Point;

/**
 * Used to keep track of a collision on the map.
 *
 * @author Lucas Hereld <duckman@piratehook.com>
 */
public class Collision
{
    private Thing thing = null;
    private Point position;

    /**
     *
     *
     * @param thing the Thing that did the colliding
     * @param x the x coordinate it was trying to go to
     * @param y the y coordinate it was trying to go to
     */
    public Collision(Thing thing,Point position)
    {
        this.thing = thing;
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

    public Point getPosition()
    {
        return position;
    }
}
