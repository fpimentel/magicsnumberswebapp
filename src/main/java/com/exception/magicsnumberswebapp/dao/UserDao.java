package com.exception.magicsnumberswebapp.dao;

import com.exception.magicsnumbersws.entities.User;
import com.exception.magicsnumbersws.exception.SaveUsersDataException;
import com.exception.magicsnumbersws.exception.SearchAllUserException;
import java.util.List;

/**
 *
 * @author fpimentel
 */
public interface UserDao {

    public List<User> getAllUsers() throws SearchAllUserException;

    public User getUserByCredentials(String userName, String pass) throws SearchAllUserException;

    void saveUsersData(List<User> users) throws SaveUsersDataException;

    public void saveUser(User user) throws SaveUsersDataException;

    public User findUserByUserName(String userName) throws SearchAllUserException;
    
    public void updateUserPassword(int idUser,String newPassword) throws SaveUsersDataException;
}
