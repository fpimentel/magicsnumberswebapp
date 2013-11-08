package com.exception.magicsnumberswebapp.view.converter;

import com.exception.magicsnumberswebapp.constants.Profile;
import com.exception.magicsnumberswebapp.controller.LoginController;
import com.exception.magicsnumberswebapp.service.ConsortiumService;
import com.exception.magicsnumbersws.entities.Consortium;
import com.exception.magicsnumbersws.entities.User;
import com.exception.magicsnumbersws.exception.SearchAllConsortiumException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author fpimentel
 */
@Component
@FacesConverter("consortiumConverter")
@Scope("session")
public class ConsortiumConverter implements Converter {

    private static final Logger LOG = Logger.getLogger(ConsortiumConverter.class.getName());
    @Autowired    
    private ConsortiumService consortiumService;
    private List<Consortium> consortiums;
    @Autowired
    private LoginController loginController;

    public List<Consortium> getConsortiums() {
        LOG.log(Level.INFO, "Begin=>getConsortiums in ConsortiumConverter ");
        if (this.consortiums == null) {
            User loggedUser = loginController.getUser();
            try {
                if (loggedUser.getProfile().getId() == Profile.ADMINISTRATOR.getId()) {
                    this.consortiums = this.consortiumService.findAllConsortiumActive();
                } else {
                    this.consortiums = this.consortiumService.findAll(loggedUser.getId());
                }
            } catch (SearchAllConsortiumException ex) {
                LOG.log(Level.SEVERE, "Error buscando los consorcio SearchAllConsortiumException", ex);
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, "Error buscando los consorcio Exception ", ex);
            }
        }
        return this.consortiums;
    }

    public void setConsortiums(List<Consortium> consortiums) {
        this.consortiums = consortiums;
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String submittedValue) {
        int id = Integer.parseInt(submittedValue);
        for (Consortium currConsortium : getConsortiums()) {
            if (currConsortium.getId() == id) {
                return currConsortium;
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            return String.valueOf(((Consortium) value).getId());
        }
    }
}
