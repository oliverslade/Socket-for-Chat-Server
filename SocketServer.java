package ICA2.Phase2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Local server application that accepts two clients and sends objects from
 * client 1 to client 2 and vice versa.
 *
 * @author Jack Turton, Raheel Rabbani, Michael Lee and Oliver Slade.
 */
public class SocketServer
{

    /**
     * Defines a new ServerSocket object.
     */
    private ServerSocket serverSocket = null;
    /**
     * Defines a socket for client1 to communicate over.
     */
    private Socket client1 = null;
    /**
     * Defines a socket for client2 to communicate over.
     */
    private Socket client2 = null;

    /**
     * Waits for two clients to connect to the server and then creates a new
     * thread for both clients that continuously processes serialized messages
     * back and forth between the two clients.
     */
    public SocketServer()
    {

        try
        {
            // Creates a server socket and gives it an arbitary port number.
            serverSocket = new ServerSocket(1337);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        //Server waits for two connections to be accepted and wont progress until
        //two clients are connected.
        while (true)
        {
            try
            {

                if (client1 == null)
                {
                    client1 = serverSocket.accept();
                    System.out.println("Client 1 connected to server");
                }
                if (client2 == null)
                {
                    client2 = serverSocket.accept();
                    System.out.println("Client 2 connected to server");
                }
                if (client1 != null && client2 != null)
                {
                    break;
                }
            } catch (IOException e)
            {
                System.out.println("I/O error: " + e);
            }
        }
        System.out.println("Both clients connected");
        //Creates a new thread that handles client 1's outgoing messages and 
        //client 2's incoming messages.
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {

                try
                {
                    while (true)
                    {
                        ObjectInputStream ois = new ObjectInputStream(client1.getInputStream());
                        ObjectOutputStream oos = new ObjectOutputStream(client2.getOutputStream());
                        oos.writeObject(ois.readObject());
                        System.out.println("Wrote message from client1 to client2 ");
                    }
                } catch (IOException e)
                {
                    System.out.println("Couldn't write from client 1 to client 2");
                } catch (ClassNotFoundException e)
                {
                    System.out.println("Some derp seinding weird classes..");
                    e.printStackTrace();
                }
            }
        }).start();
        //Creates a new thread that handles client 2's outgoing messages and 
        //client 1's incoming messages.
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    while (true)
                    {
                        ObjectInputStream ois = new ObjectInputStream(client2.getInputStream());
                        ObjectOutputStream oos = new ObjectOutputStream(client1.getOutputStream());
                        oos.writeObject(ois.readObject());
                        System.out.println("Wrote message from client2 to client1 ");
                    }
                } catch (IOException e)
                {
                    System.out.println("Couldn't write from client 2 to client 1");
                } catch (ClassNotFoundException e)
                {
                    System.out.println("Some derp seinding weird classes..");
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * Creates a new SocketServer.
     *
     * @param args
     */
    public static void main(String[] args)
    {
        new SocketServer();
    }
}
