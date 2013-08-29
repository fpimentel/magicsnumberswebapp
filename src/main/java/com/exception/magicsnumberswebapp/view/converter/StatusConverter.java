package com.exception.magicsnumberswebapp.view.converter;

import com.exception.magicsnumberswebapp.service.StatusService;
import com.exception.magicsnumbersws.entities.Status;
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
@FacesConverter("statusConverter")
public class StatusConverter implements Converter {

    @Autowired
    private StatusService statusService;
    private List<Status> status;

    public List<Status> getStatus() {
        if (status == null) {
            status = statusService.getStatus();
        }
        return status;
    }

    public void setStatus(List<Status> status) {
        this.status = status;
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String submittedValue) {
        int id = 0;
        try {
            id = Integer.parseInt(submittedValue);
            for (Status currStatus : getStatus()) {
                if (currStatus.getId() == id) {
                    return currStatus;
                }
            }
        } catch (Exception ex) {
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            return String.valueOf(((Status) value).getId());
        }
    }
}
