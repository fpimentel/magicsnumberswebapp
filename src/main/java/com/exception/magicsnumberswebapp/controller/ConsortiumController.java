package com.exception.magicsnumberswebapp.controller;

import com.exception.magicsnumberswebapp.datamodel.ConsortiumDataModel;
import com.exception.magicsnumberswebapp.datamodel.ConsortiumGeneralLimitDataModel;
import com.exception.magicsnumberswebapp.service.ConsortiumService;
import com.exception.magicsnumberswebapp.service.LotteryService;
import com.exception.magicsnumberswebapp.service.StatusService;
import com.exception.magicsnumbersws.containers.ConsortiumContainer;
import com.exception.magicsnumbersws.entities.Bet;
import com.exception.magicsnumbersws.entities.BetBanking;
import com.exception.magicsnumbersws.entities.Consortium;
import com.exception.magicsnumbersws.entities.ConsortiumGeneralLimit;
import com.exception.magicsnumbersws.entities.Lottery;
import com.exception.magicsnumbersws.entities.Status;
import com.exception.magicsnumbersws.entities.Time;
import com.exception.magicsnumbersws.entities.User;
import com.exception.magicsnumbersws.exception.FindConsortiumGeneralLimitException;
import com.exception.magicsnumbersws.exception.FindLotteryCloseHourException;
import com.exception.magicsnumbersws.exception.FindLotteryException;
import com.exception.magicsnumbersws.exception.SaveConsortiumDataException;
import com.exception.magicsnumbersws.exception.SearchAllBetBankingException;
import com.exception.magicsnumbersws.exception.SearchAllConsortiumException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.springframework.context.annotation.Scope;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author cpimentel
 */
@Controller
@Scope("view")
public class ConsortiumController {

    @Autowired
    private ConsortiumService consortiumService;
    @Autowired
    private StatusService statusService;
    @Autowired
    private LoginController loginController;
    @Autowired
    private LotteryService lotteryService;
    private List<Consortium> updatedConsortiums;
    private Consortium selectedConsortium;
    private boolean editMode = false;
    private boolean editConsortiumMode = false;
    private ConsortiumDataModel consortiumDataModel;
    private ConsortiumGeneralLimitDataModel consortiumGeneralLimitDataModel;
    private List<Status> status;
    private DualListModel<BetBanking> consortiumDualList;
    private Status selectedStatus;
    private List<BetBanking> availableBetBanking;
    private List<BetBanking> asignedBetBanking;
    private Lottery selectedLottery;
    private ConsortiumGeneralLimit selectedConsortiumGeneralLimit;
    private List<Lottery> lotteries;
    private List<Bet> bets;
    private Bet selectedBet;
    private Time selectedTime;
    private List<Time> times;
    private float amountLimit;
    private ConsortiumGeneralLimit consortiumGeneralLimitToDelete;

    public ConsortiumGeneralLimit getSelectedConsortiumGeneralLimit() {
        if (this.selectedConsortiumGeneralLimit == null) {
            this.selectedConsortiumGeneralLimit = new ConsortiumGeneralLimit();
        }
        return selectedConsortiumGeneralLimit;
    }

    public void setSelectedConsortiumGeneralLimit(ConsortiumGeneralLimit selectedConsortiumGeneralLimit) {
        this.selectedConsortiumGeneralLimit = selectedConsortiumGeneralLimit;
    }

    public ConsortiumController() {

        updatedConsortiums = new ArrayList<Consortium>();
    }

    public ConsortiumGeneralLimit getConsortiumGeneralLimitToDelete() {
        if (this.consortiumGeneralLimitToDelete == null) {
            this.consortiumGeneralLimitToDelete = new ConsortiumGeneralLimit();
        }
        return consortiumGeneralLimitToDelete;
    }

    public void setConsortiumGeneralLimitToDelete(ConsortiumGeneralLimit consortiumToDelete) {
        this.consortiumGeneralLimitToDelete = consortiumToDelete;
        this.consortiumGeneralLimitDataModel.getConsortiumGeneralLimits().remove(this.consortiumGeneralLimitToDelete);
        this.editConsortiumMode = false;
    }

    public void onRowSelectToConsortiumGeneralLimit(SelectEvent event) {
        editConsortiumMode = true;

        setConsortiumLimitsFields();
    }

    public void setConsortiumLimitsFields() {

        try {
            this.selectedLottery = this.selectedConsortiumGeneralLimit.getLottery();

            this.bets = new ArrayList(this.selectedLottery.getBets());
            this.times = new ArrayList(this.lotteryService.findTimesByLottery(this.selectedLottery.getId()));
            this.selectedTime = this.selectedConsortiumGeneralLimit.getTime();
            this.selectedBet = this.selectedConsortiumGeneralLimit.getBet();
            this.amountLimit = this.selectedConsortiumGeneralLimit.getAmount();
        } catch (FindLotteryCloseHourException ex) {
            Logger.getLogger(ConsortiumController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ConsortiumController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void refreshConsortiumGeneralLimitDataModel() {
        try {
            this.consortiumGeneralLimitDataModel = new ConsortiumGeneralLimitDataModel(this.consortiumService.findConsortiumLimitByConsortiumId(this.selectedConsortium.getId()));
        } catch (FindConsortiumGeneralLimitException ex) {
            Logger.getLogger(ConsortiumController.class.getName()).log(Level.SEVERE, "refreshConsortiumGeneralLimitDataModel() in ConsortiumController", ex);
        } catch (Exception ex) {
            Logger.getLogger(ConsortiumController.class.getName()).log(Level.SEVERE, "refreshConsortiumGeneralLimitDataModel() in ConsortiumController", ex);
        }

    }

    public ConsortiumGeneralLimitDataModel getConsortiumGeneralLimitDataModel() {
        if (this.consortiumGeneralLimitDataModel == null) {
            this.consortiumGeneralLimitDataModel = new ConsortiumGeneralLimitDataModel(new ArrayList<ConsortiumGeneralLimit>());
        }
        return consortiumGeneralLimitDataModel;
    }

    public void setConsortiumGeneralLimitDataModel(ConsortiumGeneralLimitDataModel consortiumGeneralLimitDataModel) {
        this.consortiumGeneralLimitDataModel = consortiumGeneralLimitDataModel;
    }

    public float getAmountLimit() {
        return amountLimit;
    }

    public void setAmountLimit(float amountLimit) {
        this.amountLimit = amountLimit;
    }

    public List<Time> getTimes() {
        return times;
    }

    public void setTimes(List<Time> times) {
        this.times = times;
    }

    public Time getSelectedTime() {
        return selectedTime;
    }

    public void setSelectedTime(Time selectedTime) {
        this.selectedTime = selectedTime;
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

    public List<Lottery> getLotteries() {
        if (this.lotteries == null) {
            try {
                this.lotteries = this.lotteryService.findActiveLottery();
            } catch (FindLotteryException ex) {
                Logger.getLogger(BetBankingController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(BetBankingController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return this.lotteries;
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

    public void getBetsByLotteryOnChange(ValueChangeEvent event) {
        this.selectedLottery = (Lottery) event.getNewValue();
        this.bets = new ArrayList(this.selectedLottery.getBets());
        try {
            this.times = new ArrayList(this.lotteryService.findTimesByLottery(this.selectedLottery.getId()));
        } catch (FindLotteryCloseHourException ex) {
            Logger.getLogger(ConsortiumController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DualListModel<BetBanking> getConsortiumDualList() {
        if (this.consortiumDualList == null) {
            this.consortiumDualList = new DualListModel<BetBanking>();
        }
        return consortiumDualList;
    }

    public void setConsortiumDualList(DualListModel<BetBanking> consortiumDualList) {
        this.consortiumDualList = consortiumDualList;
    }

    public List<BetBanking> getAvailableBetBanking() {
        return this.availableBetBanking;
    }

    public void setAvariableBetBanking(List<BetBanking> availableBetBanking) {
        this.availableBetBanking = availableBetBanking;
    }

    public List<BetBanking> getAsignedBetBanking() {
        return asignedBetBanking;
    }

    public void setAsignedBetBanking(List<BetBanking> asignedBetBanking) {
        this.asignedBetBanking = asignedBetBanking;
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

    public Consortium getSelectedConsortium() {
        if (selectedConsortium == null) {
            this.selectedConsortium = new Consortium();
        }
        return this.selectedConsortium;
    }

    public void setSelectedConsortium(Consortium selectedConsortium) {
        this.selectedConsortium = selectedConsortium;
    }

    public void addNewConsortium() {
        FacesMessage msg;
        try {
            this.availableBetBanking = this.consortiumService.findBetBankingAvailable();
            this.asignedBetBanking = new ArrayList<BetBanking>();
            this.consortiumDualList = new DualListModel<BetBanking>(this.availableBetBanking, this.asignedBetBanking);
            boolean success = true;
            RequestContext reqContext = RequestContext.getCurrentInstance();
            this.selectedConsortium = new Consortium();
            editMode = false;
        } catch (SearchAllBetBankingException ex) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ha ocurrido un error buscando las bancas disponibles!", null);
            Logger.getLogger(ConsortiumController.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage(null, msg);

        } catch (Exception ex) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ha ocurrido un error buscando las bancas disponibles!", null);
            Logger.getLogger(ConsortiumController.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    private void refreshDataModel() {

        try {
            User loggedUser = loginController.getUser();
            this.consortiumDataModel = new ConsortiumDataModel(consortiumService.findAll(loggedUser.getId()));
        } catch (SearchAllConsortiumException ex) {
            Logger.getLogger(ConsortiumController.class.getName()).log(Level.SEVERE, "refreshDataModel() in ConsortiumController", ex);
        } catch (Exception ex) {
            Logger.getLogger(ConsortiumController.class.getName()).log(Level.SEVERE, "refreshDataModel() in ConsortiumController", ex);
        }
    }

    public ConsortiumDataModel getConsortiumDataModel() {
        if (this.consortiumDataModel == null) {

            refreshDataModel();
        }
        return this.consortiumDataModel;
    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg;
        try {
            this.availableBetBanking = this.consortiumService.findBetBankingAvailable();
            this.asignedBetBanking = this.consortiumService.findBetBankingAsignedToConsortium(this.selectedConsortium.getId());
            consortiumDualList = new DualListModel<BetBanking>(this.availableBetBanking, this.asignedBetBanking);
            msg = new FacesMessage("Consorcio ", ((Consortium) event.getObject()).getName());
            this.editMode = true;
            refreshConsortiumGeneralLimitDataModel();
            FacesContext.getCurrentInstance().addMessage(null, msg);

        } catch (SearchAllBetBankingException ex) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ha ocurrido un error en la seleccion de un consorcio!", null);
            Logger.getLogger(ConsortiumController.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage(null, msg);

        } catch (Exception ex) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ha ocurrido un error en la seleccion de un consorcio!", null);
            Logger.getLogger(ConsortiumController.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("Consorcio", ((Consortium) event.getObject()).getName());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void addOrUpdateConsortium(ActionEvent event) {
        boolean success = true;
        FacesMessage msg;
        RequestContext reqContext = RequestContext.getCurrentInstance();
        try {
            if (consortiumAlreadyExist()) {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Consorcio ya existe", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return;
            }
            User loggedUser = loginController.getUser();
            this.selectedConsortium.setCreationUser(loggedUser.getUserName());
            Set<BetBanking> asignedBetBankings = new HashSet<BetBanking>(consortiumDualList.getTarget());
            this.selectedConsortium.setBetBankings(asignedBetBankings);
            ConsortiumContainer consortiumContainer = new ConsortiumContainer();
            consortiumContainer.setConsortium(this.selectedConsortium);
            consortiumContainer.setConsortiumGeneralLimit(this.consortiumGeneralLimitDataModel.getConsortiumGeneralLimits());
            consortiumService.saveConsortiumData(consortiumContainer);
            if (editMode) {
                int selectedConsortiumIndex = this.consortiumDataModel.getConsortiums().indexOf(this.selectedConsortium);
                this.consortiumDataModel.getConsortiums().set(selectedConsortiumIndex, this.selectedConsortium);

            } else {
                this.selectedConsortium.setId(this.consortiumDataModel.nextConsortiumId());
                this.consortiumDataModel.getConsortiums().add(this.selectedConsortium);

            }
        } catch (SaveConsortiumDataException ex) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ha ocurrido un error registrando los consorcios!", null);
            Logger.getLogger(ConsortiumController.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        } catch (Exception ex) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ha ocurrido un error registrando los consorcios!", null);
            Logger.getLogger(ConsortiumController.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }
        cancelConsortiumGeneralLimit();


        this.selectedConsortium = null;
        reqContext.addCallbackParam("success", success);
    }

    private boolean consortiumAlreadyExist() {
        List<Consortium> consortiums = consortiumDataModel.getConsortiums();
        boolean consortiumExist = true;
        for (Consortium currConsortium : consortiums) {
            if (editMode) {
                if (!currConsortium.equals(this.selectedConsortium)) {
                    if (currConsortium.getName().equals(this.selectedConsortium.getName())) {
                        return consortiumExist;
                    }
                }
            } else {
                if (currConsortium.getName().equals(this.selectedConsortium.getName())) {
                    return consortiumExist;
                }
            }
        }
        return !consortiumExist;
    }

    public void cancelConsortiumGeneralLimit() {
        FacesMessage msg;
        try {
            this.selectedLottery = null;
            this.selectedTime = null;
            this.selectedBet = null;
            this.amountLimit = 0;
            this.editConsortiumMode = false;
        } catch (Exception ex) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ha ocurrido un error cancelando esta acción", null);
            Logger.getLogger(ConsortiumController.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }


    }

    private boolean consortiumGeneralLimitExists() {
        List<ConsortiumGeneralLimit> consortiumGeneralLimits = this.consortiumGeneralLimitDataModel.getConsortiumGeneralLimits();
        boolean consortiumGeneralLimitExist = true;
        for (ConsortiumGeneralLimit currConsortiumGeneralLimit : consortiumGeneralLimits) {
            if (editConsortiumMode) {
                if (!currConsortiumGeneralLimit.equals(this.selectedConsortiumGeneralLimit)) {//No es el que se esta editando
                    if (currConsortiumGeneralLimit.getLottery().getId() == this.selectedConsortiumGeneralLimit.getLottery().getId()
                            && currConsortiumGeneralLimit.getBet().getId() == this.selectedConsortiumGeneralLimit.getBet().getId()
                            && currConsortiumGeneralLimit.getTime().getId() == this.selectedConsortiumGeneralLimit.getTime().getId()) {
                        return consortiumGeneralLimitExist;
                    }
                }
            } else {
                if (currConsortiumGeneralLimit.getLottery().getId() == this.selectedConsortiumGeneralLimit.getLottery().getId()
                        && currConsortiumGeneralLimit.getBet().getId() == this.selectedConsortiumGeneralLimit.getBet().getId()
                        && currConsortiumGeneralLimit.getTime().getId() == this.selectedConsortiumGeneralLimit.getTime().getId()) {
                    return consortiumGeneralLimitExist;
                }
            }
        }
        return !consortiumGeneralLimitExist;
    }

    private void fillDataToSelectedConsortiumGeneralLimit() {
        if (editConsortiumMode == false) {
            this.selectedConsortiumGeneralLimit = new ConsortiumGeneralLimit();
            this.selectedConsortiumGeneralLimit.setId(this.consortiumGeneralLimitDataModel.nextConsortiumGeneralLimitId());
            this.selectedConsortiumGeneralLimit.setCreationUser(this.loginController.getUserName());
        }
        this.selectedConsortiumGeneralLimit.setLottery(this.selectedLottery);
        this.selectedConsortiumGeneralLimit.setBet(this.selectedBet);
        this.selectedConsortiumGeneralLimit.setTime(this.selectedTime);
        this.selectedConsortiumGeneralLimit.setAmount(this.amountLimit);

    }

    public void addOrUpdateConsortiumGeneralLimit(ActionEvent event) {
        boolean success = true;
        FacesMessage msg;
        fillDataToSelectedConsortiumGeneralLimit();
        if (this.editConsortiumMode) {

            if (consortiumGeneralLimitExists()) {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Limite general ya existe", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                success = false;
                return;
            }
            int indexToUpdate = this.consortiumGeneralLimitDataModel.getConsortiumGeneralLimits().indexOf(this.selectedConsortiumGeneralLimit);
            this.consortiumGeneralLimitDataModel.getConsortiumGeneralLimits().set(indexToUpdate, this.selectedConsortiumGeneralLimit);
            cancelConsortiumGeneralLimit();
        } else {
            if (consortiumGeneralLimitExists()) {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Limite general ya existe", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                success = false;
                return;
            }
            this.consortiumGeneralLimitDataModel.getConsortiumGeneralLimits().add(this.selectedConsortiumGeneralLimit);
            cancelConsortiumGeneralLimit();
        }
    }
}
