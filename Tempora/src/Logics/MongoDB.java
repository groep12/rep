/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logics;

import Application.Main;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoException;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoTimeoutException;
import com.mongodb.ServerAddress;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Hanze
 */
public class MongoDB
{

    public static Queue<Measurement> collection = new LinkedList<>();
    private static MongoClient mongoClient;
    private static DB db;
    private static DBCollection measurements;
    private static DBCollection stations;

    /**
     *
     * @return
     */
    public static boolean tryConnect()
    {
        try
        {
            MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
            builder.connectionsPerHost(Settings.MAX_CONNECTIONS);
            builder.connectTimeout(60000);
            builder.socketTimeout(10000);
            builder.socketKeepAlive(true);
            MongoClientOptions options = builder.build();
            mongoClient = new MongoClient(new ServerAddress("192.168.21.30"), options);
            db = mongoClient.getDB(Settings.DB_NAME);
            measurements = db.getCollection("Measurements");
            stations = db.getCollection("Stations");

            return true;
        }
        catch (UnknownHostException | MongoTimeoutException ex)
        {

        }
        catch (MongoException ex)
        {

        }
        return false;
    }

    public static void addMeasurement(Measurement m)
    {

        BasicDBObject doc = new BasicDBObject("STN", m.getStation())
                .append("DATE", m.getDate())
                .append("TIME", m.getTime())
                .append("TEMP", m.getTemperature())
                .append("DEWP", m.getDewPoint())
                .append("STP", m.getAirPressureStationLevel())
                .append("SLP", m.getAirPressureSeaLevel())
                .append("VISIB", m.getVisibility())
                .append("WDSP", m.getWindSpeed())
                .append("PRCP", m.getPrecipitation())
                .append("SNDP", m.getSnow())
                .append("FRSHTT", m.getEvents())
                .append("CLDC", m.getOvercast())
                .append("WNDDIR", m.getWindDirection());
        measurements.insert(doc);
        if (Receiver.countries.containsKey(m.getStation()))
        {
            collection.offer(m);
        }
        else if (m.getTemperature() >= 25)
        {
            double latitude = Receiver.latitudes.get(m.getStation());
            if (latitude > 35 & latitude < 65)
            {
                collection.offer(m);
            }
        }
        Main.setNumbers(collection.size(), Processor.processed);
    }

    /**
     *
     * @return
     */
    public static DBCollection getMeasurements()
    {
        return measurements;
    }

    /**
     *
     * @return
     */
    public static DBCollection getStations()
    {
        return stations;
    }

}
