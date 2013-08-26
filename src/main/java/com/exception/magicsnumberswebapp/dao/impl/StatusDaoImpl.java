/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exception.magicsnumberswebapp.dao.impl;

import com.exception.magicsnumberswebapp.dao.StatusDao;
import com.exception.magicsnumbersws.endpoints.LookupTablesEndpoint;
import com.exception.magicsnumbersws.entities.Status;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author fpimentel
 */
@Repository
public class StatusDaoImpl implements StatusDao{
   
    @Autowired
    private LookupTablesEndpoint lookupTablesEndpoint;
    
    private List<Status> status;
    
    public StatusDaoImpl() {
        
    }    

    
    public LookupTablesEndpoint getLookupTablesEndpoint() {
        return lookupTablesEndpoint;
    }

    public void setLookupTablesEndpoint(LookupTablesEndpoint lookupTablesEndpoint) {
        this.lookupTablesEndpoint = lookupTablesEndpoint;
    }         
    
    @Override
    public List<Status> getStatus() {
        return status;
    }

    public void setStatus(List<Status> status) {
        this.status = status;
    }

    @Override
    @PostConstruct
    public void loadStatusData() {
        status = lookupTablesEndpoint.getAllStatus();
    }
        
}
