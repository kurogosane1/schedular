/**
 * @author Syed Khurshid
 */
package schedular.Model;

/**
 * This is the First Level Division Object
 */
public class FirstLevelDivision {
    /**
     * This is the Division Unique ID
     */
    private int divisionID;
    /**
     * This is the Division name 
     */
    private String Division;
    /**
     * This is the Country ID that its related
     */
    public int countryID;
    /**
     * Default Constructor
     * @param divisionID default division ID
     * @param division default division name
     * @param countryID default country ID
     */
    public FirstLevelDivision(int divisionID, String division, int countryID) {
        this.divisionID = divisionID;
        Division = division;
        this.countryID = countryID;
    }
    
    /**
     * This is to get the division ID
     * @return divisionID which is the Division ID
     */
    public int getDivisionID() {
        return divisionID;
    }
    /**
     * This sets the Division iD
     * @param divisionID this is what will be set in the database
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }
    /**
     * 
     * This will return the Division
     * @return Division the Division from the database
     */
    public String getDivision() {
        return Division;
    }
    /**
     * This is going to set the deviision into the database
     * @param division this is what will be input into the database
     */
    public void setDivision(String division) {
        Division = division;
    }
    /**
     * This returns the Country ID that is connected to the Country class
     * @return countryID which is the Country ID
     */
    public int getCountryID() {
        return countryID;
    }
    
    
}
