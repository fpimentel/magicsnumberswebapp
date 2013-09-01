package com.exception.magicsnumberswebapp.service.impl;

import com.exception.magicsnumberswebapp.dao.SystemOptionDao;
import com.exception.magicsnumberswebapp.service.SystemOptionService;
import com.exception.magicsnumbersws.entities.SystemOption;
import com.exception.magicsnumbersws.exception.SaveSystemOptionsDataException;
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
public class SystemOptionServiceImpl implements SystemOptionService {

    @Autowired
    private SystemOptionDao systemOptionDao;
    private List<SystemOption> systemOptions;

    @PostConstruct
    public void loadSytemOptionData() throws SearchAllSystemOptionException {
        systemOptions = systemOptionDao.findAll();
    }

    public List<SystemOption> getSystemOptions() {
        return systemOptions;
    }

    public void setSystemOptions(List<SystemOption> systemOptions) {
        this.systemOptions = systemOptions;
    }

    public SystemOptionDao getSystemOptionDao() {
        return systemOptionDao;
    }

    public void setSystemOptionDao(SystemOptionDao systemOptionDao) {
        this.systemOptionDao = systemOptionDao;
    }

    @Override
    public List<SystemOption> findAll() throws SearchAllSystemOptionException {
        return systemOptionDao.findAll();
    }

    @Override
    public void saveSystemOptionsData(List<SystemOption> systemOptions) throws SaveSystemOptionsDataException {
        systemOptionDao.saveAllSystemOptionData(systemOptions);
    }
}
