
package com.exception.magicsnumberswebapp.controller;


import com.exception.magicsnumbersws.entities.Category;
import com.exception.magicsnumbersws.entities.CategoryOption;
import com.exception.magicsnumbersws.entities.Profile;
import com.exception.magicsnumbersws.entities.SystemOption;
import com.exception.magicsnumbersws.entities.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
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
    private static final Logger LOG = Logger.getLogger(MasterPageController.class.getName());

    
    public MasterPageController() {
    
    }
    
    public MasterPageController(MenuModel model, LoginController loginController) {
        this.model = model;
        this.loginController = loginController;
    }
    private void generateModelMenu(){        
        model = new DefaultMenuModel();
        try{
                User loggedUser = loginController.getUser();
                Set<Profile> profiles = loggedUser.getProfiles();
                //PROFILES
                for(Profile currentProfile : profiles){
                    Set<SystemOption> options = currentProfile.getOptions();
                    for(SystemOption currOption : options){
                        //Create a menuItem for option
                        MenuItem menuItem = new MenuItem();
                        menuItem.setId(currOption.getName());
                        menuItem.setValue(currOption.getValue());
                        menuItem.setUrl(currOption.getUrl()); 
                        
                        Set<CategoryOption> categoriesOptions = currOption.getCategories();                        
                        for(CategoryOption currCategoryOption : categoriesOptions){
                             
                             String categoryName = currCategoryOption.getCategory().getName();                             
                             Submenu subMenu = getCategoryItem(categoryName);
                             
                             if(subMenu == null){
                                 subMenu = new Submenu();
                                 subMenu.setId(categoryName);
                                 subMenu.setLabel(categoryName);
                                 subMenu.getChildren().add(menuItem);
                                 model.addSubmenu(subMenu);
                             }
                             else{
                                 subMenu.getChildren().add(menuItem);
                             }
                        }                       
                    }
                }
            }
        catch(Exception ex){
            LOG.log(Level.SEVERE, "Error generando menu principal: " + ex.getMessage());
        }
                        
        /*
        //Submenu submenu = new Submenu();
        //submenu.setLabel("Administration");
        //MenuItem menuItem = new MenuItem();
        //menuItem.setId("m11");
        //menuItem.setValue("Home");
        //menuItem.setUrl("/home"); 
        //submenu.getChildren().add(menuItem);
        
        
        
        
        
        
            
            //OPTIONS             
            /*
                            
                
                //CATEGORIES                                                
                for(Category currCategory : categories){
                    List<UIComponent>components =  model.getContents();
                    if(!menuHasSubMenu(currCategory, components)){
                         submenu = new Submenu();
                         submenu.setLabel(currCategory.getName());                         
                    }
                       
                    if(submenu.getChildren().size()>0){
                        model.addSubmenu(submenu);
                        model.
                    }            
                }
                                
            }            
        }        
        * */
    }
    
    private Submenu getCategoryItem(String categoryName){
        if(this.model.getContents() != null && this.model.getContents().size()> 0){
            for(UIComponent currComponent : this.model.getContents()){
                if(currComponent instanceof Submenu){
                    if(currComponent.getId().equals(categoryName)){
                        return (Submenu)currComponent;
                    }
                }
            }
        }
        return null;
    }
    private Submenu putMenuItem(Category category, List<UIComponent> components,SystemOption option){
        if(this.model.getContents().size()> 0){
            
        }
        boolean hasTheComponent = false;
        Submenu subMenu =  new Submenu();
        if(components.size()> 0){
            for(UIComponent currComponent : components){
                subMenu= (Submenu)currComponent;
                if(subMenu.getLabel().equals(category.getName())){
                    hasTheComponent =true;                 
                }                
                MenuItem optionMenu = new MenuItem();                
                optionMenu.setId(option.getName());
                optionMenu.setValue(option.getName());
                optionMenu.setUrl(option.getUrl());
                subMenu.getChildren().add(optionMenu);                       
            }        
        }
        else{
            subMenu = new Submenu();
        }
        return subMenu;
    }
    
    private void putItemIntoExistingComponent(String componentId){
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
