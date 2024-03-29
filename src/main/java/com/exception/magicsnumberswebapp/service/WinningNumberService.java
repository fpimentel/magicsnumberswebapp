package com.exception.magicsnumberswebapp.service;
import com.exception.magicsnumbersws.entities.WinningNumber;
import com.exception.magicsnumbersws.exception.SaveWinningNumberDataException;
import com.exception.magicsnumbersws.exception.SearchWinningNumbersException;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author cpimentel
 * @since 21-Dic-13
 */
@Service
public interface WinningNumberService {
  public List<WinningNumber> findWinningNumbers(String fromDate, String ToDate) throws SearchWinningNumbersException;
  public void saveWinningNumberInfo(WinningNumber winningNumber) throws SaveWinningNumberDataException;
  public WinningNumber findWinningNumber(int lotteryId, int timeId,String drawingDate) throws SearchWinningNumbersException;
}
