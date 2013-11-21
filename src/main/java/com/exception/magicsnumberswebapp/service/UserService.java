/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exception.magicsnumberswebapp.service;

import com.exception.magicsnumbersws.entities.User;
import com.exception.magicsnumbersws.exception.SaveUsersDataException;
import com.exception.magicsnumbersws.exception.SearchAllUserException;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author fpimentel
 */
@Service
public interface UserService {

    public List<User> getAllUsers() throws SearchAllUserException;

    public User getUserByCredentials(String userName, String pass) throws SearchAllUserException;

    void saveUsersData(List<User> users) throws SaveUsersDataException;

    public List<User> findUsersByConsortiumIds(int userId) throws SearchAllUserException;
    public void saveUser(User user) throws SaveUsersDataException;
    public User findUserByUserName( String userName) throws SearchAllUserException;
}
