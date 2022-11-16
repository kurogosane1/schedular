package schedular.DOA;

/**
 * @author Syed Khurshid
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
     */
    @Override
    public Appointments get(int id) throws SQLException {
        Connection con = Database.getConnection();
        Appointments appointment = null;
        String sql = "SELECT Appointment_ID, Title,Description,Location,Type,Start,End,Customer_ID,User_ID,Contact_ID from Appointments WHERE Appointments_ID=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            int Appointment_ID = rs.getInt("Appointment_ID");
            String Title = rs.getString("Title");
            String Description = rs.getString("Description");
            String Location = rs.getString("Location");
            String Type = rs.getString("Type");
            // String startTime = rs.getString("Start");
            // ZonedDateTime zdt = ZonedDateTime.parse(startTime);
            // String Start = zdt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDateTime ldt = LocalDateTime.parse(rs.getString("Start"), formatter);
            ZonedDateTime zdt = ZonedDateTime.of(ldt, zoneID);
            String Start = zdt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDateTime ldt2 = LocalDateTime.parse(rs.getString("End"), formatter);
            ZonedDateTime zdt2 = ZonedDateTime.of(ldt2, zoneID);
            String End = zdt2.format(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss"));
            int Customer_ID = rs.getInt("Customer_ID");
            int User_ID = rs.getInt("User_ID");
            int Contact_ID = rs.getInt("Contact_ID");
            appointment = new Appointments(Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID);  
        }
        return appointment;
    }
/**
     * Getting All the Countries List from the database
     * No parameter
     */
    @Override
    public ObservableList<Appointments> getAll() throws SQLException {
        Connection con = Database.getConnection();
        ObservableList<Appointments> appointments = FXCollections.observableArrayList();
        String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID FROM Appointments";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            int Appointment_ID = rs.getInt("Appointment_ID");
            String Title = rs.getString("Title");
            String Description = rs.getString("Description");
            String Location = rs.getString("Location");
            String Type = rs.getString("Type");
            LocalDateTime ldt = LocalDateTime.parse(rs.getString("Start"), formatter);
            ZonedDateTime zdt = ZonedDateTime.of(ldt, zoneID);
            String Start = zdt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDateTime ldt2 = LocalDateTime.parse(rs.getString("End"), formatter);
            ZonedDateTime zdt2 = ZonedDateTime.of(ldt2, zoneID);
            String End = zdt2.format(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss"));
            int Customer_ID = rs.getInt("Customer_ID");
            int User_ID = rs.getInt("User_ID");
            int Contact_ID = rs.getInt("Contact_ID");
            Appointments appointment = new Appointments(Appointment_ID, Title, Description, Location, Type, Start, End,
                    Customer_ID, User_ID, Contact_ID);
            appointments.add(appointment);
        }
        return appointments;
    }
    /**
     * This is to add a new appointment to the database
     * @param t the Appointments Object
     */
    @Override
    public int insert(Appointments t) throws SQLException {
        Connection con = Database.getConnection();
        String sql = "INSERT INTO Appointments (Title, Description,Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES (?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, t.getTitle());
        ps.setString(2, t.getDescription());
        ps.setString(3, t.getLocation());
        ps.setString(4, t.getType());
        ps.setString(5, t.getStart());
        ps.setString(6, t.getEnd());
        ps.setInt(7, t.getCustomer_id());
        ps.setInt(8, t.getUser_id());
        ps.setInt(9, t.getContact_id());
        int result = ps.executeUpdate();
        return result;
    }
    
    /**
     * This is to update the Appointments Object module
     * @param t the Appointments Object
     */
    @Override
    public int update(Appointments t) throws SQLException {
        Connection con = Database.getConnection();
        String sql = "UPDATE Appointments SET Appointment_ID=?,Title=?,Description=?,Location=?,Type=?,Start=?,End=?,Customer_ID=?,User_ID=?,Contact_ID=? WHERE Appointment_ID=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, t.getAppointmentID());
        ps.setString(2, t.getTitle());
        ps.setString(3, t.getDescription());
        ps.setString(4, t.getLocation());
        ps.setString(5, t.getType());
        ps.setString(6, t.getStart());
        ps.setString(7, t.getEnd());
        ps.setInt(8, t.getCustomer_id());
        ps.setInt(9, t.getUser_id());
        ps.setInt(10, t.getContact_id());
        ps.setInt(11, t.getAppointmentID());
        int result = ps.executeUpdate();
        return result;
    }
    /**
     * This is to delete The Appointment from the database
     * @param t which is the Appointment object
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
            int Appointment_ID = rs.getInt("Appointment_ID");
            String Title = rs.getString("Title");
            String Description = rs.getString("Description");
            String Location = rs.getString("Location");
            String Type = rs.getString("Type");
            LocalDateTime ldt = LocalDateTime.parse(rs.getString("Start"), formatter);
            ZonedDateTime zdt = ZonedDateTime.of(ldt, zoneID);
            String Start = zdt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDateTime ldt2 = LocalDateTime.parse(rs.getString("End"), formatter);
            ZonedDateTime zdt2 = ZonedDateTime.of(ldt2, zoneID);
            String End = zdt2.format(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss"));
            int Customer_ID = rs.getInt("Customer_ID");
            int User_ID = rs.getInt("User_ID");
            int Contact_ID = rs.getInt("Contact_ID");
            Appointments appointment = new Appointments(Appointment_ID, Title, Description, Location, Type, Start, End,
                    Customer_ID, User_ID, Contact_ID);
            weeklyAppointments.add(appointment);
        }
        return weeklyAppointments;
    }
    
    /**
     * This to get the Monthly Appointments 
     * @return Appointments observableArrayList
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
            int Appointment_ID = rs.getInt("Appointment_ID");
            String Title = rs.getString("Title");
            String Description = rs.getString("Description");
            String Location = rs.getString("Location");
            String Type = rs.getString("Type");
            LocalDateTime ldt = LocalDateTime.parse(rs.getString("Start"), formatter);
            ZonedDateTime zdt = ZonedDateTime.of(ldt, zoneID);
            String Start = zdt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDateTime ldt2 = LocalDateTime.parse(rs.getString("End"), formatter);
            ZonedDateTime zdt2 = ZonedDateTime.of(ldt2, zoneID);
            String End = zdt2.format(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss"));
            int Customer_ID = rs.getInt("Customer_ID");
            int User_ID = rs.getInt("User_ID");
            int Contact_ID = rs.getInt("Contact_ID");
            Appointments appointment = new Appointments(Appointment_ID, Title, Description, Location, Type, Start, End,
                    Customer_ID, User_ID, Contact_ID);
            weeklyAppointments.add(appointment);
        }
        return weeklyAppointments;
    }
    
    /**
     * This is to get all the APpointments by Customer ID
     * @param id which is the Customer ID
     * @return Appointments which is the Observable List
     */
    public ObservableList<Appointments> getByCustomerID(int id) throws SQLException {
        Connection con = Database.getConnection();
        ObservableList<Appointments> appointments = FXCollections.observableArrayList();
        String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID FROM Appointments WHERE Customer_ID =?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int Appointment_ID = rs.getInt("Appointment_ID");
            String Title = rs.getString("Title");
            String Description = rs.getString("Description");
            String Location = rs.getString("Location");
            String Type = rs.getString("Type");
            LocalDateTime ldt = LocalDateTime.parse(rs.getString("Start"), formatter);
            ZonedDateTime zdt = ZonedDateTime.of(ldt, zoneID);
            String Start = zdt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDateTime ldt2 = LocalDateTime.parse(rs.getString("End"), formatter);
            ZonedDateTime zdt2 = ZonedDateTime.of(ldt2, zoneID);
            String End = zdt2.format(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss"));
            int Customer_ID = rs.getInt("Customer_ID");
            int User_ID = rs.getInt("User_ID");
            int Contact_ID = rs.getInt("Contact_ID");
            Appointments appointment = new Appointments(Appointment_ID, Title, Description, Location, Type, Start, End,
                    Customer_ID, User_ID, Contact_ID);
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
        String sql = "SELECT Start as Months,Type, count(*) AS AppointmentTotal FROM APPOINTMENTS GROUP BY Start, Type";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        while (rs.next()) {
            String month = rs.getString("Months");
            LocalDateTime ldt = LocalDateTime.parse(month,formatter);
            String type = rs.getString("Type");
            Integer total = rs.getInt("AppointmentTotal");
            Type apptType = new Type(ldt.getMonth().toString(), type, total);
            result.add(apptType);
        }
        return result;
    }
}
