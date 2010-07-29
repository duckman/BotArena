/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package botarena.util;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author lucashereld
 */
public class Packet implements Serializable
{
    static final long serialVersionUID = 1;
    private Command cmd = null;
    private ArrayList<String> peram = null;
    
    public Packet(Command cmd,ArrayList<String> peram)
    {
        this.cmd = cmd;
        this.peram = peram;
    }
    
    public Command getCommand()
    {
        return cmd;
    }
    
    public ArrayList<String> getParameter()
    {
        return peram;
    }
}
