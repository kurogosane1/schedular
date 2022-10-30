package schedular.DOA;
import java.sql.SQLException;

import javafx.collections.ObservableList;

/**
 * Generic Interface for the database
 */
public interface DOA<T> {
    T get(int id) throws SQLException;

    ObservableList<T> getAll() throws SQLException;

    int save(T t) throws SQLException;

    int insert(T t) throws SQLException;

    int update(T t) throws SQLException;

    int delete(T t) throws SQLException;
    
}
