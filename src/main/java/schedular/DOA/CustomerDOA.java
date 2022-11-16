/**
 * refactoring code that is being used again and again
 * @author Syed Khurshid
 */
package schedular.DOA;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import schedular.Model.Customer;
import schedular.Model.DivisionInfoTable;
import schedular.connect.Database;
/**
 * Customers DOA for SQL
 */
public class CustomerDOA implements DOA<Customer> {
    /**
     * Retrieve individual customers
     * @param id which is from the Customers Object
     * @throws SQLException if an error occurs
     */
    @Override
    public Customer get(int id) throws SQLException {
        Connection con = Database.getConnection();
        Customer customer = null;
        String sql = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID FROM CUSTOMERS Where Customer_ID = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int old = rs.getInt("Customer_ID");
            String Customer_Name = rs.getString("Customer_Name");
            String Address = rs.getString("Address");
            String Postal_Code = rs.getString("Postal_Code");
            String Phone = rs.getString("Phone");
            int Division_ID = rs.getInt("Division_ID");

            customer = new Customer(old, Customer_Name, Address, Postal_Code, Phone, Division_ID);
        }
        return customer;
    }
    
    /**
     * Retrieve all the Customers from the database
     * @throws SQLException if an error occurs
     */
    @Override
    public ObservableList<Customer> getAll() throws SQLException {
        Connection con = Database.getConnection();
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        String sql = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID FROM CUSTOMERS";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            int Customer_ID = rs.getInt("Customer_ID");
            String Customer_Name = rs.getString("Customer_Name");
            String Address = rs.getString("Address");
            String Postal_Code = rs.getString("Postal_Code");
            String Phone = rs.getString("Phone");
            int Division_ID = rs.getInt("Division_ID");
            Customer customer = new Customer(Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID);
            customers.add(customer);
        }
        return customers;
    }
    
    /**This is to add a Customer to the database
     * @param t which is a Customer Object
     * @throws SQLException if an error occurs
     */
    @Override
    public int insert(Customer t) throws SQLException {
        Connection con = Database.getConnection();
        String sql = "INSERT INTO CUSTOMERS (Customer_Name,Address, Postal_Code, Phone,Create_Date,Created_By,Last_Update,Last_Updated_By,Division_ID) VALUES (?,?,?,?,NOW(),?,NOW(),?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, t.getCustomerName());
        ps.setString(2, t.getCustomerAddress());
        ps.setString(3, t.getPostalCode());
        ps.setString(4, t.getPhoneNumber());
        // ps.setString(5, LocalDateTime.now().toString());
        ps.setString(5, "Saad");
        // ps.setString(7, LocalDateTime.now().toString());
        ps.setString(6, "Saad");
        ps.setInt(7, t.getDivision_id());
        int result = ps.executeUpdate();
        return result;
    }
    
    /**
     * This is to update Customer in the Customer Database
     * @param t which is the Customer Object
     * @throws SQLException if an error occurs
     */
    @Override
    public int update(Customer t) throws SQLException {
        Connection con = Database.getConnection();
        String sql = "UPDATE CUSTOMERS SET Customer_ID=?,Customer_Name=?,Address=?,Postal_Code=?,Phone=?,Create_Date=?,Created_By=?,Last_Update=?,Last_Updated_By=?,Division_ID=? WHERE Customer_ID=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, t.getCustomerId());
        ps.setString(2, t.getCustomerName());
        ps.setString(3, t.getCustomerAddress());
        ps.setString(4, t.getPostalCode());
        ps.setString(5, t.getPhoneNumber());
        ps.setString(6, LocalDateTime.now().toString());
        ps.setString(7, "Saad"); // Later have the user here
        ps.setString(8, LocalDateTime.now().toString());
        ps.setString(9, "Saad");
        ps.setInt(10, t.getDivision_id());
        ps.setInt(11, t.getCustomerId());
        int result = ps.executeUpdate();
        return result;
    }
    
    /**
     * Deleting the Customer Data from the database
     * @param t which is the Customer Object
     * @throws SQLException if an error occurs
     */
    @Override
    public int delete(Customer t) throws SQLException {
        Connection con = Database.getConnection();
        String sql = "DELETE FROM CUSTOMERS WHERE Customer_ID=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, t.getCustomerId());
        int result = ps.executeUpdate();
        return result;
    }
    /**
     * This is to get the converted Table for helping into determining the number of Customers under a division
     * @return DivisionInfoTable ObservableList Array Object which shows the Divisions name and the total number of customers under the division
     * @throws SQLException for SQLException when they occur
     */
    public ObservableList<DivisionInfoTable> getDivisions() throws SQLException {
        Connection con = Database.getConnection();
        ObservableList<DivisionInfoTable> result = FXCollections.observableArrayList();
        String sql = "SELECT FIRST_LEVEL_DIVISIONS.Division AS DIVNAME, COUNT(CUSTOMERS.Customer_Name) AS CUSTOMERTOTAL FROM CUSTOMERS INNER JOIN FIRST_LEVEL_DIVISIONS on CUSTOMERS.Division_ID=FIRST_LEVEL_DIVISIONS.Division_ID GROUP BY DIVNAME";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            String div = rs.getString("DIVNAME");
            Integer total = rs.getInt("customerTotal");
            DivisionInfoTable divs = new DivisionInfoTable(div, total);
            result.add(divs);
        }
        return result;
    } 
}
