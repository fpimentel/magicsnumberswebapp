package com.exception.magicsnumberswebapp.controller;

import com.exception.magicsnumberswebapp.datamodel.SystemOptionDataModel;
import com.exception.magicsnumberswebapp.service.CategoryService;
import com.exception.magicsnumberswebapp.service.StatusService;
import com.exception.magicsnumberswebapp.service.SystemOptionService;
import com.exception.magicsnumbersws.entities.Category;
import com.exception.magicsnumbersws.entities.Status;
import com.exception.magicsnumbersws.entities.SystemOption;
import com.exception.magicsnumbersws.exception.SaveSystemOptionsDataException;
import com.exception.magicsnumbersws.exception.SearchAllSystemOptionException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.springframework.context.annotation.Scope;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author cpimentel
 */
@ManagedBean
@Scope
@Controller
public class SystemOptionController {

    @Autowired
    private SystemOptionService systemOptionService;
    @Autowired
    private StatusService statusService;
    @Autowired
    private CategoryService categoryService;
    private List<SystemOption> updatedOptions;
    private SystemOption selectedSystemOption;
    private boolean editMode = false;
    private SystemOptionDataModel systemOptionDataModel;
    private List<Status> status;
    private Status selectedStatus;
    private List<Category> categories;
    private Category selectedCategory;

    public SystemOptionController() {
        updatedOptions = new ArrayList<SystemOption>();
    }

    public List<Category> getCategories() {        
           if(this.categories == null) {
            this.categories = this.categoryService.getCategories();
        }
        return this.categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Category getSelectedCategory() {
        return this.selectedCategory;
    }

    public void setSelectedCategory(Category selectedCategory) {
        this.selectedCategory = selectedCategory;
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

    public SystemOption getSelectedSystemOption() {       
        if (this.selectedSystemOption == null) {
            this.selectedSystemOption = new SystemOption();
        }
        return this.selectedSystemOption;
    }

    public void setSelectedSystemOption(SystemOption systemOption) {
        this.selectedSystemOption = systemOption;
    }

   /* public void resetData(ActionEvent event) {
        
        boolean success = true;
        RequestContext reqContext = RequestContext.getCurrentInstance();
        reqContext.addCallbackParam("success", success);
        
    }*/

    public void addNewSystemOption() {
        boolean success = true;
        RequestContext reqContext = RequestContext.getCurrentInstance();               
        this.selectedSystemOption = new SystemOption(); 
        editMode = false;
        reqContext.addCallbackParam("success", success);        
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

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Opcion ", ((SystemOption) event.getObject()).getName());
        editMode = true;
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("Opcion", ((SystemOption) event.getObject()).getName());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void addOrUpdateSystemOption(ActionEvent event) {
        boolean success = true;
        FacesMessage msg;
        RequestContext reqContext = RequestContext.getCurrentInstance();
        if (optionAlreadyExist()) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Opcion ya existe", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            success = false;
            return;
        }
        if (editMode) {
            SystemOption systemOptionToRegister = new SystemOption();
            systemOptionToRegister.setId(selectedSystemOption.getId());
            int index = systemOptionDataModel.getSystemOptions().indexOf(systemOptionToRegister);
            systemOptionDataModel.getSystemOptions().set(index, selectedSystemOption);
            int updatedSystemOptionIndex = updatedOptions.indexOf(systemOptionToRegister);
            if (updatedSystemOptionIndex >= 0) {
                updatedOptions.set(updatedSystemOptionIndex, selectedSystemOption);
            } else {
                updatedOptions.add(selectedSystemOption);
            }
            editMode = false;
        } else {
            selectedSystemOption.setId(systemOptionDataModel.nextSystemOptionId());
            updatedOptions.add(selectedSystemOption);
            systemOptionDataModel.getSystemOptions().add(selectedSystemOption);
        }
        selectedSystemOption = null;
        reqContext.addCallbackParam("success", success);
    }

    public void saveAll() {
        FacesMessage msg;


        if (updatedOptions.size() > 0) {
            try {
                systemOptionService.saveSystemOptionsData(updatedOptions);
            } catch (SaveSystemOptionsDataException ex) {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ha ocurrido un error registrando las opciones", null);
                Logger.getLogger(SystemOptionController.class.getName()).log(Level.SEVERE, null, ex);
                throw new ValidatorException(msg);

            }
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No hay nuevos cambios para registrar", null);
            throw new ValidatorException(msg);
        }
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Grabado exitosamente", null);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    private boolean optionAlreadyExist() {
        List<SystemOption> systemOptions = systemOptionDataModel.getSystemOptions();
        boolean systemOptionExist = true;
        for (SystemOption currSystemOption : systemOptions) {
            if (editMode) {
                if (!currSystemOption.equals(this.selectedSystemOption)) {
                    if (currSystemOption.getName().equals(selectedSystemOption.getName())) {
                        return systemOptionExist;
                    }
                }
            } else {
                if (currSystemOption.getName().equals(this.selectedSystemOption.getName())) {
                    return systemOptionExist;
                }
            }
        }
        return !systemOptionExist;
    }
}
