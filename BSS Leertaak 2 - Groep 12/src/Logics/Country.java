package Logics;

/**
 * A class to save country data in
 * 
 * @author Groep 12
 */
public class Country
{
    private String name;
    private String shortName;
    private int count;    

    /**
     * Constructs the class
     * 
     * @param name - Name of the country
     * @param count - Count of how many stations the country has
     */
    public Country(String name, int count)
    {
        setCount(count);
        setName(name);
    }
    
    /**
     * @return the count of how many stations the country has
     */
    public int getCount()
    {
        return count;
    }

    private void setCount(int count)
    {
        this.count = count;
    }

    /**
     * @return the name of the country
     */
    public String getName()
    {
        return name;
    }

    private void setName(String Name)
    {
        if(Name.length() <= 25) this.shortName = Name;
        else this.shortName = Name.substring(0, 25);        
        this.name = Name;        
    }
    
    @Override
    public String toString()
    {
        return shortName;
    }
}
