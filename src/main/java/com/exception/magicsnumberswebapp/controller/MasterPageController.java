
package com.exception.magicsnumberswebapp.controller;


import com.exception.magicsnumbersws.entities.Profile;
import com.exception.magicsnumbersws.entities.SystemOption;
import com.exception.magicsnumbersws.entities.User;
import java.util.Set;
import javax.faces.bean.ManagedBean;
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
@ManagedBean
@Controller
@Scope
public class MasterPageController {
    
    private MenuModel model; 
    @Autowired
    private LoginController loginController;    

    
    public MasterPageController() {
    
    }
    
    public MasterPageController(MenuModel model, LoginController loginController) {
        this.model = model;
        this.loginController = loginController;
    }
    private void generateModelMenu(){
        model = new DefaultMenuModel();
        Submenu submenu = new Submenu();
        submenu.setLabel("Administration");
        User loggedUser = loginController.getUser();                
        Set<Profile> profiles = loggedUser.getProfiles();
        
        for(Profile currentProfile : profiles){
            Set<SystemOption> options = currentProfile.getOptions();
            for(SystemOption currentOption : options){
                
            }
            
        }
        
        model.addSubmenu(submenu);
    }
            
    public MenuModel getModel() {   
        generateModelMenu();
        /*
        MenuItem menuItem = new MenuItem();
        menuItem.setId("m11");
        menuItem.setValue("Home");
        menuItem.setUrl("/home");        
        submenu.getChildren().add(menuItem);
        menuItem = new MenuItem();
        menuItem.setValue("Page d'accueil");
        menuItem.setUrl("/accueil");
        submenu.getChildren().add(menuItem);
        model.addSubmenu(submenu);      
        model.addSubmenu(submenu);*/
        return model;
    }

    public void setModel(MenuModel model) {
       
        this.model = model;
    }        
    
    
}
