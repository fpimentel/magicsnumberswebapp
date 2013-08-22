
package com.exception.magicsnumberswebapp.service.impl;

import com.exception.magicsnumberswebapp.dao.UserDao;
import com.exception.magicsnumberswebapp.service.UserService;
import com.exception.magicsnumbersws.entities.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author fpimentel
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;
    
    @Override
    public List<User> getAllUsers() {       
        return userDao.getAllUsers();
    }

    @Override
    public User getUserByCredentials(String userName, String pass) {        
        return userDao.getUserByCredentials(userName, pass);
    }   

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
    
}
