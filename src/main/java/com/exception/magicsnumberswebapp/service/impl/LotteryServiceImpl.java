package com.exception.magicsnumberswebapp.service.impl;

import com.exception.magicsnumberswebapp.dao.LotteryDao;
import com.exception.magicsnumberswebapp.service.LotteryService;
import com.exception.magicsnumbersws.containers.LotteryContainer;
import com.exception.magicsnumbersws.entities.Lottery;
import com.exception.magicsnumbersws.entities.LotteryCloseHour;
import com.exception.magicsnumbersws.entities.Time;
import com.exception.magicsnumbersws.exception.CloseHourLotteryConfigNotFoundtException;
import com.exception.magicsnumbersws.exception.FindLotteryCloseHourException;
import com.exception.magicsnumbersws.exception.FindLotteryException;
import com.exception.magicsnumbersws.exception.SaveLotteryException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author cpimentel
 */
@Service
public class LotteryServiceImpl implements LotteryService {

    @Autowired
    private LotteryDao lotteryDao;

    @Override
    public List<Lottery> findActiveLottery() throws FindLotteryException {
        return this.lotteryDao.findActiveLottery();
    }

    @Override
    public Lottery findLotteryById(int Id) throws FindLotteryException {
        return this.lotteryDao.findLotteryById(Id);
    }

    @Override
    public List<Time> findAvailableTimesByLotteryId(int lotteryId) throws FindLotteryCloseHourException, CloseHourLotteryConfigNotFoundtException {
        return lotteryDao.findAvailableTimesByLotteryId(lotteryId);
    }

    @Override
    public List<Lottery> findLotteries() throws FindLotteryException {
        return this.lotteryDao.findLotteries();
    }

    @Override
    public List<LotteryCloseHour> findAvailableCloseHour(int lotteryId) throws FindLotteryCloseHourException {
        return this.lotteryDao.findAvailableCloseHour(lotteryId);
    }

    @Override
    public void saveLotteryInf(LotteryContainer lotteryContainer) throws SaveLotteryException {
        this.lotteryDao.saveLotteryInf(lotteryContainer);
    }

    
    

    
}
