package com.exception.magicsnumberswebapp.controller;

import com.exception.magicsnumberswebapp.datamodel.WinningNumberDataModel;
import com.exception.magicsnumberswebapp.service.LotteryService;
import com.exception.magicsnumberswebapp.service.WinningNumberService;
import com.exception.magicsnumbersws.entities.Lottery;
import com.exception.magicsnumbersws.entities.Time;
import com.exception.magicsnumbersws.entities.WinningNumber;
import com.exception.magicsnumbersws.exception.FindLotteryCloseHourException;
import com.exception.magicsnumbersws.exception.FindLotteryException;
import com.exception.magicsnumbersws.exception.SaveWinningNumberDataException;
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
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.context.RequestContext;
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
    @Autowired
    private WinningNumberService winningNumberService;
    private Lottery selectedLottery;
    private List<Time> times;
    private Set<Lottery> lotteries;
    private Time selectedTime;
    private Date startingDate;
    private Date finishDate;
    private Date drawingDate;
    private WinningNumberDataModel winningDataModel;
    private WinningNumber selectedWinningNumber;
    private Boolean winningNumberEditMode = false;
    private String FirstNumber;
    private String SecondNumber;
    private String thirdNumber;
    private String userName;

    public WinningNumberController() {
        winningDataModel = new WinningNumberDataModel(new ArrayList<WinningNumber>());
        this.startingDate = new Date();
        this.finishDate = new Date();
        this.drawingDate = new Date();
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

    public String getThirdNumber() {
        return thirdNumber;
    }

    public void setThirdNumber(String thirdNumber) {
        this.thirdNumber = thirdNumber;
    }

    public void loadTimesByLottery() {
        FacesMessage msg;
        try {
            this.times = new ArrayList(this.lotteryService.findTimesByLottery(this.selectedLottery.getId()));
        } catch (FindLotteryCloseHourException ex) {
            Logger.getLogger(WinningNumberController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(WinningNumberController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Date getDrawingDate() {
        return drawingDate;
    }

    public void setDrawingDate(Date drawingDate) {
        this.drawingDate = drawingDate;
    }

    public void onRowSelect(SelectEvent event) {
        winningNumberEditMode = true;
        String winningNumbers = this.selectedWinningNumber.getNumbers();
        try {
            this.times = new ArrayList(this.lotteryService.findTimesByLottery(this.selectedWinningNumber.getLottery().getId()));
            this.selectedTime = this.selectedWinningNumber.getTime();
            this.selectedLottery = this.selectedWinningNumber.getLottery();
            final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            final String drawingDateString = formatter.format(this.selectedWinningNumber.getCreationDate());
            this.drawingDate = formatter.parse(drawingDateString);

            String[] winningNumbersArray = winningNumbers.split("-");
            for (int i = 0; i < winningNumbersArray.length; i++) {
                if (i == 0) {
                    this.FirstNumber = winningNumbersArray[i];
                }
                if (i == 1) {
                    this.SecondNumber = winningNumbersArray[i];
                }
                if (i == 2) {
                    this.thirdNumber = winningNumbersArray[i];
                }
            }
        } catch (FindLotteryCloseHourException ex) {
            Logger.getLogger(WinningNumberController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(WinningNumberController.class.getName()).log(Level.SEVERE, null, ex);
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
        this.selectedWinningNumber = new WinningNumber();
        cleanFields();
        this.winningNumberEditMode = false;
    }
    
    private void cancel(ActionEvent event){
        cleanFields();
        winningNumberEditMode = false;
    }
    private void cleanFields() {
        this.selectedTime = null;
        this.selectedLottery = null;
        this.selectedWinningNumber = new WinningNumber();
        this.FirstNumber = "";
        this.SecondNumber = "";
        this.thirdNumber = "";
    }

    public boolean existWinningNumber() {
        FacesMessage msg;
        boolean success = true;
        boolean exist = false;
        try {
            RequestContext reqContext = RequestContext.getCurrentInstance();
            int lotteryId = this.selectedLottery.getId();
            int timeId = this.selectedTime.getId();
            WinningNumber winningNumberTmp;
            String drawingDateStr = new SimpleDateFormat("dd-MM-yyyy").format(this.drawingDate);
            winningNumberTmp = this.winningNumberService.findWinningNumber(lotteryId, timeId, drawingDateStr);
            if (winningNumberEditMode) {
                if (winningNumberTmp.getId() != this.selectedWinningNumber.getId()) {
                    exist = true;
                }
            } else {
                if (winningNumberTmp != null) {
                    exist = true;
                }
            }            

        } catch (SearchWinningNumbersException ex) {
            Logger.getLogger(WinningNumberController.class.getName()).log(Level.SEVERE, null, ex);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Problema validando la informacion", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            success = false;
        } catch (Exception ex) {
            Logger.getLogger(WinningNumberController.class.getName()).log(Level.SEVERE, null, ex);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Problema validando la informacion", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            success = false;
        }
        return exist;
    }

    public void addOrUpdateInfo(ActionEvent event) {
        boolean success = true;
        FacesMessage msg;
        this.userName = this.loginController.getUser().getUserName();
        RequestContext reqContext = RequestContext.getCurrentInstance();
        this.selectedWinningNumber.setLottery(this.selectedLottery);
        this.selectedWinningNumber.setTime(this.selectedTime);
        this.selectedWinningNumber.setDrawingDate(this.drawingDate);
        String numbers = this.FirstNumber + "-" + this.SecondNumber + "-" + this.thirdNumber;
        this.selectedWinningNumber.setNumbers(numbers);
        if (existWinningNumber()) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ya este sorteo fue insertado.", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            success = false;
            reqContext.addCallbackParam("success", success);           
            return;
        }
        try {
            if (winningNumberEditMode) {
                this.winningNumberService.saveWinningNumberInfo(selectedWinningNumber);
                int indexToUpdate = this.winningDataModel.getWinningNumbers().indexOf(this.selectedWinningNumber);
                this.winningDataModel.getWinningNumbers().set(indexToUpdate, selectedWinningNumber);
            } else {
                this.selectedWinningNumber.setCreationDate(new Date());
                this.selectedWinningNumber.setCreationUser(userName);
                this.winningNumberService.saveWinningNumberInfo(selectedWinningNumber);
                findWinningNumbers();
            }
            this.winningNumberEditMode = false;
            cleanFields();

        } catch (SaveWinningNumberDataException ex) {
            Logger.getLogger(WinningNumberController.class.getName()).log(Level.SEVERE, null, ex);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Problema registrando la informacion", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            success = false;
            return;
        } catch (Exception ex) {
            Logger.getLogger(WinningNumberController.class.getName()).log(Level.SEVERE, null, ex);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Problema registrando la informacion", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            success = false;
            return;
        }
        reqContext.addCallbackParam("success", success);
    }
}