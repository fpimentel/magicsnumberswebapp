package com.exception.magicsnumberswebapp.service.impl;

import com.exception.magicsnumberswebapp.dao.BetBankingDao;
import com.exception.magicsnumberswebapp.service.BetBankingService;
import com.exception.magicsnumbersws.containers.BetBankingContainer;
import com.exception.magicsnumbersws.entities.Bet;
import com.exception.magicsnumbersws.entities.BetBanking;
import com.exception.magicsnumbersws.entities.BetBankingBetLimit;
import com.exception.magicsnumbersws.entities.BlockingNumberBetBanking;
import com.exception.magicsnumbersws.exception.DeleteBetBankingBetLimitException;
import com.exception.magicsnumbersws.exception.FindBetException;
import com.exception.magicsnumbersws.exception.FindBetLimitException;
import com.exception.magicsnumbersws.exception.FindBlockingNumberException;
import com.exception.magicsnumbersws.exception.SaveBetBankingBetLimitException;
import com.exception.magicsnumbersws.exception.SaveBetBankingInfoException;
import com.exception.magicsnumbersws.exception.SaveBlockingNumberException;
import com.exception.magicsnumbersws.exception.SearchAllBetBankingException;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author cpimentel
 */
@Service
public class BetBankingServiceImpl implements BetBankingService {

    @Autowired
    private BetBankingDao betBankingDao;
    private List<BetBanking> betbanking;

    @Override
    public List<BetBanking> findAllBetBanking(int consortiumid) throws SearchAllBetBankingException {
        return this.betBankingDao.findAll(consortiumid);
    }

    @Override
    public BetBanking findById(int id) throws SearchAllBetBankingException {
        return this.betBankingDao.findById(id);
    }

    @Override
    public List<BetBanking> findAllBetBanking() throws SearchAllBetBankingException {
        return this.betBankingDao.findAllBetBanking();
    }

    @Override
    public List<BetBanking> findBetBankingByUserId(int userId) throws SearchAllBetBankingException {
        return this.betBankingDao.findBetBankingByUserId(userId);
    }

    @Override
    public List<BetBanking> findBetBankingsToConsortiumsAssignedToUser(int userId) throws SearchAllBetBankingException {
        return this.betBankingDao.findBetBankingsToConsortiumsAssignedToUser(userId);
    }

    @Override
    public List<BetBankingBetLimit> findBetLimitsByBetBankingId(int betBankingId) throws FindBetLimitException {
        return this.betBankingDao.findBetLimitsByBetBankingId(betBankingId);
    }

    @Override
    public List<BlockingNumberBetBanking> findBlockNumbersBetBanking(int betBankingId) throws FindBlockingNumberException {
        return this.betBankingDao.findBlockNumbersBetBanking(betBankingId);
    }

    @Override
    public void saveBetBankingInformation(BetBanking betBanking) throws SaveBetBankingInfoException {
        this.betBankingDao.saveBetBankingInformation(betBanking);
    }

    @Override
    public void saveBetBankingBetLimitInformation(List<BetBankingBetLimit> betBankingBetLimits) throws SaveBetBankingBetLimitException, FindBetLimitException, DeleteBetBankingBetLimitException {
        this.betBankingDao.saveBetBankingBetLimitInformation(betBankingBetLimits);
    }

    @Override
    public void saveBlockingNumberInformation(List<BlockingNumberBetBanking> list) throws SaveBlockingNumberException, DeleteBetBankingBetLimitException, FindBetLimitException, FindBlockingNumberException {
        this.betBankingDao.saveBlockingNumberInformation(list);
    }

    @Override
    public void saveBetBankingInformation(BetBankingContainer bbc) throws FindBlockingNumberException, SaveBlockingNumberException, SaveBetBankingInfoException, FindBetLimitException, DeleteBetBankingBetLimitException, SaveBetBankingBetLimitException {
        this.betBankingDao.saveBetBankingInformation(bbc);
    }

    @Override
    public List<Bet> findBetsByLotteryAndBetBanking(int lotteryId, int betBankingId) throws FindBetException {
        return this.betBankingDao.findBetsByLotteryAndBetBanking(lotteryId, betBankingId);
    }
    
}
