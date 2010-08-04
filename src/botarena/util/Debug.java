/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package botarena.util;

/**
 * A helper class to handle printing exceptions
 *
 * @author Lucas Hereld <duckman@piratehook.com>
 */
public class Debug
{
    /**
     * Prints exceptions nicely
     *
     * @param ex The exception to print
     */
    public static void printex(Exception ex)
    {
        if(true)
        {
            System.out.println(ex.toString());
            ex.printStackTrace(System.out);
        }
    }
}
