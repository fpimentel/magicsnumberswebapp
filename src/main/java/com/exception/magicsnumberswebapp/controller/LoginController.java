package com.exception.magicsnumberswebapp.controller;
import com.exception.magicsnumberswebapp.service.UserService;
import com.exception.magicsnumbersws.entities.User;
import com.exception.magicsnumbersws.exception.SearchAllUserException;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    private User user;
    private String userName;
    private String password;
    private FacesContext facesContext;
    @Autowired
    private UserService userService;
    private String HOME_PAGE = "home";
    private String LOGIN_PAGE = "login";

    public LoginController() {
        
    }

    public LoginController(String userName, String password) {
        facesContext = FacesContext.getCurrentInstance();
        this.userName = userName;
        this.password = password;
    }

    public String getUserByCredential() {
        return HOME_PAGE;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String isValidUser() {
        String actionToExcecute = LOGIN_PAGE;
        FacesMessage msg;
        try {
            user = userService.getUserByCredentials(this.userName, this.password);
            if (user != null) {
                actionToExcecute = HOME_PAGE;
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario invalido", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (SearchAllUserException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, "Ocurrio un error buscando todos los usuarios ", ex);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ha ocurrido un error validando credenciales", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, "Ha ocurrido un error validando las credenciales de usuario ", ex.getMessage());
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ha ocurrido un error validando credenciales", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

        return actionToExcecute;
    }
}
