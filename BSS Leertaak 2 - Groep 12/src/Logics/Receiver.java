package Logics;

import Application.Main;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class that receives all the connections from the UNWDMI Generator
 *
 *
 * @author Groep 12
 */
public class Receiver implements Runnable
{

    private static Thread blinker;
    private static LinkedList<OpenSocket> sockets;
    private ServerSocket server;

    private Receiver()
    {
        sockets = new LinkedList<>();
    }

    /**
     *
     * @return
     */
    public static Receiver startReceiving()
    {
        Receiver receiver = new Receiver();
        Main.setThreadCount(sockets.size());
        receiver.start();
        return receiver;
    }

    private void start()
    {
        blinker = new Thread(this);
        blinker.setPriority(10);
        blinker.start();
    }

    /**
     *
     */
    public void stop()
    {
        try
        {
            int count = sockets.size();
            for (OpenSocket socket : sockets)
            {
                socket.stop(); 
                count--;
                Main.setThreadCount(count--);
            }            
            server.close();
            Main.addLine("Stopped receiving on "
                    + "localhost:" + Settings.PORT);
        }
        catch (IOException ex)
        {
            Main.addLine(ex);
        }
        
        blinker = null;
    }

    @Override
    public void run()
    {
        Thread thisThread = Thread.currentThread();
        try
        {
            while (blinker == thisThread)
            {
                if (server == null)
                {
                    server = new ServerSocket(Settings.PORT);
                    Main.addLine("Started receiving on "
                            + "localhost:" + Settings.PORT);
                }
                while (sockets.size() <= Settings.MAX_CONNECTIONS && blinker == thisThread)
                {
                    if (!server.isClosed())
                    {
                        Socket client = server.accept();
                        OpenSocket os = OpenSocket.makeConnection(client);
                        sockets.add(os);
                        Main.setThreadCount(sockets.size());
                    }
                }
            }
        }
        catch (SocketException ex)
        {
        }
        catch (IOException ex)
        {
            Main.addLine(ex);
        }

    }
}
