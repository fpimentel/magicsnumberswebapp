package com.exception.magicsnumberswebapp.service;
import com.exception.magicsnumbersws.containers.LotteryContainer;
import com.exception.magicsnumbersws.entities.Lottery;
import com.exception.magicsnumbersws.entities.LotteryCloseHour;
import com.exception.magicsnumbersws.entities.Time;
import com.exception.magicsnumbersws.exception.CloseHourLotteryConfigNotFoundtException;
import com.exception.magicsnumbersws.exception.FindLotteryCloseHourException;
import com.exception.magicsnumbersws.exception.FindLotteryException;
import com.exception.magicsnumbersws.exception.SaveLotteryException;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;

/**
 *
 * @author cpimentel
 */
@Service
public interface LotteryService {
  public List<Lottery> findActiveLottery() throws FindLotteryException;
  public Lottery findLotteryById(int Id) throws FindLotteryException;
  public List<Time> findAvailableTimesByLotteryId( int lotteryId) throws FindLotteryCloseHourException, CloseHourLotteryConfigNotFoundtException;
  public List<Lottery> findLotteries() throws FindLotteryException;
  public List<LotteryCloseHour> findAvailableCloseHour(int lotteryId) throws FindLotteryCloseHourException;
  public void saveLotteryInf(LotteryContainer lotteryContainer)throws SaveLotteryException; 
  public Set<Time> findTimesByLottery(int LotteryId) throws FindLotteryCloseHourException; 
  
}
