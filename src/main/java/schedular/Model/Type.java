/**
 * Another class would be suitable or a subset class would work also
 * Using SQL to do more of the LocalDate Time work than else where
 */
package schedular.Model;

/**
 * @author Syed Khurshid
 */

 /**
  * This is a class for the Converted Appointments table to show the Types of Appointments and total number of them based on the months
  */
 public class Type {
    /**
     * This is the Month the Appointments fall under
     */
    private String Months;
    /**
     * This is the Types of Appointments
     */
    private String Types;
    /**
     * This is the total Number of Appointments that fall under a Type
     */
    private Integer count;
    /**
     * Default Constructor
     * @param months which is the month that the user has selected
     * @param types which is the string type of the meeting the user selects
     * @param count count is the total number of appointments that fall under that type
     */
    public Type(String months, String types, Integer count) {
        Months = months;
        Types = types;
        this.count = count;
    }
    /**
     * This is to get the month of the Appointment
     * @return String format of the Month of the Appointment
     */
    public String getMonths() {
        return Months;
    }
    /**
     * This is to set the Month of the Appointments. Not going to be used
     * @param months String format
     */
    public void setMonths(String months) {
        Months = months;
    }
    /**
     * IDE generated to get the Types of the Appointments
     * @return Tyoes that are String format
     */
    public String getTypes() {
        return Types;
    }
    /**
     * This is to set the Types of the Appointments
     * @param types that are in string format
     */
    public void setTypes(String types) {
        Types = types;
    }
    /**
     * This is to get the total Customers of the appointment
     * @return total Customers in Integer format
     */
    public Integer getCount() {
        return count;
    }
    /**
     * This is to set the Total Count in Integer Format
     * @param count total Custoemrs in integer
     */
    public void setCount(Integer count) {
        this.count = count;
    }

}
