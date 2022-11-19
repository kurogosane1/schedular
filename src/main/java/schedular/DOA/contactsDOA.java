package schedular.DOA;
/**
 * @author Syed Khurshid
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import schedular.Model.Contacts;
import schedular.connect.Database;

/**
 * This is the Contacts DOA to access to the database to connect, modify, update and delete contacts
 */
public class ContactsDOA implements DOA<Contacts> {
    /**
     * This is to get the Contact by its ID
     * @param id which is the Contact ID
     * @throws SQLException if an error occurs
     */
    @Override
    public Contacts get(int id) throws SQLException {
        Connection con = Database.getConnection();
        Contacts contact = null;
        String sql = "Select * FROM CONTACTS WHERE Contact_ID =?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
            int customerId = rs.getInt("Contact_ID");
            String customerName = rs.getString("Contact_Name");
            String email = rs.getString("Email");
            contact = new Contacts(customerId, customerName, email);
        }
        return contact;
    }
    /**
     * This is to get All the Contacts
     * @throws SQLException if an error occurs
     */
    @Override
    public ObservableList<Contacts> getAll() throws SQLException {
        Connection conn = Database.getConnection();
        ObservableList<Contacts> contacts = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Contacts";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            int contactID = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String email = rs.getString("Email");
            Contacts contact = new Contacts(contactID, contactName, email);
            contacts.add(contact);
        }
        return contacts;
    }
    
    /**
     * This is to insert a new contact into the Contacts Database
     * @param t which is the Contact object
     * @throws SQLException if an error occurs
     */
    @Override
    public int insert(Contacts t) throws SQLException {
        Connection con = Database.getConnection();
        String sql = "INSERT INTO CONTACTS (Contact_Name, Email) VALUES (?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, t.getContactName());
        ps.setString(2, t.getEmailAddress());
        int result = ps.executeUpdate();
        return result;
    }
    
    /**
     * This is to update a contact with new information in the database
     * @param t which is the Contact object
     * @throws SQLException if an error occurs
     */
    @Override
    public int update(Contacts t) throws SQLException {
        Connection con = Database.getConnection();
        String sql = "UPDATE CONTACTS SET Contact_Name=?,Email=? WHERE Contact_ID=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, t.getContactName());
        ps.setString(2, t.getEmailAddress());
        ps.setInt(3, t.getContact_ID());
        int result = ps.executeUpdate();
        return result;
    }
    /**
     * This is to delete a contact from the database
     * @param t which is the Contact object
     * @throws SQLException if an error occurs
     */
    @Override
    public int delete(Contacts t) throws SQLException {
        Connection con = Database.getConnection();
        String sql = "DELETE FROM CONTACTS WHERE Contact_ID=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, t.getContact_ID());
        int result = ps.executeUpdate();
        return result;
    }
    
}
