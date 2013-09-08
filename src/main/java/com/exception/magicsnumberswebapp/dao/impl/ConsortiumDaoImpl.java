package com.exception.magicsnumberswebapp.dao.impl;
import com.exception.magicsnumberswebapp.dao.ConsortiumDao;
import com.exception.magicsnumbersws.endpoints.LookupTablesEndpoint;
import com.exception.magicsnumbersws.entities.BetBanking;
import com.exception.magicsnumbersws.entities.Consortium;
import com.exception.magicsnumbersws.exception.SaveConsortiumDataException;
import com.exception.magicsnumbersws.exception.SearchAllBetBankingException;
import com.exception.magicsnumbersws.exception.SearchAllConsortiumException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author cpimentel
 */
@Repository
public class ConsortiumDaoImpl implements ConsortiumDao {

    @Autowired
    private LookupTablesEndpoint lookupTableEndpoint;

    public ConsortiumDaoImpl() {
    }

    @Override
    public List<Consortium> findAll(int userId) throws SearchAllConsortiumException {
        return lookupTableEndpoint.findConsortiumByUserId(userId);
    }

    @Override
    public void saveConsortiumsData(List<Consortium> list) throws SaveConsortiumDataException {
        lookupTableEndpoint.saveConsortiumsData(list);
    }
    @Override
    public List<BetBanking>  findBetBankingAvailable() throws SearchAllBetBankingException {
        return lookupTableEndpoint.findAvailableBetBankings();
    }
    @Override
    public List<BetBanking> findBetBankingAsignedToConsortium(int consortiumId) throws SearchAllBetBankingException {
        return lookupTableEndpoint.findBetBankingAsignedToConsortium(consortiumId);
    }
    @Override
    public void saveConsortiumData(Consortium consortium) throws SaveConsortiumDataException {         
        this.lookupTableEndpoint.saveConsortiumData(consortium);
    }
}
