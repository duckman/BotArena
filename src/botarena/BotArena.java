/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package botarena;

import botarena.util.Collision;
import botarena.util.Database;
import botarena.util.Debug;
import botarena.util.Direction;
import botarena.util.Map;
import botarena.util.Thing;
import java.awt.Point;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The main game engine. Keeps track of whats going on and of what components
 * are doing.
 *
 * @author Lucas Hereld <duckman@piratehook.com>
 */
public class BotArena implements Runnable
{
    private Database db = null;
    private Map map = null;
    private NetworkListener network = null;
    private boolean running = false;
    private ArrayList<Thing> things = null;
    private ArrayList<Collision> collisions = null;
    
    /**
     * The constructor
     */
    public BotArena()
    {
        try
        {
            db = (Database)Class.forName(Config.getConfig("dbEngine")).newInstance();
            db.setup();
        }
        catch(ClassNotFoundException ex)
        {
            // should prollyy throw a custom exception
            Debug.printex(ex);
        }
        catch (InstantiationException ex)
        {
            Logger.getLogger(BotArena.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex)
        {
            Logger.getLogger(BotArena.class.getName()).log(Level.SEVERE, null, ex);
        }
        map = new BruteForceMap(this);
        network = new NetworkListener(this);
        things = new ArrayList<Thing>();
    }
    
    /**
     * Add a Thing to the list. These are all the things, weather they are on
     * the map or not.
     *
     * @param thing The Thing to be added
     */
    public void addThing(Thing thing)
    {
        things.add(thing);
        map.addThing(map.randomPoint(), thing);
    }

    public void addThing(Thing host,Thing newThing,Direction direction)
    {
        Point position = map.getPosition(host);
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
        things.add(newThing);
        map.addThing(position, newThing);
    }

    /**
     * Remove a thing. It also make sure it is removed from the Map as well.
     *
     * @param thing The Thing to be removed
     */
    public void removeThing(Thing thing)
    {
        things.remove(thing);
        map.removeThing(thing);
    }

    /**
     * Tells the map to move a Thing and also reports a collision if that
     * happens.
     *
     * @param thing The Thing to move
     * @param x The x coordinate of where to move it
     * @param y The y coordinate of where to move it
     * @return true if it moved, otherwise false
     */
    public boolean moveThing(Thing thing, Point newPostition)
    {
        if(map.move(thing, newPostition))
        {
            return true;
        }
        else
        {
            collisions.add(new Collision(thing,newPostition));
        }

        return true;
    }

    /**
     * Takes a Thing and spawns it in a random place
     *
     * @param thing The Thing to respawn
     */
    public void respawnThing(Thing thing)
    {
        addThing(thing);
    }

    /**
     * Getter for the Database
     *
     * @return The Database
     */
    public Database getDB()
    {
        return db;
    }

    /**
     * Getter for the Map
     *
     * @return The Map
     */
    public Map getMap()
    {
        return map;
    }
    
    @SuppressWarnings("empty-statement")
    @Override
    public void run()
    {
        new Thread(network).start();
        while(running)
        {
            long start = System.currentTimeMillis();
            
            try
            {
                collisions = new ArrayList<Collision>();
                for(int x=0;x<things.size();x++)
                {
                    things.get(x).step();
                }

                // handle collisions
                for(int x=0;x<collisions.size();x++)
                {
                    Collision collision = collisions.get(x);

                    // could have been removed by another Thing
                    if(map.exists(collision.getThing()))
                    {
                        // make sure there is still a conflict
                        if(!moveThing(collision.getThing(),collision.getPosition()))
                        {
                            if(collision.getThing().collideWith(map.getThing(collision.getPosition())))
                            {
                                moveThing(collision.getThing(),collision.getPosition());
                            }
                        }
                    }
                }
                collisions = null;

                Thread.sleep(16 - (System.currentTimeMillis() - start));
            }
            catch(InterruptedException ex)
            {
                System.out.println(ex.toString());
                ex.printStackTrace(System.out);
            }
        }
    }

    /**
     * Stops all the threads running for a clean stop.
     */
    public void stop()
    {
        network.stop();
        for(int x=0;x<things.size();x++)
        {
            things.get(x).stop();
        }
        running = false;
    }
}
