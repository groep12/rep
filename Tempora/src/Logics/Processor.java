/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logics;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Hanze
 */
public class Processor implements Runnable
{

    private Thread blinker;
    private final String firstPartQuery;

    private final Connection connection;

    private Processor()
    {
        SqlDB sqlDB = new SqlDB();
        connection = sqlDB.openConnection();
        firstPartQuery = "INSERT INTO "
                + "measurement"
                + "(STN,DATE,TIME,TEMP,DEWP,STP,SLP,VISIB,WDSP,PRCP,SNDP,FRSHTT,CLDC,WNDDIR) "
                + "VALUES";
    }

    /**
     *
     * @return
     */
    public static Processor startProcessing()
    {
        Processor processor = new Processor();
        processor.start();
        return processor;
    }

    private void start()
    {
        blinker = new Thread(this);
        blinker.setPriority(10);
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
            Statement statement = connection.createStatement();
            StringBuilder query = new StringBuilder();
            boolean first = true;
            int count = 0;
            int countBatch = 0;
            while (blinker == thisThread)
            {
                while (true)
                {
                    if (OpenSocket.collection.size() > 8000)
                    {
                        Measurement m = OpenSocket.collection.remove();
                        count++;
                        if (first)
                        {
                            query.append(firstPartQuery);
                            first = false;
                        }
                        else
                        {
                            query.append(", ");
                        }
                        query.append("('");
                        query.append(m.getStation());
                        query.append("','");
                        query.append(m.getDate());
                        query.append("','");
                        query.append(m.getTime());
                        query.append("','");
                        query.append(m.getTemperature());
                        query.append("','");
                        query.append(m.getDewPoint());
                        query.append("','");
                        query.append(m.getAirPressureStationLevel());
                        query.append("','");
                        query.append(m.getAirPressureSeaLevel());
                        query.append("','");
                        query.append(m.getVisibility());
                        query.append("','");
                        query.append(m.getWindSpeed());
                        query.append("','");
                        query.append(m.getPrecipitation());
                        query.append("','");
                        query.append(m.getSnow());
                        query.append("','");
                        query.append(m.getEvents());
                        query.append("','");
                        query.append(m.getOvercast());
                        query.append("','");
                        query.append(m.getWindDirection());
                        query.append("')");
                        if (count == 80)
                        {
                            statement.addBatch(query.toString());
                            first = true;
                            query = new StringBuilder();

                            count = 0;
                            countBatch++;
                        }
                        if (countBatch == 100)
                        {
                            statement.executeBatch();
                            statement.clearBatch();
                            countBatch = 0;
                        }
                    }
                }
            }
        }
        catch (SQLException ex)
        {

        }
    }

}
