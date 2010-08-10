/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package botarena.util;

import botarena.Bot;
import botarena.BotArena;
import botarena.ClientSocket;
import botarena.Config;
import java.awt.Point;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * This class represents an SQL database. The idea is that this class holds all
 * the SQL statements so that the same code doesn't have to be written over and
 * over.
 *
 * @author Lucas Hereld <duckman@piratehook.com>
 */
public abstract class SQLDatabase implements Database
{
    Connection conn = null;
    
    /**
     * Sets up the connection to the DB server based on whats in Config
     */
    @Override
    public void setup()
    {
        try
        {
            conn = getConnection(Config.getConfig("dbHost"),
                    Config.getConfig("dbDb"),
                    Config.getConfig("dbUser"),
                    Config.getConfig("dbPass"));
        }
        catch(Exception ex)
        {
            Debug.printex(ex);
            Runtime.getRuntime().exit(0);
        }
    }

    /**
     * Function will be overwritten to provide a connection to the database. This
     * is only used by the setup function
     *
     * @param host The database host
     * @param database The database to be used
     * @param username The username to connect to the database
     * @param password The password to connect to the database
     * @return Returns the Connection made
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    protected abstract Connection getConnection(String host, String database, String username,
                    String password) throws InstantiationException,
                    IllegalAccessException, ClassNotFoundException, SQLException;
    
    /**
     * Execute a SQL statement and return a ResultSet
     *
     * @param query The SQL query to execute
     * @return A ResultSet of what was returned
     */
    protected ResultSet execute(String query)
    {
        Statement stmt = null;
        
        try
        {
            stmt = conn.createStatement();

            if(stmt.execute(query))
            {
                return stmt.getResultSet();
            }
            else
            {
                return null;
            }
        }
        catch(SQLException ex)
        {
            Debug.printex(ex);
        }
        
        return null;
    }
    
    /**
     * Getter function to retrieve a users password from the database
     *
     * @param username The username of the user to lookup
     * @return A String containing the password
     */
    @Override
    public String getPassword(String username)
    {
        ResultSet rs = null;
        
        rs = execute("SELECT password FROM user WHERE username='"+username+"'");
        
        try
        {
            if(rs.next())
            {
                return rs.getString("password");
            }
            else
            {
                return null;
            }
        }
        catch(SQLException ex)
        {
            Debug.printex(ex);
        }
        
        return null;
    }
    
    /**
     * A getter function to retrieve a list of Bots belonging to a user
     * @param username The username of the user to lookup
     * @return An ArrayList of Bot names
     */
    @Override
    public ArrayList<String> getBotList(String username)
    {
        ArrayList<String> bots = new ArrayList<String>();
        ResultSet rs = null;
        
        rs = execute("SELECT name FROM bot WHERE uid=(SELECT uid FROM user WHERE username='"+username+"')");
        
        try
        {
            if(rs.next())
            {
                do
                {
                    bots.add(rs.getString("name"));
                }
                while(rs.next());
            }
        }
        catch(SQLException ex)
        {
            Debug.printex(ex);
        }
        
        return bots;
    }

    /**
     * Loads a Bot and returns it
     *
     * @param master The master BotArena
     * @param socket The ClientSocket associated with this Bot
     * @param name The name of the Bot
     * @return A instantiated Bot ready to do battle
     */
    @Override
    public Bot loadBot(BotArena master,ClientSocket socket,String name)
    {
        Bot bot = null;
        ResultSet rs = null;

        rs = execute("SELECT * FROM bot WHERE name='"+name+"'");

        try
        {
            if(rs.next())
            {
                do
                {
                    bot = new Bot(master,socket,name);
                }
                while(rs.next());
            }
        }
        catch(SQLException ex)
        {
            Debug.printex(ex);
        }

        return bot;
    }

    /**
     * A function to load the saved Map from the database.
     *
     * @return An ArrayList of Things that are on the Map
     */
    @Override
    public ArrayList<MapRow> loadMap()
    {
        ArrayList<MapRow> things = new ArrayList<MapRow>();
        ResultSet rs = null;

        execute("DELETE FROM map WHERE type = 'BOT' OR type = 'PROJECTILE'");
        rs = execute("SELECT * FROM map");

        try
        {
            if(rs.next())
            {
                do
                {
                    Thing thing = null;
                    switch(Type.valueOf(rs.getString("type")))
                    {
                        case OBSTACLE:
                            //thing = new Obstacle();
                        case NPC:
                            //thing = new NPC();
                    }
                }
                while(rs.next());
            }
        }
        catch(SQLException ex)
        {
            Debug.printex(ex);
        }

        return things;
    }
    
    /**
     * A function to build the database up if one hasn't been created
     */
    public void createTables()
    {
    	String query =
            "CREATE TABLE `bot` ("+
            "  `bid` int(11) NOT NULL AUTO_INCREMENT,"+
            "  `uid` int(11) NOT NULL,"+
            "  `name` varchar(64) NOT NULL,"+
            "  `level` int(11) NOT NULL,"+
            "  `weapon1` int(11) NOT NULL,"+
            "  `weapon2` int(11) NOT NULL,"+
            "  `armor_front` int(11) NOT NULL,"+
            "  `armor_back` int(11) NOT NULL,"+
            "  `armor_left` int(11) NOT NULL,"+
            "  `armor_right` int(11) NOT NULL,"+
            "  `engine` int(11) NOT NULL,"+
            "  PRIMARY KEY (`bid`),"+
            "  KEY `uid` (`uid`)"+
            ") ENGINE=MyISAM DEFAULT CHARSET=latin1 ;"+

            "CREATE TABLE `item` ("+
            "  `iid` int(11) NOT NULL AUTO_INCREMENT,"+
            "  `name` varchar(64) NOT NULL,"+
            "  `type` enum('WEAPON','ARMOR','ENGINE') NOT NULL,"+
            "  PRIMARY KEY (`iid`)"+
            ") ENGINE=MyISAM DEFAULT CHARSET=latin1 ;"+

            "CREATE TABLE `map` ("+
            "  `guid` int(11) NOT NULL,"+
            "  `type` enum('OBSTACLE','NPC') NOT NULL,"+
            "  `id` int(11) NOT NULL,"+
            "  `x` int(11) NOT NULL,"+
            "  `y` int(11) NOT NULL,"+
            "  PRIMARY KEY (`guid`)"+
            ") ENGINE=MyISAM DEFAULT CHARSET=latin1;"+

            "CREATE TABLE `npc` ("+
            "  `nid` int(11) NOT NULL AUTO_INCREMENT,"+
            "  `name` varchar(64) NOT NULL,"+
            "  `level` int(11) NOT NULL,"+
            "  `weapon1` int(11) NOT NULL,"+
            "  `weapon2` int(11) NOT NULL,"+
            "  `armor_front` int(11) NOT NULL,"+
            "  `armor_back` int(11) NOT NULL,"+
            "  `armor_left` int(11) NOT NULL,"+
            "  `armor_right` int(11) NOT NULL,"+
            "  `engine` int(11) NOT NULL,"+
            "  PRIMARY KEY (`nid`)"+
            ") ENGINE=MyISAM DEFAULT CHARSET=latin1 ;"+

            "CREATE TABLE `obstacle` ("+
            "  `oid` int(11) NOT NULL AUTO_INCREMENT,"+
            "  `name` varchar(64) NOT NULL,"+
            "  PRIMARY KEY (`oid`)"+
            ") ENGINE=MyISAM DEFAULT CHARSET=latin1 ;"+

            "CREATE TABLE `projectile` ("+
            "  `pid` int(11) NOT NULL AUTO_INCREMENT,"+
            "  `name` varchar(64) NOT NULL,"+
            "  `damage` int(11) NOT NULL,"+
            "  `range` int(11) NOT NULL,"+
            "  PRIMARY KEY (`pid`)"+
            ") ENGINE=MyISAM DEFAULT CHARSET=latin1 ;"+

            "CREATE TABLE `user` ("+
            "  `uid` int(11) NOT NULL AUTO_INCREMENT,"+
            "  `username` varchar(64) NOT NULL,"+
            "  `password` varchar(64) NOT NULL,"+
            "  PRIMARY KEY (`uid`)"+
            ") ENGINE=MyISAM  DEFAULT CHARSET=latin1 ;";

        execute(query);
    }
}
