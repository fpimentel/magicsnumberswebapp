package com.exception.magicsnumberswebapp.controller;

import com.exception.magicsnumberswebapp.datamodel.SystemOptionDataModel;
import com.exception.magicsnumberswebapp.datamodel.UserDataModel;
import com.exception.magicsnumberswebapp.service.StatusService;
import com.exception.magicsnumberswebapp.service.SystemOptionService;
import com.exception.magicsnumberswebapp.service.UserService;
import com.exception.magicsnumbersws.entities.Status;
import com.exception.magicsnumbersws.entities.SystemOption;
import com.exception.magicsnumbersws.entities.User;
import com.exception.magicsnumbersws.exception.SearchAllSystemOptionException;
import com.exception.magicsnumbersws.exception.SearchAllUserException;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.springframework.context.annotation.Scope;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class SystemOptionController {

    @Autowired
    private SystemOptionService systemOptionService;
    @Autowired
    private StatusService statusService;
    private SystemOption selectedSystemOption;
    private SystemOptionDataModel systemOptionDataModel;
    private List<Status> status;

    public List<Status> getStatus() {
        if (status == null) {
            status = statusService.getStatus();
        }
        return status;
    }

    public void setStatus(List<Status> status) {
        this.status = status;
    }

    public SystemOptionController() {
    }

    public SystemOption getSelectedSystemOption() {
        return selectedSystemOption;
    }

    public void setSelectedSystemOption(SystemOption selectedSystemOption) {
        this.selectedSystemOption = selectedSystemOption;
    }

    public SystemOptionDataModel getSystemOptionDataModel() {
        if (this.systemOptionDataModel == null) {
            try {
                this.systemOptionDataModel = new SystemOptionDataModel(systemOptionService.findAll());
            } catch (SearchAllSystemOptionException ex) {
                Logger.getLogger(SystemOptionController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return this.systemOptionDataModel;
    }

    public void setSystemOptionDataModel(SystemOptionDataModel systemOptionDataModel) {
        this.systemOptionDataModel = systemOptionDataModel;
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
}
