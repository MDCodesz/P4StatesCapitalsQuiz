package edu.uga.cs.p4statescapitalsquiz;


/**
 * This class (a POJO) represents a single job lead, including the id, company name,
 * capital number, URL, and some cityTwo.
 * The id is -1 if the object has not been persisted in the database yet, and
 * the db table's primary key value, if it has been persisted.
 */
public class Question {

    private long   id;
    private String stateName;
    private String capital;
    private String cityOne;
    private String cityTwo;

    public Question()
    {
        this.id = -1;
        this.stateName = null;
        this.capital = null;
        this.cityOne = null;
        this.cityTwo = null;
    }

    public Question( String stateName, String capital, String cityOne, String cityTwo ) {
        this.id = -1;  // the primary key id will be set by a setter method
        this.stateName = stateName;
        this.capital = capital;
        this.cityOne = cityOne;
        this.cityTwo = cityTwo;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getStateName()
    {
        return stateName;
    }

    public void setStateName(String stateName)
    {
        this.stateName = stateName;
    }

    public String getCapital()
    {
        return capital;
    }

    public void setCapital(String capital)
    {
        this.capital = capital;
    }

    public String getCityOne()
    {
        return cityOne;
    }

    public void setCityOne(String cityOne)
    {
        this.cityOne = cityOne;
    }

    public String getCityTwo()
    {
        return cityTwo;
    }

    public void setCityTwo(String cityTwo)
    {
        this.cityTwo = cityTwo;
    }

    public String toString()
    {
        return id + ": " + stateName + " " + capital + " " + cityOne + " " + cityTwo;
    }
}
