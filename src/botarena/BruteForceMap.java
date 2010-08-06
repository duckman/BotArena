/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package botarena;

import botarena.util.Database;
import botarena.util.Map;
import botarena.util.MapRow;
import botarena.util.Thing;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * This is not the intended Map class to be used for ever. The Map could be one
 * of the most complicated parts of this program, so for now we are just making
 * it work, normally with brute force methods :-P. A proper Map class will be
 * written when the time comes.
 *
 * @author Lucas Hereld <duckman@piratehook.com>
 */
public class BruteForceMap implements Map
{
    private BotArena master = null;
    private HashMap<Integer, Thing> guidToThing = null;
    private HashMap<Integer, Point> guidToPoint = null;
    private HashMap<Point, Thing> pointToThing = null;
    private Random rand = null;
    private int upperBound = 0;
    private int lowerBound = 0;
    private int leftBound = 0;
    private int rightBound = 0;
    private int nextGuid = 0;

    BruteForceMap(BotArena master)
    {
        this.master = master;
        guidToThing = new HashMap<Integer, Thing>();
        guidToPoint = new HashMap<Integer, Point>();
        pointToThing = new HashMap<Point, Thing>();
        rand = new Random();
        load();
    }

    private void load()
    {
        Database db = master.getDB();

        ArrayList<MapRow> list = db.loadMap();

        for(int x=0;x<list.size();x++)
        {
            updateBounds(list.get(x).getPosition());
            guidToThing.put(list.get(x).getGuid(), list.get(x).getThing());
            guidToPoint.put(list.get(x).getGuid(), list.get(x).getPosition());
            pointToThing.put(list.get(x).getPosition(), list.get(x).getThing());
            nextGuid = list.get(x).getGuid() + 1;
        }
    }

    /**
     * Add a Think to the map
     *
     * @param x x coordinate
     * @param y y coordinate
     * @param thing the Thing
     * @return true if nothing was in the way, false otherwise
     */
    @Override
    public boolean addThing(Point position,Thing thing)
    {
        thing.setGuid(generateGuid());
        updateBounds(position);
        guidToThing.put(thing.getGuid(), thing);
        guidToPoint.put(thing.getGuid(), position);
        pointToThing.put(position, thing);
        return true;
    }

    /**
     * Get what ever Thing is at a certain point
     *
     * @param x x coordinate
     * @param y y coordinate
     * @return the Thing or null if nothing is there
     */
    @Override
    public Thing getThing(Point postion)
    {
        return pointToThing.get(postion);
    }

    @Override
    public Point getPosition(Thing thing)
    {
        return guidToPoint.get(thing.getGuid());
    }

    /**
     * Checks if a Thing is on the map
     *
     * @param thing The Thing
     * @return true if the Thing was found, false otherwise
     */
    @Override
    public boolean exists(Thing thing)
    {
        return guidToThing.containsValue(thing);
    }

    /**
     * Remove a Thing from th map
     *
     * @param thing the Thing
     */
    @Override
    public void removeThing(Thing thing)
    {
        int guid = thing.getGuid();
        guidToThing.remove(guid);
        pointToThing.remove(guidToPoint.remove(guid));
    }

    /**
     * Gets a list of Things within a certain distance of a point
     *
     * @param x x coordinate
     * @param y y coordinate
     * @param distance The distance to look
     * @return an ArrayList of Things
     */
    @Override
    public ArrayList<Thing> getThings(Point postion,int distance)
    {
        ArrayList<Thing> list = new ArrayList<Thing>();

        for(int left = postion.x-distance;left <= postion.x+distance;left++)
        {
            for(int top = postion.y-distance;top <= postion.y+distance;top++)
            {
                if(Point.distance(postion.x, postion.y, left, top) <= distance)
                {
                    if(pointToThing.containsKey(new Point(left,top)))
                    {
                        list.add(pointToThing.get(new Point(left,top)));
                    }
                }
            }
        }

        return list;
    }
    
    /**
     * Move a Thing to a new point
     *
     * @param thing the Thing
     * @param x new x coordinate
     * @param y new y coordinate
     * @return returns true if nothing was in the new point, false otherwise
     */
    @Override
    public boolean move(Thing thing,Point postion)
    {
        Point newPoint = guidToPoint.get(thing.getGuid());
        newPoint.move(postion.x, postion.y);

        if(pointToThing.containsKey(newPoint))
        {
            return false;
        }

        removeThing(thing);
        addThing(newPoint,thing);
        
        return true;
    }

    /**
     * Generates a random Point that is within the bounds of the Map
     *
     * @return a random Point
     */
    @Override
    public Point randomPoint()
    {
        int x = leftBound + rand.nextInt(rightBound - leftBound);
        int y = lowerBound + rand.nextInt(upperBound - lowerBound);
        return new Point(x,y);
    }

    /**
     * Checks if a Point is within the current bounds, and updates the bounds if
     * needed.
     *
     * @param postion The Point to check
     */
    private void updateBounds(Point position)
    {
        int x = position.x;
        int y = position.y;

        if(x+10 > rightBound)
        {
            rightBound = x+10;
        }
        else if(x - 10 < leftBound)
        {
            leftBound = x-10;
        }
        
        if(y+10 > upperBound)
        {
            upperBound = y+10;
        }
        else if(y - 10 < lowerBound)
        {
            lowerBound = y-10;
        }
    }

    @Override
    public int generateGuid()
    {
        return nextGuid++;
    }
}
