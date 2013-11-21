
package com.exception.magicsnumberswebapp.service.impl;

import com.exception.magicsnumberswebapp.dao.UserDao;
import com.exception.magicsnumberswebapp.service.UserService;
import com.exception.magicsnumbersws.endpoints.SecurityEndPoint;
import com.exception.magicsnumbersws.entities.User;
import com.exception.magicsnumbersws.exception.SaveUsersDataException;
import com.exception.magicsnumbersws.exception.SearchAllUserException;
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
    @Autowired
    private SecurityEndPoint securityEndpoint;
    
    @Override
    public List<User> getAllUsers() throws SearchAllUserException{       
        return userDao.getAllUsers();
    }

    @Override
    public User getUserByCredentials(String userName, String pass) throws SearchAllUserException{        
        return userDao.getUserByCredentials(userName, pass);
    }   

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void saveUsersData(List<User> users) throws SaveUsersDataException {
        userDao.saveUsersData(users);
    }

    @Override
    public List<User> findUsersByConsortiumIds(int userId) throws SearchAllUserException {
        return this.securityEndpoint.findUsersByConsortiumIds(userId);
    }

    @Override
    public void saveUser(User user) throws SaveUsersDataException {
         this.userDao.saveUser(user);
    }

    @Override
    public User findUserByUserName(String userName) throws SearchAllUserException {
        return this.userDao.findUserByUserName(userName);
    }
    
}
