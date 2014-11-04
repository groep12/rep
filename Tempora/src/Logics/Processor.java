/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logics;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Hanze
 */
public class Processor implements Runnable
{

    private Thread blinker;
    private final Connection connection;
    private PreparedStatement preparedStatement;
    private final StringBuilder query;

    public Processor() throws SQLException
    {
        SqlDB sqlDB = new SqlDB();
        connection = sqlDB.openConnection();
        query = new StringBuilder();
        query.append("INSERT INTO ");
        query.append("measurement2");
        query.append("(STN,DATE,TIME,TEMP,DEWP,STP,SLP,VISIB,WDSP,PRCP,SNDP,FRSHTT,CLDC,WNDDIR) ");
        query.append("VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
        for (int i = 1; i < 40; i++)
        {
            query.append(", (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        }
        preparedStatement = connection.prepareStatement(query.toString());
    }

    /**
     *
     * @return
     */
    public static Processor startProcessing() throws SQLException
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
        int count = 0;
        int batchCount = 0;
        int ps = 0;
        while (blinker == thisThread)
        
        {

            for (;;)
            {

                try
                {

                    
                    while (OpenSocket.collection.size() > 0)
                    {
                        Measurement m = OpenSocket.collection.poll();
                        
                            
                       
                        count++;
                            
                        
                        
                        preparedStatement.setInt((ps+1), m.getStation());
                        preparedStatement.setDate((ps+2), m.getDate());
                        preparedStatement.setTime((ps+3), m.getTime());
                        preparedStatement.setDouble((ps+4), m.getTemperature());
                        preparedStatement.setDouble((ps+5), m.getDewPoint());
                        preparedStatement.setDouble((ps+6), m.getAirPressureStationLevel());
                        preparedStatement.setDouble((ps+7), m.getAirPressureSeaLevel());
                        preparedStatement.setDouble((ps+8), m.getVisibility());
                        preparedStatement.setDouble((ps+9), m.getWindSpeed());
                        preparedStatement.setDouble((ps+10), m.getPrecipitation());
                        preparedStatement.setDouble((ps+11), m.getSnow());
                        preparedStatement.setString((ps+12), m.getEvents());
                        preparedStatement.setDouble((ps+13), m.getOvercast());
                        preparedStatement.setInt((ps+14), m.getWindDirection());
                        ps += 14;    
                        if (count == 40)
                        {
                            preparedStatement.addBatch();

                            batchCount++;
                            count = 0;
                            ps = 0;
                        }
                        if (batchCount == 100)
                        {
                            preparedStatement.executeBatch();                            
                            preparedStatement.clearBatch();
                            //preparedStatement = connection.prepareStatement(query.toString());
                            batchCount = 0;
                        }
                         
                    }
                }
                catch (SQLException ex)
                {
                    System.out.println(ex);
                }
            }
        }
    }

}
