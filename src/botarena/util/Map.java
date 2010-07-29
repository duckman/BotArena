/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package botarena.util;

import java.util.ArrayList;

/**
 *
 * @author lucashereld
 */
public interface Map
{
    public boolean addThing(int x,int y,Thing thing);
    
    public ArrayList<Thing> getThings(int x,int y,int distance);

    public void removeThing(Thing thing);

    public boolean move(Thing thing,int x,int y);
}
