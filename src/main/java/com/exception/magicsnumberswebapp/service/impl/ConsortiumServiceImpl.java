package com.exception.magicsnumberswebapp.service.impl;
import com.exception.magicsnumberswebapp.dao.ConsortiumDao;
import com.exception.magicsnumberswebapp.service.ConsortiumService;
import com.exception.magicsnumbersws.containers.ConsortiumContainer;
import com.exception.magicsnumbersws.entities.BetBanking;
import com.exception.magicsnumbersws.entities.Consortium;
import com.exception.magicsnumbersws.entities.ConsortiumGeneralLimit;
import com.exception.magicsnumbersws.exception.FindConsortiumGeneralLimitException;
import com.exception.magicsnumbersws.exception.SaveConsortiumDataException;
import com.exception.magicsnumbersws.exception.SearchAllBetBankingException;
import com.exception.magicsnumbersws.exception.SearchAllConsortiumException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author cpimentel
 */
@Service
public class ConsortiumServiceImpl implements ConsortiumService {

    @Autowired
    private ConsortiumDao consortiumDao;
    private List<Consortium> consortium;

    public ConsortiumDao getConsortiumDao() {
        return consortiumDao;
    }

    public void setConsortiumDao(ConsortiumDao consortiumDao) {
        this.consortiumDao = consortiumDao;
    }

    public List<Consortium> getConsortium() {
        return consortium;
    }

    public void setConsortium(List<Consortium> consortium) {
        this.consortium = consortium;
    }
    
    @Override
    public List<Consortium> findAll(int userId) throws SearchAllConsortiumException {
      return this.consortiumDao.findAll(userId);
    }

    @Override
    public void saveConsortiumsData(List<Consortium> list) throws SaveConsortiumDataException {
        this.consortiumDao.saveConsortiumsData(list);
    }

    @Override
    public List<BetBanking> findBetBankingAvailable() throws SearchAllBetBankingException {
        return consortiumDao.findBetBankingAvailable();
    }

    @Override
    public List<BetBanking> findBetBankingAsignedToConsortium(int consortiumId) throws SearchAllBetBankingException {
        return consortiumDao.findBetBankingAsignedToConsortium(consortiumId);
    }

    @Override
    public void saveConsortiumData(ConsortiumContainer consortium) throws SaveConsortiumDataException {
        this.consortiumDao.saveConsortiumData(consortium);
    }

    @Override
    public List<Consortium> findAllConsortiumActive() throws SearchAllConsortiumException {
        return this.consortiumDao.findAll();
    }

    @Override
    public List<ConsortiumGeneralLimit> findConsortiumLimitByConsortiumId(int consortiumId) throws FindConsortiumGeneralLimitException {
        return this.consortiumDao.findConsortiumLimitByConsortiumId(consortiumId);
    }



    
}
