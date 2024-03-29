package com.exception.magicsnumberswebapp.dao.impl;
import com.exception.magicsnumberswebapp.dao.UserDao;
import com.exception.magicsnumbersws.endpoints.SecurityEndPoint;
import com.exception.magicsnumbersws.entities.User;
import com.exception.magicsnumbersws.exception.SaveUsersDataException;
import com.exception.magicsnumbersws.exception.SearchAllUserException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author fpimentel
 */
@Repository
public class UserDaoImpl implements UserDao{
   
    @Autowired
    private SecurityEndPoint securityEndpoint;
    private int ACTIVO = 1;
    private List<User> users;
    private boolean usersDataLoaded;

    public UserDaoImpl() {
        
    }    

    public SecurityEndPoint getSecurityEndpoint() {
        return securityEndpoint;
    }

    public void setSecurityEndpoint(SecurityEndPoint securityEndpoint) {
        this.securityEndpoint = securityEndpoint;
    }

   
   
    public List<User> getUsers() {
        return users;
    }
    public void setUsers(List<User> users) {
        this.users = users;
    }        
    @Override
    public List<User> getAllUsers() throws SearchAllUserException{        
        //if(users == null){
            users = securityEndpoint.getAllUsers();
        //}        
        return users;
    }        

    @Override
    public User getUserByCredentials(String userName, String pass) throws SearchAllUserException{         
        return securityEndpoint.getUserByCredential(userName, pass);       
       /*if(users == null)
        {
            users = getAllUsers();
        }
        for(User currentUser : users){
            boolean isValidUser =  currentUser.getUserName().equals(userName) 
                                    && currentUser.getPassword().equals(pass) 
                                    && currentUser.getStatus().getId() == ACTIVO;             
            if(isValidUser){
                return currentUser;
            }
        }                    
        return null;*/
    }

    @Override
    public void saveUsersData(List<User> users) throws SaveUsersDataException {
        securityEndpoint.saveUsersData(users);
    }

    @Override
    public void saveUser(User user) throws SaveUsersDataException {
        this.securityEndpoint.saveUser(user);
    }

    @Override
    public User findUserByUserName(String userName) throws SearchAllUserException {
        return this.securityEndpoint.findUserByUserName(userName);
    }

    @Override
    public void updateUserPassword(int idUser, String newPassword) throws SaveUsersDataException {
       this.securityEndpoint.updateUserPassword(idUser, newPassword);
    }
}
