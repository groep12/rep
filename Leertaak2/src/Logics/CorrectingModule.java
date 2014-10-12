package Logics;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import org.w3c.dom.Document;

/**
 * A class for making corrections to incomplete or corrupt weather data.  
 * So it can do this, it also saves the last 30 entries for all 8000 weather stations 
 * So that it can make good corrections
 * 
 * @author Groep 12
 */
public class CorrectingModule
{
    private final HashMap<Integer, HashMap> previousValues;

    /**
     * Constructs the class and the initializes the HashMap that stores the data
     */
    public CorrectingModule()
    {
        previousValues = new HashMap<>();

    }

    /**
     * Stores the weather data and keeps the last 30 entries
     * It stores them on Station Number level
     * When there are 30 entries, it removes the first entry from the Queue and puts a new one at the back of the Queue
     * 
     * weather data based on missing or
     * @param doc XML document
     * @param tagName Tag name of the XML element
     * @param item Index of the element
     * @return
     */
    public String correctAndGetValue(Document doc, String tagName, int item)
    {
        int stationNumber = Integer.parseInt(doc.getElementsByTagName("STN").item(item).getFirstChild().getNodeValue());
        if (doc.getElementsByTagName(tagName).item(item).getFirstChild() == null)
        {
            return correction(stationNumber, tagName);
        }
        else
        {
            String nodeValue = doc.getElementsByTagName(tagName).item(item).getFirstChild().getNodeValue();
            HashMap<String, Queue> pvStation;
            if (previousValues.containsKey(stationNumber))
            {
                pvStation = previousValues.get(stationNumber);;
                Queue<String> q;
                if (pvStation.containsKey(tagName))
                {
                    q = pvStation.get(tagName);
                    if (q.size() == 30) q.remove();                    
                    if (tagName.equals("TEMP"))
                    {
                        nodeValue = calculateTemp(nodeValue, averageDouble((LinkedList) q));
                    }
                    q.add(nodeValue);
                }
                else
                {
                    q = new LinkedList<>();
                    q.add(nodeValue);
                    pvStation.put(tagName, q);
                }
            }
            else
            {
                pvStation = new HashMap<>();
                previousValues.put(stationNumber, pvStation);
            }
            return nodeValue;
        }
    }

    private String correction(Integer stationNumber, String tagName)
    {
        String corrected = "0";
        if (previousValues.containsKey(stationNumber))
        {
            HashMap<String, Queue> pvStation = previousValues.get(stationNumber);
            if (pvStation.containsKey(tagName))
            {
                Queue q = pvStation.get(tagName);
                switch (tagName)
                {
                    case "TEMP":
                    case "DEWP":
                    case "STP":
                    case "SLP":
                    case "VISIB":
                    case "WDSP":
                    case "PRCP":
                    case "SNDP":
                    case "CLDC":
                        corrected = averageDouble((LinkedList) q);
                        break;
                    case "WNDDIR":
                        corrected = averageInt((LinkedList) q);
                        break;

                    case "FRSHTT":
                        corrected = lastEvent((LinkedList) q);
                        break;
                    default:
                        corrected = "FOUT";
                        break;
                }
            }
        }
        return corrected;
    }

    private String calculateTemp(String currentTemp, String averageTemp)
    {
        if (currentTemp.isEmpty())
        {
            return averageTemp;
        }
        else
        {
            double c = Double.parseDouble(currentTemp);
            double a = Double.parseDouble(averageTemp);
            Double difference = Math.abs(((c / a) * 100) - 100);
            if (difference >= 20)
            {
                return averageTemp;
            }
            return currentTemp;
        }
    }

    private String averageDouble(LinkedList<String> doubles)
    {
        if (doubles.isEmpty())
        {
            return "0";
        }
        else
        {
            double total = 0;
            for (String d : doubles)
            {
                double value = Double.parseDouble(d);
                total = total + value;
            }
            Double average = total / doubles.size();
            return average.toString();
        }
    }

    private String averageInt(LinkedList<String> integers)
    {
        if (integers.isEmpty())
        {
            return "0";
        }
        else
        {
            int total = 0;
            for (String i : integers)
            {
                int value = Integer.parseInt(i);
                total = total + value;
            }
            Integer average = total / integers.size();
            return average.toString();
        }
    }

    private String lastEvent(LinkedList<String> events)
    {
        if (events.isEmpty())
        {
            return "999999";
        }
        else
        {
            int FRSHTT[] = new int[6];
            for (String e : events)
            {
                for (int i = 0; i < 5; i++)
                {
                    FRSHTT[i] += Integer.parseInt(e.substring(i, i + 1));
                }
            }
            int count = events.size();
            String calculatedEvent = "";
            for (int i = 0; i < 5; i++)
            {
                int calc = FRSHTT[i] / count;
                calculatedEvent += calc + "";
            }
            return calculatedEvent;
        }
    }

}
