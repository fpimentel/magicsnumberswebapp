package com.exception.magicsnumberswebapp.service.impl;

import com.exception.magicsnumberswebapp.dao.DaysDao;
import com.exception.magicsnumberswebapp.service.DayService;
import com.exception.magicsnumbersws.entities.Day;
import com.exception.magicsnumbersws.exception.FindDayException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author cpimentel
 */
@Service
public class DayServiceImpl implements DayService {

    @Autowired
    private DaysDao dayDao;

    @Override
    public List<Day> findAllDays() throws FindDayException {
        return this.dayDao.findAllDays();
    }

    @Override
    public Day findDayById(int dayId) throws FindDayException {
        return this.dayDao.findDayById(dayId);
    }





    
}
