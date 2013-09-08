package com.exception.magicsnumberswebapp.dao;

import com.exception.magicsnumbersws.entities.BetBanking;
import com.exception.magicsnumbersws.entities.Consortium;
import com.exception.magicsnumbersws.exception.SaveConsortiumDataException;
import com.exception.magicsnumbersws.exception.SearchAllBetBankingException;
import com.exception.magicsnumbersws.exception.SearchAllConsortiumException;
import java.util.List;

/**
 *
 * @author cpimentel
 * @since 02-sept-13
 */
public interface ConsortiumDao {

    List<Consortium> findAll(int userId) throws SearchAllConsortiumException;

    public void saveConsortiumsData(List<Consortium> list) throws SaveConsortiumDataException;

    public List<BetBanking> findBetBankingAvailable() throws SearchAllBetBankingException;

    public List<BetBanking> findBetBankingAsignedToConsortium(int consortiumId) throws SearchAllBetBankingException;

    public void saveConsortiumData(Consortium consortium) throws SaveConsortiumDataException;
}
