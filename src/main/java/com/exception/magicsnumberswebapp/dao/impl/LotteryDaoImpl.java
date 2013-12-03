package com.exception.magicsnumberswebapp.dao.impl;

import com.exception.magicsnumberswebapp.dao.LotteryDao;
import com.exception.magicsnumbersws.containers.LotteryContainer;
import com.exception.magicsnumbersws.endpoints.LookupTablesEndpoint;
import com.exception.magicsnumbersws.entities.Lottery;
import com.exception.magicsnumbersws.entities.LotteryCloseHour;
import com.exception.magicsnumbersws.entities.Time;
import com.exception.magicsnumbersws.exception.CloseHourLotteryConfigNotFoundtException;
import com.exception.magicsnumbersws.exception.FindLotteryCloseHourException;
import com.exception.magicsnumbersws.exception.FindLotteryException;
import com.exception.magicsnumbersws.exception.SaveLotteryException;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author cpimentel
 * @since 12-oct-13
 */
@Repository
public class LotteryDaoImpl implements LotteryDao {

    @Autowired
    private LookupTablesEndpoint lookupTablesEndpoint;

    public LotteryDaoImpl() {
    }

    @Override
    public List<Lottery> findActiveLottery() throws FindLotteryException {
        return lookupTablesEndpoint.findActiveLottery();
    }

    @Override
    public Lottery findLotteryById(int Id) throws FindLotteryException {
        return lookupTablesEndpoint.findLotteryById(Id);
    }

    @Override
    public List<Time> findAvailableTimesByLotteryId(int lotteryId) throws FindLotteryCloseHourException, CloseHourLotteryConfigNotFoundtException {
        return lookupTablesEndpoint.findAvailableTimesByLotteryId(lotteryId);
    }

    @Override
    public List<Lottery> findLotteries() throws FindLotteryException {
        return this.lookupTablesEndpoint.findLotteries();
    }

    @Override
    public List<LotteryCloseHour> findAvailableCloseHour(int lotteryId) throws FindLotteryCloseHourException {
        return this.lookupTablesEndpoint.findAvailableCloseHour(lotteryId);
    }

    @Override
    public void saveLotteryInf(LotteryContainer lotteryContainer) throws SaveLotteryException {
        this.lookupTablesEndpoint.saveLotteryInf(lotteryContainer);
    }

    @Override
    public Set<Time> findTimesByLottery(int LotteryId) throws FindLotteryCloseHourException {
        return this.lookupTablesEndpoint.findTimesByLottery(LotteryId);
    }
}
