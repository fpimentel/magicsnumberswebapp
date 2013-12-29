package com.exception.magicsnumberswebapp.dao;

import com.exception.magicsnumbersws.entities.WinningNumber;
import com.exception.magicsnumbersws.exception.SearchWinningNumbersException;
import java.util.List;

/**
 *
 * @author cpimentel
 * @since 21-Dic-13
 */
public interface WinningNumberDao {

    public List<WinningNumber> findWinningNumbers(String fromDate, String ToDate) throws SearchWinningNumbersException;
}
