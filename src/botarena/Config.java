/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package botarena;

import botarena.util.Debug;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * A class representing the server's configuration
 *
 * @author Lucas Hereld <duckman@piratehook.com>
 */
public class Config
{
    private static boolean loaded = false;
    private static HashMap<String, String> conf;

    /**
     * Reads config.conf or loads the defaults into the class
     */
    private static void load()
    {
        conf = new HashMap<String, String>();

        try
        {
            BufferedReader file = new BufferedReader(new FileReader("config.conf"));

            String line = null;
            while((line = file.readLine()) != null)
            {
                if(!line.startsWith("//") || !line.isEmpty())
                {
                    String[] args = line.split("=");
                    for(int x=0;x<args.length;x++)
                    {
                        args[x].trim();
                    }
                    conf.put(args[0], args[1]);
                }
            }
        }
        catch(FileNotFoundException ex)
        {
            // set defaults
            conf.put("dbEngine", "botarena.MySQL");
            conf.put("dbHost", "localhost");
            conf.put("dbUser", "botarena");
            conf.put("dbPass", "botarena");
            conf.put("dbDb", "botarena");
        }
        catch(IOException ex)
        {
            Debug.printex(ex);
        }
    }

    private static void isLoaded()
    {
        if(!loaded)
        {
            load();
        }
    }

    /**
     * Retrieves a configuration from the list
     *
     * @param key The variable your looking for
     * @return The configuration your looking for
     */
    public static String getConfig(String key)
    {
        isLoaded();
        
        return conf.get(key);
    }
}
