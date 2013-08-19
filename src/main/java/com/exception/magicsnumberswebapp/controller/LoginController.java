/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exception.magicsnumberswebapp.controller;
import com.exception.magicsnumberswebapp.service.UserService;
import javax.faces.bean.ManagedBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
/**
 *
 * @author fpimentel
 */
@ManagedBean
@Scope
public class LoginController {
    
    @Autowired
    UserService userService;    
    
    private String userName;
    private String password;

    public LoginController(){}
    
    public LoginController(UserService userService, String userName, String password) {
        this.userService = userService;
        this.userName = userName;
        this.password = password;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
            
    public String getUserName() {
        userService=userService;
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
