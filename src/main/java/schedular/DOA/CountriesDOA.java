/**
 * This is the Countries Database Object Module
 * Refactoring code especially that are being reused again and again
 * 
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
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import schedular.Model.Countries;
import schedular.connect.Database;

/**
 * This is the Countries Data Base Object interface to get all the Countries from the Database
 */
public class CountriesDOA implements DOA<Countries>{
    
    /** 
     * @see schedular.DOA.DOA#get(int)
     * @param id which is the ID of the country
     * @throws SQLException which in case of SQL database error side
     * This is to see if the requested Country exists or not in the database based on the ID
     */
    @Override
    public Countries get(int id) throws SQLException {
        Connection con = Database.getConnection();
        Countries country = null;
        String sql = "SELECT Country_ID, Country from COUNTRIES WHERE Country_ID=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int count_id = rs.getInt("Country_ID");
            String Country = rs.getString("Country");
            country = new Countries(count_id, Country);
        }
        Database.closePreparedStatement(ps);
        Database.closeResultSet(rs);
        return country;
    };
    
    /**
     * Getting all the countries list from the database.
     * No parameter
     * @throws SQLException if an error occurs on SQL database side 
     */
    @Override
    public ObservableList<Countries> getAll() throws SQLException {
        Connection con = Database.getConnection();
        ObservableList<Countries> countries = FXCollections.observableArrayList();
        String sql = "SELECT Country_ID, Country FROM COUNTRIES";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            int Country_ID = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");
            Countries country = new Countries(Country_ID, countryName);
            countries.add(country);
        }
        Database.closeStatement(stmt);
        Database.closeResultSet(rs);
        return countries;
    }
    /**
     * Inserting a new Country into the database
     * @param t which is the Countries Object
     * @throws SQLException which in case of a SQL Error
     */
    @Override
    public int insert(Countries t) throws SQLException {
        Connection con = Database.getConnection();
        String sql = "INSERT INTO COUNTRIES (Country, Create_Date, Created_By, Last_Update, Last_Updated_By) VALUES (?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, t.getCountry());
        LocalDateTime start = LocalDateTime.now();
        ps.setString(2, start.toString());
        ps.setString(3, "Saad");
        ps.setString(4, LocalDateTime.now().toString());
        ps.setString(5, "Saad");
        int result = ps.executeUpdate();
        Database.closePreparedStatement(ps);
        return result;
    }
    
    /**
     * Updating a Country value in the Countries Database
     * @param t which is the Countries t then selected to be updated in the database
     * @throws SQLException which in case of SQL error
     */
    @Override
    public int update(Countries t) throws SQLException {
        Connection con = Database.getConnection();
        String sql = "UPDATE COUNTRIES SET Country_ID=?,Country=? WHERE Country_ID = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, t.getCountryID());
        ps.setString(2, t.getCountry());
        ps.setInt(3, t.getCountryID()); // This is to get SQL to check the Country ID as an extra parameter
        int result = ps.executeUpdate();
        return result;
    }
    /**
     * Deleting a value in a database
     * @param t Countries Object which is then used to delete the country value from the database
     * @throws SQLException which in case of SQL error
     */
    @Override
    public int delete(Countries t) throws SQLException {
        Connection con = Database.getConnection();
        String sql = "DELETE FROM COUNTRIES WHERE Country_ID=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, t.getCountryID());
        int result = ps.executeUpdate();
        return result;
    }
    /**
     * This is to get the Country Object by its name
     * @param countries is the Country name
     * @return country which is a Country Object
     * @throws SQLException which in case of SQL error occurs
     */
    public Countries getIdByCountryName(String name) throws SQLException {
        Connection con = Database.getConnection();
        Countries country = null;
        String sql = "SELECT Country_ID, Country from COUNTRIES WHERE Country=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int count_id = rs.getInt("Country_ID");
            String Country = rs.getString("Country");
            country = new Countries(count_id, Country);
        }
        Database.closePreparedStatement(ps);
        Database.closeResultSet(rs);
        return country;
    };
}
