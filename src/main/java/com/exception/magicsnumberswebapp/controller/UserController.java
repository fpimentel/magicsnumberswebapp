package com.exception.magicsnumberswebapp.controller;

import com.exception.magicsnumberswebapp.datamodel.UserDataModel;
import com.exception.magicsnumberswebapp.service.BetBankingService;
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
import com.exception.magicsnumbersws.exception.SearchAllBetBankingException;
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
    @Autowired
    private BetBankingService betBankingService;
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
        if (consortiumsActive != null) {
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

        }
        this.consortiumDualList = new DualListModel<Consortium>(this.avariableConsortiums, this.assignedConsortiums);
    }

    public void setAssignedAndAvailableBetBankings(List<Consortium> consortiumsActive) {
        this.availableBetBankings = new HashSet<BetBanking>();
        this.assignedBetBankings = new HashSet<BetBanking>();
        if (consortiumsActive != null) {
            for (Consortium currConsortium : consortiumsActive) {
                List<BetBanking> betBankings = new ArrayList<BetBanking>(currConsortium.getBetBankings());
                if (betBankings != null) {
                    //Primer for: bancas del consorcio asociado al usuario logueado
                    for (BetBanking betBankingCurrent : betBankings) {
                        //Segundo for: bancas del usuario seleccionado 
                        boolean isAssigned = false;
                        if (this.selectedUser != null && this.selectedUser.getBetBankings() != null) {
                            for (BetBanking currentBetBankingSelectedUser : this.selectedUser.getBetBankings()) {
                                if (betBankingCurrent.equals(currentBetBankingSelectedUser)) {
                                    this.assignedBetBankings.add(betBankingCurrent);
                                    isAssigned = true;
                                }
                            }
                        }
                        //Si la banca del consorcio no existe en el usuario seleccionado se coloca como disponible 
                        if (!isAssigned) {
                            this.availableBetBankings.add(betBankingCurrent);
                        }
                    }
                }
            }
        }
        this.betBankingDualList = new DualListModel<BetBanking>(new ArrayList(this.availableBetBankings), new ArrayList(this.assignedBetBankings));
    }

    public void setAssignedAndAvailableBetBankingsForAdmin(List<BetBanking> betBankingActive) {
        this.availableBetBankings = new HashSet<BetBanking>();
        this.assignedBetBankings = new HashSet<BetBanking>();
        if (betBankingActive != null) {
            //Todas las bancas debido a que el usuario es administrador del sistema
            for (BetBanking betBankingCurrent : betBankingActive) {
                //Segundo for: bancas del usuario seleccionado 
                boolean isAssigned = false;
                if (this.selectedUser != null && this.selectedUser.getBetBankings() != null) {
                    for (BetBanking currentBetBankingSelectedUser : this.selectedUser.getBetBankings()) {
                        if (betBankingCurrent.equals(currentBetBankingSelectedUser)) {
                            this.assignedBetBankings.add(betBankingCurrent);
                            isAssigned = true;
                        }
                    }
                }
                //Si la banca del consorcio no existe en el usuario seleccionado se coloca como disponible 
                if (!isAssigned) {
                    this.availableBetBankings.add(betBankingCurrent);
                }
            }
        }
        this.betBankingDualList = new DualListModel<BetBanking>(new ArrayList(this.availableBetBankings), new ArrayList(this.assignedBetBankings));
    }

    public void loadAssignedAndAvailableConsortiumActive() {
        List<Consortium> consortiumsActive = new ArrayList<Consortium>();
        List<BetBanking> betBankingActive = new ArrayList<BetBanking>();
        if (this.loginController.getUser().getProfile().getId() == com.exception.magicsnumberswebapp.constants.Profile.ADMINISTRATOR.getId()) {
            try {
                //SI es administrador busco todos los consorcios  que estan activos
                // y tambien busco todas las bancas del sistema
                consortiumsActive = this.consortiumService.findAllConsortiumActive();
                setAssignedAndAvailableConsortium(consortiumsActive);
                betBankingActive = this.betBankingService.findAllBetBanking();

                setAssignedAndAvailableBetBankingsForAdmin(betBankingActive);

            } catch (SearchAllConsortiumException ex) {
                Logger.getLogger(UserController.class
                        .getName()).log(Level.SEVERE, null, ex);
            } catch (SearchAllBetBankingException ex) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
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
            int statusTypeBasicId = com.exception.magicsnumberswebapp.constants.StatusType.BASIC.getId();
            status = statusService.getStatusByStatusType(statusTypeBasicId);
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
        this.editMode = false;
        cleanComponent();
        loadAssignedAndAvailableConsortiumActive();
    }

    private void loadUsers() {
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
    }

    public UserDataModel getUserDataModel() {
        if (this.userDataModel == null) {
            loadUsers();
        }
        return userDataModel;
    }

    public void cleanComponent() {
        this.editMode = false;
        this.selectedUser = null;

        if (this.consortiumDualList.getTarget() != null) {
            this.consortiumDualList.getTarget().clear();
        }
        if (this.consortiumDualList.getSource() != null) {
            this.consortiumDualList.getSource().clear();
        }
        if (this.betBankingDualList.getSource() != null) {
            this.betBankingDualList.getSource().clear();
        }
        if (this.betBankingDualList.getTarget() != null) {
            this.betBankingDualList.getTarget().clear();
        }
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
        if (this.editMode == false) {
            this.selectedUser.setId(null);
            if (this.selectedUser.getPassword() == null || this.selectedUser.getPassword().length() < 1 || this.selectedUser.getPassword().isEmpty()) {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Clave es requerida!", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return;
            }
        }
        //== com.exception.magicsnumberswebapp.constants.Profile.BANKER.getId()
        if (this.selectedUser.getProfile().getId() == null || this.selectedUser.getProfile().getId() < 1) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Perfil es requerido!", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }
        if (this.selectedUser.getProfile().getId() == com.exception.magicsnumberswebapp.constants.Profile.BANKER.getId()) {
            if (this.betBankingDualList.getTarget() == null || this.betBankingDualList.getTarget().size() < 1) {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Debe Asignar una banca a un usuario con perfil de  banquera!", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return;
            }
            if (this.betBankingDualList.getTarget().size() > 1) {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Para un usuario de perfil banquera solo se puede asignar una banca", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return;
            }


        }
        RequestContext reqContext = RequestContext.getCurrentInstance();
        if (userAlreadyExist()) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Nombre Usuario: " + this.selectedUser.getUserName() + " ya existe! en el sistema ", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }
        this.selectedUser.setConsortiums(new HashSet<Consortium>(this.consortiumDualList.getTarget()));
        this.selectedUser.setBetBankings(new HashSet<BetBanking>(this.betBankingDualList.getTarget()));
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
        loadUsers();
        reqContext.addCallbackParam("success", success);
        this.editMode = false;
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
        Boolean exist = false;
        try {
            User users = userService.findUserByUserName(this.selectedUser.getUserName());
            if (users != null) {
                if (editMode) {
                    if (this.selectedUser.getId() != users.getId()) {
                        exist = true;
                    }
                } else {
                    exist = true;
                }
            }
        } catch (SearchAllUserException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return exist;
    }
}
