/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logics;

import Application.Main;
import com.mongodb.BasicDBObject;
import com.mongodb.BulkWriteOperation;
import com.mongodb.BulkWriteResult;
import com.mongodb.CommandResult;
import com.mongodb.Cursor;
import com.mongodb.MongoException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoTimeoutException;
import com.mongodb.ParallelScanOptions;
import com.mongodb.ServerAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 *
 * @author Hanze
 */
public class MongoDB
{

    private static MongoClient mongoClient;
    private static DB db;
    private static DBCollection measurements;

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
            mongoClient = new MongoClient(new ServerAddress("localhost"), options);
            db = mongoClient.getDB(Settings.DB_NAME);
            measurements = db.getCollection("Measurements");
            Main.addLine("Number of measurements: " + measurements.getCount());
            return true;
        }
        catch (UnknownHostException | MongoTimeoutException ex)
        {
            Main.addLine(ex);
        }
        catch (MongoException ex)
        {
            Main.addLine(ex);
        }
        return false;
    }

    private static void addMeasurement(Measurement m)
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
    }

    public static void addMeasurments(MeasurementCollection collection)
    {
        Measurement m;
        while (collection.size() > 0)
        {
            m = collection.pop();
            addMeasurement(m);
        }
    }

}
