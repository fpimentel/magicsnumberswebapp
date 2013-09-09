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
import javax.faces.context.FacesContext;
import org.springframework.context.annotation.Scope;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author fpimentel
 */
@Controller
@Scope("view")
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
    private List<Profile> avariableProfiles;
    private List<Profile> selecetedProfiles;
    private DualListModel<Profile> profileDualList;
    @Autowired
    private ProfileConverter profileConverter;
    private List<SelectItem> categories;
    private String selection;

    public UserController() {
        avariableProfiles = new ArrayList<Profile>();
        avariableProfiles.add(new Profile(1, "Fausto", null, null, null, 1));
        avariableProfiles.add(new Profile(1, "Pedro", null, null, null, 1));
        selecetedProfiles = new ArrayList<Profile>();
        profileDualList = new DualListModel<Profile>(avariableProfiles, selecetedProfiles);
        updatedUsers = new ArrayList<User>();

    }

    public ProfileConverter getProfileConverter() {
        return profileConverter;
    }

    public DualListModel<Profile> getProfileDualList() {
        return profileDualList;
    }

    public void setProfileDualList(DualListModel<Profile> profileDualList) {
        this.profileDualList = profileDualList;
    }

    public List<Profile> getAvariableProfiles() {
        return avariableProfiles;
    }

    public void setAvariableProfiles(List<Profile> avariableProfiles) {
        this.avariableProfiles = avariableProfiles;
    }

    public List<Profile> getSelecetedProfiles() {
        return selecetedProfiles;
    }

    public void setSelecetedProfiles(List<Profile> selecetedProfiles) {
        this.selecetedProfiles = selecetedProfiles;
    }

    public void setProfileConverter(ProfileConverter profileConverter) {
        this.profileConverter = profileConverter;
    }

    public List<Profile> getAutoCompleteProfiles(String query) {
        List<Profile> suggestions = new ArrayList<Profile>();
        query = query.toUpperCase();
        for (Profile p : profileConverter.getProfiles()) {
            if (p.getName().toUpperCase().contains(query)) {
                suggestions.add(p);
            }
        }

        return suggestions;
    }

    public List<SelectItem> getCategories() {
        return categories;
    }

    public void setCategories(List<SelectItem> categories) {
        this.categories = categories;
    }

    public String getSelection() {
        return selection;
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }

    public List<Profile> getProfiles() {
        return profileConverter.getProfiles();
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
        if (selectedUser == null) {
            selectedUser = new User();
        }
        return selectedUser;
    }

    public void resetData(ActionEvent event) {
        selectedUser = new User();
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public void addNewUser() {
        this.selectedUser = new User();
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

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Usuario", ((User) event.getObject()).getFirtName());
        editMode = true;
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("Usuario", ((User) event.getObject()).getFirtName());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void addOrUpdateUser(ActionEvent event) {
        boolean success = true;
        FacesMessage msg;
        RequestContext reqContext = RequestContext.getCurrentInstance();
        if (userAlreadyExist()) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario ya existe", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            success = false;
            return;
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
        } else {
            selectedUser.setId(userDataModel.nextUserId());
            updatedUsers.add(selectedUser);
            userDataModel.getUsers().add(selectedUser);
        }
        selectedUser = null;
        reqContext.addCallbackParam("success", success);
    }

    public void saveAll() {
        FacesMessage msg;
        boolean success = true;
        RequestContext reqContext = RequestContext.getCurrentInstance();
        try {
            if (updatedUsers.size() > 0) {
                userService.saveUsersData(updatedUsers);
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No hay nuevos cambios para registrar", null);
                success = false;
                throw new ValidatorException(msg);
            }

        } catch (SaveUsersDataException ex) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ocurrio un error guardando la informacion de los usuarios.", null);
            success = false;
            throw new ValidatorException(msg);
        }
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Grabado exitosamente", null);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    private boolean userAlreadyExist() {
        List<User> users = userDataModel.getUsers();
        boolean userExist = true;
        for (User currUser : users) {
            if (editMode) {
                if (!currUser.equals(selectedUser)) {
                    if (currUser.getUserName().equals(selectedUser.getUserName())) {
                        return userExist;
                    }
                }
            } else {
                if (currUser.getUserName().equals(selectedUser.getUserName())) {
                    return userExist;
                }
            }
        }
        return !userExist;
    }
}
