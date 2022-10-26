/**
 * Using a seperate database that is not SQL related but rather NOSQL related
 */
package schedular.DOA;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    private static final String GETALL = "SELECT * FROM contacts";
    private static final String INSERT_CONTACT = "INSERT INTO CONTACTS (Contact_Name, Email) VALUES (?,?)";

    public static ObservableList<Contacts> getAllContacts() throws SQLException {
        ObservableList<Contacts> contactsList = FXCollections.observableArrayList();
        PreparedStatement ps = Database.getConnection().prepareStatement(GETALL);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int contactID = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String contactEmail = rs.getString("Email");
            Contacts contact = new Contacts(contactID, contactName, contactEmail);
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
