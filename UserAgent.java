package ICA2.Phase2;

/**
 * User Agent is a wrapper for the Meta Agent class.
 *
 * @author Jack Turton, Raheel Rabbani, Michael Lee and Oliver Slade.
 */
public class UserAgent extends MetaAgent
{

    /**
     * This instantiates the MessageListener interface.
     */
    private MessageListener listener;

    /**
     * Calls the MetaAgent constructor and passes the name to the super class
     * method.
     *
     * @param name is the name of the user agent.
     */
    public UserAgent(String name)
    {
        super(name);
        System.out.println("Created user: " + name);
    }

    /**
     * is the overwritten runnable method for a UserAgent that calls the
     * messageReceived method of the interface and passes it the message
     * provided as a parameter.
     *
     * @param msg is the message object.
     */
    @Override
    public void msgHandler(Message msg)
    {
        //If the listener has not been added to an agent by mistake, this stops 
        //from throwing a NullPointerException.
        if (listener != null)
        {
            listener.messageReceived(msg);
        }
        System.out.println("user: " + getName() + " received message: " + msg.getMessage());
    }

    /**
     * This is a setter for the MessageListener.
     *
     * @param listener sets listener to this listener.
     */
    public void setMessageListener(MessageListener listener)
    {
        this.listener = listener;
    }
}
