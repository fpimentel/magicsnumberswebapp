package com.exception.magicsnumberswebapp.controller;

import com.exception.magicsnumberswebapp.datamodel.LotteryCloseHourDataModel;
import com.exception.magicsnumberswebapp.datamodel.LotteryDataModel;
import com.exception.magicsnumberswebapp.service.BetBankingService;
import com.exception.magicsnumberswebapp.service.BetService;
import com.exception.magicsnumberswebapp.service.DayService;
import com.exception.magicsnumberswebapp.service.LotteryService;
import com.exception.magicsnumberswebapp.service.StatusService;
import com.exception.magicsnumberswebapp.service.TimeService;
import com.exception.magicsnumberswebapp.view.converter.ConsortiumConverter;
import com.exception.magicsnumbersws.containers.LotteryContainer;
import com.exception.magicsnumbersws.entities.Bet;
import com.exception.magicsnumbersws.entities.Day;
import com.exception.magicsnumbersws.entities.Lottery;
import com.exception.magicsnumbersws.entities.LotteryCloseHour;
import com.exception.magicsnumbersws.entities.Status;
import com.exception.magicsnumbersws.entities.Time;
import com.exception.magicsnumbersws.exception.FindBetException;
import com.exception.magicsnumbersws.exception.FindDayException;
import com.exception.magicsnumbersws.exception.FindLotteryCloseHourException;
import com.exception.magicsnumbersws.exception.FindLotteryException;
import com.exception.magicsnumbersws.exception.FindTimeException;
import com.exception.magicsnumbersws.exception.SaveLotteryException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import org.springframework.context.annotation.Scope;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DualListModel;
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
    private LotteryService lotteryService;
    @Autowired
    private BetService betService;
    @Autowired
    private DayService dayService;
    @Autowired
    private TimeService timeService;
    private LotteryContainer lotteryContainer;
    private LotteryCloseHour selectedLotteryCloseHour;
    private Time selectedTime;
    private List<Time> times;
    private Lottery selectedLottery;
    private boolean lotteryEditMode = false;
    private boolean lotteryCloseHourEditMode = false;
    private LotteryDataModel lotteryDataModel;
    private List<Status> status;
    private Status selectedStatus;
    private List<Lottery> lotteries;
    private DualListModel<Bet> betList;
    private List<Bet> availableBets;
    private List<Bet> asignedBets;
    private List<Bet> activeBets;
    private List<Day> days;
    private LotteryCloseHourDataModel lotteryCloseHourDataModel;
    private Day seletedDay;
    private Date Hour;
    private LotteryCloseHour lotteryCloseHourDelete;

    public LotteryController() {
        this.availableBets = new ArrayList<Bet>();
    }

    public List<Bet> getAvailableBets() {
        return availableBets;
    }

    public void setAvailableBets(List<Bet> availableBets) {
        this.availableBets = availableBets;
    }

    public List<Bet> getAsignedBets() {
        return asignedBets;
    }

    public void setAsignedBets(List<Bet> asignedBets) {
        this.asignedBets = asignedBets;
    }

    public DualListModel<Bet> getBetList() {
        if (this.betList == null) {
            this.betList = new DualListModel<Bet>();
        }
        return betList;
    }

    public void setBetList(DualListModel<Bet> betList) {
        this.betList = betList;
    }

    public LotteryCloseHour getLotteryCloseHourDelete() {
        return lotteryCloseHourDelete;
    }

    public LotteryContainer getLotteryContainer() {
        return lotteryContainer;
    }

    public void setLotteryContainer(LotteryContainer lotteryContainer) {
        this.lotteryContainer = lotteryContainer;
    }

    public void setLotteryCloseHourDelete(LotteryCloseHour lotteryCloseHourDelete) {
        this.lotteryCloseHourDelete = lotteryCloseHourDelete;
        this.lotteryCloseHourDataModel.getLotteryCloseHour().remove(lotteryCloseHourDelete);
    }

    public Date getHour() {
        return Hour;
    }

    public void setHour(Date Hour) {
        this.Hour = Hour;
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

    public Time getSelectedTime() {
        if (this.selectedTime == null) {
            this.selectedTime = new Time();
        }
        return selectedTime;
    }

    public void setSelectedTime(Time selectedTime) {
        this.selectedTime = selectedTime;
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

    public List<Time> getTimes() {
        if (this.times == null) {
            try {
                this.times = this.timeService.findAllTimes();
            } catch (FindTimeException ex) {
                Logger.getLogger(LotteryController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(LotteryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return times;
    }

    public void setTimes(List<Time> times) {
        this.times = times;
    }

    public List<Day> getDays() {
        if (this.days == null) {
            try {
                this.days = this.dayService.findAllDays();
            } catch (FindDayException ex) {
                Logger.getLogger(LotteryController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(LotteryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return days;
    }

    public void setDays(List<Day> days) {
        this.days = days;
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

    public LotteryDataModel getLotteryDataModel() {
        if (this.lotteryDataModel == null) {
            refreshDataModel();
        }
        return lotteryDataModel;
    }

    public void setLotteryDataModel(LotteryDataModel lotteryDataModel) {
        this.lotteryDataModel = lotteryDataModel;
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
        cClearField();
        this.lotteryCloseHourDataModel = new LotteryCloseHourDataModel(new ArrayList<LotteryCloseHour>());
        this.lotteryEditMode = false;
    }

    private void refreshDataModel() {
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

    public void fillAvailableBets() {
        for (Bet currBet : this.activeBets) {
            if (!this.asignedBets.contains(currBet)) {
                this.availableBets.add(currBet);
            }
        }

    }

    public void onRowSelect(SelectEvent event) {
        this.lotteryEditMode = true;
        this.asignedBets = new ArrayList(this.selectedLottery.getBets());
        this.availableBets = new ArrayList<Bet>();
        if (this.activeBets == null) {
            try {
                this.activeBets = betService.findActiveBets();
            } catch (FindBetException ex) {
            } catch (Exception ex) {
            }
        }
        fillAvailableBets();
        this.betList = new DualListModel<Bet>(this.availableBets, this.asignedBets);
        refreshLotteryCloseHourDataModel();
    }

    public boolean isEmptyRequireField() {
        return this.Hour == null || this.Hour.getTime() < 1 ? true : false;
    }

    public void onRowSelectCloseHour() {
        this.seletedDay = this.selectedLotteryCloseHour.getDay();
        this.selectedTime = this.selectedLotteryCloseHour.getTime();
        this.lotteryCloseHourEditMode = true;
        DateFormat formater = new SimpleDateFormat("HH:mm");
        try {
            this.Hour = formater.parse(this.selectedLotteryCloseHour.getHour());
        } catch (ParseException ex) {
            Logger.getLogger(LotteryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void fillCloseHourData() {
        if (!lotteryCloseHourEditMode) {
            this.selectedLotteryCloseHour = new LotteryCloseHour();
            this.selectedLotteryCloseHour.setId(this.lotteryCloseHourDataModel.nextLotteryCloseHourId());
        }
        this.selectedLotteryCloseHour.setDay(this.seletedDay);
        this.selectedLotteryCloseHour.setTime(selectedTime);
        final Calendar cal = Calendar.getInstance();
        cal.setTime(this.Hour);
        String hourAndMinute = String.format("%02d", cal.get(Calendar.HOUR_OF_DAY)) + ":" + String.format("%02d", cal.get(Calendar.MINUTE));
        this.selectedLotteryCloseHour.setHour(hourAndMinute);
    }

    private boolean lotteryCloseHourExist() {
        List<LotteryCloseHour> lotteryCloseHours = this.lotteryCloseHourDataModel.getLotteryCloseHour();
        boolean lotteryCloseHourExist = true;
        for (LotteryCloseHour currLotteryCloseHour : lotteryCloseHours) {
            if (this.lotteryCloseHourEditMode) {
                if (!currLotteryCloseHour.equals(this.selectedLotteryCloseHour)) {//No es el que se esta editando
                    if (currLotteryCloseHour.getDay().getId() == this.selectedLotteryCloseHour.getDay().getId()
                            && currLotteryCloseHour.getTime().getId() == this.selectedLotteryCloseHour.getTime().getId()) {
                        return lotteryCloseHourExist;
                    }
                }
            } else {
                if (currLotteryCloseHour.getDay().getId() == this.selectedLotteryCloseHour.getDay().getId()
                        && currLotteryCloseHour.getTime().getId() == this.selectedLotteryCloseHour.getTime().getId()) {
                    return lotteryCloseHourExist;
                }
            }
        }
        return !lotteryCloseHourExist;
    }

    private boolean isMissingLotteryCloseHourFields() {
        return this.lotteryCloseHourDataModel == null
                || this.lotteryCloseHourDataModel.getLotteryCloseHour() == null
                || this.lotteryCloseHourDataModel.getLotteryCloseHour().size() < 1 ? true : false;
    }

    public void addOrUpdateLotteryCloseHour(ActionEvent event) {

        boolean success = true;
        FacesMessage msg;
        RequestContext reqContext = RequestContext.getCurrentInstance();
        try {
            if (isEmptyRequireField()) {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Día , tanda y hora son requeridos!", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                success = false;
                return;
            }
            fillCloseHourData();
            if (lotteryCloseHourExist()) {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Horario ya existe", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                success = false;
                return;
            }

            if (this.lotteryCloseHourEditMode) {
                int indexToUpdate = this.lotteryCloseHourDataModel.getLotteryCloseHour().indexOf(this.selectedLotteryCloseHour);
                this.lotteryCloseHourDataModel.getLotteryCloseHour().set(indexToUpdate, this.selectedLotteryCloseHour);
            } else {
                this.lotteryCloseHourDataModel.getLotteryCloseHour().add(this.selectedLotteryCloseHour);
            }
            cClearField();
            reqContext.addCallbackParam("success", success);
        } catch (Exception ex) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error registrando información!", null);
            Logger.getLogger(ConsortiumController.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            Logger.getLogger(LotteryController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void addOrUpdateInfo(ActionEvent event) {
        boolean success = true;
        FacesMessage msg;
        RequestContext reqContext = RequestContext.getCurrentInstance();
        try {
            if (isMissingLotteryCloseHourFields()) {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Al menos un horario debe ser configurado", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return;
            }
            LotteryContainer lotteryContainer = new LotteryContainer();
            if (!this.lotteryEditMode) {
                this.selectedLottery.setId(null);
            }
            this.selectedLottery.setBets(new HashSet(this.betList.getTarget()));
            lotteryContainer.setLottery(this.selectedLottery);
            lotteryContainer.setLotteryCloseHour(this.lotteryCloseHourDataModel.getLotteryCloseHour());
            this.lotteryService.saveLotteryInf(lotteryContainer);
            if (this.lotteryEditMode) {
                int indexToUpdate = this.lotteryDataModel.getLottery().indexOf(this.selectedLottery);
                this.lotteryDataModel.getLottery().set(indexToUpdate, this.selectedLottery);
            } else {
                refreshDataModel();
            }

        } catch (SaveLotteryException ex) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ha ocurrido un error registrando esta loteria!", null);
            Logger.getLogger(ConsortiumController.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            Logger.getLogger(LotteryController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ha ocurrido un error registrando esta loteria!", null);
            Logger.getLogger(ConsortiumController.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            Logger.getLogger(LotteryController.class.getName()).log(Level.SEVERE, null, ex);
        }

        reqContext.addCallbackParam("success", success);
    }

    public void cClearField() {
        this.Hour = null;
        this.selectedTime = this.times.get(0);
        this.seletedDay = this.days.get(0);
        this.lotteryCloseHourEditMode = false;
        this.selectedLotteryCloseHour = new LotteryCloseHour();
    }
}
