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
 *
 * @author Lucas Hereld <duckman@piratehook.com>
 */
public class MySQL extends SQLDatabase
{
    Connection conn = null;
    
    protected Connection getConnection(String host, String database, String username,
                    String password) throws InstantiationException,
                    IllegalAccessException, ClassNotFoundException, SQLException {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            return DriverManager.getConnection("jdbc:mysql://"+host+"/"+database+"?user="+username+"&password="+password);
    }
    
}
