/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package botarena.util;

/**
 *
 * @author lucashereld
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
