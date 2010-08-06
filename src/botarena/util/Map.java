/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package botarena.util;

import java.awt.Point;
import java.util.ArrayList;

/**
 * Interface to represent the entire map. Keeps track of what and where
 * everything is.
 *
 * @author Lucas Hereld <duckman@piratehook.com>
 */
public interface Map
{
    /**
     * Add a Think to the map
     *
     * @param x x coordinate
     * @param y y coordinate
     * @param thing the Thing
     * @return true if nothing was in the way, false otherwise
     */
    public boolean addThing(Point postion,Thing thing);

    /**
     * Get what ever Thing is at a certain point
     *
     * @param x x coordinate
     * @param y y coordinate
     * @return the Thing or null if nothing is there
     */
    public Thing getThing(Point position);

    public Point getPosition(Thing thing);

    /**
     * Checks if a Thing is on the map
     *
     * @param thing The Thing
     * @return true if the Thing was found, false otherwise
     */
    public boolean exists(Thing thing);
    
    /**
     * Gets a list of Things within a certain distance of a point
     *
     * @param x x coordinate
     * @param y y coordinate
     * @param distance The distance to look
     * @return an ArrayList of Things
     */
    public ArrayList<Thing> getThings(Point postion,int distance);

    /**
     * Remove a Thing from th map
     *
     * @param thing the Thing
     */
    public void removeThing(Thing thing);

    /**
     * Move a Thing to a new point
     *
     * @param thing the Thing
     * @param x new x coordinate
     * @param y new y coordinate
     * @return returns true if nothing was in the new point, false otherwise
     */
    public boolean move(Thing thing,Point postion);

    /**
     * Generates a random Point that is within the bounds of the Map
     *
     * @return a random Point
     */
    public Point randomPoint();

    /**
     *
     */
    public int generateGuid();
}
