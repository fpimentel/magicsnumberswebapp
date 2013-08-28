package com.exception.magicsnumberswebapp.controller;

import com.exception.magicsnumberswebapp.datamodel.UserDataModel;
import com.exception.magicsnumberswebapp.service.StatusService;
import com.exception.magicsnumberswebapp.service.UserService;
import com.exception.magicsnumberswebapp.view.converter.ProfileConverter;
import com.exception.magicsnumbersws.entities.Profile;
import com.exception.magicsnumbersws.entities.Status;
import com.exception.magicsnumbersws.entities.User;
import com.exception.magicsnumbersws.exception.SearchAllUserException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.springframework.context.annotation.Scope;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author fpimentel
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
    private List<Profile> profiles;
    @Autowired
    private ProfileConverter profileConverter;    

    public ProfileConverter getProfileConverter() {
        return profileConverter;
    }

    public void setProfileConverter(ProfileConverter profileConverter) {
        this.profileConverter = profileConverter;
    }
    
    public UserController() {
        
    }

    public List<Profile> getProfiles(String query) {
        List<Profile> suggestions = new ArrayList<Profile>();   
        query = query.toUpperCase();
        for(Profile p : profileConverter.getProfiles()) {  
            if(p.getName().toUpperCase().contains(query))  
                suggestions.add(p);  
        }  
          
        return suggestions;  
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }
    
    
    public Status getSelectedStatus() {
        return selectedStatus;
    }

    public void setSelectedStatus(Status selectedStatus) {
        this.selectedStatus = selectedStatus;
    }

    public List<Status> getStatus() {
        if (status == null) {
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

        FacesMessage msg = new FacesMessage("Usuario", ((User) event.getObject()).getFirtName());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("Usuario", ((User) event.getObject()).getFirtName());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void addOrUpdateUser(ActionEvent actionEvent) {
        String name = "fausto";
         boolean success = true;         
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario ya existe", selectedUser.getUserName());
        List<User> userss = userDataModel.getUsers();
        List<User> userss2 = userDataModel.getUsers();
        /*
        
       

        if (userAlreadyExist()) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario ya existe", selectedUser.getUserName());
            success = false;            
        }                
        */
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("success", success);
    }
    

    private boolean userAlreadyExist() {
        List<User> users = userDataModel.getUsers();
        int counterUserExist = 0;
        for (User currUser : users) {
            if (currUser.getUserName().equals(selectedUser.getUserName())) {
                counterUserExist++;
            }
        }
        return (counterUserExist == 2);
    }

}
