package com.exception.magicsnumberswebapp.controller;

import com.exception.magicsnumberswebapp.datamodel.UserDataModel;
import com.exception.magicsnumberswebapp.service.ConsortiumService;
import com.exception.magicsnumberswebapp.service.StatusService;
import com.exception.magicsnumberswebapp.service.UserService;
import com.exception.magicsnumberswebapp.view.converter.ProfileConverter;
import com.exception.magicsnumbersws.entities.Consortium;
import com.exception.magicsnumbersws.entities.Profile;
import com.exception.magicsnumbersws.entities.Status;
import com.exception.magicsnumbersws.entities.User;
import com.exception.magicsnumbersws.exception.SaveUsersDataException;
import com.exception.magicsnumbersws.exception.SearchAllConsortiumException;
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
    private LoginController loginController;
    @Autowired
    private StatusService statusService;
    @Autowired
    private ConsortiumService consortiumService;
    private List<User> updatedUsers;
    private User selectedUser;
    private boolean editMode = false;
    private UserDataModel userDataModel;
    private List<Status> status;
    private Status selectedStatus;
    private List<Consortium> avariableConsortiums;
    private List<Consortium> assignedConsortiums;
    private DualListModel<Consortium> consortiumDualList;
    @Autowired
    private ProfileConverter profileConverter;
    private List<SelectItem> categories;
    private String selection;

    public UserController() {
        updatedUsers = new ArrayList<User>();
        this.assignedConsortiums = new ArrayList<Consortium>();
        this.avariableConsortiums = new ArrayList<Consortium>();
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public ProfileConverter getProfileConverter() {

        return profileConverter;
    }

    public List<Consortium> getAvariableConsortiums() {
        if (this.avariableConsortiums == null) {
            this.avariableConsortiums = new ArrayList<Consortium>();
        }
        return avariableConsortiums;
    }

    public void setAvariableConsortiums(List<Consortium> avariableConsortiums) {
        this.avariableConsortiums = avariableConsortiums;
    }

    public DualListModel<Consortium> getConsortiumDualList() {
        if (this.consortiumDualList == null) {
            this.consortiumDualList = new DualListModel<Consortium>();
        }
        return consortiumDualList;
    }

    public List<Consortium> getAssignedConsortiums() {
        if (this.assignedConsortiums == null) {
            this.assignedConsortiums = new ArrayList<Consortium>();
        }
        return assignedConsortiums;
    }

    public void setAssignedConsortiums(List<Consortium> assignedConsortiums) {
        this.assignedConsortiums = assignedConsortiums;
    }

    public void setConsortiumDualList(DualListModel<Consortium> consortiumDualList) {
        this.consortiumDualList = consortiumDualList;
    }

    public void setProfileConverter(ProfileConverter profileConverter) {
        this.profileConverter = profileConverter;
    }

    public void setAssignedAndAvailableConsortium(List<Consortium> consortiumsActive) {
        for (Consortium currConsortium : consortiumsActive) {
            List<User> users = new ArrayList<User>(currConsortium.getUsers());
            //validamos si el consorcio actual esta asignado al usuario y lo agregamos en la lista de asignado 
            //si la misma no lo contiene de lo contrario lo agregamos en la lista de disponible
            if (users.contains(this.selectedUser)) {
                if (!this.assignedConsortiums.contains(currConsortium)) {
                    this.assignedConsortiums.add(currConsortium);
                }
            } else {
                if (!this.avariableConsortiums.contains(currConsortium)) {
                    this.avariableConsortiums.add(currConsortium);
                }
            }
        }
        this.consortiumDualList = new DualListModel<Consortium>(this.avariableConsortiums, this.assignedConsortiums);
    }

    public void loadAssignedAndAvailableConsortiumActive() {
        List<Consortium> consortiumsActive = new ArrayList<Consortium>();
        if (this.loginController.getUser().getProfile().getId() == com.exception.magicsnumberswebapp.constants.Profile.ADMINISTRATOR.getId()) {
            try {
                //SI es administrador busco todos los consorcios  que estan activos
                consortiumsActive = this.consortiumService.findAllConsortiumActive();
                setAssignedAndAvailableConsortium(consortiumsActive);
            } catch (SearchAllConsortiumException ex) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                //Si el usuario no es administrador del sistema solo podra ver y asignar a los usuario
                //consorcios que le pertenezcan
                consortiumsActive = this.consortiumService.findAll(this.selectedUser.getId());
                setAssignedAndAvailableConsortium(consortiumsActive);
            } catch (SearchAllConsortiumException ex) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public List<Profile> getAutoCompleteProfiles(String query) {
        List<Profile> suggestions = new ArrayList<Profile>();
        query = query.toUpperCase();
        for (Profile p : profileConverter.getProfiles()) {
            if (p.getName().toUpperCase().contains(query)) {
                suggestions.add(p);
            }
        }
        //validamos que si no es el administrador del sistema no muestre el perfil administrador
        //y asi evitamos que  un lacayo pueda asignar a x usuario un perfil de administrador
        if (this.loginController.getUser().getProfile().getId()
                != com.exception.magicsnumberswebapp.constants.Profile.ADMINISTRATOR.getId()) {
            Profile profileAdminitrator = new Profile();
            profileAdminitrator.setId(com.exception.magicsnumberswebapp.constants.Profile.ADMINISTRATOR.getId());
            suggestions.remove(profileAdminitrator);
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
                //SI es administrador del sistema buscamos todos los usuarios
                if (this.loginController.getUser().getProfile().getId() == com.exception.magicsnumberswebapp.constants.Profile.ADMINISTRATOR.getId()) {
                    this.userDataModel = new UserDataModel(userService.getAllUsers());
                } else {
                    //SI no es administrador buscamos todos los usuarios de los consorcios asociados al usuario logeado
                    List<User> users = this.userService.findUsersByConsortiumIds(this.loginController.getUser().getId());
                    this.userDataModel = new UserDataModel(new ArrayList<User>(users));
                }

            } catch (SearchAllUserException ex) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return userDataModel;
    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Usuario", ((User) event.getObject()).getFirtName());
        editMode = true;
        loadAssignedAndAvailableConsortiumActive();
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
