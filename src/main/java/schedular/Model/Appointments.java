/**
 * @author Syed Khurshid
 * Preference would be to use a NoSQL database like MangoDB
 */
package schedular.Model;
// import java.sql.String;

/**
 * This is the Appointments User Object
 */
public class Appointments {
    private int AppointmentID;
    private String Title;
    private String Description;
    private String Location;
    private String Type;
    private String Start;
    private String End;
    public int customer_id;
    public int user_id;
    public int contact_id;

    /**
     * Default Constructor
     * @param appointmentID
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param customer_id
     * @param user_id
     * @param contact_id
     */
    public Appointments(int appointmentID, String title, String description, String location, String type,
            String start, String end, int customer_id, int user_id, int contact_id)
            {
        AppointmentID = appointmentID;
        Title = title;
        Description = description;
        Location = location;
        Type = type;
        Start = start;
        End = end;
        this.customer_id = customer_id;
        this.user_id = user_id;
        this.contact_id = contact_id;
    }
    /**
     * This is to get the appointmentID
     * @return appointmentID is returned from the database
     */
    public int getAppointmentID() {
        return AppointmentID;
    }
    /**
     * This is to create a new AppointmentID
     * @param appointmentID parameter from the form
     */
    public void setAppointmentID(int appointmentID) {
        AppointmentID = appointmentID;
    }
    /**
     * This is to get the Title of the appointment
     * @return title of the appointment
     */
    public String getTitle() {
        return Title;
    }
    /**
     * This is to set the Title of the appointment into the database
     */
    public void setTitle(String title) {
        Title = title;
    }
    /**
     * This is to get the appointment description
     * @return appointment description from the database
     */
    public String getDescription() {
        return Description;
    }
    /**
     * This is set the appointment as described
     * @param description appointment description is then placed into the database
     */
    public void setDescription(String description) {
        Description = description;
    }
    /**
     * This is to get the location from the database
     * @return location from the database
     */
    public String getLocation() {
        return Location;
    }
    /**
     * This is setting the location of the appointment
     * @param location from the user is then placed into the database
     */
    public void setLocation(String location) {
        Location = location;
    }
    /**
     * This is to get the type of the meeting
     * @return meeting type
     */
    public String getType() {
        return Type;
    }
    /**
     * This is to set the type
     * @param type is set into the database
     */
    public void setType(String type) {
        Type = type;
    }
    /**
     * This is to get the time of the start of the appointment
     * @return time is then input
     */
    public String getStart() {
        return Start;
    }
    /**
     * This is to set the start time into the database
     * @param start
     */
    public void setStart(String start) {
        Start = start;
    }
    /**
     * This is to get the end time of the appointment
     * @return appointment end time is received from the database
     */
    public String getEnd() {
        return End;
    }
    /**
     * This is to set the end time into the database
     * @param end is then pushed into the database
     */
    public void setEnd(String end) {
        End = end;
    }
    /**
     * This is to get the Customer ID which is linked to the Customer class
     * @return Customer id from the database which is from the customer class
     */
    public int getCustomer_id() {
        return customer_id;
    }
    /**
     * This is to get the user id from the database
     * @return this is to get the user id which we will then retrieve from the User Class
     */
    public int getUser_id() {
        return user_id;
    }
    /**
     * This is to get the Contact ID
     * @return this is to get the Contact ID from the database which we will then retrieve from the Contact Class
     */
    public int getContact_id() {
        return contact_id;
    }
       
    
}
