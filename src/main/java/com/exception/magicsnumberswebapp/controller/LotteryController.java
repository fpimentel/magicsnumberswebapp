package com.exception.magicsnumberswebapp.controller;

import com.exception.magicsnumberswebapp.datamodel.BetLimitDataModel;
import com.exception.magicsnumberswebapp.datamodel.LotteryCloseHourDataModel;
import com.exception.magicsnumberswebapp.datamodel.LotteryDataModel;
import com.exception.magicsnumberswebapp.service.BetBankingService;
import com.exception.magicsnumberswebapp.service.BetService;
import com.exception.magicsnumberswebapp.service.LotteryService;
import com.exception.magicsnumberswebapp.service.StatusService;
import com.exception.magicsnumberswebapp.view.converter.ConsortiumConverter;
import com.exception.magicsnumbersws.containers.BetBankingContainer;
import com.exception.magicsnumbersws.entities.Bet;
import com.exception.magicsnumbersws.entities.BetBanking;
import com.exception.magicsnumbersws.entities.BetBankingBetLimit;
import com.exception.magicsnumbersws.entities.BlockingNumberBetBanking;
import com.exception.magicsnumbersws.entities.Consortium;
import com.exception.magicsnumbersws.entities.Day;
import com.exception.magicsnumbersws.entities.Lottery;
import com.exception.magicsnumbersws.entities.LotteryCloseHour;
import com.exception.magicsnumbersws.entities.Status;
import com.exception.magicsnumbersws.entities.User;
import com.exception.magicsnumbersws.exception.FindBetLimitException;
import com.exception.magicsnumbersws.exception.FindLotteryCloseHourException;
import com.exception.magicsnumbersws.exception.FindLotteryException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Scope;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author cpimentel
 */
@Controller
@Scope("view")
public class LotteryController {

    @Autowired
    private BetBankingService bankingService;
    @Autowired
    private StatusService statusService;
    @Autowired
    private ConsortiumConverter consortiumConverter;
    @Autowired
    private LoginController loginController;
    @Autowired
    private BetService betService;
    @Autowired
    private LotteryService lotteryService;
    @Autowired
    private BetBankingService betBankingService;
    private BetBanking selectedBetBanking;
    private BetBankingBetLimit selectedBetBankingBetLimit;
    private LotteryCloseHour selectedLotteryCloseHour;
    private Bet selectedBet;
    private Lottery selectedLottery;
    private double amountLimit;
    private boolean editMode = false;
    private boolean lotteryEditMode = false;
    private LotteryDataModel lotteryDataModel;
    private List<Status> status;
    private Status selectedStatus;
    private List<Bet> bets;
    private List<Lottery> lotteries;
    private BetLimitDataModel betBankingBetLimitDataModel;
    private LotteryCloseHourDataModel lotteryCloseHourDataModel;
    private BetBankingBetLimit betLimitToDelete;
    private BlockingNumberBetBanking blockNumberToDelete;
    private int NumberToBlock;
    private int commission;
    private BetBankingContainer betBakingContainer;
    private int firstTabIndex = 0;
    private Day seletedDay;

    public LotteryController() {
    }

    public Day getSeletedDay() {
        if (this.seletedDay == null) {
            this.seletedDay = new Day();
        }
        return seletedDay;
    }

    public void setSeletedDay(Day seletedDay) {
        this.seletedDay = seletedDay;
    }

    public Lottery getSelectedLottery() {
        if (this.selectedLottery == null) {
            this.selectedLottery = new Lottery();
        }
        return selectedLottery;
    }

    public void setSelectedLottery(Lottery selectedLottery) {
        this.selectedLottery = selectedLottery;
    }

    public int getFirstTabIndex() {
        return firstTabIndex;
    }

    public void getBetsByLotteryOnChange(ValueChangeEvent event) {
        this.selectedLottery = (Lottery) event.getNewValue();
        this.bets = new ArrayList(this.selectedLottery.getBets());
    }

    public void setBetBakingContainer(BetBankingContainer betBakingContainer) {
        this.betBakingContainer = betBakingContainer;
    }

    public BlockingNumberBetBanking getBlockNumberToDelete() {
        if (this.blockNumberToDelete == null) {
            this.blockNumberToDelete = new BlockingNumberBetBanking();
        }
        return blockNumberToDelete;
    }

    public void setBlockNumberToDelete(BlockingNumberBetBanking blockNumberToDelete) {
        this.blockNumberToDelete = blockNumberToDelete;

        this.editMode = false;
    }

    public int getNumberToBlock() {
        return NumberToBlock;
    }

    public LotteryCloseHour getSelectedLotteryCloseHour() {
        if (this.selectedLotteryCloseHour == null) {
            this.selectedLotteryCloseHour = new LotteryCloseHour();
        }
        return selectedLotteryCloseHour;
    }

    public void setSelectedLotteryCloseHour(LotteryCloseHour selectedLotteryCloseHour) {
        this.selectedLotteryCloseHour = selectedLotteryCloseHour;
    }

    public LotteryCloseHourDataModel getLotteryCloseHourDataModel() {
        if (this.lotteryCloseHourDataModel == null) {
            this.lotteryCloseHourDataModel = new LotteryCloseHourDataModel(new ArrayList<LotteryCloseHour>());
        }
        return lotteryCloseHourDataModel;
    }

    public void setLotteryCloseHourDataModel(LotteryCloseHourDataModel lotteryCloseHourDataModel) {
        this.lotteryCloseHourDataModel = lotteryCloseHourDataModel;
    }

    public void setNumberToBlock(int NumberToBlock) {
        this.NumberToBlock = NumberToBlock;
    }

    public Bet getSelectedBet() {
        return selectedBet;
    }

    public int getCommission() {
        return commission;
    }

    public void setCommission(int commission) {
        this.commission = commission;
    }

    public void setSelectedBet(Bet selectedBet) {
        this.selectedBet = selectedBet;
    }

    public double getAmountLimit() {
        return amountLimit;
    }

    public void setAmountLimit(double amountLimit) {
        this.amountLimit = amountLimit;
    }

    public BetBankingBetLimit getSelectedBetBankingBetLimit() {
        if (this.selectedBetBankingBetLimit == null) {
            this.selectedBetBankingBetLimit = new BetBankingBetLimit();
        }
        return selectedBetBankingBetLimit;
    }

    public BetBankingBetLimit getBetLimitToDelete() {
        if (this.betLimitToDelete == null) {
            this.betLimitToDelete = new BetBankingBetLimit();
        }
        return betLimitToDelete;
    }

    public void setBetLimitToDelete(BetBankingBetLimit betLimitToDelete) {
        this.betLimitToDelete = betLimitToDelete;
        this.betBankingBetLimitDataModel.getBetLimits().remove(betLimitToDelete);
        this.editMode = false;
    }

    public void setSelectedBetBankingBetLimit(BetBankingBetLimit selectedBetBankingBetLimit) {
        this.selectedBetBankingBetLimit = selectedBetBankingBetLimit;
    }

    public BetLimitDataModel getBetBankingBetLimitDataModel() {
        if (this.betBankingBetLimitDataModel == null) {
            this.betBankingBetLimitDataModel = new BetLimitDataModel(new ArrayList<BetBankingBetLimit>());
        }
        return this.betBankingBetLimitDataModel;
    }

    public void setBetBankingBetLimitDataModel(BetLimitDataModel betBankingBetLimitDataModel) {
        this.betBankingBetLimitDataModel = betBankingBetLimitDataModel;
    }

    public List<Lottery> getLotteries() {
        if (this.lotteries == null) {
            try {
                this.lotteries = this.lotteryService.findActiveLottery();
            } catch (FindLotteryException ex) {
                Logger.getLogger(LotteryController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(LotteryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return this.lotteries;
    }

    public void setLotteries(List<Lottery> lotteries) {
        this.lotteries = lotteries;
    }

    public List<Bet> getBets() {

        return this.bets;
    }

    public void setBets(List<Bet> bets) {
        this.bets = bets;
    }

    public LotteryDataModel getLotteryDataModel() {
        if (this.lotteryDataModel == null) {
            refreshDataModel();
        }
        return lotteryDataModel;
    }

    public void setLotteryDataModel(LotteryDataModel lotteryDataModel) {
        this.lotteryDataModel = lotteryDataModel;
    }

    public BetBanking getSelectedBetBanking() {
        if (this.selectedBetBanking == null) {
            this.selectedBetBanking = new BetBanking();
        }
        return this.selectedBetBanking;
    }

    public void setSelectedBetBanking(BetBanking selectedBetBanking) {
        this.selectedBetBanking = selectedBetBanking;
    }

    public Status getSelectedStatus() {
        return this.selectedStatus;
    }

    public void setSelectedStatus(Status selectedStatus) {
        this.selectedStatus = selectedStatus;
    }

    public List<Status> getStatus() {
        if (this.status == null) {
            int statusTypeBasicId = com.exception.magicsnumberswebapp.constants.StatusType.BASIC.getId();
            this.status = statusService.getStatusByStatusType(statusTypeBasicId);

        }
        return this.status;
    }

    public void setStatus(List<Status> status) {
        this.status = status;
    }

    public void addNewLottery() {
        this.selectedLottery = new Lottery();
        this.lotteryEditMode = false;
    }

    private void refreshDataModel() {
        User loggedUser = loginController.getUser();
        try {
            this.lotteryDataModel = new LotteryDataModel(this.lotteryService.findLotteries());

        } catch (FindLotteryException ex) {
            Logger.getLogger(LotteryController.class.getName()).log(Level.SEVERE, "refreshDataModel() in LotteryController", ex);
        } catch (Exception ex) {
            Logger.getLogger(LotteryController.class.getName()).log(Level.SEVERE, "refreshDataModel() in LotteryController", ex);
        }
    }

    private void refreshLotteryCloseHourDataModel() {
        try {
            this.lotteryCloseHourDataModel = new LotteryCloseHourDataModel(this.lotteryService.findAvailableCloseHour(this.selectedLottery.getId()));
        } catch (FindLotteryCloseHourException ex) {
            Logger.getLogger(ConsortiumController.class.getName()).log(Level.SEVERE, "refreshBlockinNumberDataModel() in BetBankingController", ex);
        } catch (Exception ex) {
            Logger.getLogger(ConsortiumController.class.getName()).log(Level.SEVERE, "refreshBlockinNumberDataModel() in BetBankingController", ex);
        }
    }

    private void refreshBetLimitDataModel() {
        try {
            this.betBankingBetLimitDataModel = new BetLimitDataModel(bankingService.findBetLimitsByBetBankingId(this.selectedBetBanking.getId()));
        } catch (FindBetLimitException ex) {
            Logger.getLogger(ConsortiumController.class.getName()).log(Level.SEVERE, "refreshBetLimitDataModel() in BetBankingController", ex);
        } catch (Exception ex) {
            Logger.getLogger(ConsortiumController.class.getName()).log(Level.SEVERE, "refreshBetLimitDataModel() in BetBankingController", ex);
        }
    }

    public List<Consortium> getAutoCompleteConsortiums(String query) {
        List<Consortium> suggestions = new ArrayList<Consortium>();
        query = query.toUpperCase();
        for (Consortium consortium : consortiumConverter.getConsortiums()) {
            if (consortium.getName().toUpperCase().contains(query.toUpperCase())) {
                suggestions.add(consortium);
            }
        }
        return suggestions;
    }

    public void onRowSelect(SelectEvent event) {
        lotteryEditMode = true;
        /*refreshBetLimitDataModel();*/
        refreshLotteryCloseHourDataModel();
    }
}
