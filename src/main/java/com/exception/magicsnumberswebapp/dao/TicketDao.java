package com.exception.magicsnumberswebapp.dao;

import com.exception.magicsnumbersws.entities.Ticket;
import com.exception.magicsnumbersws.exception.FindBetLimitException;
import com.exception.magicsnumbersws.exception.FindBlockingNumberException;
import com.exception.magicsnumbersws.exception.FindTicketException;
import com.exception.magicsnumbersws.exception.SaveTicketException;
import java.util.List;

/**
 *
 * @author cpimentel
 * @since 29-oct-13
 */
public interface TicketDao {

    public void saveTicket(Ticket ticket) throws SaveTicketException;
    public String getNumbersBlocks(int bankingId, String numbers) throws FindBlockingNumberException;
    public String findBetBankingBetLimitAmount(int betBankingId, int lotteryId, int betId ) throws FindBetLimitException;
    public List<Ticket> findTickets(int betBankingId, String fromDate, String toDate) throws FindTicketException;
    public List<Ticket> findTodayTicketByUserName(String userName)throws FindTicketException;
}
