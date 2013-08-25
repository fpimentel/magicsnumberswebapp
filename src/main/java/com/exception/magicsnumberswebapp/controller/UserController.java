/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exception.magicsnumberswebapp.controller;

import com.exception.magicsnumberswebapp.service.UserService;
import com.exception.magicsnumbersws.entities.User;
import com.exception.magicsnumbersws.exception.SaveUsersDataException;
import com.exception.magicsnumbersws.exception.SearchAllUserException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;
import org.springframework.context.annotation.Scope;
import java.lang.Exception;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Cristian
 */
@ManagedBean
@Scope
@Controller
public class UserController {

    private List<User> users;
    private Set<User> editedUsers;
       
    @Autowired
    private UserService userService;
    
    public UserController() {
        editedUsers = new HashSet<User>();
    }

    public List<User> getUsers() {
        try {
            this.users= userService.getAllUsers();            
        } catch (SearchAllUserException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);            
        }
        return this.users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
    private void createMessage(String header,String message) {
        FacesMessage msgToAction = new FacesMessage(header,message);

        FacesContext.getCurrentInstance().addMessage(null, msgToAction);
    }

    public void updateUser(RowEditEvent event) {
        
        User user = new User();
        try {
            
            user = ((User) event.getObject());
            
            editedUsers.add(user);
            createMessage("Usuario! ",user.getFirtName() + " " + user.getLastName() + " fue actualizado!");
        } catch (Exception ex) {
            createMessage("Error Actualizando ",  user.getFirtName() + " " + user.getLastName());
        }
    }

    public void cancelUser(RowEditEvent event) {

        User user = new User();
        try {
            user = ((User) event.getObject());
            createMessage("Usuario!",  user.getFirtName() + " " + user.getLastName()+" fue cancelado");
        } catch (Exception ex) {
            createMessage("Error cancelando ",  user.getFirtName() + " " + user.getLastName());
        }
    }
    public void saveUsersData()
    {        
        try 
        {
            if(this.editedUsers.size() > 0){
                userService.saveUsersData(this.editedUsers);
            }
        } catch (SaveUsersDataException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);            
        }        
    }
}
