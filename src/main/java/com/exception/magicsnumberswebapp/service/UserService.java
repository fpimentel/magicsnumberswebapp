/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exception.magicsnumberswebapp.service;

import com.exception.magicsnumbersws.entities.User;
import java.util.List;

/**
 *
 * @author fpimentel
 */
public interface UserService {
    public List<User> getAllUsers();
    public User getUserByCredentials(String userName, String pass);
}
