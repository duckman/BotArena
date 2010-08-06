/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package botarena.util;

import java.awt.Point;

/**
 *
 * @author Lucas Hereld <duckman@piratehook.com>
 */
public class MapRow
{
    private int guid = -1;
    private Thing thing = null;
    private Point position = null;

    public MapRow(int guid,Thing thing,Point position)
    {
        this.guid = guid;
        this.thing = thing;
        this.position = position;
    }

    public int getGuid()
    {
        return guid;
    }

    public Point getPosition()
    {
        return position;
    }

    public Thing getThing()
    {
        return thing;
    }
}
