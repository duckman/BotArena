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
 *
 * @author Lucas Hereld <duckman@piratehook.com>
 */
public class Config
{
    private static boolean loaded = false;
    private static HashMap<String, String> conf;

    public static void load()
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
            conf.put("dbEngine", "SQLite");
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

    public static String getConfig(String key)
    {
        isLoaded();

        return conf.get(key);
    }
}
