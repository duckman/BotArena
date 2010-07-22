/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package botarena;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author lucashereld
 */
public class Bot extends Thing
{
    private BotArena master = null;
    private ClientSocket socket = null;
    private String name = null;
    private Point position = null;
    private int radius = 5;

    Bot(BotArena master,ClientSocket socket,String name,int x,int y)
    {
        this.master = master;
        this.socket = socket;
        this.name = name;
        position = new Point(x,y);
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public Type getType()
    {
        return Type.BOT;
    }

    @Override
    public Point getPosition()
    {
        return position;
    }

    @Override
    public void setPosition(Point position)
    {
        this.position = position;
    }

    @Override
    protected void step(Packet pkt)
    {
        Map map = master.getMap();

        switch(pkt.getCommand())
        {
            // 0 - direction
            case MOVE:
                switch(Enum.valueOf(Direction.class, pkt.getParameter().get(0)))
                {
                    case UP:
                        map.move(this, position.x, position.y+1);
                        break;
                    case DOWN:
                        map.move(this, position.x, position.y-1);
                        break;
                    case LEFT:
                        map.move(this, position.x-1, position.y);
                        break;
                    case RIGHT:
                        map.move(this, position.x+1, position.y);
                        break;
                }
                break;
            // 0 - spell
            // 1 - direction
            case CAST:
                break;
        }

        ArrayList<Thing> things = map.getThings(position.x, position.y, radius);
        ArrayList<String> perams = new ArrayList<String>();

        for(int x=0;x<things.size();x++)
        {
            perams.add(things.get(x).toString());
        }

        socket.send(Command.VIEW, perams);
    }

    @Override
    public void stop()
    {
        socket.stop();
    }
}
