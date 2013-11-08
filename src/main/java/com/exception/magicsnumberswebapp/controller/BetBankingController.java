package com.exception.magicsnumberswebapp.controller;

import com.exception.magicsnumberswebapp.constants.Profile;
import com.exception.magicsnumberswebapp.datamodel.BetBankingDataModel;
import com.exception.magicsnumberswebapp.datamodel.BetBlockingNumberDataModel;
import com.exception.magicsnumberswebapp.datamodel.BetLimitDataModel;
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
import com.exception.magicsnumbersws.entities.Lottery;
import com.exception.magicsnumbersws.entities.Status;
import com.exception.magicsnumbersws.entities.User;
import com.exception.magicsnumbersws.exception.FindBetLimitException;
import com.exception.magicsnumbersws.exception.FindBlockingNumberException;
import com.exception.magicsnumbersws.exception.FindLotteryException;
import com.exception.magicsnumbersws.exception.SaveBetBankingInfoException;
import com.exception.magicsnumbersws.exception.SearchAllBetBankingException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.springframework.context.annotation.Scope;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author cpimentel
 */
@Controller
@Scope("view")
public class BetBankingController {

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
    private BlockingNumberBetBanking selectedBlockingNumber;
    private Bet selectedBet;
    private Lottery selectedLottery;
    private double amountLimit;
    private boolean editMode = false;
    private boolean betBankingEditMode = false;
    private BetBankingDataModel betBankingDataModel;
    private List<Status> status;
    private Status selectedStatus;
    private List<Bet> bets;
    private List<Lottery> lotteries;
    private BetLimitDataModel betBankingBetLimitDataModel;
    private BetBlockingNumberDataModel blockingNumberDataModel;
    private BetBankingBetLimit betLimitToDelete;
    private BlockingNumberBetBanking blockNumberToDelete;
    private int NumberToBlock;
    private int commission;
    private BetBankingContainer betBakingContainer;
    private int firstTabIndex = 0;

    public BetBankingController() {
    }

    public Lottery getSelectedLottery() {
        if (this.selectedLottery != null) {
            if (this.selectedLottery.getBets() != null) {
                this.bets = new ArrayList(this.selectedLottery.getBets());
            }
        }
        return this.selectedLottery;
    }

    public void setSelectedLottery(Lottery lottery) {
        this.selectedLottery = lottery;
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
        this.blockingNumberDataModel.getBetBlockingNumbers().remove(blockNumberToDelete);
        this.editMode = false;
    }

    public int getNumberToBlock() {
        return NumberToBlock;
    }

    public BlockingNumberBetBanking getSelectedBlockingNumber() {
        if (this.selectedBlockingNumber == null) {
            this.selectedBlockingNumber = new BlockingNumberBetBanking();
        }
        return this.selectedBlockingNumber;
    }

    public void setSelectedBlockingNumber(BlockingNumberBetBanking selectedBlockingNumber) {
        this.selectedBlockingNumber = selectedBlockingNumber;
    }

    public BetBlockingNumberDataModel getBlockingNumberDataModel() {
        if(this.blockingNumberDataModel == null){
            this.blockingNumberDataModel = new BetBlockingNumberDataModel(new ArrayList<BlockingNumberBetBanking>());
        }
        return this.blockingNumberDataModel;
    }

    public void setBlockingNumberDataModel(BetBlockingNumberDataModel blockingNumberDataModel) {
        this.blockingNumberDataModel = blockingNumberDataModel;
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
        if(this.betBankingBetLimitDataModel== null){
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

    public List<Bet> getBets() {

        return this.bets;
    }

    public void setBets(List<Bet> bets) {
        this.bets = bets;
    }

    public BetBankingDataModel getBetBankingDataModel() {
        if (this.betBankingDataModel == null) {
            refreshDataModel();
        }
        return this.betBankingDataModel;
    }

    public void setBetBankingDataModel(BetBankingDataModel betBankingDataModel) {
        this.betBankingDataModel = betBankingDataModel;
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
            this.status = this.statusService.getStatus();
        }
        return this.status;
    }

    public void setStatus(List<Status> status) {
        this.status = status;
    }

    public void addNewBetBanking() {
        this.selectedBetBanking = new BetBanking();
        this.selectedBetBankingBetLimit = new BetBankingBetLimit();
        this.selectedBlockingNumber = new BlockingNumberBetBanking();
        this.betBankingBetLimitDataModel = new BetLimitDataModel(new ArrayList<BetBankingBetLimit>());
        this.blockingNumberDataModel = new BetBlockingNumberDataModel(new ArrayList<BlockingNumberBetBanking>());
        editMode = false;
    }

    private void refreshDataModel() {
        User loggedUser = loginController.getUser();
        try {
            if (loggedUser.getProfile().getId() == Profile.ADMINISTRATOR.getId()) {
                this.betBankingDataModel = new BetBankingDataModel(this.bankingService.findAllBetBanking());
            } else {
                List<BetBanking> betBankings = this.bankingService.findBetBankingByUserId(loggedUser.getId());
                if (betBankings != null && betBankings.size() > 0) {
                    this.betBankingDataModel = new BetBankingDataModel(betBankings);
                } else {
                    this.betBankingDataModel = new BetBankingDataModel(this.bankingService.findBetBankingsToConsortiumsAssignedToUser(loggedUser.getId()));
                }
            }

        } catch (SearchAllBetBankingException ex) {
            Logger.getLogger(BetBankingController.class.getName()).log(Level.SEVERE, "refreshDataModel() in ConsortiumController", ex);
        }
    }

    private void refreshBlockinNumberDataModel() {
        try {
            this.blockingNumberDataModel = new BetBlockingNumberDataModel(this.bankingService.findBlockNumbersBetBanking(this.selectedBetBanking.getId()));
        } catch (FindBlockingNumberException ex) {
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
        betBankingEditMode = true;
        refreshBetLimitDataModel();
        refreshBlockinNumberDataModel();
    }

    public void onRowSelectToBetAsociated(SelectEvent event) {
        editMode = true;
        this.selectedLottery = this.selectedBetBankingBetLimit.getLottery();
        this.bets = new ArrayList<Bet>(selectedLottery.getBets());
        this.selectedBet = this.selectedBetBankingBetLimit.getBet();
        this.amountLimit = this.selectedBetBankingBetLimit.getAmountLimit();
        this.commission = this.selectedBetBankingBetLimit.getCommission();
    }

    public void onRowSelectToBlockingNumber(SelectEvent event) {
        editMode = true;
        this.NumberToBlock = this.selectedBlockingNumber.getNumber();
    }

    public void cancel() {
        editMode = false;
        this.commission = 0;
        this.amountLimit = 0.0;
        //this.selectedBet= new Bet();
    }

    public void cancelBLockNumber() {
        this.editMode = false;
        this.NumberToBlock = 0;

    }

    public void addOrUpdateNumberToBlock(ActionEvent event) {
        boolean success = true;
        FacesMessage msg;

        if (editMode) {
            this.selectedBlockingNumber.setNumber(NumberToBlock);
            if (blockNumberBetBankingExists()) {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Numero a bloquear: " + this.selectedBlockingNumber.getNumber() + "  ya existe!", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                success = false;
                return;
            }
            int indexToUpdate = this.blockingNumberDataModel.getBetBlockingNumbers().indexOf(this.selectedBlockingNumber);
            this.blockingNumberDataModel.getBetBlockingNumbers().set(indexToUpdate, this.selectedBlockingNumber);
            cancelBLockNumber();
        } else {

            this.selectedBlockingNumber = new BlockingNumberBetBanking();
            this.selectedBlockingNumber.setCreationUser(loginController.getUserName());
            this.selectedBlockingNumber.setId(this.blockingNumberDataModel.nextBlockingId());
            this.selectedBlockingNumber.setNumber(NumberToBlock);
            this.selectedBlockingNumber.setBetBanking(this.selectedBetBanking);
            if (blockNumberBetBankingExists()) {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Numero a bloquear: " + this.selectedBlockingNumber.getNumber() + "  ya existe!", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                success = false;
                return;
            }
            this.blockingNumberDataModel.getBetBlockingNumbers().add(this.selectedBlockingNumber);

            cancelBLockNumber();
        }
    }

    private boolean isMissingBetBankingFields() {
        return this.selectedBetBanking.getConsortium() == null ? true : false;
    }

    public void addOrUpdateInfo(ActionEvent event) {
        boolean success = true;
        FacesMessage msg;
        RequestContext reqContext = RequestContext.getCurrentInstance();

        try {
            if (isMissingBetBankingFields()) {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Consorcio es requerido", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return;
            }
            if (betBankingAlreadyExist()) {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Banca a registrar ya existe", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return;
            }
            this.selectedBetBanking.setCreationUser(loginController.getUser().getUserName());
            this.betBakingContainer = new BetBankingContainer();
            this.betBakingContainer.setBetBanking(this.selectedBetBanking);
            this.betBakingContainer.setBetBankingBetLimits(this.betBankingBetLimitDataModel.getBetLimits());
            this.betBakingContainer.setBlockingNumbers(this.blockingNumberDataModel.getBetBlockingNumbers());

            this.betBankingService.saveBetBankingInformation(this.betBakingContainer);

            if (betBankingEditMode) {
                int selectedBetBankingIndex = this.betBankingDataModel.getBetbankings().indexOf(this.selectedBetBanking);
                this.betBankingDataModel.getBetbankings().set(selectedBetBankingIndex, this.selectedBetBanking);
            } else {
                //AQUI DECIDIMOS REFRESCAR EL DUAL MODEL DE LA BASE DE DATOS,
                //YA QUE PODRIA EXISTIR INCONVENIENTES CON LOS ID GENERADOS.                
                refreshDataModel();
            }
            betBankingEditMode = false;
        } catch (SaveBetBankingInfoException ex) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ha ocurrido un error guardando la informacion de la banca", null);
            Logger.getLogger(ConsortiumController.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        } catch (Exception ex) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ha ocurrido un error registrando los consorcios!", null);
            Logger.getLogger(ConsortiumController.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }
        this.selectedBetBanking = null;
        reqContext.addCallbackParam("success", success);
    }

    private boolean betBankingAlreadyExist() {
        List<BetBanking> betBankings = this.betBankingDataModel.getBetbankings();
        boolean betBankingExist = true;
        for (BetBanking currBetBanking : betBankings) {
            if (betBankingEditMode) {
                if (!currBetBanking.equals(this.selectedBetBanking)) {
                    if (currBetBanking.getName().toLowerCase().equals(this.selectedBetBanking.getName().toLowerCase())) {
                        return betBankingExist;
                    }
                }
            } else {
                if (currBetBanking.getName().toLowerCase().equals(this.selectedBetBanking.getName().toLowerCase())) {
                    return betBankingExist;
                }
            }
        }
        return !betBankingExist;
    }

    public boolean validateMissingRequiredFieldBetLimitInBetBanking() {
        return this.selectedBet == null || this.selectedLottery == null ? true : false;
    }

    public void addOrUpdateBetBankingLimit(ActionEvent event) {
        boolean success = true;
        FacesMessage msg;
        RequestContext reqContext = RequestContext.getCurrentInstance();

        if (validateMissingRequiredFieldBetLimitInBetBanking()) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Loteria y jugada requerida", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            success = false;
            return;
        }
        if (editMode) {
            fillDataToSelectedBetBanking();

            if (betLimitAlreadyExist()) {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Jugada ya existe", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                success = false;
                return;
            }
            int indexToUpdate = betBankingBetLimitDataModel.getBetLimits().indexOf(selectedBetBankingBetLimit);
            this.betBankingBetLimitDataModel.getBetLimits().set(indexToUpdate, selectedBetBankingBetLimit);
            cancel();
        } else {
            this.selectedBetBankingBetLimit = new BetBankingBetLimit();
            this.selectedBetBankingBetLimit.setId(this.betBankingBetLimitDataModel.nextBetBankingBetLimitId());
            fillDataToSelectedBetBanking();
            if (betLimitAlreadyExist()) {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Jugada ya existe", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                success = false;
                return;
            }
            this.betBankingBetLimitDataModel.getBetLimits().add(this.selectedBetBankingBetLimit);
            cancel();
        }
        reqContext.addCallbackParam("success", success);
    }

    private void fillDataToSelectedBetBanking() {
        this.selectedBetBankingBetLimit.setBet(this.selectedBet);
        this.selectedBetBankingBetLimit.setCommission(this.commission);
        this.selectedBetBankingBetLimit.setBetBanking(this.selectedBetBanking);
        this.selectedBetBankingBetLimit.setAmountLimit(this.amountLimit);
        this.selectedBetBankingBetLimit.setLottery(this.selectedLottery);
        this.selectedBetBankingBetLimit.setCreationUser(this.loginController.getUserName());
    }

    private boolean betLimitAlreadyExist() {
        List<BetBankingBetLimit> betBankingLimits = betBankingBetLimitDataModel.getBetLimits();
        boolean betBankingBetLimitExist = true;
        for (BetBankingBetLimit currBetBankingLimit : betBankingLimits) {
            if (editMode) {
                if (!currBetBankingLimit.equals(this.selectedBetBankingBetLimit)) {//No es el que se esta editando
                    if (currBetBankingLimit.getBet().getName().equals(this.selectedBetBankingBetLimit.getBet().getName())
                            && currBetBankingLimit.getLottery().getName().equals(this.selectedBetBankingBetLimit.getLottery().getName())) {
                        return betBankingBetLimitExist;
                    }
                }
            } else {
                if (currBetBankingLimit.getBet().getName().equals(this.selectedBetBankingBetLimit.getBet().getName())
                        && currBetBankingLimit.getLottery().getName().equals(this.selectedBetBankingBetLimit.getLottery().getName())) {
                    return betBankingBetLimitExist;
                }
            }
        }
        return !betBankingBetLimitExist;
    }

    private boolean blockNumberBetBankingExists() {
        List<BlockingNumberBetBanking> blockNumbers = this.blockingNumberDataModel.getBetBlockingNumbers();
        boolean blockNumberExist = true;
        for (BlockingNumberBetBanking currBlockNumber : blockNumbers) {
            if (editMode) {
                if (!currBlockNumber.equals(this.selectedBlockingNumber)) {//No es el que se esta editando
                    if (currBlockNumber.getNumber() == this.selectedBlockingNumber.getNumber()) {
                        return blockNumberExist;
                    }
                }
            } else {
                if (currBlockNumber.getNumber() == this.selectedBlockingNumber.getNumber()) {
                    return blockNumberExist;
                }
            }
        }
        return !blockNumberExist;
    }
}
