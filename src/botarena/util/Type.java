/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package botarena.util;

/**
 * This enum represents the different types of Things in our little world
 *
 * @author Lucas Hereld <duckman@piratehook.com>
 */
public enum Type
{
    /**
     * A player made Bot
     */
    BOT,
    /**
     * Some static Thing like a rock
     */
    OBSTACLE,
    /**
     * A projectile fired from something
     */
    PROJECTILE,
    /**
     * A Bot that is controlled by the server
     */
    NPC
}
