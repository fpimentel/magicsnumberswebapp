package com.exception.magicsnumberswebapp.dao;

import com.exception.magicsnumbersws.entities.Ticket;
import com.exception.magicsnumbersws.exception.FindBetLimitException;
import com.exception.magicsnumbersws.exception.FindBlockingNumberException;
import com.exception.magicsnumbersws.exception.SaveTicketException;

/**
 *
 * @author cpimentel
 * @since 29-oct-13
 */
public interface TicketDao {

    public void saveTicket(Ticket ticket) throws SaveTicketException;
    public String getNumbersBlocks(int bankingId, String numbers) throws FindBlockingNumberException;
    public String findBetBankingBetLimitAmount(int betBankingId, int lotteryId, int betId ) throws FindBetLimitException;
}
