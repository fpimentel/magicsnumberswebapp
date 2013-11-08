package com.exception.magicsnumberswebapp.dao;

import com.exception.magicsnumbersws.entities.Lottery;
import com.exception.magicsnumbersws.entities.Time;
import com.exception.magicsnumbersws.exception.CloseHourLotteryConfigNotFoundtException;
import com.exception.magicsnumbersws.exception.FindLotteryCloseHourException;
import com.exception.magicsnumbersws.exception.FindLotteryException;
import java.util.List;

/**
 *
 * @author cpimentel
 * @since 19-sept-13
 */
public interface LotteryDao {

    public List<Lottery> findActiveLottery() throws FindLotteryException;
    public Lottery findLotteryById(int Id) throws FindLotteryException;
    public List<Time> findAvailableTimesByLotteryId( int lotteryId) throws FindLotteryCloseHourException, CloseHourLotteryConfigNotFoundtException;
}
