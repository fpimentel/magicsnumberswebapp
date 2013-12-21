package com.exception.magicsnumberswebapp.controller;

import com.exception.magicsnumberswebapp.constants.Profile;
import com.exception.magicsnumberswebapp.datamodel.TicketDataModel;
import com.exception.magicsnumberswebapp.service.BetBankingService;
import com.exception.magicsnumberswebapp.service.ConsortiumService;
import com.exception.magicsnumberswebapp.service.LotteryService;
import com.exception.magicsnumberswebapp.service.StatusService;
import com.exception.magicsnumberswebapp.service.TicketService;
import com.exception.magicsnumbersws.entities.Bet;
import com.exception.magicsnumbersws.entities.BetBanking;
import com.exception.magicsnumbersws.entities.Consortium;
import com.exception.magicsnumbersws.entities.Lottery;
import com.exception.magicsnumbersws.entities.Status;
import com.exception.magicsnumbersws.entities.Ticket;
import com.exception.magicsnumbersws.entities.Time;
import com.exception.magicsnumbersws.exception.FindLotteryCloseHourException;
import com.exception.magicsnumbersws.exception.FindTicketException;
import com.exception.magicsnumbersws.exception.SearchAllConsortiumException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author fpimentel
 */
@Controller
@Scope("view")
public class TicketReportController {

    private String betBankingName;
    @Autowired
    private LoginController loginController;
    @Autowired
    private ConsortiumService consortiumService;
    @Autowired
    private BetBankingService betBankingService;
    @Autowired
    private StatusService statusService;
    @Autowired
    private TicketService ticketService;
    private LotteryService lotteryService;
    private Consortium selectedConsortium;
    private BetBanking selectedBetBanking;
    private Bet selectedBet;
    private Lottery selectedLottery;
    private Time selectedTime;
    private Status selectedStatus;
    private List<Consortium> consortiums;
    private List<BetBanking> betBankings;
    private List<Lottery> lotteries;
    private List<Bet> bets;
    private Set<Time> times;
    private List<Status> status;
    private Date startingDate;
    private Date finishDate;
    private TicketDataModel ticketDataModel;
    private Ticket selectedTicket;
    private float totalSellTicketAmount;
    private int totalSellTicketQty;
    private float totalWinTicketAmount;
    private int totalWinTicketQty;

    public TicketReportController() {
        ticketDataModel = new TicketDataModel(new ArrayList<Ticket>());
        this.startingDate = new Date();
        this.finishDate = new Date();
    }

    public Consortium getSelectedConsortium() {
        return selectedConsortium;
    }

    public void setSelectedConsortium(Consortium selectedConsortium) {
        this.selectedConsortium = selectedConsortium;
    }

    public BetBanking getSelectedBetBanking() {
        return selectedBetBanking;
    }

    public void setSelectedBetBanking(BetBanking selectedBetBanking) {
        this.selectedBetBanking = selectedBetBanking;
    }

    public List<BetBanking> getBetBankings() {
        return betBankings;
    }

    public void setBetBankings(List<BetBanking> betBankings) {
        this.betBankings = betBankings;
    }

    public List<Lottery> getLotteries() {
        return lotteries;
    }

    public void setLotteries(List<Lottery> lotteries) {
        this.lotteries = lotteries;
    }

    public Lottery getSelectedLottery() {
        return selectedLottery;
    }

    public void setSelectedLottery(Lottery selectedLottery) {
        this.selectedLottery = selectedLottery;
    }

    public Bet getSelectedBet() {
        return selectedBet;
    }

    public void setSelectedBet(Bet selectedBet) {
        this.selectedBet = selectedBet;
    }

    public List<Bet> getBets() {
        return bets;
    }

    public void setBets(List<Bet> bets) {
        this.bets = bets;
    }

    public Time getSelectedTime() {
        return selectedTime;
    }

    public void setSelectedTime(Time selectedTime) {
        this.selectedTime = selectedTime;
    }

    public Set<Time> getTimes() {
        return times;
    }

    public void setTimes(Set<Time> times) {
        this.times = times;
    }

    public Status getSelectedStatus() {
        return selectedStatus;
    }

    public void setSelectedStatus(Status selectedStatus) {
        this.selectedStatus = selectedStatus;
    }

    public Date getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public TicketDataModel getTicketDataModel() {
        return ticketDataModel;
    }

    public void setTicketDataModel(TicketDataModel ticketDataModel) {
        this.ticketDataModel = ticketDataModel;
    }

    public List<Status> getStatus() {
        if (this.status == null) {
            int statusTypeBasicId = com.exception.magicsnumberswebapp.constants.StatusType.TICKET.getId();
            this.status = statusService.getStatusByStatusType(statusTypeBasicId);
        }
        return this.status;
    }

    public void setStatus(List<Status> status) {
        this.status = status;
    }

    public void onRowSelect(SelectEvent event) {
    }

    public Ticket getSelectedTicket() {
        return selectedTicket;
    }

    public void setSelectedTicket(Ticket selectedTicket) {
        this.selectedTicket = selectedTicket;
    }

    public float getTotalSellTicketAmount() {
        return totalSellTicketAmount;
    }

    public void setTotalSellTicketAmount(float totalSellTicketAmount) {
        this.totalSellTicketAmount = totalSellTicketAmount;
    }

    public int getTotalSellTicketQty() {
        return totalSellTicketQty;
    }

    public void setTotalSellTicketQty(int totalSellTicketQty) {
        this.totalSellTicketQty = totalSellTicketQty;
    }

    public float getTotalWinTicketAmount() {
        return totalWinTicketAmount;
    }

    public void setTotalWinTicketAmount(float totalWinTicketAmount) {
        this.totalWinTicketAmount = totalWinTicketAmount;
    }

    public int getTotalWinTicketQty() {
        return totalWinTicketQty;
    }

    public void setTotalWinTicketQty(int totalWinTicketQty) {
        this.totalWinTicketQty = totalWinTicketQty;
    }

    public List<Consortium> getConsortiums() {
        if (this.consortiums == null) {
            try {
                if (this.loginController.getUser().getProfile().getId() == Profile.ADMINISTRATOR.getId()) {
                    this.consortiums = this.consortiumService.findAllConsortiumActive();
                } else if (this.loginController.getUser().getProfile().getId() == Profile.CONSORTIUMADMIN.getId()) {
                    this.consortiums = this.consortiumService.findAll(this.loginController.getUser().getId());
                }
            } catch (SearchAllConsortiumException ex) {
                Logger.getLogger(TicketReportController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(TicketReportController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return this.consortiums;
    }

    public void setConsortiums(List<Consortium> consortiums) {
        this.consortiums = consortiums;
    }

    public void getBetBankingByConsortiumOnChange(ValueChangeEvent event) {
        this.selectedConsortium = (Consortium) event.getNewValue();
        if (this.selectedConsortium != null) {
            this.betBankings = new ArrayList<BetBanking>(this.selectedConsortium.getBetBankings());
        }
    }

    public void getLotteriesByBetBankingOnChange(ValueChangeEvent event) {
        for (BetBanking currBetBanking : betBankings) {
            if (currBetBanking.getId() == ((BetBanking) event.getNewValue()).getId()) {
                this.selectedBetBanking = currBetBanking;
            }
        }
        if (this.selectedBetBanking != null) {
            this.lotteries = new ArrayList<Lottery>(this.selectedBetBanking.getLotteries());
        }
    }

    public void getDataByLotteryOnChange(ValueChangeEvent event) {
        this.selectedLottery = (Lottery) event.getNewValue();
        FacesMessage msg;
        if (this.selectedLottery != null) {
            this.bets = new ArrayList<Bet>(this.selectedLottery.getBets());
            try {
                times = lotteryService.findTimesByLottery(selectedLottery.getId());
            } catch (FindLotteryCloseHourException ex) {
                Logger.getLogger(TicketReportController.class.getName()).log(Level.SEVERE, null, ex);
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error buscando las tandas!", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } catch (Exception ex) {
                Logger.getLogger(TicketReportController.class.getName()).log(Level.SEVERE, null, ex);
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error buscando las tandas!", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
    }

    private void sumTickets() {
        this.totalSellTicketAmount = 0.0f;
        this.totalSellTicketQty = 0;
        this.totalWinTicketAmount = 0.0f;
        this.totalWinTicketQty = 0;

        List<Ticket> tickets = this.ticketDataModel.getTickets();
        if (tickets != null) {
            for (Ticket currTicket : tickets) {
                this.totalSellTicketAmount += currTicket.getTotalBetAmount();
                this.totalSellTicketQty += 1;
                if (currTicket.getStatus() != null) {
                    final int WIN_TICKET_STATUS = com.exception.magicsnumberswebapp.constants.Status.WIN.getId();
                    if (currTicket.getStatus().getId() == WIN_TICKET_STATUS) {
                        this.totalWinTicketAmount += currTicket.getTotalWinAmount();
                        this.totalWinTicketQty += 1;
                    }
                }
            }
        }
    }

    public void findTickets(ActionEvent event) {
        FacesMessage msg;
        String fromDate = new SimpleDateFormat("dd-MM-yyyy").format(this.startingDate);
        String toDate = new SimpleDateFormat("dd-MM-yyyy").format(this.finishDate);
        final int betBankingId = this.selectedBetBanking.getId();

        try {
            this.ticketDataModel = new TicketDataModel(this.ticketService.findTickets(betBankingId, fromDate, toDate));            
            sumTickets();
        } catch (FindTicketException ex) {
            Logger.getLogger(TicketReportController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TicketReportController.class.getName()).log(Level.SEVERE, null, ex);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error buscando los ticket!", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
}