package com.exception.magicsnumberswebapp.controller;

import com.exception.magicsnumberswebapp.datamodel.UserDataModel;
import com.exception.magicsnumberswebapp.service.ConsortiumService;
import com.exception.magicsnumberswebapp.service.StatusService;
import com.exception.magicsnumberswebapp.service.UserService;
import com.exception.magicsnumberswebapp.view.converter.ProfileConverter;
import com.exception.magicsnumbersws.entities.BetBanking;
import com.exception.magicsnumbersws.entities.Consortium;
import com.exception.magicsnumbersws.entities.Profile;
import com.exception.magicsnumbersws.entities.Status;
import com.exception.magicsnumbersws.entities.User;
import com.exception.magicsnumbersws.exception.SaveUsersDataException;
import com.exception.magicsnumbersws.exception.SearchAllConsortiumException;
import com.exception.magicsnumbersws.exception.SearchAllUserException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    private Set<BetBanking> availableBetBankings;
    private Set<BetBanking> assignedBetBankings;
    private DualListModel<Consortium> consortiumDualList;
    private DualListModel<BetBanking> betBankingDualList;
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

    public Set<BetBanking> getAvailableBetBankings() {
        if (this.availableBetBankings == null) {
            this.availableBetBankings = new HashSet<BetBanking>();
        }
        return this.availableBetBankings;

    }

    public void setAvailableBetBankings(Set<BetBanking> availableBetBankings) {

        this.availableBetBankings = availableBetBankings;
    }

    public Set<BetBanking> getAssignedBetBankings() {
        if (this.assignedBetBankings == null) {
            this.assignedBetBankings = new HashSet<BetBanking>();
        }
        return assignedBetBankings;
    }

    public void setAssignedBetBankings(Set<BetBanking> assignedBetBankings) {
        this.assignedBetBankings = assignedBetBankings;
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
        this.assignedConsortiums = new ArrayList<Consortium>();
        this.avariableConsortiums = new ArrayList<Consortium>();
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

    public void setAssignedAndAvailableBetBankings(List<Consortium> consortiumsActive) {
        this.availableBetBankings = new HashSet<BetBanking>();
        this.assignedBetBankings = new HashSet<BetBanking>();
        for (Consortium currConsortium : consortiumsActive) {
            List<BetBanking> betBankings = new ArrayList<BetBanking>(currConsortium.getBetBankings());
            //Primer for: bancas del consorcio asociado al usuario logueado
            for (BetBanking betBankingCurrent : betBankings) {
                //Segundo for: bancas del usuario seleccionado 
                boolean isAssigned = false;
                for (BetBanking currentBetBankingSelectedUser : this.selectedUser.getBetBankings()) {
                    if (betBankingCurrent.equals(currentBetBankingSelectedUser)) {
                        this.assignedBetBankings.add(betBankingCurrent);
                        isAssigned = true;
                    }
                }
                //Si la banca del consorcio no existe en el usuario seleccionado se coloca como disponible 
                if (!isAssigned) {
                    isAssigned = false;
                    this.availableBetBankings.add(betBankingCurrent);
                }
            }
        }
        this.betBankingDualList = new DualListModel<BetBanking>(new ArrayList(this.availableBetBankings), new ArrayList(this.assignedBetBankings));
    }

    public void loadAssignedAndAvailableConsortiumActive() {
        List<Consortium> consortiumsActive = new ArrayList<Consortium>();
        if (this.loginController.getUser().getProfile().getId() == com.exception.magicsnumberswebapp.constants.Profile.ADMINISTRATOR.getId()) {
            try {
                //SI es administrador busco todos los consorcios  que estan activos
                //lo mismo sucedera con las bancas
                consortiumsActive = this.consortiumService.findAllConsortiumActive();
                setAssignedAndAvailableConsortium(consortiumsActive);
                setAssignedAndAvailableBetBankings(consortiumsActive);

            } catch (SearchAllConsortiumException ex) {
                Logger.getLogger(UserController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                //Si el usuario no es administrador del sistema solo podra ver y asignar a los usuario
                //consorcios que le pertenezcan lo mismo sucedera con las bancas
                consortiumsActive = this.consortiumService.findAll(this.loginController.getUser().getId());
                setAssignedAndAvailableConsortium(consortiumsActive);
                setAssignedAndAvailableBetBankings(consortiumsActive);


            } catch (SearchAllConsortiumException ex) {
                Logger.getLogger(UserController.class
                        .getName()).log(Level.SEVERE, null, ex);
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

    public DualListModel<BetBanking> getBetBankingDualList() {
        if (this.betBankingDualList == null) {
            this.betBankingDualList = new DualListModel<BetBanking>();
        }
        return this.betBankingDualList;

    }

    public void setBetBankingDualList(DualListModel<BetBanking> betBankingDualList) {
        this.betBankingDualList = betBankingDualList;
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
            Logger.getLogger(UserController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return userDataModel;
    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Usuario", ((User) event.getObject()).getFirtName());
        editMode = true;
        loadAssignedAndAvailableConsortiumActive();

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
            return;
        }
        this.selectedUser.setBetBankings(new HashSet<BetBanking>(this.betBankingDualList.getTarget()));
        this.selectedUser.setConsortiums(new HashSet<Consortium>(this.consortiumDualList.getTarget()));
        try {
            this.selectedUser.setCreationUser(this.loginController.getUser().getUserName());
            this.userService.saveUser(this.selectedUser);
        } catch (SaveUsersDataException ex) {
            success = false;
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            success = false;
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ha ocurrido un error con este usuario", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        getUserDataModel();
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
