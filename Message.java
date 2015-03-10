package ICA2.Phase2;

import java.io.Serializable;

/**
 * This class is used for creating a message object with the inputs in the test
 * harness, it also allows functionality to get each part of a message
 * individually.
 *
 * @author Jack Turton, Raheel Rabbani, Michael Lee and Oliver Slade.
 */
public class Message implements Serializable
{

    /**
     * This is the sender of the message.
     */
    private String sender;

    /**
     * This is the recipient of the message.
     */
    private String recipient;

    /**
     * This is the body of the message.
     */
    private String msg;

    /**
     * Sets the the local instances to the parameters passed into the
     * constructor.
     *
     * @param sender is the name of the sender of the message.
     * @param recipient is the name of the recipient of the message.
     * @param msg is the body of the message to be sent.
     */
    public Message(String sender, String recipient, String msg)
    {
        this.sender = sender;
        this.recipient = recipient;
        this.msg = msg;
    }

    /**
     * Formats the message object into a single string.
     *
     * @return the message as a single string.
     */
    public String getMessage()
    {
        return sender + " says: " + msg + "*" + recipient;
    }

    /**
     * Getter for the sender.
     *
     * @return the sender as a string.
     */
    public String getSender()
    {
        return sender;
    }

    /**
     * Getter for the recipient.
     *
     * @return the recipient as a string.
     */
    public String getRecipient()
    {
        return recipient;
    }

    /**
     * Getter for the message body.
     *
     * @return the message body as a string.
     */
    public String getMsg()
    {
        return msg;
    }
}
