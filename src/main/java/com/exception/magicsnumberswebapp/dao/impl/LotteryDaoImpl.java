package com.exception.magicsnumberswebapp.dao.impl;
import com.exception.magicsnumberswebapp.dao.LotteryDao;
import com.exception.magicsnumbersws.endpoints.LookupTablesEndpoint;
import com.exception.magicsnumbersws.entities.Lottery;
import com.exception.magicsnumbersws.entities.Time;
import com.exception.magicsnumbersws.exception.CloseHourLotteryConfigNotFoundtException;
import com.exception.magicsnumbersws.exception.FindLotteryCloseHourException;
import com.exception.magicsnumbersws.exception.FindLotteryException;
import java.util.List;
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
}
