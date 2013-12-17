package com.exception.magicsnumberswebapp.controller;

import com.exception.magicsnumberswebapp.constants.Profile;
import com.exception.magicsnumberswebapp.service.BetBankingService;
import com.exception.magicsnumberswebapp.service.ConsortiumService;
import com.exception.magicsnumbersws.entities.Bet;
import com.exception.magicsnumbersws.entities.BetBanking;
import com.exception.magicsnumbersws.entities.Consortium;
import com.exception.magicsnumbersws.entities.Lottery;
import com.exception.magicsnumbersws.entities.Time;
import com.exception.magicsnumbersws.exception.SearchAllConsortiumException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.event.ValueChangeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author cpimentel
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
    private Consortium selectedConsortium;
    private BetBanking selectedBetBanking;
    private Bet selectedBet;
    private Lottery selectedLottery;
    private Time selectedTime;
    private List<Consortium> consortiums;
    private List<BetBanking> betBankings;
    private List<Lottery> lotteries;
    private List<Bet> bets;
    private List<Time> times;

    public TicketReportController() {
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

    public List<Time> getTimes() {
        return times;
    }

    public void setTimes(List<Time> times) {
        this.times = times;
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
        for(BetBanking currBetBanking : betBankings){
            if(currBetBanking.getId() == ((BetBanking) event.getNewValue()).getId()){
                this.selectedBetBanking = currBetBanking;
            }
        }        
        if (this.selectedBetBanking != null) {
            this.lotteries = new ArrayList<Lottery>(this.selectedBetBanking.getLotteries());
        }
    }

    public void getBetsByLotteryOnChange(ValueChangeEvent event) {
        this.selectedLottery = (Lottery) event.getNewValue();
        if (this.selectedLottery != null) {
            this.bets = new ArrayList<Bet>(this.selectedLottery.getBets());
        }
    }
}