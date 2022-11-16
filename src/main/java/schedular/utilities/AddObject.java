package schedular.utilities;

public interface AddObject {
    void pushToDatabase(Integer id, String title, String description, String location, String type, String startUTC, String endUTC, Integer customerID, Integer UserID, Integer contactID );
}
