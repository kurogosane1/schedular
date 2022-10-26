/**
 * Preference would be to use NoSQL tools like MangoDB which will reduce the relationship links
 */
package schedular.Model;
/**
 * @author Syed Khurshid
 */
public class Contacts {
    private int contact_ID;
    private String contactName;
    private String emailAddress;
    /**
     * Default Constructor
     * @param contact_ID
     * @param contactName
     * @param emailAddress
     */
    public Contacts(int contact_ID, String contactName, String emailAddress) {
        this.contact_ID = contact_ID;
        this.contactName = contactName;
        this.emailAddress = emailAddress;
    }
    /**
     * This is to get the Contact ID
     * @return returns the Contact ID from the database
     */
    public int getContact_ID() {
        return contact_ID;
    }
    /**
     * This will place the contact_ID into the database
     * @param contact_ID parameter will be added to the database
     */
    public void setContact_ID(int contact_ID) {
        this.contact_ID = contact_ID;
    }
    /**
     * This is to get the Contacts Name
     * @return Contract name from the database
     */
    public String getContactName() {
        return contactName;
    }
    /**
     * Setting the Contacts name
     * @param contactName is then input into the Contacts Database
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
    /**
     * Gets the emailAddress of the contact
     * @return email Address of the Contacts person
     */
    public String getEmailAddress() {
        return emailAddress;
    }
    /**
     * Sets the email address from the Contacts email address
     * @param emailAddress is input into the database
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    
    
}
