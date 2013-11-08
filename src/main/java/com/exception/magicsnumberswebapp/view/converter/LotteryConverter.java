package com.exception.magicsnumberswebapp.view.converter;

import com.exception.magicsnumberswebapp.service.LotteryService;
import com.exception.magicsnumbersws.entities.BetBanking;
import com.exception.magicsnumbersws.entities.Lottery;
import com.exception.magicsnumbersws.exception.FindLotteryException;
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
 */
@Component
@FacesConverter("lotteryConverter")
public class LotteryConverter implements Converter {

    @Autowired
    private LotteryService lotteryService;
    private Lottery lottery;
    private static final Logger LOG = Logger.getLogger(LotteryConverter.class.getName());

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String submittedValue) {
        Lottery lottery = new Lottery();
        if (submittedValue.length() > 0) {
            int id = Integer.parseInt(submittedValue);
             lottery=getLottery(id);
        }
        return lottery;
    }

    public Lottery getLottery(int id) {
        try {
            this.lottery = this.lotteryService.findLotteryById(id);
        } catch (FindLotteryException ex) {
            LOG.log(Level.SEVERE, "getLottery in LotteryConverter", ex);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "getLottery in LotteryConverter", ex);
        }

        return this.lottery;
    }

    public void setLottery(Lottery lottery) {
        this.lottery = lottery;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            return String.valueOf(((Lottery) value).getId());
        }
    }
}
