package com.exception.magicsnumberswebapp.view.converter;

import com.exception.magicsnumberswebapp.service.DayService;
import com.exception.magicsnumbersws.entities.Day;
import com.exception.magicsnumbersws.exception.FindDayException;
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
@FacesConverter("dayConverter")
public class DayConverter implements Converter {

    @Autowired
    private DayService dayService;
    private Day day;
    private static final Logger LOG = Logger.getLogger(DayConverter.class.getName());

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String submittedValue) {
        Day day = new Day();
        if (submittedValue.length() > 0) {
            int id = Integer.parseInt(submittedValue);
            day = getDay(id);
        }
        return day;
    }
    public Day getDay(int day) {
        try {
            this.day = this.dayService.findDayById(day);
        } catch (FindDayException ex) {
            Logger.getLogger(DayConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (Exception ex) {
            Logger.getLogger(DayConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.day;
    }
    public void setDay(Day day) {
        this.day = day;
    }
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            return String.valueOf(((Day) value).getId());
        }
    }
}
