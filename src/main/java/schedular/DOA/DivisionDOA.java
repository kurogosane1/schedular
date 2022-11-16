/**
 * @author Syed Khurshid
 */
package schedular.DOA;

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
 * Divisions DOA for SQL
 */
public class DivisionDOA implements DOA<FirstLevelDivision>{
/**
 * Get the Division based on ID
 * @param id which is from the Divisions Object
 * @throws SQLException if an error occurs
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
     * @throws SQLException if an error occurs
     */
    @Override
    public ObservableList<FirstLevelDivision> getAll() throws SQLException {
        Connection con = Database.getConnection();
        ObservableList<FirstLevelDivision> divisions = FXCollections.observableArrayList();
        String sql = "SELECT Division_ID,Division,Country_ID  FROM FIRST_LEVEL_DIVISIONS";
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
     * @throws SQLException
     * @return Division
     */
    @Override
    public int insert(FirstLevelDivision t) throws SQLException {
        return 0;
    }
    /**
     * This is to update the Division into the database but this will not be used
     * @param t which is a First Level Division Object
     * @throws SQLException
     * @return Division
     */
    @Override
    public int update(FirstLevelDivision t) throws SQLException {
        return 0;
    }
    /**
     * This is to delete the Division into the database but this will not be used
     * @param t which is a First Level Division Object
     * @throws SQLException
     * @return Division
     */
    @Override
    public int delete(FirstLevelDivision t) throws SQLException {
        return 0;
    }
    
}
