/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package botarena;

import java.util.ArrayList;

/**
 *
 * @author lucashereld
 */
public class BotArena implements Runnable
{
    private Database db = null;
    private Map map = null;
    private NetworkListener network = null;
    private boolean running = false;
    private ArrayList<Thing> things = null;
    
    BotArena()
    {
        db = new MySQL("localhost","botarena","botarena","botarena");
        map = new BruteForceMap(this);
        network = new NetworkListener(this);
        things = new ArrayList<Thing>();
    }
    
    public void addThing(Thing thing)
    {
        things.add(thing);
        map.addThing(thing.getPosition().x, thing.getPosition().y, thing);
    }

    public void removeThing(Thing thing)
    {
        things.remove(thing);
        map.removeThing(thing);
    }

    public Database getDB()
    {
        return db;
    }

    public Map getMap()
    {
        return map;
    }
    
    @SuppressWarnings("empty-statement")
    public void run()
    {
        new Thread(network).start();
        while(running)
        {
            long start = System.currentTimeMillis();
            
            try
            {
                for(int x=0;x<things.size();x++)
                {
                    things.get(x).step();
                }
                Thread.sleep(16 - (System.currentTimeMillis() - start));
            }
            catch(InterruptedException ex)
            {
                System.out.println(ex.toString());
                ex.printStackTrace(System.out);
            }
        }
    }

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
