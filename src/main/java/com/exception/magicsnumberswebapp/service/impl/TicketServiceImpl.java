package com.exception.magicsnumberswebapp.service.impl;

import com.exception.magicsnumberswebapp.dao.TicketDao;
import com.exception.magicsnumberswebapp.service.TicketService;
import com.exception.magicsnumbersws.entities.Ticket;
import com.exception.magicsnumbersws.exception.FindBetLimitException;
import com.exception.magicsnumbersws.exception.FindBlockingNumberException;
import com.exception.magicsnumbersws.exception.FindTicketException;
import com.exception.magicsnumbersws.exception.SaveTicketException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author cpimentel
 * 24-oct-13
 */
@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketDao ticketDao;    

    @Override
    public void saveTicket(Ticket ticket) throws SaveTicketException {
        this.ticketDao.saveTicket(ticket);
    }

    @Override
    public String getNumbersBlocks(int bankingId, String numbers) throws FindBlockingNumberException {
        return this.ticketDao.getNumbersBlocks(bankingId, numbers);
    }

    @Override
    public String findBetBankingBetLimitAmount(int betBankingId, int lotteryId, int betId) throws FindBetLimitException {
        return this.ticketDao.findBetBankingBetLimitAmount(betBankingId, lotteryId, betId);
    }

    @Override
    public List<Ticket> findTickets(int betBankingId, String fromDate, String toDate) throws FindTicketException {
        return this.ticketDao.findTickets(betBankingId, fromDate, toDate);        
    }

    @Override
    public List<Ticket> findTodayTicketByUserName(String userName) throws FindTicketException {
        return this.ticketDao.findTodayTicketByUserName(userName);
    }
}
