package com.exception.magicsnumberswebapp.service.impl;
import com.exception.magicsnumberswebapp.dao.BetBankingDao;
import com.exception.magicsnumberswebapp.service.BetBankingService;
import com.exception.magicsnumbersws.entities.BetBanking;
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
    public List<BetBanking> findAllBetBanking() throws SearchAllBetBankingException {
        return betBankingDao.findAll();
    }

    
    
}
