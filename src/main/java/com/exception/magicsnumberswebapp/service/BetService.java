package com.exception.magicsnumberswebapp.service;
import com.exception.magicsnumbersws.entities.Bet;
import com.exception.magicsnumbersws.exception.FindBetException;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author cpimentel
 */
@Service
public interface BetService {
  public List<Bet> findActiveBets() throws FindBetException;
  
}
