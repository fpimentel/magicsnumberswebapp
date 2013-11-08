package com.exception.magicsnumberswebapp.service.impl;

import com.exception.magicsnumberswebapp.dao.BetDao;
import com.exception.magicsnumberswebapp.service.BetService;
import com.exception.magicsnumbersws.entities.Bet;
import com.exception.magicsnumbersws.exception.FindBetException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author cpimentel
 */
@Service
public class BetServiceImpl implements BetService {

    @Autowired
    private BetDao betDao;

    @Override
    public List<Bet> findActiveBets() throws FindBetException {
        return betDao.findActiveBets();
    }
}
