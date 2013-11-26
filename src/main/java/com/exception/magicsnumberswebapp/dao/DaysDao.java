package com.exception.magicsnumberswebapp.dao;

import com.exception.magicsnumbersws.entities.Day;
import com.exception.magicsnumbersws.exception.FindDayException;
import java.util.List;

/**
 *
 * @author cpimentel
 * @since 19-sept-13
 */
public interface DaysDao {

    
    public List<Day> findAllDays() throws FindDayException;
    
}
