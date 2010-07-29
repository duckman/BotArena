/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package botarena;

import botarena.util.Packet;
import botarena.util.Command;
import botarena.util.Direction;
import botarena.util.Type;
import botarena.util.Thing;
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
    private int hp = 100;

    public Bot(BotArena master,ClientSocket socket,String name,int x,int y)
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
        switch(pkt.getCommand())
        {
            // 0 - direction
            case MOVE:
                switch(Enum.valueOf(Direction.class, pkt.getParameter().get(0)))
                {
                    case UP:
                        master.moveThing(this, position.x, position.y+1);
                        break;
                    case DOWN:
                        master.moveThing(this, position.x, position.y-1);
                        break;
                    case LEFT:
                        master.moveThing(this, position.x-1, position.y);
                        break;
                    case RIGHT:
                        master.moveThing(this, position.x+1, position.y);
                        break;
                }
                break;
            // 0 - spell
            // 1 - direction
            case CAST:
                break;
        }

        ArrayList<Thing> things = master.getMap().getThings(position.x, position.y, radius);
        ArrayList<String> perams = new ArrayList<String>();

        for(int x=0;x<things.size();x++)
        {
            perams.add(things.get(x).toString());
        }

        socket.send(Command.VIEW, perams);
    }

    @Override
    public boolean collideWith(Thing thing)
    {
        switch(thing.getType())
        {
            case BOT:
            case OBSTACLE:
            case WALL:
                return false;
            case PROJECTILE:
                hp -= thing.damage(0);
                if(hp <= 0)
                {
                    master.removeThing(this);
                    master.respawnThing(this);
                }
                break;
        }

        return true;
    }

    @Override
    public void stop()
    {
        socket.stop();
    }
}
