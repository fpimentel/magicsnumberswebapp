package com.exception.magicsnumberswebapp.controller;

import com.exception.magicsnumberswebapp.datamodel.ConsortiumDataModel;
import com.exception.magicsnumberswebapp.service.ConsortiumService;
import com.exception.magicsnumberswebapp.service.StatusService;
import com.exception.magicsnumbersws.entities.BetBanking;
import com.exception.magicsnumbersws.entities.Consortium;
import com.exception.magicsnumbersws.entities.Status;
import com.exception.magicsnumbersws.entities.User;
import com.exception.magicsnumbersws.exception.SaveConsortiumDataException;
import com.exception.magicsnumbersws.exception.SearchAllBetBankingException;
import com.exception.magicsnumbersws.exception.SearchAllConsortiumException;
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
import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author cpimentel
 */
@ManagedBean
@Scope("request")
@Controller
public class ConsortiumController {

    @Autowired
    private ConsortiumService consortiumService;
    @Autowired
    private StatusService statusService;
    @Autowired
    private LoginController loginController;
    private List<Consortium> updatedConsortiums;
    private Consortium selectedConsortium;
    private boolean editMode = false;
    private ConsortiumDataModel consortiumDataModel;
    private List<Status> status;
    private DualListModel<BetBanking> consortiumDualList;
    private Status selectedStatus;
    private List<BetBanking> availableBetBanking;
    private List<BetBanking> asignedBetBanking;

    public ConsortiumController() {

        updatedConsortiums = new ArrayList<Consortium>();
    }

    public DualListModel<BetBanking> getConsortiumDualList() {
        return consortiumDualList;
    }

    public void setConsortiumDualList(DualListModel<BetBanking> consortiumDualList) {
        this.consortiumDualList = consortiumDualList;
    }

    public List<BetBanking> getAvailableBetBanking() {
        return this.availableBetBanking;
    }

    public void setAvariableBetBanking(List<BetBanking> availableBetBanking) {
        this.availableBetBanking = availableBetBanking;
    }

    public List<BetBanking> getAsignedBetBanking() {
        return asignedBetBanking;
    }

    public void setAsignedBetBanking(List<BetBanking> asignedBetBanking) {
        this.asignedBetBanking = asignedBetBanking;
    }

    public Status getSelectedStatus() {
        return this.selectedStatus;
    }

    public void setSelectedStatus(Status selectedStatus) {
        this.selectedStatus = selectedStatus;
    }

    public List<Status> getStatus() {
        if (this.status == null) {
            this.status = this.statusService.getStatus();
        }
        return this.status;
    }

    public void setStatus(List<Status> status) {
        this.status = status;
    }

    public Consortium getSelectedConsortium() {
        if (this.selectedConsortium == null) {
            this.selectedConsortium = new Consortium();
        }
        return this.selectedConsortium;
    }

    public void setSelectedConsortium(Consortium selectedConsortium) {
        this.selectedConsortium = selectedConsortium;
    }

    public void addNewConsortium() {
        boolean success = true;
        RequestContext reqContext = RequestContext.getCurrentInstance();
        this.selectedConsortium = new Consortium();
        editMode = false;
        reqContext.addCallbackParam("success", success);
    }

    public ConsortiumDataModel getConsortiumDataModel() {
        if (this.consortiumDataModel == null) {
            try {
                User loggedUser = loginController.getUser();
                this.consortiumDataModel = new ConsortiumDataModel(consortiumService.findAll(loggedUser.getId()));
            } catch (SearchAllConsortiumException ex) {
                Logger.getLogger(ConsortiumController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return this.consortiumDataModel;
    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg;
        try 
        {

            this.availableBetBanking = this.consortiumService.findBetBankingAvailable();
            this.asignedBetBanking = this.consortiumService.findBetBankingAsignedToConsortium(this.selectedConsortium.getId());
            consortiumDualList = new DualListModel<BetBanking>(this.availableBetBanking, this.asignedBetBanking);
            msg = new FacesMessage("Consorcio ", ((Consortium) event.getObject()).getName());
            editMode = true;
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (SearchAllBetBankingException ex) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ha ocurrido un error en la seleccion de un consorcio!", null);
            Logger.getLogger(ConsortiumController.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage(null, msg);

        } catch (Exception ex) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ha ocurrido un error en la seleccion de un consorcio!", null);
            Logger.getLogger(ConsortiumController.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage(null, msg);

        }

    }

    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("Consorcio", ((Consortium) event.getObject()).getName());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void addOrUpdateConsortium(ActionEvent event) {

        boolean success = true;
        FacesMessage msg;
        RequestContext reqContext = RequestContext.getCurrentInstance();
        if (consortiumAlreadyExist()) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Consorcio ya existe", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;

        }
        User loggedUser = loginController.getUser();
        this.selectedConsortium.setCreationUser(loggedUser.getUserName());
        if (editMode) {
            Consortium consortiumToRegister = new Consortium();
            consortiumToRegister.setId(this.selectedConsortium.getId());
            int index = consortiumDataModel.getConsortiums().indexOf(consortiumToRegister);
            consortiumDataModel.getConsortiums().set(index, this.selectedConsortium);
            int updatedConsortiumIndex = updatedConsortiums.indexOf(consortiumToRegister);



            if (updatedConsortiumIndex >= 0) {
                updatedConsortiums.set(updatedConsortiumIndex, this.selectedConsortium);
            } else {
                updatedConsortiums.add(this.selectedConsortium);
            }
            editMode = false;
        } else {
            this.selectedConsortium.setId(consortiumDataModel.nextConsortiumId());
            updatedConsortiums.add(selectedConsortium);
            consortiumDataModel.getConsortiums().add(this.selectedConsortium);
        }
        this.selectedConsortium = null;
        reqContext.addCallbackParam("success", success);
    }

    public void saveAll() {
        FacesMessage msg;


        if (updatedConsortiums.size() > 0) {
            try {
                consortiumService.saveConsortiumsData(updatedConsortiums);
            } catch (SaveConsortiumDataException ex) {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ha ocurrido un error registrando los consorcios!", null);
                Logger.getLogger(ConsortiumController.class.getName()).log(Level.SEVERE, null, ex);
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return;
            } catch (Exception ex) {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ha ocurrido un error registrando los consorcios!", null);
                Logger.getLogger(ConsortiumController.class.getName()).log(Level.SEVERE, null, ex);
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return;
            }
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No hay nuevos cambios para registrar", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Grabado exitosamente", null);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    private boolean consortiumAlreadyExist() {
        List<Consortium> consortiums = consortiumDataModel.getConsortiums();
        boolean consortiumExist = true;
        for (Consortium currConsortium : consortiums) {
            if (editMode) {
                if (!currConsortium.equals(this.selectedConsortium)) {
                    if (currConsortium.getName().equals(this.selectedConsortium.getName())) {
                        return consortiumExist;
                    }
                }
            } else {
                if (currConsortium.getName().equals(this.selectedConsortium.getName())) {
                    return consortiumExist;
                }
            }
        }
        return !consortiumExist;
    }
}
