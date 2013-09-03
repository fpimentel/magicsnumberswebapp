package com.exception.magicsnumberswebapp.service.impl;

import com.exception.magicsnumberswebapp.dao.ConsortiumDao;
import com.exception.magicsnumberswebapp.dao.SystemOptionDao;
import com.exception.magicsnumberswebapp.service.ConsortiumService;
import com.exception.magicsnumbersws.entities.Consortium;
import com.exception.magicsnumbersws.entities.SystemOption;
import com.exception.magicsnumbersws.entities.User;
import com.exception.magicsnumbersws.exception.SaveSystemOptionsDataException;
import com.exception.magicsnumbersws.exception.SearchAllConsortiumException;
import com.exception.magicsnumbersws.exception.SearchAllSystemOptionException;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author cpimentel
 */
@Service
public class ConsortiumServiceImpl implements ConsortiumService {

    @Autowired
    private ConsortiumDao consortiumDao;
    private List<Consortium> consortium;

    public ConsortiumDao getConsortiumDao() {
        return consortiumDao;
    }

    public void setConsortiumDao(ConsortiumDao consortiumDao) {
        this.consortiumDao = consortiumDao;
    }

    public List<Consortium> getConsortium() {
        return consortium;
    }

    public void setConsortium(List<Consortium> consortium) {
        this.consortium = consortium;
    }
    
    @Override
    public List<Consortium> findAll(int userId) throws SearchAllConsortiumException {
      return this.consortiumDao.findAll(userId);  
    }

    
}
