package com.exception.magicsnumberswebapp.service.impl;

import com.exception.magicsnumberswebapp.dao.ProfileDao;
import com.exception.magicsnumberswebapp.service.ProfileService;
import com.exception.magicsnumbersws.entities.Profile;
import com.exception.magicsnumbersws.exception.SearchAllProfileException;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author fpimentel
 * @since 28-Agosto-2013
 */
@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileDao profileDao;
    private List<Profile> profiles;
    

    @PostConstruct
    @Override
    public void loadProfileData() throws SearchAllProfileException {
        profiles = profileDao.findAll();
    }

    public ProfileDao getProfilenDao() {
        return profileDao;
    }

    public void setProfilenDao(ProfileDao profileDao) {
        this.profileDao = profileDao;
    }

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

    @Override
    public List<Profile> findAll() throws SearchAllProfileException {
        return profiles;
    }
}
