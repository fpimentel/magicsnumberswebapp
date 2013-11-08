package com.exception.magicsnumberswebapp.dao.impl;

import com.exception.magicsnumberswebapp.dao.TicketDao;
import com.exception.magicsnumbersws.endpoints.BusinessEndpoint;
import com.exception.magicsnumbersws.entities.Ticket;
import com.exception.magicsnumbersws.exception.FindBetLimitException;
import com.exception.magicsnumbersws.exception.FindBlockingNumberException;
import com.exception.magicsnumbersws.exception.SaveTicketException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author cpimentel
 */
@Repository
public class TicketDaoImpl implements TicketDao {

    @Autowired
    private BusinessEndpoint businessEndpoint;

    @Override
    public void saveTicket(Ticket ticket) throws SaveTicketException {
        this.businessEndpoint.saveTicket(ticket);
    }

    @Override
    public String getNumbersBlocks(int bankingId, String numbers) throws FindBlockingNumberException {
        return this.businessEndpoint.isNumbersBlocks(bankingId, numbers);
    }

    @Override
    public String findBetBankingBetLimitAmount(int betBankingId, int lotteryId, int betId) throws FindBetLimitException {
        return this.businessEndpoint.findBetBankingBetLimitAmount(betBankingId, lotteryId, betId);
    }

    
}
