/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exception.magicsnumberswebapp.dao.impl;

import com.exception.magicsnumberswebapp.dao.UserDao;
import com.exception.magicsnumbersws.endpoints.SecurityEndPoint;
import com.exception.magicsnumbersws.entities.User;
import com.exception.magicsnumbersws.exception.SaveUsersDataException;
import com.exception.magicsnumbersws.exception.SearchAllUserException;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author fpimentel
 */
@Repository
public class UserDaoImpl implements UserDao{
   
    @Autowired
    private SecurityEndPoint magicNumberWSClient;
    
    private List<User> users;
    private boolean usersDataLoaded;

    public UserDaoImpl() {
        
    }    

    public SecurityEndPoint getMagicNumberWSClient() {
        return magicNumberWSClient;
    }

    public void setMagicNumberWSClient(SecurityEndPoint magicNumberWSClient) {
        this.magicNumberWSClient = magicNumberWSClient;
    }       
   
    public List<User> getUsers() {
        return users;
    }
    public void setUsers(List<User> users) {
        this.users = users;
    }        
    @Override
    public List<User> getAllUsers() throws SearchAllUserException{        
        if(users == null){
            users = magicNumberWSClient.getAllUsers();
        }        
        return users;
    }        

    @Override
    public User getUserByCredentials(String userName, String pass) throws SearchAllUserException{
       if(users == null)
        {
            users = getAllUsers();
        }
        for(User currentUser : users){
            boolean isValidUser =  currentUser.getUserName().equals(userName) 
                                    && currentUser.getPassword().equals(pass); 
            if(isValidUser){
                return currentUser;
            }
        }                    
        return null;
    }

    @Override
    public void saveUsersData(Set<User> users) throws SaveUsersDataException {
        magicNumberWSClient.saveUsersData(users);
    }


}
