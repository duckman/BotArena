/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package botarena.util;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Lucas Hereld <duckman@piratehook.com>
 */
public interface Map
{
    public boolean addThing(int x,int y,Thing thing);

    public Thing getThing(int x,int y);

    public boolean exists(Thing thing);
    
    public ArrayList<Thing> getThings(int x,int y,int distance);

    public void removeThing(Thing thing);

    public boolean move(Thing thing,int x,int y);

    public Point randomPoint();
}
