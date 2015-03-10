package ICA2.Phase2;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * The MetaAgent class provides functionality for each of the agents in this
 * program.
 *
 * @author Jack Turton, Raheel Rabbani, Michael Lee and Oliver Slade.
 */
public abstract class MetaAgent extends LinkedBlockingQueue<Message>
{

    /**
     * Name allows every agent to have a name.
     */
    private String name;

    /**
     * This is a local reference for a portal.
     */
    private Portal portal;

    /**
     * All agents take name and set themselves to that name using this
     * constructor. This also starts the runnable thread "td" which allows every
     * agent to run its own overwritten version of msgHandler().
     *
     * @param name the name of the agent.
     */
    public MetaAgent(String name)
    {
        this.name = name;
        Thread td = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while (true)
                {
                    try
                    {
                        msgHandler(take());
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                        System.out.println("Probably not possible to get this error.");
                    }
                }
            }
        });
        td.start();
    }

    /**
     * This method is overwritten by each agent and takes a message as a
     * parameter.
     *
     * @param msg is the message passed in by the agent taken off its blocking
     * queue.
     */
    public abstract void msgHandler(Message msg);

    /**
     * This method takes recipient and msg and then calls msgHandler only after
     * its called wrap on the variables.
     *
     * @param recipient this is the recipient specified in the test harness GUI
     * recipient field.
     * @param msg this is the message the user sends in the test harness GUI
     * message field.
     */
    public void sendMessage(String recipient, String msg)
    {
        portal.msgHandler(wrap(name, recipient, msg));
    }

    /**
     * It takes the sender, recipient and message then passes them and creates a
     * new message object with these parameters.
     *
     * @param name is the name of the sender.
     * @param reci is the name of the recipient.
     * @param msg is the body of the message to be sent.
     * @return a Message object.
     */
    public Message wrap(String name, String reci, String msg)
    {
        return new Message(name, reci, msg);
    }

    /**
     * Sets this instance of a portal to the portal passed as a parameter. Then
     * adds an agent to the portals hash map.
     *
     * @param portal is the portal that needs to be set.
     */
    public void setPortal(Portal portal)
    {
        this.portal = portal;
        portal.addAgent(this.getName(), this);
        System.out.println("Added portal '" + portal + "' to agent: " + getName());
    }

    /**
     * Is a getter that returns the name of a specific agent.
     *
     * @return the name of an agent.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Is a getter that returns a Portal.
     *
     * @return a Portal.
     */
    public Portal getPortal()
    {
        return portal;
    }
}
