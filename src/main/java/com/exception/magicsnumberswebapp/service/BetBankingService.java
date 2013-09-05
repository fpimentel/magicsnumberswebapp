package com.exception.magicsnumberswebapp.service;
import com.exception.magicsnumbersws.entities.BetBanking;
import com.exception.magicsnumbersws.exception.SearchAllBetBankingException;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author cpimentel
 */
@Service
public interface BetBankingService {
  List<BetBanking> findAllBetBanking()throws SearchAllBetBankingException;
}
