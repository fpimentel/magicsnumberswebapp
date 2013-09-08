package com.exception.magicsnumberswebapp.dao.impl;
import com.exception.magicsnumberswebapp.dao.BetBankingDao;
import com.exception.magicsnumbersws.endpoints.LookupTablesEndpoint;
import com.exception.magicsnumbersws.entities. BetBanking;
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

    
}
