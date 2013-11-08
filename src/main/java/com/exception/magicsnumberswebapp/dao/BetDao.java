package com.exception.magicsnumberswebapp.dao;

import com.exception.magicsnumbersws.entities.Bet;
import com.exception.magicsnumbersws.exception.FindBetException;
import java.util.List;

/**
 *
 * @author cpimentel
 * @since 19-sept-13
 */
public interface BetDao {

    public List<Bet> findActiveBets() throws FindBetException;
}
