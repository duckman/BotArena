/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package botarena.util;

/**
 * The available commands the server and client can send to each other
 *
 * @author Lucas Hereld <duckman@piratehook.com>
 */
public enum Command
{
    /**
     * Used for authentication, normally sent by the client to the server.
     * Expected arguments: username,password
     */
    AUTHENTICATE,
    /**
     * Used to log a Bot into the world.
     * Expected arguments: Bot name
     */
    LOGIN,
    /**
     * Sent by the server to let the client know what the Bot sees
     * Expected arguments: An ArrayList of Things
     */
    VIEW,
    /**
     * Sent by the client to tell the server a Bot wants to move.
     * Expected arguments: direction
     */
    MOVE,
    /**
     * Sent by the client to tell the server a Bot wants to fire something
     * Expected arguments: projectile name,direction
     */
    FIRE
}
