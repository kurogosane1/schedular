/**
 * Using noSQL datbase
 * SQL injection protection can further inhance the security of the App
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import schedular.Model.FirstLevelDivision;
import schedular.connect.Database;

/**
 * Divisions DOA for First Level devision for Creation, Read, Update and Delete with SQL database connection
 */
public class DivisionDOA implements DOA<FirstLevelDivision>{
/**
 * Get the Division based on ID
 * @param id which is from the Divisions Object
 * @throws SQLException if an error occurs on SQL database side
 * @return FirstLevelDivision which is First Level Devision object
 */
    @Override
    public FirstLevelDivision get(int id) throws SQLException {
        Connection con = Database.getConnection();
        FirstLevelDivision div = null;
        String sql = "SELECT Division_ID, Division, Country_ID FROM FIRST_LEVEL_DIVISIONS WHERE Division_ID=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int ids = rs.getInt("Division_ID");
            String divname = rs.getString("Division");
            int country_id = rs.getInt("Country_ID");
            div = new FirstLevelDivision(ids, divname, country_id);

        }
        return div;
    }
    
    /**
     * Getting all the Divisions from the database
     * We will be using this to get the Customer information
     * @throws SQLException if an error occurs on SQL database side
     * @return divisions which is a ObservableList FirstLevelDivision array
     */
    @Override
    public ObservableList<FirstLevelDivision> getAll() throws SQLException {
        Connection con = Database.getConnection();
        ObservableList<FirstLevelDivision> divisions = FXCollections.observableArrayList();
        String sql = "SELECT Division_ID,Division,Country_ID FROM FIRST_LEVEL_DIVISIONS";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            int Division_ID = rs.getInt("Division_ID");
            String fDivision = rs.getString("Division");
            int Country_ID = rs.getInt("Country_ID");
            FirstLevelDivision division = new FirstLevelDivision(Division_ID, fDivision, Country_ID);
            divisions.add(division);
        }
        return divisions;
    }
    
    /**
     * This is to add the Division into the database but this will not be used
     * @param t which is a First Level Division Object
     * @throws SQLException if an error occurs on the database side
     * @return Division which is the FirstLevelDivision Object
     */
    @Override
    public int insert(FirstLevelDivision t) throws SQLException {
        return 0;
    }
    /**
     * This is to update the Division into the database but this will not be used
     * @param t which is a First Level Division Object
     * @throws SQLException if an error occurs on the database side
     * @return Division which is the First Level Divisions Object
     */
    @Override
    public int update(FirstLevelDivision t) throws SQLException {
        return 0;
    }
    /**
     * This is to delete the Division into the database but this will not be used
     * @param t which is a First Level Division Object
     * @throws SQLException if an error occurs on the database side
     * @return Division which is the First Level Divisions Object
     */
    @Override
    public int delete(FirstLevelDivision t) throws SQLException {
        return 0;
    }
    
    /**
     * This is to get all the divisions that Match the ID
     * @param id which is the Country ID 
     * @return divisions which is the First Level Divisions Object array in an observableArrayList
     */
    public ObservableList<FirstLevelDivision> getAllByID(int id) throws SQLException {
        Connection con = Database.getConnection();
        ObservableList<FirstLevelDivision> divisions = FXCollections.observableArrayList();
        String sql = "SELECT Division_ID, Division, Country_ID FROM FIRST_LEVEL_DIVISIONS WHERE Country_ID=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int ids = rs.getInt("Division_ID");
            String divname = rs.getString("Division");
            int country_id = rs.getInt("Country_ID");
            FirstLevelDivision div = new FirstLevelDivision(ids, divname, country_id);
            divisions.add(div);
        }
        return divisions;
    }
    /**
     * This is to get the Division ID From the database
     * @param name this is name of the Division
     * @return id this is the Division ID that is returned
     * @throws SQLException in case of an error on the SQL side
     */
    public int getDivisionID(String name) throws SQLException {
        Connection con = Database.getConnection();
        int id = 0;
        String sql = "SELECT Division_ID FROM FIRST_LEVEL_DIVISIONS WHERE Division=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            id = rs.getInt("Division_ID");
        }
        return id;
    }
    /**
     * This is to get the Country ID for the divisions
     * @param name this is the Division name passed
     * @return id which is the COuntry ID
     * @throws SQLException if an error occurs
     */
     public int getCountryID(String name) throws SQLException {
        Connection con = Database.getConnection();
        int id=0;
        String sql = "SELECT Country_ID FROM FIRST_LEVEL_DIVISIONS WHERE Division=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            id = rs.getInt("Country_ID");
        }
        return id;
    }
}
