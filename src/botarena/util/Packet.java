/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package botarena.util;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A class the represents a Packet to be sent to and from the clients
 *
 * @author Lucas Hereld <duckman@piratehook.com>
 */
public class Packet implements Serializable
{
    static final long serialVersionUID = 1;
    private Command cmd = null;
    private ArrayList<String> param = null;
    
    /**
     * Constructs a Packet with a Command, and a parameter list
     *
     * @param cmd the Command
     * @param param a list of parameters
     */
    public Packet(Command cmd,ArrayList<String> param)
    {
        this.cmd = cmd;
        this.param = param;
    }
    
    /**
     * Getter for the Command
     *
     * @return the Command
     */
    public Command getCommand()
    {
        return cmd;
    }
    
    /**
     * Getter for the parameter list
     * @return An ArrayList of parameters
     */
    public ArrayList<String> getParameter()
    {
        return param;
    }
}
