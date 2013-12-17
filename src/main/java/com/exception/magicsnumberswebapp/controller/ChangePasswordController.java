package com.exception.magicsnumberswebapp.controller;

import com.exception.magicsnumberswebapp.constants.URL;
import com.exception.magicsnumberswebapp.service.UserService;
import com.exception.magicsnumberswebapp.util.Security;
import com.exception.magicsnumbersws.exception.SaveUsersDataException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
/**
 *
 * @author fpimentel
 * @since16-dic-2013
 */
@Controller
@Scope("view")
public class ChangePasswordController {

    private FacesContext facesContext;
    @Autowired
    private LoginController loginController;
    @Autowired
    private UserService userService;
    private String currPassword;
    private String newPassword;
    private String passwordConfirm;

    public ChangePasswordController() {
    }

    public ChangePasswordController(String userName, String password) {
        facesContext = FacesContext.getCurrentInstance();
    }

    public String getCurrPassword() {
        return currPassword;
    }

    public void setCurrPassword(String currPassword) {
        this.currPassword = currPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public boolean isValidPass() {
        return (loginController.getUser().getPassword().equals(
                Security.encryptToMD5(this.currPassword)));
    }

    public boolean isValidNewPass() {
        return (this.newPassword.equals(this.passwordConfirm));
    }

    public void changePassword() {
        FacesMessage msg;
        if (!isValidPass()) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Clave actual invalida", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }
        if (!isValidNewPass()) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Clave nueva y Confirmación deben coincidir", "");
            return;
        }
        int userId = this.loginController.getUser().getId();
        try {
            userService.updateUserPassword(userId, this.newPassword);
            Security.logoutSession();
            Security.forward(URL.LOGIN_PAGE.getPath());            

        } catch (SaveUsersDataException ex) {
            Logger.getLogger(ChangePasswordController.class.getName()).log(Level.SEVERE, null, ex);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error actualizando clave", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            Logger.getLogger(ChangePasswordController.class.getName()).log(Level.SEVERE, null, ex);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error actualizando clave", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void cancel() throws IOException {
        Security.forward(URL.MASTER_PAGE.getPath());           
    }
}
