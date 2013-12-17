package com.exception.magicsnumberswebapp.view.converter;

import com.exception.magicsnumberswebapp.service.BetBankingService;
import com.exception.magicsnumbersws.entities.BetBanking;
import com.exception.magicsnumbersws.exception.SearchAllBetBankingException;
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
 * @author fpimentel
 */
@Component
@FacesConverter("betBankingConverter")
public class BetBakingConverter implements Converter {

    @Autowired
    private BetBankingService betBankingService;
    private BetBanking betbanking;
    private static final Logger LOG = Logger.getLogger(BetBakingConverter.class.getName());

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String submittedValue) {
        BetBanking betBanking = new BetBanking();
        if (submittedValue.length() > 0) {
            int id = Integer.parseInt(submittedValue);
            betBanking = getBetbanking(id);
        }
        return betBanking;
    }

    public BetBanking getBetbanking(int id) {
        try {
            betbanking = this.betBankingService.findById(id);
        } catch (SearchAllBetBankingException ex) {
            LOG.log(Level.SEVERE, "getBetbanking in BetBankingConverter".concat(ex.getMessage()));
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "getBetbanking in BetBankingConverter".concat(ex.getMessage()));
        }

        return this.betbanking;
    }

    public void setBetbanking(BetBanking betbanking) {
        this.betbanking = betbanking;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            return String.valueOf(((BetBanking) value).getId());
        }
    }
}
