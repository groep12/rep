package Logics;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;


/**
 * A class that has and holds a connection open with the UNWDMI Generator
 *
 * @author Groep 12
 */
class OpenSocket implements Runnable
{

    private final Socket socket;
    public static final Queue<Measurement> collection = new LinkedList<>();
    private final XmlReader xmlReader;
    
    
    

    public OpenSocket(Socket _client)
    {
        socket = _client;
        xmlReader = new XmlReader();
        
    }
  


    @Override
    public void run()
    {
        String newLine = System.getProperty("line.separator");
        StringBuilder sb;
        String line;
        
        try
        {
            while (true)
            {
                BufferedReader reader;
                reader = getBufferedReader();
                sb = new StringBuilder();
              
                boolean isNotFirst = false;
                while ((line = reader.readLine()) != null)
                {
                    
                    if (line.contains("<?xml version=\"1.0\"?>") && isNotFirst)
                    {
                        collection.addAll(xmlReader.parse(sb));
                        sb = new StringBuilder();
                        
                    }
                    isNotFirst = true;
                    sb.append(line);
                    sb.append(newLine);
                }
            }
            
        }
        catch (IOException ex)
        {
            
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

   

}