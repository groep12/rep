package Logics;

/**
 * A class to store station data
 * 
 * @author Groep 12
 */
public class Station
{

    private int number;
    private String name;
    private String shortName;

    /**
     * Constructs the class with a station name and number
     * 
     * @param Number
     * @param Name
     */
    public Station(int Number, String Name)
    {
        setNumber(Number);
        setName(Name);
    }

    /**
     * @return the station number
     */
    public int getNumber()
    {
        return number;
    }

    private void setNumber(int Number)
    {
        this.number = Number;
    }

    /**
     * @return the name of the station
     */
    public String getName()
    {
        return name;
    }

    private void setName(String Name)
    {
        if (Name.length() <= 25) shortName = Name;
        else this.shortName = Name.substring(0, 25);        
        this.name = Name;
    }

    @Override
    public String toString()
    {
        return shortName + "(" + number + ")";
    }
}
