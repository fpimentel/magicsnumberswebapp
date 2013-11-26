package com.exception.magicsnumberswebapp.service;
import com.exception.magicsnumbersws.entities.Day;
import com.exception.magicsnumbersws.exception.FindDayException;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author cpimentel
 */
@Service
public interface DayService {
  public List<Day> findAllDays() throws FindDayException;
  
}
