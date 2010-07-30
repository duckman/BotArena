/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package botarena.util;

/**
 *
 * @author Lucas Hereld <duckman@piratehook.com>
 */
public class Collision
{
    private Thing thing = null;
    private int x;
    private int y;

    public Collision(Thing thing,int x,int y)
    {
        this.thing = thing;
        this.x = x;
        this.y = y;
    }

    public Thing getThing()
    {
        return thing;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }
}
