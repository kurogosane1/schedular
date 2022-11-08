/**
 * Using a seperate database that is not SQL related but rather NOSQL related
 */
package schedular.DOA;

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
 * @author Syed Khurshid
 */
public class contactsDOA {
    public contactsDOA() {
    }

    private static final String INSERT_CONTACT = "INSERT INTO CONTACTS (Contact_Name, Email) VALUES (?,?)";
    /**
     * This is to get All the Contacts in a list
     * @return Contacts ObservableList from the database
     * @throws SQLException
     */
    public static ObservableList<Contacts> getAllContacts() throws SQLException {
        Connection con = Database.getConnection();
        ObservableList<Contacts> contactsList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM CONTACTS";
        Statement smt = con.createStatement();
        ResultSet rs = smt.executeQuery(sql);
        while (rs.next()) {
            int contact_ID = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String email = rs.getString("Email");
            Contacts contact = new Contacts(contact_ID, contactName, email);
            contactsList.add(contact);
        }
        return contactsList;
    }
    
    public Contacts create(Contacts dto) {
        try(PreparedStatement ps = Database.getConnection().prepareStatement(INSERT_CONTACT)) {
            ps.setString(1, dto.getContactName());
            ps.setString(2, dto.getEmailAddress());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return null;
    }

    // public static String getGet() {
    //     return GET;
    // }
}
