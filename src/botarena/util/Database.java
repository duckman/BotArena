/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package botarena.util;

import java.util.ArrayList;

/**
 * The abstract interface to define a database. The idea is to have an easy way
 * to add new database types so you can store your data anyway you want
 *
 * @author Lucas Hereld <duckman@piratehook.com>
 */
public interface Database
{
    /**
     * If your database needs any kind of setup, this is where you want to do it
     * instead of the constructor. This is done because of the way the database
     * is instantiated.
     */
    public void setup();

    /**
     * Retrieves the password for a user
     *
     * @param username username to look up
     * @return returns the user's password
     */
    public String getPassword(String username);
    
    /**
     * Retrieves a list of bots belonging to a particular account
     *
     * @param username username to look up
     * @return returns the bots that belong to the user
     */
    public ArrayList<String> getBotList(String username);

    /**
     * Loads the current Map from the database
     * @return an ArrayList of things present on the map
     */
    public ArrayList<MapRow> loadMap();

    /**
     * Loads the specified bot from the database and returns it
     *
     * @param master teh Master
     * @param socket the ClientSocket that the bot belongs to
     * @param name the Bot to load
     * @return The Bot ready to go
     */
    public String loadBot(String name);
}
