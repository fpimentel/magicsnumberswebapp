package com.exception.magicsnumberswebapp.controller;

import com.exception.magicsnumberswebapp.controller.rules.TicketSaleRule;
import com.exception.magicsnumberswebapp.datamodel.TicketDetailDataModel;
import com.exception.magicsnumberswebapp.reports.beans.TicketSaleData;
import com.exception.magicsnumberswebapp.service.BetBankingService;
import com.exception.magicsnumberswebapp.service.LotteryService;
import com.exception.magicsnumberswebapp.service.TicketService;
import com.exception.magicsnumbersws.entities.Bet;
import com.exception.magicsnumbersws.entities.BetBanking;
import com.exception.magicsnumbersws.entities.Lottery;
import com.exception.magicsnumbersws.entities.Ticket;
import com.exception.magicsnumbersws.entities.TicketDetail;
import com.exception.magicsnumbersws.entities.Time;
import com.exception.magicsnumbersws.exception.CloseHourLotteryConfigNotFoundtException;
import com.exception.magicsnumbersws.exception.FindBetLimitException;
import com.exception.magicsnumbersws.exception.FindBlockingNumberException;
import com.exception.magicsnumbersws.exception.FindLotteryCloseHourException;
import com.exception.magicsnumbersws.exception.SaveTicketException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author cpimentel
 */
@Controller
@Scope("view")
public class TicketSaleController {

    private String betBankingName;
    @Autowired
    private LoginController loginController;
    @Autowired
    private LotteryService lotteryService;
    @Autowired
    private BetBankingService betBankingService;
    @Autowired
    private TicketService ticketService;
    private int quantityToPlaySelectedBet;
    private Lottery selectedLottery;
    private List<Bet> bets;
    private List<Time> times;
    private Set<Lottery> lotteries;
    private Bet selectedBet;
    private Time selectedTime;
    private TicketDetailDataModel ticketDetailsDataModel;
    private TicketDetail ticketDetailToDelete;
    private TicketDetail selectedTicketDetail;
    private String numbersToPlay;
    private BigDecimal betAmount;
    private BigDecimal ticketTotalAmount;
    private Ticket ticket;
    private boolean isValidFields = true;
    private boolean editMode = false;

    public TicketSaleController() {
        this.ticketTotalAmount = new BigDecimal(0).setScale(0, BigDecimal.ROUND_DOWN);
    }

    public Set<Lottery> getLotteries() {
        if (this.lotteries == null) {
            this.lotteries = TicketSaleRule.getUserLoggedLotteries(this.loginController.getUser());
        }
        return lotteries;
    }

    public void setLotteries(Set<Lottery> lotteries) {
        this.lotteries = lotteries;
    }

    public TicketDetail getSelectedTicketDetail() {
        if (this.selectedTicketDetail == null) {
            this.selectedTicketDetail = new TicketDetail();
        }
        return this.selectedTicketDetail;
    }

    public void setSelectedTicketDetail(TicketDetail selectedTicketDetail) {
        this.selectedTicketDetail = selectedTicketDetail;
    }

    public List<Time> getTimes() {
        return times;
    }

    public TicketDetailDataModel getTicketDetailsDataModel() {
        if (ticketDetailsDataModel == null) {
            this.ticketDetailsDataModel = new TicketDetailDataModel(new ArrayList<TicketDetail>());
        }
        return this.ticketDetailsDataModel;
    }

    public void setTicketDetailsDataModel(TicketDetailDataModel ticketDetailsDataModel) {
        this.ticketDetailsDataModel = ticketDetailsDataModel;
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

    public int getQuantityToPlaySelectedBet() {
        return quantityToPlaySelectedBet;
    }

    public void setQuantityToPlaySelectedBet(int quantityToPlaySelectedBet) {
        this.quantityToPlaySelectedBet = quantityToPlaySelectedBet;
    }

    public Lottery getSelectedLottery() {        
        return this.selectedLottery;
    }

    public void setSelectedLottery(Lottery selectedLottery) {
        this.selectedLottery = selectedLottery;
    }

    public BigDecimal getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(BigDecimal betAmount) {
        this.betAmount = betAmount;
    }

    public String getNumbersToPlay() {
        return numbersToPlay;
    }

    public void setNumbersToPlay(String numbersToPlay) {
        this.numbersToPlay = numbersToPlay;
    }

    public void getNumberQuantityToPlay(ValueChangeEvent event) {
        this.selectedBet = (Bet) event.getNewValue();
        this.quantityToPlaySelectedBet = this.selectedBet.getNumberQtyToPlay();
    }

    public TicketDetail getTicketDetailToDelete() {
        if (this.ticketDetailToDelete == null) {
            this.ticketDetailToDelete = new TicketDetail();
        }
        return this.ticketDetailToDelete;
    }

    public void setTicketDetailToDelete(TicketDetail ticketDetailToDelete) {
        this.ticketTotalAmount = this.ticketTotalAmount.subtract(ticketDetailToDelete.getBetAmount());
        this.ticketDetailToDelete = ticketDetailToDelete;
        this.ticketDetailsDataModel.getTicketDetails().remove(this.ticketDetailToDelete);
    }

    public BigDecimal getTicketTotalAmount() {
        return ticketTotalAmount;
    }

    public void setTicketTotalAmount(BigDecimal ticketTotalAmount) {
        this.ticketTotalAmount = ticketTotalAmount;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public boolean isIsValidFields() {
        return isValidFields;
    }

    public void setIsValidFields(boolean isValidFields) {
        this.isValidFields = isValidFields;
    }

    public void onRowSelectTicketSale(SelectEvent event) {
        this.editMode = true;
        this.selectedLottery = this.selectedTicketDetail.getLottery();
        loadTimesAndBetsByLottery();
        this.selectedTime = this.selectedTicketDetail.getTime();
        this.selectedBet = this.selectedTicketDetail.getBet();
        this.betAmount = this.selectedTicketDetail.getBetAmount();
        this.numbersToPlay = this.selectedTicketDetail.getNumbersPlayed();
        this.editMode = false;
    }

    public void addBetsToTicket(ActionEvent event) {
        setIsValidFields(true);
        FacesMessage msg;
        BetBanking betBanking = (BetBanking) this.loginController.getUser().getBetBankings().toArray()[0];
        String numberBlocks = "";
        String amountLimit = "";

        try {
            numberBlocks = this.ticketService.getNumbersBlocks(betBanking.getId(), this.numbersToPlay);
            amountLimit = this.ticketService.findBetBankingBetLimitAmount(betBanking.getId(), this.selectedLottery.getId(), this.selectedBet.getId());
            if (!numberBlocks.isEmpty()) {
                setIsValidFields(false);
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Numero(s): " + numberBlocks + " estan bloqueado(s)", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return;
            }
            double amountLimitToCompare = !amountLimit.isEmpty() ? Double.parseDouble(amountLimit) : 0;
            if (this.betAmount.doubleValue() > amountLimitToCompare && amountLimitToCompare > 0) {
                setIsValidFields(false);
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha excedido el monto maximo de esta jugada!  monto: " + amountLimitToCompare, "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return;
            }
            if (!isValidateInfoRequired()) {
                setIsValidFields(false);
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Loteria, Turno, Jugada, Numeros y monto Requeridos", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return;
            }
            this.selectedTicketDetail = new TicketDetail();
            this.selectedTicketDetail.setId(this.ticketDetailsDataModel.nextBetBankingId());
            this.selectedTicketDetail.setLottery(this.selectedLottery);
            this.selectedTicketDetail.setTime(this.selectedTime);
            this.selectedTicketDetail.setBet(this.selectedBet);
            this.selectedTicketDetail.setBetAmount(this.betAmount);
            this.selectedTicketDetail.setNumbersPlayed(this.numbersToPlay);
            ticketDetailsDataModel.getTicketDetails().add(this.selectedTicketDetail);
            this.ticketTotalAmount = this.ticketTotalAmount.add(this.selectedTicketDetail.getBetAmount());
        } catch (FindBlockingNumberException ex) {
            Logger.getLogger(TicketSaleController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FindBetLimitException ex) {
            Logger.getLogger(TicketSaleController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TicketSaleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Boolean isValidateInfoRequired() {
        Boolean isValid = true;
        if (this.selectedLottery != null && this.selectedTime != null
                && this.selectedBet != null && this.betAmount.floatValue() > 0
                && this.numbersToPlay.length() > 0) {
            return isValid;
        } else {
            return isValid = false;
        }
    }

    private void initReportData(Ticket ticket, List<TicketSaleData> ticketSaleDataList,Map parameters) {
        TicketSaleData ticketSaleData;
        parameters.put("BET_BANKING_NAME", this.betBankingName);
        parameters.put("TICKET_TOTAL_AMOUNT", Float.valueOf(this.ticketTotalAmount.toString()));
        if (ticket.getTicketDetails() != null) {
            for (TicketDetail currTicketDetail : ticket.getTicketDetails()) {
                ticketSaleData = new TicketSaleData();
                ticketSaleData.setLottery(currTicketDetail.getLottery().getName());
                ticketSaleData.setTime(currTicketDetail.getTime().getName());
                ticketSaleData.setBet(currTicketDetail.getBet().getName());
                ticketSaleData.setNumbers(currTicketDetail.getNumbersPlayed());
                ticketSaleData.setBetAmount(Float.valueOf(currTicketDetail.getBetAmount().toString()));
                ticketSaleDataList.add(ticketSaleData);
            }
        }
    }

    private void generateTicketReport(List<TicketSaleData> ticketSaleDataList,Map parameters) throws JRException, IOException {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(ticketSaleDataList);
        ec.responseReset();
        InputStream jasperReportsIS = getClass().getResourceAsStream("/jasperreports/ticketSale.jasper");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReportsIS,parameters,beanCollectionDataSource);
        HttpServletResponse httpServletResponse = (HttpServletResponse) ec.getResponse();        
        httpServletResponse.setContentType("application/pdf");
        httpServletResponse.setHeader("Content-Disposition","inline; filename=\"File.pdf\"");
        ServletOutputStream servletOuputStream = httpServletResponse.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOuputStream);
        fc.responseComplete();
    }

    public void saveTicket(ActionEvent event) {
        FacesMessage msg;
        this.editMode = false;
        try {
            this.ticket = new Ticket();
            this.ticket.setCreationUser(this.loginController.getUser().getUserName());
            this.ticket.setTicketDetails(new HashSet<TicketDetail>(this.ticketDetailsDataModel.getTicketDetails()));
            BetBanking betBankingBanker = (BetBanking) this.loginController.getUser().getBetBankings().toArray()[0];
            this.ticket.setBetBanking(betBankingBanker);
            if (ticketDetailsDataModel.getTicketDetails().size() < 1) {
                setIsValidFields(false);
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Debe agregar al menos una jugada ", null);
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return;
            }
            this.ticketService.saveTicket(this.ticket);
            List<TicketSaleData> ticketSaleDataList = new ArrayList<TicketSaleData>();
            this.ticketDetailsDataModel = new TicketDetailDataModel(new ArrayList<TicketDetail>());            
            Map parameters = new HashMap();
            initReportData(this.ticket, ticketSaleDataList,parameters);
            generateTicketReport(ticketSaleDataList,parameters);           
            this.ticketTotalAmount = new BigDecimal(0);
            this.quantityToPlaySelectedBet = 0;
        } catch (SaveTicketException ex) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ha ocurrido un error salvando las jugadas de este ticket", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            Logger.getLogger(TicketSaleController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "error desconocido salvando las jugadas de este ticket", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            Logger.getLogger(TicketSaleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadTimesAndBetsByLottery() {
        FacesMessage msg;
        try {
            BetBanking betBanking = (BetBanking) this.loginController.getUser().getBetBankings().toArray()[0];
            this.times = this.lotteryService.findAvailableTimesByLotteryId(this.selectedLottery.getId());
            this.bets = new ArrayList(this.betBankingService.findBetsByLotteryAndBetBanking(this.selectedLottery.getId(), betBanking.getId()));
        } catch (FindLotteryCloseHourException ex) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Problemas cargando las tandas en loteria" + this.selectedLottery.getId() + "", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            Logger.getLogger(TicketSaleController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CloseHourLotteryConfigNotFoundtException ex) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No se ha configurado tandas para la loteria" + this.selectedLottery.getId() + "", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            Logger.getLogger(TicketSaleController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Problemas cargando las tandas en loteria" + this.selectedLottery.getId() + "", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            Logger.getLogger(TicketSaleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getBetsByLotteryOnChange(ValueChangeEvent event) {
        this.selectedLottery = (Lottery) event.getNewValue();
        loadTimesAndBetsByLottery();
    }

    public void changeLottery() {
        for (Lottery currentLottery : this.lotteries) {
            if (!currentLottery.equals(this.selectedLottery)) {
                this.selectedLottery = currentLottery;
                loadTimesAndBetsByLottery();
                return;
            }
        }

    }

    public void changeTurn() {
        for (Time currentTime : this.times) {
            if (!currentTime.equals(this.selectedTime)) {
                this.selectedTime = currentTime;
                return;
            }
        }
    }

    public void changeBet() {
        for (Bet currentBet : this.bets) {
            if (!currentBet.equals(this.selectedBet)) {
                this.selectedBet = currentBet;
                this.quantityToPlaySelectedBet = currentBet.getNumberQtyToPlay();
                return;
            }
        }
    }

    public List<Bet> getBets() {
        return bets;
    }

    public void setBets(List<Bet> bets) {
        this.bets = bets;
    }

    public Bet getSelectedBet() {
        return selectedBet;
    }

    public void setSelectedBet(Bet selectedBet) {
        this.selectedBet = selectedBet;
    }

    public String getBetBankingName() {
        if (this.loginController.getUser().getBetBankings() != null) {
            BetBanking betBanking = (BetBanking) this.loginController.getUser().getBetBankings().toArray()[0];
            if (betBanking.getName() != null) {
                betBankingName = betBanking.getName();
            } else {
                betBankingName = "Nombre de banca no encontrado";
            }
        }
        return betBankingName;
    }
}