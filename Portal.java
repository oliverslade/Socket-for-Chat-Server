package ICA2.Phase2;

import java.util.HashMap;

/**
 * This class is a blocking queue that passes messages between agents stored on
 * its hash map.
 *
 * @author Jack Turton, Raheel Rabbani, Michael Lee and Oliver Slade.
 */
public class Portal extends MetaAgent
{

    /**
     * This hash map which has the ability to store agents with the key being
     * the name of the agent added.
     */
    private HashMap<String, MetaAgent> routingTable = new HashMap<>();
    /**
     * A local instantiation of a socketAgent.
     */
    private SocketAgent socketAgent;

    /**
     * Calls the meta agent's constructor, providing it with a name. Sets the
     * local SocketAgent to the agent passed as a parameter. Sets the portal in
     * the SocketAgent to this portal.
     *
     * @param name
     * @param socketAgent
     */
    public Portal(String name, SocketAgent socketAgent)
    {
        super(name);
        this.socketAgent = socketAgent;
        this.socketAgent.setPortal(this);
    }

    /**
     * is the overwritten runnable method for a portal, which calls the
     * recipientOf method and offers the message to the recipient.
     *
     * @param msg is the message object.
     */
    @Override
    public void msgHandler(Message msg)
    {
        // routingTable.g
        recipientOf(msg).offer(msg);
    }

    /**
     * This method adds an agent to the hash map.
     *
     * @param name is the name being passed in.
     * @param agent is the specific agent to be added.
     */
    public void addAgent(String name, MetaAgent agent)
    {
        routingTable.put(name, agent);
    }

    /**
     * Checks if the message recipient isn't on the routingTable. If it isn't on
     * the routingTable, sends the message over the socket. Otherwise, the
     * message is sent to the recipient found on the routingTable.
     *
     * @param msg is the message object being passed in.
     * @return the recipients agent.
     */
    public MetaAgent recipientOf(Message msg)
    {
        if (routingTable.get(msg.getRecipient()) == null)
        {
            System.out.println("Forwarding message: " + msg + " over socket");
            return socketAgent;
        }
        System.out.println("Forwarding message: " + msg + " locally");
        return routingTable.get(msg.getRecipient());
    }

    /**
     * Gets the name of an portal agent.
     *
     * @return the outcome of the get name method.
     */
    @Override
    public String toString()
    {
        return getName();
    }

    /**
     * Returns true if user is on the routingTable, else returns false.
     *
     * @param user the user that is to be checked.
     * @return true or false.
     */
    public boolean isKnownUser(String user)
    {
        return routingTable.get(user) != null;
    }
}
