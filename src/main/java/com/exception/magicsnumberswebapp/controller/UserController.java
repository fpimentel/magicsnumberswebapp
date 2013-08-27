/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exception.magicsnumberswebapp.controller;

import com.exception.magicsnumberswebapp.datamodel.UserDataModel;
import com.exception.magicsnumberswebapp.service.StatusService;
import com.exception.magicsnumberswebapp.service.UserService;
import com.exception.magicsnumbersws.entities.Status;
import com.exception.magicsnumbersws.entities.User;
import com.exception.magicsnumbersws.exception.SaveUsersDataException;
import com.exception.magicsnumbersws.exception.SearchAllUserException;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;
import org.springframework.context.annotation.Scope;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
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

    @Autowired
    private UserService userService;
    
    @Autowired
    private StatusService statusService;
    
    private User selectedUser;
    private UserDataModel userDataModel;
    private List<Status> status;
    
    private Status selectedStatus;

    public UserController() {
        
    }

    public Status getSelectedStatus() {
        return selectedStatus;
    }

    public void setSelectedStatus(Status selectedStatus) {
        this.selectedStatus = selectedStatus;
    }
        
    public List<Status> getStatus() {
        if(status == null){
            status = statusService.getStatus();
        }
        return status;        
    }

    public void setStatus(List<Status> status) {
        this.status = status;
    }

    
    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public UserDataModel getUserDataModel() {
        if (this.userDataModel == null) {
            try {                
                this.userDataModel = new UserDataModel(userService.getAllUsers());
            } catch (SearchAllUserException ex) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return userDataModel;
    }

    private void createMessage(String header, String message) {
        FacesMessage msgToAction = new FacesMessage(header, message);

        FacesContext.getCurrentInstance().addMessage(null, msgToAction);
    }


    public void onRowSelect(SelectEvent event) {

        FacesMessage msg = new FacesMessage("User Selected", ((User) event.getObject()).getFirtName());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("Car Unselected", ((User) event.getObject()).getFirtName());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    public void addOrUpdateUser() {  
        RequestContext context = RequestContext.getCurrentInstance();  
        FacesMessage msg = null;  
        boolean success = true;  
        
        if(userAlreadyExist()){
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario ya existe", selectedUser.getUserName());  
            success = false;  
        }                        
                
        FacesContext.getCurrentInstance().addMessage(null, msg);  
        context.addCallbackParam("success", success);  
    }
    
    private boolean userAlreadyExist(){
        List<User> users = userDataModel.getUsers();
        int counterUserExist = 0;
        for(User currUser : users){
            if(currUser.getUserName().equals(selectedUser.getUserName())){
                counterUserExist++;
            }
        }
        return (counterUserExist==2);
    }
    /*
    public void addOrUpdateUser(ActionEvent actionEvent){
        //userDataModel.getUsers();
        RequestContext context = RequestContext.getCurrentInstance();  
        FacesMessage msg = null;  
        boolean success = true;  
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario agregado", "BIEN");    
        FacesContext.getCurrentInstance().addMessage(null, msg);  
        context.addCallbackParam("success", success);                            
    }
    */
}
