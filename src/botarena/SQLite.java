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
 * A SQLite implementation of the Database
 * 
 * @author jtwb
 */
public class SQLite extends SQLDatabase
{
    Connection conn = null;
    
    /**
     * Sets it up
     *
     * @param host Not used
     * @param database The database to be used
     * @param username Not used
     * @param password Not used
     * @return Returns the Connection made
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Override
    protected Connection getConnection(String host, String database, String username,
                    String password) throws InstantiationException,
                    IllegalAccessException, ClassNotFoundException, SQLException {
            String curDir = System.getProperty("user.dir");
            System.out.println("Current working dir = " + curDir);
            Class.forName("org.sqlite.JDBC").newInstance();
            return DriverManager.getConnection("jdbc:sqlite:resource/"+database+".db");
    }

}
