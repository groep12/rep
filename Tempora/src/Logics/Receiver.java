package Logics;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
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
    public static HashMap<Integer, String> countries;
    public static HashMap<Integer, Double> latitudes;
   // private final Processor processor;

    private Receiver() throws IOException, SQLException
    {
        
        getCountries();
                getLatitudes();

        
        server = new ServerSocket(Settings.PORT);
        threadPool = Executors.newFixedThreadPool(Settings.MAX_CONNECTIONS); 
        
                 
        
        Processor.startProcessing();
        
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
    
    
    /**
     * Gets a linked list with all the weather stations that currently have data order by station name
     * 
     * @return
     */
    private void getCountries()
    {
        
        try
        {
            countries = new HashMap<>();
            SqlDB sqlDB = new SqlDB();
            Connection connection = sqlDB.openConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT stn, country "
                    + "FROM stations ");

            while (result.next())
            {
                countries.put(result.getInt("stn"), result.getString("country"));
            }
        }
        catch (SQLException exception)
        {
            
        }
        
    }
    
    private void getLatitudes()
    {
        
        try
        {
            countries = new HashMap<>();
            SqlDB sqlDB = new SqlDB();
            Connection connection = sqlDB.openConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT stn, latitude "
                    + "FROM stations ");

            while (result.next())
            {
                countries.put(result.getInt("stn"), result.getString("latitude"));
            }
        }
        catch (SQLException exception)
        {
            
        }
        
    }
    
 }
