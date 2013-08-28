package com.exception.magicsnumberswebapp.service;
import com.exception.magicsnumbersws.entities.Profile;
import com.exception.magicsnumbersws.exception.SearchAllProfileException;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author fpimentel
 */
@Service
public interface ProfileService {
    List<Profile> getProfiles();
    List<Profile> findAll()throws SearchAllProfileException;
    void loadProfileData() throws SearchAllProfileException;
}
