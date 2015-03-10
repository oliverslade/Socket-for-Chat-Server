package ICA2.Phase2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * An extension of meta agent that passes Messages over a socket.
 *
 * @author Jack Turton, Raheel Rabbani, Michael Lee and Oliver Slade.
 */
public class SocketAgent extends MetaAgent
{

    /**
     * Defines a new socket to be communicated over.
     */
    private Socket socket;

    /**
     * Calls the MetaAgent's constructor, providing it with a name. Tries to
     * instantiate the socket with a given IP address and port number. Then
     * calls makeReadLoop method.
     *
     * @param name
     */
    public SocketAgent(String name)
    {
        super(name);
        try
        {
            socket = new Socket("127.0.0.1", 1337);
        } catch (IOException e)
        {
            System.out.println("Server probably not running");
            e.printStackTrace();
        }
        makeReadLoop();
    }

    /**
     * Creates a new thread that reads the input from the connected socket,
     * putting it into a message object. Message object is then checked to see
     * whether message recipient is on the Portal's routingTable. If the
     * recipient is not located on the routingTable, the message is discarded.
     */
    private void makeReadLoop()
    {
        Thread t = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while (socket.isConnected())
                {
                    try
                    {
                        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                        Message m2 = (Message) ois.readObject();

                        if (getPortal().isKnownUser(m2.getRecipient()))
                        {
                            getPortal().msgHandler(m2);
                        }
                    } catch (IOException e)
                    {
                        System.out.println("Couldn't read the socket stream");
                        e.printStackTrace();
                    } catch (ClassNotFoundException e)
                    {
                        System.out.println("Some derp sending weird objects");
                        e.printStackTrace();
                    }
                }

            }
        });
        t.start();
    }

    /**
     * Creates a new object output stream and writes the message passed as a
     * parameter to the output stream.
     *
     * @param msg The message object to be sent.
     */
    @Override
    public void msgHandler(Message msg)
    {
        try
        {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Socket agent sending out message " + msg);
            oos.writeObject(msg);
        } catch (IOException e)
        {
            System.out.println("Can't write to server (probably down?)");
            e.printStackTrace();
        }
    }
}
