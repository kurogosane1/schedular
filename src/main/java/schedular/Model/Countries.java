/**
 * @author Syed Khurshid
 */
package schedular.Model;

// class is user to store the current countries in the database
public class Countries {
    /**
     * This is the default Country ID
     */
    private int countryID;
    /**
     * This is the default Country Name
     */
    private String country;
    /**
     * default Constructor
     * @param countryID default country ID
     * @param country default Country name
     */
    public Countries(int countryID, String country) {
        this.countryID = countryID;
        this.country = country;
    }
    
    /**
     * This is to return the Country ID
     * @return countryID which is the ID of the country
     */
    public int getCountryID() {
        return countryID;
    }
    /**
     * This is set the Country ID 
     * @param countryID which is used to update into the database based on the ID
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }
    /**
     * THis is to get the Country name
     * @return Country name
     */
    public String getCountry() {
        return country;
    }
    /**
     * This is to set the Country name
     * @param country this will search by the country in the database
     */
    public void setCountry(String country) {
        this.country = country;
    }
    
    
}
