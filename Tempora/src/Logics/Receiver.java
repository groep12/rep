package Logics;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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

    private Thread blinker;
    private final ServerSocket server;
    private final ExecutorService threadPool;
    private final Processor processor;

    private Receiver() throws IOException, SQLException
    {
        server = new ServerSocket(Settings.PORT);
        threadPool = Executors.newFixedThreadPool(Settings.MAX_CONNECTIONS); 
        processor = Processor.startProcessing();
    }

    /**
     *
     * @return
     */
    public static Receiver startReceiving() throws IOException, SQLException
    {
        Receiver receiver = new Receiver();
        receiver.start();
        return receiver;
    }

    private void start()
    {
        blinker = new Thread(this);
        blinker.setPriority(8);
        blinker.start();
    }

    public void stop()
    {
        blinker = null;
    }

    @Override
    public void run()
    {
        Thread thisThread = Thread.currentThread();
        try
        {
            for (;;)
            {

                threadPool.execute(new OpenSocket(server.accept()));                
                
            }
        }
        catch (IOException ex)
        {
            threadPool.shutdown();
        }
        
    }
    
 }
