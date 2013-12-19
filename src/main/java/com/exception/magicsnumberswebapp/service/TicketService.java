package com.exception.magicsnumberswebapp.service;

import com.exception.magicsnumbersws.containers.TicketReportContainer;
import com.exception.magicsnumbersws.entities.Ticket;
import com.exception.magicsnumbersws.exception.FindBetLimitException;
import com.exception.magicsnumbersws.exception.FindBlockingNumberException;
import com.exception.magicsnumbersws.exception.FindTicketException;
import com.exception.magicsnumbersws.exception.SaveTicketException;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author cpimentel
 */
@Service
public interface TicketService {

    public void saveTicket(Ticket ticket) throws SaveTicketException;
    public String getNumbersBlocks(int bankingId, String numbers) throws FindBlockingNumberException;
    public String findBetBankingBetLimitAmount(int betBankingId, int lotteryId, int betId ) throws FindBetLimitException;
    public List<Ticket> findTickets(TicketReportContainer ticketReportContainer) throws FindTicketException;
}
