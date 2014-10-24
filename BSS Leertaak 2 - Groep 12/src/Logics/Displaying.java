/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logics;

import Application.Main;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;


/**
 *
 * @author Hanze
 */
public class Displaying implements Runnable
{

    private LinkedList<Station> stations;

    private static Connection connection;
    private Thread blinker;

    private Displaying()
    {
        //DatabaseConnection db = DatabaseConnection.create();
        //connection = db.openConnection();
    }

    /**
     *
     * @return
     */
    public static Displaying startDisplaying()
    {
        Displaying displayData = new Displaying();
        displayData.start();
        return displayData;
    }

    /**
     *
     * @return
     */
    public long getMeasurementCount()
    {
       

        return MongoDB.getMeasurements().count();
    }

    /**
     *
     * @return
     */
    public long getStationCount()
    {
   

        return MongoDB.getStations().count();
    }

    /**
     *
     * @param stationId
     * @return
     */
    public float getAverageTemps(int stationId)
    {
        int total = 0;

        
        BasicDBObject search = new BasicDBObject();
        BasicDBObject select = new BasicDBObject();


        search.put("STN", stationId);
        

        select.put("temp", 1);

        DBCursor cursor = MongoDB.getMeasurements().find(search, select);
        Iterator iterator = cursor.iterator();

        if (iterator.hasNext())
        {
            for (int i = 0; i < cursor.length(); i++)
            {
                total += Integer.parseInt(iterator.next().toString());
            }

            return total / cursor.length();
        }

        return -999;
    }

    

    
   

    private void start()
    {
        blinker = new Thread(this);
        blinker.setPriority(10);
        blinker.start();
    }

    private void stop()
    {
        blinker = null;
    }

    @Override
    public void run()
    {
        Thread thisThread = Thread.currentThread();
        while (blinker == thisThread)
        {

        }
    }

}
