/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exception.magicsnumberswebapp.controller;
import com.exception.magicsnumberswebapp.service.UserService;
import com.exception.magicsnumbersws.endpoints.SecurityEndPoint;
import com.exception.magicsnumbersws.entities.User;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
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
public class LoginController {                  
    
    @Autowired
    private SecurityEndPoint magicNumberWSClient;
    private String userName;
    private String password;
    private FacesContext facesContext;
    @Autowired
    private UserService userService;

    public LoginController(){
         facesContext = FacesContext.getCurrentInstance();                    
    }
    
    public LoginController(String userName, String password) {        
        facesContext = FacesContext.getCurrentInstance();
        this.userName = userName;
        this.password = password;
    }

    public String getUserByCredential(){
        return "home";
    }    
            
    public String getUserName() {
        return userName;        
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String isValidUser(){        
        String actionToExcecute = "login";
        User user = userService.getUserByCredentials(this.userName, this.password);
        if(user != null){            
            actionToExcecute = "home";
        }
        else{            
          
        facesContext.addMessage(null, new FacesMessage("Successful", "Hello " + "probar"));          
        }
        return actionToExcecute;
    }       
}
