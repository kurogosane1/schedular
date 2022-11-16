/**
 * Maybe changing to a better implementation of interface
 * @author Syed Khurshid
 */
package schedular.utilities;

/**
 * This is a interface to push to the Database
 */
public interface AddObject {
    /**
     * This is to push to the Database to add Appointments
     * @param id appointment ID
     * @param title This is the title of the appointment
     * @param description this is the description of the appointment
     * @param location this is the location of the appointment
     * @param type this is the type of the appointment
     * @param startUTC this is the Start Time of the appointment
     * @param endUTC this is the End Time of the appointment
     * @param customerID this is the customer ID of the appointment
     * @param UserID this is the user ID of the appointment
     * @param contactID this is the contact ID of the appointment
     */
    void pushToDatabase(Integer id, String title, String description, String location, String type, String startUTC, String endUTC, Integer customerID, Integer UserID, Integer contactID );
}
