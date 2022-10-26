package schedular.Model;

public class FirstLevelDivision {
    private int divisionID;
    private String Division;
    public int countryID;
    /**
     * Default Constructor
     * @param divisionID
     * @param division
     * @param countryID
     */
    public FirstLevelDivision(int divisionID, String division, int countryID) {
        this.divisionID = divisionID;
        Division = division;
        this.countryID = countryID;
    }
    
    /**
     * Returns the Division iD
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
     * @return returns the Devision from the database
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
     */
    public int getCountryID() {
        return countryID;
    }
    
    
}
