/**
 * Refactoring code that is being used again and again into a better form
 * More easier interface for Join and GROUP BY can be added here
 */
package schedular.DOA;

/**
 * @author Syed Khurshid
 */
import java.sql.SQLException;
import javafx.collections.ObservableList;

/**
 * Generic Interface for the database
 */
public interface DOA<T> {
    /**
     * This is to get the requested record from the database
     * @param id is the row id 
     * @return Object being requested
     * @throws SQLException if an error occurs on the database side
     */
    T get(int id) throws SQLException;
    /**
     * This is to get all the records of the Objects in the database
     * @return ObservableList of the object
     * @throws SQLException if an error occurs on the database side 
     */
    ObservableList<T> getAll() throws SQLException;
    /**
     * This is to add the specified data to the database
     * @param t can be a generic value of the object
     * @return a result varies
     * @throws SQLException if an error occurs on the database side
     */
    int insert(T t) throws SQLException;
    /**
     * This is to Update the Data
     * @param t can be a generic value of the object
     * @return a result varies
     * @throws SQLException if an error occurs on the database side
     */
    int update(T t) throws SQLException;
    /**
     * This is to Delete the Data from the database and source
     * @param t can be generic value of the object
     * @return a result varies
     * @throws SQLException if an error occurs on the database side
     */
    int delete(T t) throws SQLException;
        // int save(T t) throws SQLException;
}
