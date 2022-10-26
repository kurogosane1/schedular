package schedular.Model;
/**
 * @author Syed Khurshid
 */
public class Customer {
    private int customerId;
    private String customerName;
    private String customerAddress;
    private String postalCode;
    private String phoneNumber;
    public int division_id;
    /**
     * Default Constructor
     * @param customerId
     * @param customerName
     * @param customerAddress
     * @param postalCode
     * @param phoneNumber
     * @param division_id
     */
    public Customer(int customerId, String customerName, String customerAddress, String postalCode, String phoneNumber,
            int division_id) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.division_id = division_id;
    }
    /**
     * This is to get the Customer ID from the database
     * @return
     */
    public int getCustomerId() {
        return customerId;
    }
    /**
     * This is to set the Customer ID in the database
     * @param customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    /**
     * This is to get the Customer name from the database
     * @return
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
     * @return postal cost from the database
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
     * @return phone number of the customer from the database
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
    
}
