/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exception.magicsnumberswebapp.dao;

import com.exception.magicsnumbersws.entities.User;
import com.exception.magicsnumbersws.exception.SearchAllUserException;
import java.util.List;

/**
 *
 * @author fpimentel
 */
public interface UserDao {
    public List<User> getAllUsers() throws SearchAllUserException;        
    public User getUserByCredentials(String userName, String pass) throws SearchAllUserException;          
}
