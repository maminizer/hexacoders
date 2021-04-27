/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IServices;

import Entities.User;
import java.sql.SQLException;
import javafx.collections.ObservableList;

/**
 *
 * @author khamm
 */
public interface IServiceUser {
    public ObservableList<User> AfficheUser(String username) throws SQLException;
}
