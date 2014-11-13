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

    private static final SqlDB sqlDB = new SqlDB();
    private Connection connection;
    public static int processed = 0;
    private static final int batchEntries = 25;
    private static final int batchSize = 40;
    private Thread blinker;
    private PreparedStatement preparedStatement;

    public Processor() throws SQLException
    {
        connection = sqlDB.openConnection();
        createPreparedStatement(connection, batchEntries);
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
        int total = batchEntries * batchSize;
        int count = 0;
        int batchCount = 0;
        Measurement m;
        while (blinker == thisThread)
        {
            for (;;)
            {
                try
                {
                    while (MongoDB.collection.size() > total)
                    {
                        m = MongoDB.collection.poll();
                        processed++;
                        setPreparedStatement(count++, m);

                        if (count == batchEntries)
                        {
                            preparedStatement.addBatch();
                            count = 0;
                            batchCount++;
                        }
                        if (batchCount == batchSize)
                        {
                            preparedStatement.executeBatch();
                            preparedStatement.clearBatch();
                            connection.close();
                            connection = sqlDB.openConnection();
                            createPreparedStatement(connection, batchEntries);
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

    private void createPreparedStatement(Connection connection, int aantal) throws SQLException
    {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO ");
        query.append("measurement");
        query.append(" (STN,DATE,TIME,TEMP,DEWP,STP,SLP,VISIB,WDSP,PRCP,SNDP,FRSHTT,CLDC,WNDDIR) ");
        query.append("VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
        for (int i = 1; i < aantal; i++)
        {
            query.append(", (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        }
        preparedStatement = connection.prepareStatement(query.toString());
    }

    private void setPreparedStatement(int aantal, Measurement m) throws SQLException
    {
        int ps = 14 * aantal;
        preparedStatement.setInt((ps + 1), m.getStation());
        preparedStatement.setDate((ps + 2), m.getDate());
        preparedStatement.setTime((ps + 3), m.getTime());
        preparedStatement.setDouble((ps + 4), m.getTemperature());
        preparedStatement.setDouble((ps + 5), m.getDewPoint());
        preparedStatement.setDouble((ps + 6), m.getAirPressureStationLevel());
        preparedStatement.setDouble((ps + 7), m.getAirPressureSeaLevel());
        preparedStatement.setDouble((ps + 8), m.getVisibility());
        preparedStatement.setDouble((ps + 9), m.getWindSpeed());
        preparedStatement.setDouble((ps + 10), m.getPrecipitation());
        preparedStatement.setDouble((ps + 11), m.getSnow());
        preparedStatement.setString((ps + 12), m.getEvents());
        preparedStatement.setDouble((ps + 13), m.getOvercast());
        preparedStatement.setInt((ps + 14), m.getWindDirection());
    }

}
