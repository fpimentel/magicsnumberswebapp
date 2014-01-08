package com.exception.magicsnumberswebapp.service.impl;

import com.exception.magicsnumberswebapp.dao.WinningNumberDao;
import com.exception.magicsnumberswebapp.service.WinningNumberService;
import com.exception.magicsnumbersws.entities.WinningNumber;
import com.exception.magicsnumbersws.exception.SaveWinningNumberDataException;
import com.exception.magicsnumbersws.exception.SearchWinningNumbersException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author cpimentel
 * @since 21-Dic-13
 */
@Service
public class WinningServiceImpl implements WinningNumberService {
    @Autowired
    private WinningNumberDao winningNumberDao;    
    
    @Override
    public List<WinningNumber> findWinningNumbers(String fromDate, String ToDate) throws SearchWinningNumbersException {
        return this.winningNumberDao.findWinningNumbers(fromDate, ToDate);
    }

    @Override
    public void saveWinningNumberInfo(WinningNumber winningNumber) throws SaveWinningNumberDataException {
        this.winningNumberDao.saveWinningNumberInfo(winningNumber);
    }

    @Override
    public WinningNumber findWinningNumber(int lotteryId, int timeId, String drawingDate) throws SearchWinningNumbersException {
        return this.winningNumberDao.findWinningNumber(lotteryId, timeId, drawingDate);
    }
}
