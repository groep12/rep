package Logics;

import java.sql.Time;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * A class to store measurement data
 *  
 * @author Groep 12
 */
public class Measurement
{

    private int station;
    private java.sql.Date date;
    private Time time;
    private double temperature;
    private double dewPoint;
    private double airPressureStationLevel;
    private double airPressureSeaLevel;
    private double visibility;
    private double windSpeed;
    private double precipitation;
    private double snow;
    private String events;
    private double overcast;
    private int windDirection;

    /**
     *
     * @param station
     */
    public Measurement(String station)
    {
        setStation(station);
    }

    /**
     * @return the station
     */
    public int getStation()
    {
        return station;
    }

    private void setStation(String station)
    {
        this.station = Integer.parseInt(station);
    }

    /**
     * @return the airPressureStationLevel
     */
    public double getAirPressureStationLevel()
    {
        return airPressureStationLevel;
    }

    /**
     *
     * @param airPressureStationLevel
     */
    public void setAirPressureStationLevel(String airPressureStationLevel)
    {
        this.airPressureStationLevel = Double.parseDouble(airPressureStationLevel);
    }

    /**
     * @return the airPressureSeaLevel
     */
    public double getAirPressureSeaLevel()
    {
        return airPressureSeaLevel;
    }

    /**
     *
     * @param airPressureSeaLevel
     */
    public void setAirPressureSeaLevel(String airPressureSeaLevel)
    {
        this.airPressureSeaLevel = Double.parseDouble(airPressureSeaLevel);
    }

    /**
     * @return the visibility
     */
    public double getVisibility()
    {
        return visibility;
    }

    /**
     *
     * @param visibility
     */
    public void setVisibility(String visibility)
    {
        this.visibility = Double.parseDouble(visibility);
    }

    /**
     * @return the windSpeed
     */
    public double getWindSpeed()
    {
        return windSpeed;
    }

    /**
     *
     * @param windSpeed
     */
    public void setWindSpeed(String windSpeed)
    {
        this.windSpeed = Double.parseDouble(windSpeed);
    }

    /**
     * @return the precipitation
     */
    public double getPrecipitation()
    {
        return precipitation;
    }

    /**
     *
     * @param precipitation
     */
    public void setPrecipitation(String precipitation)
    {
        this.precipitation = Double.parseDouble(precipitation);
    }

    /**
     * @return the snow
     */
    public double getSnow()
    {
        return snow;
    }

    /**
     *
     * @param snow
     */
    public void setSnow(String snow)
    {
        this.snow = Double.parseDouble(snow);
    }

    /**
     * @return the events
     */
    public String getEvents()
    {
        return events;
    }

    /**
     * @param events the events to set
     */
    public void setEvents(String events)
    {
        this.events = events;
    }

    /**
     * @return the overcast
     */
    public double getOvercast()
    {
        return overcast;
    }

    /**
     *
     * @param overcast
     */
    public void setOvercast(String overcast)
    {
        this.overcast = Double.parseDouble(overcast);
    }

    /**
     * @return the windDirection
     */
    public int getWindDirection()
    {
        return windDirection;
    }

    /**
     *
     * @param windDirection
     */
    public void setWindDirection(String windDirection)
    {
        this.windDirection = Integer.parseInt(windDirection);
    }

    /**
     * @return the date
     */
    public Date getDate()
    {
        return date;
    }

    /**
     *
     * @param date
     * @throws ParseException
     */
    public void setDate(String date) throws ParseException
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date temp = sdf.parse(date);
        java.sql.Date sqlDate = new java.sql.Date(temp.getTime());
        this.date = sqlDate;
    }

    /**
     * @return the time
     */
    public Time getTime()
    {
        return time;
    }

    /**
     *
     * @param time
     */
    public void setTime(String time)
    {
        Time temp = Time.valueOf(time);
        this.time = temp;
    }

    /**
     * @return the temperature
     */
    public double getTemperature()
    {
        return temperature;
    }

    /**
     *
     * @param temperature
     */
    public void setTemperature(String temperature)
    {
        this.temperature = Double.parseDouble(temperature);
    }

    /**
     * @return the dewPoint
     */
    public double getDewPoint()
    {
        return dewPoint;
    }

    /**
     *
     * @param dewPoint
     */
    public void setDewPoint(String dewPoint)
    {
        this.dewPoint = Double.parseDouble(dewPoint);

    }
}
