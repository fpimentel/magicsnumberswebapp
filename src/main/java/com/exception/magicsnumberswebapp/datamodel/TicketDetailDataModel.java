package com.exception.magicsnumberswebapp.datamodel;

import com.exception.magicsnumbersws.entities.TicketDetail;
import java.util.Collections;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;
import org.springframework.beans.BeanUtils;

public class TicketDetailDataModel extends ListDataModel<TicketDetail> implements SelectableDataModel<TicketDetail> {

    private List<TicketDetail> ticketDetails;

    public TicketDetailDataModel() {
    }

    public TicketDetailDataModel(List<TicketDetail> data) {
        super(data);
        this.ticketDetails = data;
    }

    public List<TicketDetail> getTicketDetails() {
        return this.ticketDetails;
    }

    public void setTicketDetails(List<TicketDetail> ticketDetails) {
        this.ticketDetails = ticketDetails;
    }

    public int nextBetBankingId() {
        if (this.ticketDetails == null || this.ticketDetails.isEmpty()) {
            return 0;
        }
        TicketDetail ticketDetails = Collections.max(this.ticketDetails);
        return ticketDetails.getId() + 1;
    }
    @Override
    public Object getRowKey(TicketDetail ticketDetails) {
        return ticketDetails.getId();
    }

    @Override
    public TicketDetail getRowData(String rowkey) {
        try {
            List<TicketDetail> ticketDetails = (List<TicketDetail>) getWrappedData();
            for (TicketDetail ticketDetailsCurrent : ticketDetails) {
                if (ticketDetailsCurrent.getId().toString().equals(rowkey)) {
                    TicketDetail ticketDetailsCopy = new TicketDetail();
                    BeanUtils.copyProperties(ticketDetailsCurrent, ticketDetailsCopy);
                    return ticketDetailsCopy;
                }
            }

        } catch (NumberFormatException ex) {
            int i = 0;
        }
        return null;
    }
}