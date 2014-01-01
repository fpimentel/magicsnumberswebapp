package com.exception.magicsnumberswebapp.controller;

import com.exception.magicsnumberswebapp.service.TicketService;
import com.exception.magicsnumbersws.entities.Ticket;
import com.exception.magicsnumbersws.exception.FindTicketException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author fpimentel
 * @since 21-dic-2013
 */
@Controller
@Scope("view")
public class CloseBoxController {

    @Autowired
    private TicketService ticketService;
    @Autowired
    private LoginController loginController;
    private int twoThousandQty = 0;
    private int oneThousandQty = 0;
    private int fiveHundredQty = 0;
    private int twoHundredQty = 0;
    private int oneHundredQty = 0;
    private int fiftyQty = 0;
    private int twentyFiveQty = 0;
    private int twentyQty = 0;
    private int tenQty = 0;
    private int fiveQty = 0;
    private int oneQty = 0;
    private float twoThousandLabelAmount = 0.0f;
    private float oneThousandLabelAmount = 0.0f;
    private float fiveHundredLabelAmount = 0.0f;
    private float twoHundredLabelAmount = 0.0f;
    private float oneHundredLabelAmount = 0.0f;
    private float fiftyLabelAmount = 0.0f;
    private float twentyFiveLabelAmount = 0.0f;
    private float twentyLabelAmount = 0.0f;
    private float tenLabelAmount = 0.0f;
    private float fiveLabelAmount = 0.0f;
    private float oneLabelAmount = 0.0f;
    private float totalSellTicketAmount = 0.0f;
    private float totalPayedTicket = 0.0f;
    private float totalInBoxAmountt = 0.0f;
    private float totalInCashAmountt = 0.0f;
    private float differenceAmount = 0.0f;
    private float totalAmount = 0.0f;
    private List<Ticket> tickets;
    private String userName;

    public CloseBoxController() {
    }

    @PostConstruct
    private void findResumeData() {
        userName = loginController.getUser().getUserName();
        try {
            tickets = ticketService.findTodayTicketByUserName(userName);
            if (tickets != null) {
                for (Ticket currTicket : tickets) {
                    totalSellTicketAmount += currTicket.getTotalBetAmount();
                }
            }
        } catch (FindTicketException ex) {
            Logger.getLogger(CloseBoxController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void sumTotal() {
        this.totalAmount = (this.twoThousandLabelAmount
                + this.oneThousandLabelAmount
                + this.fiveHundredLabelAmount
                + this.twoHundredLabelAmount
                + this.oneHundredLabelAmount
                + this.fiftyLabelAmount
                + this.twentyFiveLabelAmount
                + this.twentyLabelAmount
                + this.tenLabelAmount
                + this.fiveLabelAmount
                + this.oneLabelAmount);
    }

    private void calculateDifferenceAmount() {
        this.differenceAmount = (this.totalAmount - this.totalSellTicketAmount);
    }

    public void downloadFile(ActionEvent event) throws JRException, IOException {
       // String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        ArrayList<Ticket> tickeList = new ArrayList<Ticket>();
        Ticket tic = new Ticket();
        tic.setId(111);
        tickeList.add(tic);
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(tickeList);        
        ec.responseReset();
        JasperPrint jasperPrint = JasperFillManager.fillReport("C:\\Personal\\Proyectos\\2013\\LOTERIA\\Source\\magicsnumberswebapp\\src\\main\\resources\\CloseBox.jasper", new HashMap(), beanCollectionDataSource);
        HttpServletResponse httpServletResponse = (HttpServletResponse) ec.getResponse();        
        httpServletResponse.addHeader("Content-disposition","attachment; filename=cuadre.pdf" );
        ServletOutputStream servletOuputStream = httpServletResponse.getOutputStream();        
        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOuputStream);
        fc.responseComplete();
    }

    public void cancel(ActionEvent event) {
        this.twoThousandLabelAmount = 0.0f;
        this.oneThousandLabelAmount = 0.0f;
        this.fiveHundredLabelAmount = 0.0f;
        this.twoHundredLabelAmount = 0.0f;
        this.oneHundredLabelAmount = 0.0f;
        this.fiftyLabelAmount = 0.0f;
        this.twentyFiveLabelAmount = 0.0f;
        this.twentyLabelAmount = 0.0f;
        this.tenLabelAmount = 0.0f;
        this.fiveLabelAmount = 0.0f;
        this.oneLabelAmount = 0.0f;

        this.twoThousandQty = 0;
        this.oneThousandQty = 0;
        this.fiveHundredQty = 0;
        this.twoHundredQty = 0;
        this.oneHundredQty = 0;
        this.fiftyQty = 0;
        this.twentyFiveQty = 0;
        this.twentyQty = 0;
        this.tenQty = 0;
        this.fiveQty = 0;
        this.oneQty = 0;
    }

    public float getTwoThousandLabelAmount() {
        this.twoThousandLabelAmount = (twoThousandQty * 2000);
        return twoThousandLabelAmount;
    }

    public void setTwoThousandLabelAmount(float twoThousandLabelAmount) {
        this.twoThousandLabelAmount = twoThousandLabelAmount;
    }

    public float getOneThousandLabelAmount() {
        this.oneThousandLabelAmount = (oneThousandQty * 1000);
        return oneThousandLabelAmount;
    }

    public void setOneThousandLabelAmount(float oneThousandLabelAmount) {
        this.oneThousandLabelAmount = oneThousandLabelAmount;
    }

    public float getFiveHundredLabelAmount() {
        this.fiveHundredLabelAmount = (fiveHundredQty * 500);
        return fiveHundredLabelAmount;
    }

    public void setFiveHundredLabelAmount(float fiveHundredLabelAmount) {
        this.fiveHundredLabelAmount = fiveHundredLabelAmount;
    }

    public float getTwoHundredLabelAmount() {
        this.twoHundredLabelAmount = (twoHundredQty * 200);
        return twoHundredLabelAmount;
    }

    public void setTwoHundredLabelAmount(float twoHundredLabelAmount) {
        this.twoHundredLabelAmount = twoHundredLabelAmount;
    }

    public float getOneHundredLabelAmount() {
        this.oneHundredLabelAmount = (oneHundredQty * 100);
        return oneHundredLabelAmount;
    }

    public void setOneHundredLabelAmount(float oneHundredLabelAmount) {
        this.oneHundredLabelAmount = oneHundredLabelAmount;
    }

    public float getFiftyLabelAmount() {
        this.fiftyLabelAmount = (fiftyQty * 50);
        return fiftyLabelAmount;
    }

    public void setFiftyLabelAmount(float fiftyLabelAmount) {
        this.fiftyLabelAmount = fiftyLabelAmount;
    }

    public float getTwentyFiveLabelAmount() {
        this.twentyFiveLabelAmount = (twentyFiveQty * 25);
        return twentyFiveLabelAmount;
    }

    public void setTwentyFiveLabelAmount(float twentyFiveLabelAmount) {
        this.twentyFiveLabelAmount = twentyFiveLabelAmount;
    }

    public float getTwentyLabelAmount() {
        this.twentyLabelAmount = (twentyQty * 20);
        return twentyLabelAmount;
    }

    public void setTwentyLabelAmount(float twentyLabelAmount) {
        this.twentyLabelAmount = twentyLabelAmount;
    }

    public float getTenLabelAmount() {
        this.tenLabelAmount = (tenQty * 10);
        return tenLabelAmount;
    }

    public void setTenLabelAmount(float tenLabelAmount) {
        this.tenLabelAmount = tenLabelAmount;
    }

    public float getFiveLabelAmount() {
        this.fiveLabelAmount = (fiveQty * 5);
        return fiveLabelAmount;
    }

    public void setFiveLabelAmount(float fiveLabelAmount) {
        this.fiveLabelAmount = fiveLabelAmount;
    }

    public float getOneLabelAmount() {
        this.oneLabelAmount = (oneQty * 1);
        return oneLabelAmount;
    }

    public void setOneLabelAmount(float oneLabelAmount) {
        this.oneLabelAmount = oneLabelAmount;
    }

    public int getTwoThousandQty() {
        return twoThousandQty;
    }

    public void setTwoThousandQty(int twoThousandQty) {
        this.twoThousandQty = twoThousandQty;
    }

    public int getOneThousandQty() {
        return oneThousandQty;
    }

    public void setOneThousandQty(int oneThousandQty) {
        this.oneThousandQty = oneThousandQty;
    }

    public int getFiveHundredQty() {
        return fiveHundredQty;
    }

    public void setFiveHundredQty(int fiveHundredQty) {
        this.fiveHundredQty = fiveHundredQty;
    }

    public int getTwoHundredQty() {
        return twoHundredQty;
    }

    public void setTwoHundredQty(int twoHundredQty) {
        this.twoHundredQty = twoHundredQty;
    }

    public int getOneHundredQty() {
        return oneHundredQty;
    }

    public void setOneHundredQty(int oneHundredQty) {
        this.oneHundredQty = oneHundredQty;
    }

    public int getFiftyQty() {
        return fiftyQty;
    }

    public void setFiftyQty(int fiftyQty) {
        this.fiftyQty = fiftyQty;
    }

    public int getTwentyQty() {
        return twentyQty;
    }

    public void setTwentyQty(int twentyQty) {
        this.twentyQty = twentyQty;
    }

    public int getTenQty() {
        return tenQty;
    }

    public void setTenQty(int tenQty) {
        this.tenQty = tenQty;
    }

    public int getFiveQty() {
        return fiveQty;
    }

    public void setFiveQty(int fiveQty) {
        this.fiveQty = fiveQty;
    }

    public int getOneQty() {
        return oneQty;
    }

    public void setOneQty(int oneQty) {
        this.oneQty = oneQty;
    }

    public int getTwentyFiveQty() {
        return twentyFiveQty;
    }

    public void setTwentyFiveQty(int twentyFiveQty) {
        this.twentyFiveQty = twentyFiveQty;
    }

    public float getTotalSellTicketAmount() {
        return totalSellTicketAmount;
    }

    public void setTotalSellTicketAmount(float totalSellTicketAmount) {
        this.totalSellTicketAmount = totalSellTicketAmount;
    }

    public float getTotalPayedTicket() {
        return totalPayedTicket;
    }

    public void setTotalPayedTicket(float totalPayedTicket) {
        this.totalPayedTicket = totalPayedTicket;
    }

    public float getTotalInBoxAmountt() {
        return totalInBoxAmountt;
    }

    public void setTotalInBoxAmountt(float totalInBoxAmountt) {
        this.totalInBoxAmountt = totalInBoxAmountt;
    }

    public float getTotalInCashAmountt() {
        return totalInCashAmountt;
    }

    public void setTotalInCashAmountt(float totalInCashAmountt) {
        this.totalInCashAmountt = totalInCashAmountt;
    }

    public float getDifferenceAmount() {
        calculateDifferenceAmount();
        return differenceAmount;
    }

    public void setDifferenceAmount(float differenceAmount) {
        this.differenceAmount = differenceAmount;
    }

    public float getTotalAmount() {
        sumTotal();
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }
}