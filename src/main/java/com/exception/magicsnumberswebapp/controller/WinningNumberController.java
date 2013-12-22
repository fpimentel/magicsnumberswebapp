package com.exception.magicsnumberswebapp.controller;

import com.exception.magicsnumberswebapp.datamodel.WinningNumberDataModel;
import com.exception.magicsnumberswebapp.service.LotteryService;
import com.exception.magicsnumberswebapp.service.WinningNumberService;
import com.exception.magicsnumbersws.entities.Lottery;
import com.exception.magicsnumbersws.entities.Time;
import com.exception.magicsnumbersws.entities.WinningNumber;
import com.exception.magicsnumbersws.exception.FindLotteryCloseHourException;
import com.exception.magicsnumbersws.exception.FindLotteryException;
import com.exception.magicsnumbersws.exception.SearchWinningNumbersException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author cpimentel
 * @since 21-Dic-2013
 */
@Controller
@Scope("view")
public class WinningNumberController {

    @Autowired
    private LoginController loginController;
    @Autowired
    private LotteryService lotteryService;
    private Lottery selectedLottery;
    private List<Time> times;
    private Set<Lottery> lotteries;
    private Time selectedTime;
    private Date startingDate;
    private Date finishDate;
    @Autowired
    private WinningNumberService winningNumberService;
    private WinningNumberDataModel winningDataModel;
    private WinningNumber selectedWinningNumber;
    private Boolean winningNumberEditMode = false;
    private String FirstNumber;
    private String SecondNumber;
    private String ThreeNumber;

    public WinningNumberController() {
        winningDataModel = new WinningNumberDataModel(new ArrayList<WinningNumber>());
        this.startingDate = new Date();
        this.finishDate = new Date();
    }

    public String getFirstNumber() {
        return FirstNumber;
    }

    public void setFirstNumber(String FirstNumber) {
        this.FirstNumber = FirstNumber;
    }

    public String getSecondNumber() {
        return SecondNumber;
    }

    public void setSecondNumber(String SecondNumber) {
        this.SecondNumber = SecondNumber;
    }

    public String getThreeNumber() {
        return ThreeNumber;
    }

    public void setThreeNumber(String ThreeNumber) {
        this.ThreeNumber = ThreeNumber;
    }

    public WinningNumber getSelectedWinningNumber() {
        if (this.selectedWinningNumber == null) {
            this.selectedWinningNumber = new WinningNumber();
        }
        return selectedWinningNumber;
    }

    public void setSelectedWinningNumber(WinningNumber selectedWinningNumber) {
        this.selectedWinningNumber = selectedWinningNumber;
    }

    public WinningNumberDataModel getWinningDataModel() {
        return winningDataModel;
    }

    public void setWinningDataModel(WinningNumberDataModel winningDataModel) {
        this.winningDataModel = winningDataModel;
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

    public Set<Lottery> getLotteries() {
        if (this.lotteries == null) {
            try {
                this.lotteries = new HashSet(this.lotteryService.findActiveLottery());
            } catch (FindLotteryException ex) {
                Logger.getLogger(WinningNumberController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(WinningNumberController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lotteries;
    }

    public void setLotteries(Set<Lottery> lotteries) {
        this.lotteries = lotteries;
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

    public Lottery getSelectedLottery() {
        return this.selectedLottery;
    }

    public void setSelectedLottery(Lottery selectedLottery) {
        this.selectedLottery = selectedLottery;
    }

    public void loadTimesByLottery() {
        FacesMessage msg;
        try {
            this.selectedLottery= this.selectedWinningNumber.getLottery();
            this.times = new ArrayList(this.lotteryService.findTimesByLottery(this.selectedLottery.getId()));
        } catch (FindLotteryCloseHourException ex) {
            Logger.getLogger(WinningNumberController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(WinningNumberController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void onRowSelect(SelectEvent event) {
        
        winningNumberEditMode = true;
        String winningNumbers = this.selectedWinningNumber.getNumbers();
        loadTimesByLottery();
        String[] winningNumbersArray = winningNumbers.split("-");
        for (int i = 0; i < winningNumbersArray.length; i++) {
            if (i == 0) {
                this.FirstNumber = winningNumbersArray[i];
            }
            if (i == 1) {
                this.SecondNumber = winningNumbersArray[i];
            }
            if (i == 2) {
                this.ThreeNumber = winningNumbersArray[i];
            }
        }
    }
    public void getTimesByLotteryOnChange(ValueChangeEvent event) {
        this.selectedLottery = (Lottery) event.getNewValue();
        loadTimesByLottery();
    }

    public void findWinningNumbers() {
        try {
            String fromDate = new SimpleDateFormat("dd-MM-yyyy").format(this.startingDate);
            String toDate = new SimpleDateFormat("dd-MM-yyyy").format(this.finishDate);
            this.winningDataModel = new WinningNumberDataModel(this.winningNumberService.findWinningNumbers(fromDate, toDate));
        } catch (SearchWinningNumbersException ex) {
            Logger.getLogger(WinningNumberController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(WinningNumberController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addNewWinningNumber() {
    }
}