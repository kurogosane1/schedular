/**
 *
 * @author Syed Khurshid
 */
package schedular.Model;
/**
 * This is the Customers Object class for creating Customers
 */
public class Customer {
    private int customerId;
    private String customerName;
    private String customerAddress;
    private String postalCode;
    private String phoneNumber;
    private int country_id;
    private int division_id;
    
    /**
     * Default Constructor
     * @param customerId default customer ID
     * @param customerName default constructor Customer Name
     * @param customerAddress default customer address
     * @param postalCode default customer postal code
     * @param phoneNumber default customer phone number
     * @param division_id default customer division ID
     * @param country_id this is to retrieve from the First Level Divisions table
     */
    public Customer(int customerId, String customerName, String customerAddress, String postalCode, String phoneNumber,
            int division_id, int country_id) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.division_id = division_id;
        this.country_id = country_id;
    }
    /**
     * This is to get the Customer ID from the database
     * @return customerId which is the Customer ID
     */
    public int getCustomerId() {
        return customerId;
    }
    /**
     * This is to set the Customer ID in the database
     * @param customerId which is the customer ID
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    /**
     * This is to get the Customer name from the database
     * @return customerName  which is the customer name
     */
    public String getCustomerName() {
        return customerName;
    }
    /**
     * This is to set the Customer name into the database
     * @param customerName into the database
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    /**
     * This is to return the Customer address
     * @return customerAddress which is the customer address
     */
    public String getCustomerAddress() {
        return customerAddress;
    }
    /**
     * This is to set the Customer address into the database
     * @param customerAddress is then input into the database
     */
    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }
    /**
     * This is to get the postalCode from the database
     * @return postalCode code from the database
     */
    public String getPostalCode() {
        return postalCode;
    }
    /**
     * This is input the postalCode into the database
     * @param postalCode parameter is input into the database
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    /**
     * This is to get the Customer phoneNumber
     * @return phoneNumber of the customer from the database
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }
    /**
     * This is to set the phoneNumber into the database
     * @param phoneNumber is placed into the database
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    /**
     * This is get the Division ID
     * @return division_id is returned but this is from the devision class
     */
    public int getDivision_id() {
        return division_id;
    }
    /**
     * This is to get the Country ID
     * @return country_id is returned but this is from the devision class
     */
    public int getCountry_id() {
        return country_id;
    }
    /**
     * This is to set the country_id into the database
     * @param country_id which is the integer ID of the country and search under country database
     */
    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }
    
}
