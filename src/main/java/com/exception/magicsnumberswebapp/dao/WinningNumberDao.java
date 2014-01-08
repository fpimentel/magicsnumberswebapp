package com.exception.magicsnumberswebapp.dao;

import com.exception.magicsnumbersws.entities.WinningNumber;
import com.exception.magicsnumbersws.exception.SaveWinningNumberDataException;
import com.exception.magicsnumbersws.exception.SearchWinningNumbersException;
import java.util.List;

/**
 *
 * @author cpimentel
 * @since 21-Dic-13
 */
public interface WinningNumberDao {

    public List<WinningNumber> findWinningNumbers(String fromDate, String ToDate) throws SearchWinningNumbersException;
    
    public WinningNumber findWinningNumber(int lotteryId, int timeId,String drawingDate) throws SearchWinningNumbersException;
            
    public void saveWinningNumberInfo(WinningNumber winningNumber) throws SaveWinningNumberDataException;
}
