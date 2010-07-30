/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package botarena.util;

/**
 *
 * @author Lucas Hereld <duckman@piratehook.com>
 */
public class Debug
{
    public static void printex(Exception ex)
    {
        if(true)
        {
            System.out.println(ex.toString());
            ex.printStackTrace(System.out);
        }
    }
}
