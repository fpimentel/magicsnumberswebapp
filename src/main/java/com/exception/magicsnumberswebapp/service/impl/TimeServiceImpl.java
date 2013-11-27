package com.exception.magicsnumberswebapp.service.impl;

import com.exception.magicsnumberswebapp.dao.TimeDao;
import com.exception.magicsnumberswebapp.service.TimeService;
import com.exception.magicsnumbersws.entities.Time;
import com.exception.magicsnumbersws.exception.FindTimeException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author cpimentel
 */
@Service
public class TimeServiceImpl implements TimeService {

    @Autowired
    private TimeDao timeDao;

    @Override
    public List<Time> findAllTimes() throws FindTimeException {
        return this.timeDao.findAllTimes();
    }

    @Override
    public Time findTimeById(int timeId) throws FindTimeException {
        return this.timeDao.findTimeById(timeId);
    }    
}
