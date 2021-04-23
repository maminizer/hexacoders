package IService;

import java.sql.SQLException;
import java.util.List;
import Entity.User;

public abstract class IServiceUser {
    public abstract void addUser(User u) throws SQLException;

    public abstract List<User> ShowUsers() throws SQLException ;
}