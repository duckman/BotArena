/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package botarena;

import botarena.util.SQLDatabase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A MySQL representation of the Database
 *
 * @author Lucas Hereld <duckman@piratehook.com>
 */
public class MySQL extends SQLDatabase
{
    Connection conn = null;
    
    /**
     * Sets up the connection to the MySQL server
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
    @Override
    protected Connection getConnection(String host, String database, String username,
                    String password) throws InstantiationException,
                    IllegalAccessException, ClassNotFoundException, SQLException
    {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            return DriverManager.getConnection("jdbc:mysql://"+host+"/"+database+"?user="+username+"&password="+password);
    }
    
}
