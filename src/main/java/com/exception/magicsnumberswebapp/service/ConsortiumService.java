package com.exception.magicsnumberswebapp.service;

import com.exception.magicsnumbersws.entities.BetBanking;
import com.exception.magicsnumbersws.entities.Consortium;
import com.exception.magicsnumbersws.entities.ConsortiumGeneralLimit;
import com.exception.magicsnumbersws.exception.FindConsortiumGeneralLimitException;
import com.exception.magicsnumbersws.exception.SaveConsortiumDataException;
import com.exception.magicsnumbersws.exception.SearchAllBetBankingException;
import com.exception.magicsnumbersws.exception.SearchAllConsortiumException;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author cpimentel
 */
@Service
public interface ConsortiumService {

    List<Consortium> findAll(int userId) throws SearchAllConsortiumException;

    List<Consortium> findAllConsortiumActive() throws SearchAllConsortiumException;

    public void saveConsortiumsData(List<Consortium> list) throws SaveConsortiumDataException;

    public List<BetBanking> findBetBankingAvailable() throws SearchAllBetBankingException;

    public List<BetBanking> findBetBankingAsignedToConsortium(int consortiumId) throws SearchAllBetBankingException;

    public void saveConsortiumData(Consortium consortium) throws SaveConsortiumDataException;
    public List<ConsortiumGeneralLimit> findConsortiumLimitByConsortiumId(int consortiumId) throws FindConsortiumGeneralLimitException;
}
