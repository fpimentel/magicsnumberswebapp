package com.exception.magicsnumberswebapp.service;
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
import org.springframework.stereotype.Service;

/**
 *
 * @author cpimentel
 */
@Service
public interface BetBankingService {
  List<BetBanking> findAllBetBanking(int consortiumid)throws SearchAllBetBankingException;
  BetBanking findById(int id)throws SearchAllBetBankingException;
  public List<BetBanking> findAllBetBanking() throws SearchAllBetBankingException;
  public List<BetBanking> findBetBankingByUserId(int userId) throws SearchAllBetBankingException;
  public List<BetBanking> findBetBankingsToConsortiumsAssignedToUser(int userId) throws SearchAllBetBankingException;
  public List<BetBankingBetLimit> findBetLimitsByBetBankingId(int betBankingId) throws FindBetLimitException;
  public List<BlockingNumberBetBanking> findBlockNumbersBetBanking(int betBankingId) throws FindBlockingNumberException;
  public void saveBetBankingInformation(BetBanking betBanking) throws SaveBetBankingInfoException;
  public void saveBetBankingBetLimitInformation(List<BetBankingBetLimit> betBankingBetLimits) throws SaveBetBankingBetLimitException, FindBetLimitException, DeleteBetBankingBetLimitException;
  public void saveBlockingNumberInformation(List<BlockingNumberBetBanking> list) throws SaveBlockingNumberException, DeleteBetBankingBetLimitException, FindBetLimitException, FindBlockingNumberException;
  public void saveBetBankingInformation(BetBankingContainer bbc) throws FindBlockingNumberException, SaveBlockingNumberException, SaveBetBankingInfoException, FindBetLimitException, DeleteBetBankingBetLimitException, SaveBetBankingBetLimitException;
  public List<Bet> findBetsByLotteryAndBetBanking( int lotteryId, int betBankingId) throws FindBetException;
}
