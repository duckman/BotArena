/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package botarena.util;

import java.util.Random;

/**
 * List of possible directions
 *
 * @author Lucas Hereld <duckman@piratehook.com>
 */
public enum Direction
{
    /**
     * Up, North, ^
     */
    UP,
    /**
     * Down, South
     */
    DOWN,
    /**
     * Left, West, <-
     */
    LEFT,
    /**
     * Right, East, ->
     */
    RIGHT;

    /**
     * Generates and random Direction
     *
     * @return A random Direction
     */
    public static Direction random()
    {
        Random rand = new Random();

        return Direction.values()[rand.nextInt(4)];
    }
}
