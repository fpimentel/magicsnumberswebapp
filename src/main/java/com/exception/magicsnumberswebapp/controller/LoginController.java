/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exception.magicsnumberswebapp.controller;
import javax.faces.bean.ManagedBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.exception.magicsnumbersws.endpoints.SecurityEndPoint;
import java.util.List;

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

    public LoginController(){}
    
    public LoginController(String userName, String password) {        
        this.userName = userName;
        this.password = password;
    }

    public String getUserByCredential(){
        return "home";
    }    
            
    public String getUserName() {
        List<com.exception.magicsnumbersws.entities.User> 
                users = magicNumberWSClient.getAllUsers();
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
        
}
