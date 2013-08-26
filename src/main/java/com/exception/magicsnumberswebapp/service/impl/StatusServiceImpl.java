
package com.exception.magicsnumberswebapp.service.impl;

import com.exception.magicsnumberswebapp.dao.StatusDao;
import com.exception.magicsnumberswebapp.service.StatusService;
import com.exception.magicsnumbersws.entities.Status;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author fpimentel
 */
@Service
public class StatusServiceImpl implements StatusService{

    @Autowired
    private StatusDao statusDao;

    public StatusDao getStatusDao() {
        return statusDao;
    }

    public void setStatusDao(StatusDao statusDao) {
        this.statusDao = statusDao;
    }    

    @Override
    public List<Status> getStatus() {
        return statusDao.getStatus();
    }
}
