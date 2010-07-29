/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package botarena;

import botarena.util.Database;
import botarena.util.Debug;
import botarena.util.Thing;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author lucashereld
 */
public class MySQL implements Database
{
    Connection conn = null;
    
    MySQL(String host,String database,String username,String password)
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://"+host+"/"+database+"?user="+username+"&password="+password);
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
    
    private ResultSet execute(String query)
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
}
