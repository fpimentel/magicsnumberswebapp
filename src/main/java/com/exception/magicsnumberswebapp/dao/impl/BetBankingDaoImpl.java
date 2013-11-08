package com.exception.magicsnumberswebapp.dao.impl;
import com.exception.magicsnumberswebapp.dao.BetBankingDao;
import com.exception.magicsnumbersws.containers.BetBankingContainer;
import com.exception.magicsnumbersws.endpoints.LookupTablesEndpoint;
import com.exception.magicsnumbersws.entities.Bet;
import com.exception.magicsnumbersws.entities. BetBanking;
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
import org.springframework.stereotype.Repository;

/**
 *
 * @author cpimentel
 */
@Repository
public class BetBankingDaoImpl implements BetBankingDao {

    @Autowired
    private LookupTablesEndpoint lookupTablesEndpoint;

    public BetBankingDaoImpl() {
    }

    @Override
    public List<BetBanking> findAll(int consortiumid) throws SearchAllBetBankingException {
        return lookupTablesEndpoint.findAllBetBanking(consortiumid);
    }

    @Override
    public BetBanking findById(int id) throws SearchAllBetBankingException {
        return lookupTablesEndpoint.findBetBankingById(id);
    }

    @Override
    public List<BetBanking> findAllBetBanking() throws SearchAllBetBankingException {
        return lookupTablesEndpoint.findAllBetBanking();
    }

    @Override
    public List<BetBanking> findBetBankingByUserId(int userId) throws SearchAllBetBankingException {
        return lookupTablesEndpoint.findBetBankingByUserId(userId);
    }

    @Override
    public List<BetBanking> findBetBankingsToConsortiumsAssignedToUser(int userId) throws SearchAllBetBankingException {
        return lookupTablesEndpoint.findBetBankingsToConsortiumsAssignedToUser(userId);
    }

    @Override
    public List<BetBankingBetLimit> findBetLimitsByBetBankingId(int betBankingId) throws FindBetLimitException {
        return this.lookupTablesEndpoint.findBetLimitsByBetBankingId(betBankingId);
    }

    @Override
    public List<BlockingNumberBetBanking> findBlockNumbersBetBanking(int betBankingId) throws FindBlockingNumberException {
        return this.lookupTablesEndpoint.findBlokingNumbersByBetBankingId(betBankingId);
    }

    @Override
    public void saveBetBankingInformation(BetBanking betBanking) throws SaveBetBankingInfoException {
        this.lookupTablesEndpoint.saveBetBankingInformation(betBanking);
    }

    @Override
    public void saveBetBankingBetLimitInformation(List<BetBankingBetLimit> betBankingBetLimits) throws SaveBetBankingBetLimitException, FindBetLimitException, DeleteBetBankingBetLimitException {
        this.lookupTablesEndpoint.saveBetBankingBetLimitInformation(betBankingBetLimits);
    }
    @Override
    public void saveBlockingNumberInformation(List<BlockingNumberBetBanking> list) throws SaveBlockingNumberException, DeleteBetBankingBetLimitException, FindBetLimitException, FindBlockingNumberException {
        this.lookupTablesEndpoint.saveBlockingNumberInformation(list);
    }

    @Override
    public void saveBetBankingInformation(BetBankingContainer bbc) throws FindBlockingNumberException, SaveBlockingNumberException, SaveBetBankingInfoException, FindBetLimitException, DeleteBetBankingBetLimitException, SaveBetBankingBetLimitException {
        this.lookupTablesEndpoint.saveBetBankingInformation(bbc);
    }

    @Override
    public List<Bet> findBetsByLotteryAndBetBanking(int lotteryId, int betBankingId) throws FindBetException {
        return this.lookupTablesEndpoint.findBetsByLotteryAndBetBanking(lotteryId, betBankingId);
    }

    
}
