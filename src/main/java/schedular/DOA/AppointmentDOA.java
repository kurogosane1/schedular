/**
 * This is the Appointments Data Object Module. 
 * Preference is to reduce the lines of code and move to noSQL database
 */
package schedular.DOA;

/**
 * @author Syed Khurshid
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import schedular.Model.Appointments;
import schedular.Model.Type;
import schedular.connect.Database;

/**
 * This is the Appointments Data Object Module
 */
public class AppointmentDOA implements DOA<Appointments> {
    ZoneId zoneID = ZoneId.systemDefault();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    /**
     * @param id which is the Appointment ID
     * This is to get the single Appointments
     * @throws SQLException if an error occurs
     */
    @Override
    public Appointments get(int id) throws SQLException {
        Connection con = Database.getConnection();
        Appointments appointments = null;
        String sql = "SELECT Appointment_ID, Title,Description,Location,Type,Start,End,Customer_ID,User_ID,Contact_ID from Appointments WHERE Appointments_ID=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            // This is to get the Appointment ID
            int Appointment_ID = rs.getInt("Appointment_ID");
            // This is to get the Title 
            String Title = rs.getString("Title");
            // This is to get the Description
            String Description = rs.getString("Description");
            // This is to get the Location of the Appointment
            String Location = rs.getString("Location");
            // This is to get the Type of the Appointment
            String Type = rs.getString("Type");
            //Getting the time from Database convert to UTC and then get in the Local Time Zone
            Timestamp startTime = rs.getTimestamp("Start");
            Timestamp endTime = rs.getTimestamp("End");
            // Converting the Time from UTC to Local TIme Zone
            ZonedDateTime startLDT = startTime.toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(zoneID);
            ZonedDateTime endLDT = endTime.toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(zoneID);
            // Converting to String the Date Time for Start and End
            String localdatetimestart = startLDT.format(formatter);
            String localdatetimeend = endLDT.format(formatter);

            // Getting the Customer ID, User ID and Contact ID
            int Customer_ID = rs.getInt("Customer_ID");
            int User_ID = rs.getInt("User_ID");
            int Contact_ID = rs.getInt("Contact_ID");

            // Converting to Appointments object
            appointments = new Appointments(Appointment_ID, Title, Description, Location, Type, localdatetimestart,localdatetimeend, Customer_ID, User_ID, Contact_ID);
        }
        return appointments;
    }
    
    /**
     * Getting All the Countries List from the database
     * No parameter
     * @throws SQLException if an error occurs
     */
    @Override
    public ObservableList<Appointments> getAll() throws SQLException {
        Connection con = Database.getConnection();
        ObservableList<Appointments> appointments = FXCollections.observableArrayList();
        String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID FROM Appointments";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            // This is the Appointments ID
            Integer Appointment_ID = rs.getInt("Appointment_ID");
             // This is to get the Title 
            String Title = rs.getString("Title");
            // This is to get the Description
            String Description = rs.getString("Description");
            // This is to get the Location of the Appointment
            String Location = rs.getString("Location");
            // This is to get the Type of the Appointment
            String Type = rs.getString("Type");
               //Getting the time from Database convert to UTC and then get in the Local Time Zone
            Timestamp startTime = rs.getTimestamp("Start");
            Timestamp endTime = rs.getTimestamp("End");
            // Converting the Time from UTC to Local TIme Zone
            ZonedDateTime startLDT = startTime.toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(zoneID);
            ZonedDateTime endLDT = endTime.toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(zoneID);
            // Converting to String the Date Time for Start and End
            String localdatetimestart = startLDT.format(formatter);
            String localdatetimeend = endLDT.format(formatter);

            // Getting the Customer ID, User ID and Contact ID
            int Customer_ID = rs.getInt("Customer_ID");
            int User_ID = rs.getInt("User_ID");
            int Contact_ID = rs.getInt("Contact_ID");

            // Converting to Appointments object
            Appointments appointment = new Appointments(Appointment_ID, Title, Description, Location, Type,
                    localdatetimestart, localdatetimeend, Customer_ID, User_ID, Contact_ID);
            // Adding it in the ObservableList Array
            appointments.add(appointment);
        }
        return appointments;
    }
    
    /**
     * This is to add a new appointment to the database
     * @param t the Appointments Object
     * @throws SQLException if an error occurs
     */
    @Override
    public int insert(Appointments t) throws SQLException {
        Connection con = Database.getConnection();
        String sql = "INSERT INTO Appointments (Title, Description,Location, Type, Start, End, Customer_ID, User_ID, Contact_ID,Create_Date, Created_By, Last_Update, Last_Updated_By) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        // This is to insert Appointment Title
        ps.setString(1, t.getTitle());
        // This is to insert Description
        ps.setString(2, t.getDescription());
        // This is to insert Location
        ps.setString(3, t.getLocation());
        // This is to insert Type
        ps.setString(4, t.getType());
           // This is to get the Time zone converting to Timestamp
        Timestamp startTime = Timestamp.valueOf(t.getStart());
        Timestamp endTime = Timestamp.valueOf(t.getEnd());
        // Inserting into the query
        ps.setTimestamp(5, startTime);
        ps.setTimestamp(6, endTime);
        // Getting Customer ID , User ID Contact ID
        ps.setInt(7, t.getCustomer_id());
        ps.setInt(8, t.getUser_id());
        ps.setInt(9, t.getContact_id());
        // This is to get the created_by, Last Updated Last Updated by
        ps.setString(10, ZonedDateTime.now(ZoneOffset.UTC).format(formatter).toString()); // Create_Date
        ps.setString(11, "test");// Created_By
        ps.setString(12, ZonedDateTime.now(ZoneOffset.UTC).format(formatter).toString());
        ps.setString(13, "test");
        int result = ps.executeUpdate();
        return result;
    }
    
    /**
     * This is to update the Appointments Object module
     * @param t the Appointments Object
     * @throws SQLException if an error occurs
     */
    @Override
    public int update(Appointments t) throws SQLException {
        Connection con = Database.getConnection();
        String sql = "UPDATE Appointments SET Appointment_ID=?,Title=?,Description=?,Location=?,Type=?,Start=?,End=?,Customer_ID=?,User_ID=?,Contact_ID=?,Create_Date=?, Created_By=?, Last_Update=?, Last_Updated_By=? WHERE Appointment_ID=?";
        PreparedStatement ps = con.prepareStatement(sql);
        // This is to get the Appointment ID
        ps.setInt(1, t.getAppointmentID());
        // This is to get the Title
        ps.setString(2, t.getTitle());
        // This is to add the Description
        ps.setString(3, t.getDescription());
        // This is to add the Location
        ps.setString(4, t.getLocation());
        // This is to add the Type
        ps.setString(5, t.getType());
        // This is to get the Time zone converting to Timestamp
        Timestamp startTime = Timestamp.valueOf(t.getStart());
        Timestamp endTime = Timestamp.valueOf(t.getEnd());
        // Inserting into the query
        ps.setTimestamp(6, startTime);
        ps.setTimestamp(7, endTime);
        // Getting Customer ID , User ID Contact ID
        ps.setInt(8, t.getCustomer_id());
        ps.setInt(9, t.getUser_id());
        ps.setInt(10, t.getContact_id());
        // This is to get the created_by, Last Updated Last Updated by
        ps.setString(11, ZonedDateTime.now(ZoneOffset.UTC).format(formatter).toString()); // Create_Date
        ps.setString(12, "test");// Created_By
        ps.setString(13, ZonedDateTime.now(ZoneOffset.UTC).format(formatter).toString());
        ps.setString(14, "test");
        ps.setInt(15, t.getAppointmentID());
        int result = ps.executeUpdate();
        return result;
    }
    
    /**
     * This is to delete The Appointment from the database
     * @param t which is the Appointment object
     * @throws SQLException if an error occurs
     */
    @Override
    public int delete(Appointments t) throws SQLException {
        Connection con = Database.getConnection();
        String sql = "DELETE FROM Appointments WHERE Appointment_ID=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, t.getAppointmentID());
        int result = ps.executeUpdate();
        return result;
    }

    /**
     * This is to get Weekly Appointments from the database
     * @return Appointments where we get the appointments for the week
     * @throws SQLException for an error
     */
    public ObservableList<Appointments> getApptsWeekly() throws SQLException {
        Connection con = Database.getConnection();
        ObservableList<Appointments> weeklyAppointments = FXCollections.observableArrayList();
        LocalDate now = LocalDate.now();
        LocalDate week = LocalDate.now().plusDays(7);

        String sql = "SELECT * FROM Appointments WHERE Start>=? AND START <=? ORDER BY START";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, now.toString());
        ps.setString(2, week.toString());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            // This is to get the Appointment ID
            int Appointment_ID = rs.getInt("Appointment_ID");
            // This is to get the Title 
            String Title = rs.getString("Title");
            // This is to get the Description
            String Description = rs.getString("Description");
            // This is to get the Location of the Appointment
            String Location = rs.getString("Location");
            // This is to get the Type of the Appointment
            String Type = rs.getString("Type");
            //Getting the time from Database convert to UTC and then get in the Local Time Zone
            Timestamp startTime = rs.getTimestamp("Start");
            Timestamp endTime = rs.getTimestamp("End");
            // Converting the Time from UTC to Local TIme Zone
            ZonedDateTime startLDT = startTime.toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(zoneID);
            ZonedDateTime endLDT = endTime.toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(zoneID);
            // Converting to String the Date Time for Start and End
            String localdatetimestart = startLDT.format(formatter);
            String localdatetimeend = endLDT.format(formatter);

            // Getting the Customer ID, User ID and Contact ID
            int Customer_ID = rs.getInt("Customer_ID");
            int User_ID = rs.getInt("User_ID");
            int Contact_ID = rs.getInt("Contact_ID");
            Appointments appointment = new Appointments(Appointment_ID, Title, Description, Location, Type, localdatetimestart, localdatetimeend, Customer_ID, User_ID, Contact_ID);
            weeklyAppointments.add(appointment);
        }
        return weeklyAppointments;
    }
    
    /**
     * This to get the Monthly Appointments 
     * @return Appointments observableArrayList
     * @SQLException if an error occurs
     */
    public ObservableList<Appointments> getApptsMonthly() throws SQLException {
        Connection con = Database.getConnection();
        ObservableList<Appointments> weeklyAppointments = FXCollections.observableArrayList();
        LocalDate now = LocalDate.now();
        LocalDate week = LocalDate.now().plusMonths(1);

        String sql = "SELECT * FROM Appointments WHERE Start>=? AND START <=? ORDER BY START";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, now.toString());
        ps.setString(2, week.toString());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
             // This is to get the Appointment ID
            int Appointment_ID = rs.getInt("Appointment_ID");
            // This is to get the Title 
            String Title = rs.getString("Title");
            // This is to get the Description
            String Description = rs.getString("Description");
            // This is to get the Location of the Appointment
            String Location = rs.getString("Location");
            // This is to get the Type of the Appointment
            String Type = rs.getString("Type");
            //Getting the time from Database convert to UTC and then get in the Local Time Zone
            Timestamp startTime = rs.getTimestamp("Start");
            Timestamp endTime = rs.getTimestamp("End");
            // Converting the Time from UTC to Local TIme Zone
            ZonedDateTime startLDT = startTime.toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(zoneID);
            ZonedDateTime endLDT = endTime.toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(zoneID);
            // Converting to String the Date Time for Start and End
            String localdatetimestart = startLDT.format(formatter);
            String localdatetimeend = endLDT.format(formatter);

            // Getting the Customer ID, User ID and Contact ID
            int Customer_ID = rs.getInt("Customer_ID");
            int User_ID = rs.getInt("User_ID");
            int Contact_ID = rs.getInt("Contact_ID");
            Appointments appointment = new Appointments(Appointment_ID, Title, Description, Location, Type, localdatetimestart, localdatetimeend, Customer_ID, User_ID, Contact_ID);
            weeklyAppointments.add(appointment);
        }
        return weeklyAppointments;
    }
    
    /**
     * This is to get all the APpointments by Customer ID
     * @param id which is the Customer ID
     * @return Appointments which is the Observable List
     * @throws SQLException for an error
     */
    public ObservableList<Appointments> getByContactID(int id) throws SQLException {
        Connection con = Database.getConnection();
        ObservableList<Appointments> appointments = FXCollections.observableArrayList();
        String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID FROM Appointments WHERE Contact_ID =?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
             // This is to get the Appointment ID
            int Appointment_ID = rs.getInt("Appointment_ID");
            // This is to get the Title 
            String Title = rs.getString("Title");
            // This is to get the Description
            String Description = rs.getString("Description");
            // This is to get the Location of the Appointment
            String Location = rs.getString("Location");
            // This is to get the Type of the Appointment
            String Type = rs.getString("Type");
            //Getting the time from Database convert to UTC and then get in the Local Time Zone
            Timestamp startTime = rs.getTimestamp("Start");
            Timestamp endTime = rs.getTimestamp("End");
            // Converting the Time from UTC to Local TIme Zone
            ZonedDateTime startLDT = startTime.toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(zoneID);
            ZonedDateTime endLDT = endTime.toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(zoneID);
            // Converting to String the Date Time for Start and End
            String localdatetimestart = startLDT.format(formatter);
            String localdatetimeend = endLDT.format(formatter);

            // Getting the Customer ID, User ID and Contact ID
            int Customer_ID = rs.getInt("Customer_ID");
            int User_ID = rs.getInt("User_ID");
            int Contact_ID = rs.getInt("Contact_ID");
            Appointments appointment = new Appointments(Appointment_ID, Title, Description, Location, Type, localdatetimestart, localdatetimeend, Customer_ID, User_ID, Contact_ID);
            appointments.add(appointment);
        }
        return appointments;
    }
    /**
     * This is to delete the customer by ID in the Appointments table
     * @param id is the Customer ID
     * @return an integer
     * @throws SQLException for an error
     */
    public int deleteByCustomerID(int id) throws SQLException {
        Connection con = Database.getConnection();
        String sql = "DELETE FROM Appointments WHERE Customer_ID=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        int result = ps.executeUpdate();
        return result;
    }
    /**
     * This is to get all the Apppintment Types and the Total Number of Appointments as well as the Month the appointments fall under
     * @return Types observable list which is the customized version of the appointments table
     * @throws SQLException for an error
     */
    public ObservableList<Type> getAllAppointmentTypes() throws SQLException {
        Connection con = Database.getConnection();
        ObservableList<Type> result = FXCollections.observableArrayList();
        String sql = "SELECT MONTHNAME(Start) AS MONTHS, Type, count(*) AS AppointmentTotal FROM APPOINTMENTS GROUP BY MONTHS,TYPE;";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            String month = rs.getString("Months");
            String type = rs.getString("Type");
            Integer total = rs.getInt("AppointmentTotal");
            Type apptType = new Type(month, type, total);
            result.add(apptType);
        }
        return result;
    }
}
