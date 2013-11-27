package com.exception.magicsnumberswebapp.service;

import com.exception.magicsnumbersws.entities.Time;
import com.exception.magicsnumbersws.exception.FindTimeException;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author cpimentel
 */
@Service
public interface TimeService {

    public List<Time> findAllTimes() throws FindTimeException;
    public Time findTimeById(int timeId) throws FindTimeException;
}
