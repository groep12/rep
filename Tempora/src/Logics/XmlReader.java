package Logics;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class to read the XML weather data
 *
 * @author Groep 12
 */
public class XmlReader
{

    private static CorrectingModule corrections;

    /**
     * Constructs the XML reader
     */
    public XmlReader()
    {
        corrections = new CorrectingModule();        
    }

    /**
     * Parses the XML data and gives back a link list with Measurement data
     *
     * @param xml a StringBuilder that contains XML data
     * @return Linked list with collection of measurements
     */
    public Queue<Measurement> parse(StringBuilder xml)
    {
        Queue<Measurement> collection = new LinkedList<>();
        try
        {

            DocumentBuilderFactory factory;
            DocumentBuilder builder;

            factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            builder = factory.newDocumentBuilder();

            Document doc;
            InputStream is = new ByteArrayInputStream(xml.toString().getBytes());
            doc = builder.parse(is);
            NodeList measurements = doc.getElementsByTagName("MEASUREMENT");
            Measurement m;
            for (int i = 0; i < measurements.getLength(); i++)
            {
                m = new Measurement(corrections.correctAndGetValue(doc, "STN", i));
                m.setDate(corrections.correctAndGetValue(doc, "DATE", i));
                m.setTime(corrections.correctAndGetValue(doc, "TIME", i));
                m.setTemperature(corrections.correctAndGetValue(doc, "TEMP", i));
                m.setDewPoint(corrections.correctAndGetValue(doc, "DEWP", i));
                m.setAirPressureStationLevel(corrections.correctAndGetValue(doc, "STP", i));
                m.setAirPressureSeaLevel(corrections.correctAndGetValue(doc, "SLP", i));
                m.setVisibility(corrections.correctAndGetValue(doc, "VISIB", i));
                m.setWindSpeed(corrections.correctAndGetValue(doc, "WDSP", i));
                m.setPrecipitation(corrections.correctAndGetValue(doc, "PRCP", i));
                m.setSnow(corrections.correctAndGetValue(doc, "SNDP", i));
                m.setEvents(corrections.correctAndGetValue(doc, "FRSHTT", i));
                m.setOvercast(corrections.correctAndGetValue(doc, "CLDC", i));
                m.setWindDirection(corrections.correctAndGetValue(doc, "WNDDIR", i));
                collection.add(m);
            }
        }
        catch (IOException | SAXException | ParseException | ParserConfigurationException exception)
        {

        }
        return collection;
    }

}
