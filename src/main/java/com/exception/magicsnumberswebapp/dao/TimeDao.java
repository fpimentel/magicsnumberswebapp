package com.exception.magicsnumberswebapp.dao;

import com.exception.magicsnumbersws.entities.Time;
import com.exception.magicsnumbersws.exception.FindTimeException;
import java.util.List;

/**
 *
 * @author cpimentel
 * @since 19-sept-13
 */
public interface TimeDao {

public List<Time> findAllTimes() throws FindTimeException;
public Time findTimeById( int timeId) throws FindTimeException;
    
}
