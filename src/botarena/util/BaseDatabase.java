/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package botarena.util;

import botarena.Bot;
import botarena.BotArena;
import botarena.ClientSocket;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Lucas Hereld <duckman@piratehook.com>
 */
public abstract class BaseDatabase implements Database
{
    Connection conn = null;
    
    public BaseDatabase(String host,String database,String username,String password)
    {
        try
        {
            conn = getConnection(host, database, username, password);
        }
        catch(SQLException ex)
        {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            Debug.printex(ex);
        }
        catch(Exception ex)
        {
            Debug.printex(ex);
        }
    }

	protected abstract Connection getConnection(String host, String database, String username,
			String password) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException;
    
    protected ResultSet execute(String query)
    {
        Statement stmt = null;
        
        try
        {
            stmt = conn.createStatement();
            return stmt.executeQuery(query);
        }
        catch(SQLException ex)
        {
            Debug.printex(ex);
        }
        
        return null;
    }
    
    @Override
    public String getPassword(String username)
    {
        ResultSet rs = null;
        
        rs = execute("SELECT password FROM user WHERE username='"+username+"'");
        
        try
        {
            if(rs.first())
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
    
    @Override
    public ArrayList<String> getBotList(String username)
    {
        ArrayList<String> bots = new ArrayList<String>();
        ResultSet rs = null;
        
        rs = execute("SELECT name FROM thing WHERE type='BOT' AND username='"+username+"'");
        
        try
        {
            if(rs.first())
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

    @Override
    public Bot loadBot(BotArena master,ClientSocket socket,String name)
    {
        Bot bot = null;
        ResultSet rs = null;

        rs = execute("SELECT * FROM thing WHERE type='BOT' AND name='"+name+"'");

        try
        {
            if(rs.first())
            {
                do
                {
                    bot = new Bot(master,socket,name,rs.getInt("x"),rs.getInt("y"));
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

    @Override
    public ArrayList<Thing> loadMap()
    {
        ArrayList<Thing> things = new ArrayList<Thing>();
        ResultSet rs = null;

        rs = execute("SELECT * FROM thing WHERE type!='BOT'");

//        try
//        {
//            if(rs.first())
//            {
//                String type = null;
//                do
//                {
//                    type = rs.getString("type");
//                    if(type.compareToIgnoreCase("WALL") == 0)
//                    {
//                        things.add(new Wall(rs.getString("username"),rs.getString("name"),rs.getInt("x"),rs.getInt("y")));
//                    }
//                }
//                while(rs.next());
//            }
//        }
//        catch(SQLException ex)
//        {
//            Debug.printex(ex);
//        }

        return things;
    }
    
    /*
     * We should call this if the Database hasn't been created.
     * 
     */
    public void createTables()
    {
    	String query =
    		"CREATE TABLE user (" +
    		"	username varchar(16) primary key," +
    		"	password varchar(16)," +
    		"	uid int" +
    		");" +
    		"CREATE TABLE thing (" +
    		"	tid int primary key," +
    		"	name varchar(16)," +
    		"	type varchar(16)," +
    		"	x int," +
    		"	y int" +
    		");" + 
    		"INSERT INTO user ( username, password, uid ) VALUES" +
    		"( 'test', 'test', 0 );" +
    		"INSERT INTO thing ( tid, name, type, x, y ) VALUES" +
    		"( 1000, 'Tree1', 'OBSTACLE', 3, 3 );" +
    		"INSERT INTO thing ( tid, name, type, x, y ) VALUES" +
    		"( 1001, 'Wall1', 'WALL', 0, 2 );" +
    		"INSERT INTO thing ( tid, name, type, x, y ) VALUES" +
    		"( 1002, 'Wall2', 'WALL', 1, 2 );" +
    		"INSERT INTO thing ( tid, name, type, x, y ) VALUES" +
    		"( 1003, 'Wall3', 'WALL', 2, 2 );" +
    		"INSERT INTO thing ( tid, name, type, x, y ) VALUES" +
    		"( 1004, 'Wall4', 'WALL', 3, 2 );" +
    		"INSERT INTO thing ( tid, name, type, x, y ) VALUES" +
    		"( 1005, 'Wall5', 'WALL', 4, 2 );" +
    		"INSERT INTO thing ( tid, name, type, x, y ) VALUES" +
    		"( 1006, 'Wall6', 'WALL', 5, 2 );";
    	
		execute(query);
    }
}
