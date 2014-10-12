package Logics;

import Application.Main;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;


/**
 * A class that has and holds a connection open with the UNWDMI Generator
 *
 * @author Groep 12
 */
class OpenSocket implements Runnable
{

    private final Socket socket;
    private Thread blinker;
    private long currentThreadId;
    
    private final XmlReader xmlReader;
    
    private long startTime;
    private long endTime;

    //private final XmlReader xmlReader;
    //private final Inserter inserter;
    private OpenSocket(Socket _client)
    {
        xmlReader = new XmlReader();
        socket = _client;
    }

    public static OpenSocket makeConnection(Socket client)
    {
        OpenSocket openSocket = new OpenSocket(client);
        openSocket.start();
        return openSocket;
    }

    private void start()
    {
        blinker = new Thread(this);        
        currentThreadId = blinker.getId();
        Main.addLine("Thread #" + currentThreadId + " started!");
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
        String newLine = System.getProperty("line.separator");
        StringBuilder sb;
        String line;
        MeasurementCollection collection;
        try
        {
            while (blinker == thisThread)
            {
                BufferedReader reader;
                reader = getBufferedReader();
                sb = new StringBuilder();
              
                boolean isNotFirst = false;
                while ((line = reader.readLine()) != null && blinker == thisThread)
                {
                    timerStart();
                    if (line.contains("<?xml version=\"1.0\"?>") && isNotFirst)
                    {
                        collection = xmlReader.parse(sb);
                        MongoDB.addMeasurments(collection);
                        timerStop();
                        sb = new StringBuilder();
                        
                    }
                    isNotFirst = true;
                    sb.append(line);
                    sb.append(newLine);
                }
            }
            threadStopped();
        }
        catch (IOException ex)
        {
            Main.addLine(ex);
        }
    }

    private BufferedReader getBufferedReader() throws IOException
    {
        InputStream input;
        InputStreamReader streamReader;
        input = socket.getInputStream();
        streamReader = new InputStreamReader(input);
        return new BufferedReader(streamReader);
    }

    private void threadStopped() throws IOException
    {
        socket.close();
        Main.addLine("Thread #" + currentThreadId + " stopped");
    }

    private void timerStart()
    {
        startTime = System.currentTimeMillis();
    }

    private void timerStop()
    {
        endTime = System.currentTimeMillis() - startTime;
        Main.addLine("Thread #" + currentThreadId
                + " inserted batch in " + endTime + "ms");
    }
}
