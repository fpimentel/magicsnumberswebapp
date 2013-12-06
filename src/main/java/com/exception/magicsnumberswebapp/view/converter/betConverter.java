package com.exception.magicsnumberswebapp.view.converter;

import com.exception.magicsnumberswebapp.service.BetService;
import com.exception.magicsnumbersws.entities.Bet;
import com.exception.magicsnumbersws.exception.FindBetException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author cpimentel
 * @since 19 sept-13
 */
@Component
@FacesConverter("betConverter")
public class betConverter implements Converter {

    @Autowired
    private BetService betService;
    private List<Bet> bets;
    private static final Logger LOG = Logger.getLogger(betConverter.class.getName());

    public List<Bet> getBets() {
        try {
            this.bets = this.betService.findActiveBets();
        } catch (FindBetException ex) {
            LOG.log(Level.SEVERE, "Error obteniendo las jugadas metodo :getBets in betConverter", ex);

        }

        return bets;
    }

    public void setBets(List<Bet> bets) {
        this.bets = bets;
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String submittedValue) {
        int id = 0;
        try {
            if (submittedValue.length() > 0) {
                id = Integer.parseInt(submittedValue);
                for (Bet bet : getBets()) {
                    if (bet.getId() == id) {
                        return bet;
                    }
                }
            }
        } catch (Exception ex) {
            LOG.info("Problema con las jugadas: " + ex);
        }
        return new Bet();
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            return String.valueOf(((Bet) value).getId());
        }
    }
}
