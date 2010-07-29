/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package botarena;

import botarena.util.Database;
import botarena.util.Map;
import botarena.util.Thing;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author lucashereld
 */
public class BruteForceMap implements Map
{
    private BotArena master = null;
    private HashMap<Point, Thing> things = null;
    private Random rand = null;

    BruteForceMap(BotArena master)
    {
        this.master = master;
        things = new HashMap<Point, Thing>();
        rand = new Random();
        load();
    }

    private void load()
    {
        Database db = master.getDB();

        ArrayList<Thing> things = db.loadMap();

        for(int x=0;x<things.size();x++)
        {
            this.things.put(things.get(x).getPosition(), things.get(x));
        }
    }

    @Override
    public boolean addThing(int x,int y,Thing thing)
    {
        things.put(new Point(x,y), thing);
        return true;
    }

    @Override
    public Thing getThing(int x,int y)
    {
        return things.get(new Point(x,y));
    }

    @Override
    public boolean exists(Thing thing)
    {
        return things.containsValue(thing);
    }

    @Override
    public void removeThing(Thing thing)
    {
        things.remove(thing.getPosition());
    }

    @Override
    public ArrayList<Thing> getThings(int x,int y,int distance)
    {
        ArrayList<Thing> things = new ArrayList<Thing>();

        for(int left = x-distance;left <= x+distance;left++)
        {
            for(int top = y-distance;top <= y+distance;top++)
            {
                if(Point.distance(x, y, left, top) <= distance)
                {
                    if(this.things.containsKey(new Point(left,top)))
                    {
                        things.add(this.things.get(new Point(left,top)));
                    }
                }
            }
        }

        return things;
    }
    
    @Override
    public boolean move(Thing thing,int x,int y)
    {
        Point newPoint = thing.getPosition();
        newPoint.move(x, y);

        if(things.containsKey(newPoint))
        {
            return false;
        }

        things.remove(thing.getPosition());
        thing.setPosition(newPoint);
        things.put(thing.getPosition(), thing);
        
        return true;
    }

    @Override
    public Point randomPoint()
    {
        return new Point(rand.nextInt(),rand.nextInt());
    }
}
