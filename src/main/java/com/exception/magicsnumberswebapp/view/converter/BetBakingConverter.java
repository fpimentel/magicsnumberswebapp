package com.exception.magicsnumberswebapp.view.converter;
import com.exception.magicsnumberswebapp.service.BetBankingService;
import com.exception.magicsnumbersws.entities.BetBanking;
import com.exception.magicsnumbersws.exception.SearchAllBetBankingException;
import java.util.List;
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
@FacesConverter("betbankingConverter")
public class BetBakingConverter implements Converter {

    @Autowired
    private BetBankingService betBankingService;
    private List<BetBanking> betbanking;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String submittedValue) {
        int id = Integer.parseInt(submittedValue);
        for (BetBanking currBetBanking : getBetbanking()) {
            if (currBetBanking.getId() == id) {
                return currBetBanking;
            }
        }
        return null;
    }

    public List<BetBanking> getBetbanking() {
        try {
            if (betbanking == null) {
                betbanking = this.betBankingService.findAllBetBanking();
            }
            return betbanking;
        } 
        catch (SearchAllBetBankingException ex) 
        {
            
        }
        return betbanking;
    }
    public void setBetbanking(List<BetBanking> betbanking) {
        this.betbanking = betbanking;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            return String. valueOf(((BetBanking) value).getId());
        }
    }
}
