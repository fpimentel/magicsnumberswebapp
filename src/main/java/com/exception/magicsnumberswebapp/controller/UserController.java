package com.exception.magicsnumberswebapp.controller;

import com.exception.magicsnumberswebapp.datamodel.UserDataModel;
import com.exception.magicsnumberswebapp.service.StatusService;
import com.exception.magicsnumberswebapp.service.UserService;
import com.exception.magicsnumberswebapp.view.converter.ProfileConverter;
import com.exception.magicsnumbersws.entities.Profile;
import com.exception.magicsnumbersws.entities.Status;
import com.exception.magicsnumbersws.entities.User;
import com.exception.magicsnumbersws.exception.SaveUsersDataException;
import com.exception.magicsnumbersws.exception.SearchAllUserException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.springframework.context.annotation.Scope;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;
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
    private List<User> updatedUsers;
    private User selectedUser;
    private boolean editMode = false;
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
        updatedUsers = new ArrayList<User>();
    }

    public List<Profile> getProfiles(String query) {
        List<Profile> suggestions = new ArrayList<Profile>();
        query = query.toUpperCase();
        for (Profile p : profileConverter.getProfiles()) {
            if (p.getName().toUpperCase().contains(query)) {
                suggestions.add(p);
            }
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
        editMode = true;
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("Usuario", ((User) event.getObject()).getFirtName());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /*public void addOrUpdateUser(FacesContext context, UIComponent validate, Object value) {
     boolean success = true;
     FacesMessage msg;
     RequestContext reqContext = RequestContext.getCurrentInstance();
     if (userAlreadyExist(value.toString())) {
     msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario ya existe", value.toString());
     success = false;
     throw new ValidatorException(msg);
     }
     if (updatedUsers.contains(selectedUser)) {
     for (User currUser : updatedUsers) {
     if (currUser.getId() == selectedUser.getId()) {
     currUser = selectedUser;
     }
     }
     } else {
     updatedUsers.add(selectedUser);
     }
     reqContext.addCallbackParam("success", success);
     }*/
    public void addOrUpdateUser(ActionEvent event) {
        boolean success = true;
        FacesMessage msg;
        RequestContext reqContext = RequestContext.getCurrentInstance();
        if (userAlreadyExist()) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario ya existe", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            success = false;
        }
        if (editMode) {
            User userVo = new User();
            userVo.setId(selectedUser.getId());
            int index = userDataModel.getUsers().indexOf(userVo);
            userDataModel.getUsers().set(index, selectedUser);
            int updatedUserIndex = updatedUsers.indexOf(userVo);
            if (updatedUserIndex >= 0) {
                updatedUsers.set(updatedUserIndex, selectedUser);
            } else {
                updatedUsers.add(selectedUser);
            }
            editMode = false;
        }
        reqContext.addCallbackParam("success", success);
    }

    public void saveAll() {
        FacesMessage msg;
        boolean success = true;
        RequestContext reqContext = RequestContext.getCurrentInstance();
        try {
            userService.saveUsersData(updatedUsers);
        } catch (SaveUsersDataException ex) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ocurrio un error guardando la informacion de los usuarios.", null);
            success = false;
            throw new ValidatorException(msg);
        }
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Grabado exitosamente", null);
        FacesContext.getCurrentInstance().addMessage(null, msg);
        //reqContext.addCallbackParam("success", success);
    }

    private boolean userAlreadyExist() {
        List<User> users = userDataModel.getUsers();
        boolean userExist = true;
        if (editMode) {
            for (User currUser : users) {
                if (!currUser.equals(selectedUser)) {
                    if (currUser.getUserName().equals(selectedUser.getUserName())) {
                        return userExist;
                    }
                }
            }
        }
        return !userExist;
    }
}
