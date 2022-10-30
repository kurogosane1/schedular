package schedular.DOA;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.ObservableList;
import schedular.Model.Customer;
import schedular.connect.Database;

public class CustomerDOAImpl implements CustomerDOA{
    //Retrieve customer
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

    @Override
    public ObservableList<Customer> getAll() throws SQLException {
        Connection con = Database.getConnection();
        return null;
    }

    @Override
    public int save(Customer customer) throws SQLException {
    
        return 0;
    }

    @Override
    public int insert(Customer customer) throws SQLException {
              return 0;
    }

    @Override
    public int update(Customer customer) throws SQLException {
   
        return 0;
    }

    @Override
    public int delete(Customer customer) throws SQLException {

        return 0;
    }
    
}
