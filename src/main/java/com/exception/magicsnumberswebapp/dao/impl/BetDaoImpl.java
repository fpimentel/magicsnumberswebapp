package com.exception.magicsnumberswebapp.dao.impl;

import com.exception.magicsnumberswebapp.dao.BetBankingDao;
import com.exception.magicsnumberswebapp.dao.BetDao;
import com.exception.magicsnumbersws.endpoints.LookupTablesEndpoint;
import com.exception.magicsnumbersws.entities.Bet;
import com.exception.magicsnumbersws.entities.BetBanking;
import com.exception.magicsnumbersws.exception.FindBetException;
import com.exception.magicsnumbersws.exception.SearchAllBetBankingException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author cpimentel
 * @since 19-sept-13
 */
@Repository
public class BetDaoImpl implements BetDao {

    @Autowired
    private LookupTablesEndpoint lookupTablesEndpoint;

    public BetDaoImpl() {
    }

    @Override
    public List<Bet> findActiveBets() throws FindBetException {
        return lookupTablesEndpoint.findActiveBets();
    }
}
