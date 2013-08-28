
package com.exception.magicsnumberswebapp.dao.impl;
import com.exception.magicsnumberswebapp.dao.ProfileDao;
import com.exception.magicsnumbersws.endpoints.SecurityEndPoint;
import com.exception.magicsnumbersws.entities.Profile;
import com.exception.magicsnumbersws.exception.SearchAllProfileException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author fpimentel
 */
@Repository
public class ProfileDaoImpl implements ProfileDao{
    @Autowired
    private SecurityEndPoint securityEndpoint;
    
    public ProfileDaoImpl() {
        
    }    

    @Override
    public List<Profile> findAll() throws SearchAllProfileException {
        return securityEndpoint.getAllProfiles();
    }
    
}
