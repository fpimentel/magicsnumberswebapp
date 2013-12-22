package com.exception.magicsnumberswebapp.view.converter;

import com.exception.magicsnumberswebapp.service.TimeService;
import com.exception.magicsnumbersws.entities.Time;
import com.exception.magicsnumbersws.exception.FindTimeException;
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
 * @since 25-sept-2013 Esta clase es la que se usara para los combo de tiempos.
 * existe otra llamada TimeConverter que es usada especificamente en la pantalla
 * de ticket.
 */
@Component
@FacesConverter("timeConverterUniversal")
@Scope("view")
public class TimeConverterUniversal implements Converter {

    @Autowired
    private TimeService timeService;
    private List<Time> times;
    private static final Logger LOG = Logger.getLogger(TimeConverterUniversal.class.getName());

    public Time getTimes(int timeId) {
        Time time = new Time();
        try {
            time = timeService.findTimeById(timeId);
        } catch (FindTimeException ex) {
            Logger.getLogger(TimeConverterUniversal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TimeConverterUniversal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return time;
    }

    public void setTimes(List<Time> times) {
        this.times = times;
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String submittedValue) {
        int id = 0;
        try {
            if (submittedValue != "") {
                id = Integer.parseInt(submittedValue);
                return getTimes(id);
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
