package com.exception.magicsnumberswebapp.controller;

import com.exception.magicsnumberswebapp.constants.URL;
import com.exception.magicsnumberswebapp.util.Security;
import com.exception.magicsnumbersws.entities.Category;
import com.exception.magicsnumbersws.entities.Profile;
import com.exception.magicsnumbersws.entities.SystemOption;
import com.exception.magicsnumbersws.entities.User;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.model.MenuModel;
import org.primefaces.model.DefaultMenuModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author fpimentel
 */
@Controller
@Scope("session")
public class MasterPageController {

    private MenuModel model;
    private String userName;
    @Autowired
    private LoginController loginController;
    private static final Logger LOG = Logger.getLogger(MasterPageController.class.getName());

    public MasterPageController() {
    }

    public MasterPageController(MenuModel model, LoginController loginController) {
        this.model = model;
        this.loginController = loginController;
    }

    public String getUserName() {
        this.userName = loginController.getUserName();
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void logout() throws IOException {
        Security.logoutSession();
        Security.forward(URL.LOGIN_PAGE.getPath());
    }

    public void forwardToChangePass() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();        
        ec.redirect(ec.getRequestContextPath() + "/faces/changePassword.xhtml");        
    }
    
    private void generateModelMenu() {
        model = new DefaultMenuModel();
        try {
            User loggedUser = loginController.getUser();
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            Profile profile = loggedUser.getProfile();

            Set<SystemOption> options = profile.getOptions();
            for (SystemOption currOption : options) {
                //Create a menuItem for option
                MenuItem menuItem = new MenuItem();
                menuItem.setId(currOption.getName());
                menuItem.setValue(currOption.getValue());
                menuItem.setUrl(currOption.getUrl());
                //menuItem.setOutcome(currOption.getOutCome());
                Category category = currOption.getCategory();


                String categoryName = category.getName();
                Submenu subMenu = getCategoryItem(categoryName);

                if (subMenu == null) {//Si no existe la categoria en el menu la agregamos
                    subMenu = new Submenu();
                    subMenu.setId(categoryName);
                    subMenu.setLabel(categoryName);
                    subMenu.getChildren().add(menuItem);
                    model.addSubmenu(subMenu);
                } else {
                    subMenu.getChildren().add(menuItem);
                }
            }
        } catch (Exception ex) {
            FacesMessage msg;
            msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error generando el menu", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            LOG.log(Level.SEVERE, "Error generando menu principal: " + ex);
        }
    }

    private Submenu getCategoryItem(String categoryName) {
        if (this.model.getContents() != null && this.model.getContents().size() > 0) {
            for (UIComponent currComponent : this.model.getContents()) {
                if (currComponent instanceof Submenu) {
                    if (currComponent.getId().equals(categoryName)) {
                        return (Submenu) currComponent;
                    }
                }
            }
        }
        return null;
    }

    private Submenu putMenuItem(Category category, List<UIComponent> components, SystemOption option) {
        if (this.model.getContents().size() > 0) {
        }
        boolean hasTheComponent = false;
        Submenu subMenu = new Submenu();
        if (components.size() > 0) {
            for (UIComponent currComponent : components) {
                subMenu = (Submenu) currComponent;
                if (subMenu.getLabel().equals(category.getName())) {
                    hasTheComponent = true;
                }
                MenuItem optionMenu = new MenuItem();
                optionMenu.setId(option.getName());
                optionMenu.setValue(option.getName());
                optionMenu.setUrl(option.getUrl());
                subMenu.getChildren().add(optionMenu);
            }
        } else {
            subMenu = new Submenu();
        }
        return subMenu;
    }

    public MenuModel getModel() {
        generateModelMenu();
        return model;
    }

    public void setModel(MenuModel model) {
        this.model = model;
    }
}
