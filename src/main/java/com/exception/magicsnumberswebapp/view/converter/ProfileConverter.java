package com.exception.magicsnumberswebapp.view.converter;

import com.exception.magicsnumberswebapp.service.ProfileService;
import com.exception.magicsnumbersws.entities.Profile;
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
@FacesConverter("profileConverter")
public class ProfileConverter implements Converter {

    @Autowired
    private ProfileService profileService;
    private List<Profile> profiles;

    public List<Profile> getProfiles() {
        if(profiles == null){
            profiles = profileService.getProfiles();
        }
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String submittedValue) {
        int id = Integer.parseInt(submittedValue);
        for(Profile currProfile : profiles){
            if(currProfile.getId() == id){
                return currProfile;
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
        if (value == null || value.equals("")) {  
            return "";  
        } else {  
            return String.valueOf(((Profile) value).getId());  
        }
    }
}
