
package com.exception.magicsnumberswebapp.dao.impl;
import com.exception.magicsnumberswebapp.dao.SystemOptionDao;

import com.exception.magicsnumbersws.endpoints.SecurityEndPoint;

import com.exception.magicsnumbersws.entities.SystemOption;
import com.exception.magicsnumbersws.exception.SearchAllSystemOptionException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author fpimentel
 */
@Repository
public class SystemOptionDaoImpl implements SystemOptionDao{
    @Autowired
    private SecurityEndPoint securityEndpoint;
    
    public SystemOptionDaoImpl() {
        
    }    
    @Override
    public List<SystemOption> findAll() throws SearchAllSystemOptionException {
       return securityEndpoint.getAllSystemOptions();
    }   
}
