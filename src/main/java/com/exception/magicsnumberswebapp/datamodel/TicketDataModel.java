package com.exception.magicsnumberswebapp.datamodel;

import com.exception.magicsnumbersws.entities.Ticket;
import com.exception.magicsnumbersws.entities.TicketDetail;
import java.util.Collections;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;
import org.springframework.beans.BeanUtils;
/*
 * @autor:fpimentel
 * @since:17-dic-2013
 */

public class TicketDataModel extends ListDataModel<Ticket> implements SelectableDataModel<Ticket> {

    private List<Ticket> tickets;

    public TicketDataModel() {
    }

    public TicketDataModel(List<Ticket> data) {
        super(data);
        this.tickets = data;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    

    public int nextTicketId() {
        if (this.tickets == null || this.tickets.isEmpty()) {
            return 0;
        }
        Ticket ticket = Collections.max(this.tickets);
        return ticket.getId() + 1;
    }
    @Override
    public Object getRowKey(Ticket ticket) {
        return ticket.getId();
    }

    @Override
    public Ticket getRowData(String rowkey) {
        try {
            List<Ticket> tickets = (List<Ticket>) getWrappedData();
            for (Ticket ticketCurrent : tickets) {
                if (ticketCurrent.getId().toString().equals(rowkey)) {
                    Ticket ticketCopy = new Ticket();
                    BeanUtils.copyProperties(ticketCurrent, ticketCopy);
                    return ticketCopy;
                }
            }

        } catch (NumberFormatException ex) {
            int i = 0;
        }
        return null;
    }
}