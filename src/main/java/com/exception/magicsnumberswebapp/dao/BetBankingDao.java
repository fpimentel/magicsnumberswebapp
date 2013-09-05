
package com.exception.magicsnumberswebapp.dao;


import com.exception.magicsnumbersws.entities.BetBanking;


import com.exception.magicsnumbersws.exception.SearchAllBetBankingException;

import java.util.List;


/**
 *
 * @author cpimentel
 * @since  02-sept-13
 */
public interface BetBankingDao {
   
   List<BetBanking> findAll()throws SearchAllBetBankingException;
   
}
