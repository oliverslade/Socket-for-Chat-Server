package ICA2.Phase2;

/**
 * Sets a listener for incoming messages.
 *
 * @author Jack Turton, Raheel Rabbani, Michael Lee and Oliver Slade.
 */
public interface MessageListener
{

    /**
     * Every time a message is received this method is called.
     *
     * @param m message object.
     */
    public void messageReceived(Message m);
}
