package com.exception.magicsnumberswebapp.view.converter;

import com.exception.magicsnumberswebapp.controller.TicketSaleController;
import com.exception.magicsnumberswebapp.service.LotteryService;
import com.exception.magicsnumbersws.entities.Time;
import java.util.List;
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
 * @author cpimentel
 * @since 22-oct-2013
 */
@Component
@FacesConverter("timeConverter")
@Scope("view")
public class TimeConverter implements Converter {

    @Autowired
    private LotteryService lotteryService;
    @Autowired
    private TicketSaleController ticketSaleController;
    private List<Time> times;
    private static final Logger LOG = Logger.getLogger(TimeConverter.class.getName());

    public List<Time> getTimes(int lotteryId) {
        return ticketSaleController.getTimes();
    }

    public void setTimes(List<Time> times) {
        this.times = times;
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String submittedValue) {
        int id = 0;
        try {
            if (submittedValue.length() > 0) {
                id = Integer.parseInt(submittedValue);
                for (Time currTime : getTimes(id)) {
                    if (currTime.getId() == id) {
                        return currTime;
                    }
                }
            }

        } catch (Exception ex) {
            LOG.info("Problema configurando las jugadas: " + ex);
        }
        return new Time();



    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            return String.valueOf(((Time) value).getId());
        }
    }
}
