package com.exception.magicsnumberswebapp.controller;

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

    private int twoThousandQty= 0;
    private int oneThousandQty= 0;
    private int fiveHundredQty= 0;
    private int twoHundredQty= 0;
    private int oneHundredQty= 0;    
    private int fiftyQty= 0;
    private int twentyFiveQty=0;
    private int twentyQty=0;
    private int tenQty=0;
    private int fiveQty=0;
    private int oneQty=0;
    private float twoThousandLabelAmount= 0.0f;
    private float oneThousandLabelAmount= 0.0f;
    private float fiveHundredLabelAmount= 0.0f;
    private float twoHundredLabelAmount= 0.0f;
    private float oneHundredLabelAmount= 0.0f;
    private float fiftyLabelAmount= 0.0f;
    private float twentyFiveLabelAmount= 0.0f;
    private float twentyLabelAmount= 0.0f;
    private float tenLabelAmount= 0.0f;
    private float fiveLabelAmount= 0.0f;
    private float oneLabelAmount= 0.0f;
    private float totalSellTicketAmount=0.0f;
    private float totalPayedTicket=0.0f;
    private float totalInBoxAmountt=0.0f;
    private float totalInCashAmountt=0.0f;
    private float differenceAmount=0.0f;
    private float totalAmount=0.0f;
    
    
    public CloseBoxController() {
    
    }

    public float getTwoThousandLabelAmount() {
        return twoThousandLabelAmount;
    }

    public void setTwoThousandLabelAmount(float twoThousandLabelAmount) {
        this.twoThousandLabelAmount = twoThousandLabelAmount;
    }
    

    public float getOneThousandLabelAmount() {
        return oneThousandLabelAmount;
    }

    public void setOneThousandLabelAmount(float oneThousandLabelAmount) {
        this.oneThousandLabelAmount = oneThousandLabelAmount;
    }
    

    public float getFiveHundredLabelAmount() {
        return fiveHundredLabelAmount;
    }

    public void setFiveHundredLabelAmount(float fiveHundredLabelAmount) {
        this.fiveHundredLabelAmount = fiveHundredLabelAmount;
    }
    

    public float getTwoHundredLabelAmount() {
        return twoHundredLabelAmount;
    }

    public void setTwoHundredLabelAmount(float twoHundredLabelAmount) {
        this.twoHundredLabelAmount = twoHundredLabelAmount;
    }
    

    public float getOneHundredLabelAmount() {
        return oneHundredLabelAmount;
    }

    public void setOneHundredLabelAmount(float oneHundredLabelAmount) {
        this.oneHundredLabelAmount = oneHundredLabelAmount;
    }
    

    public float getFiftyLabelAmount() {
        return fiftyLabelAmount;
    }

    public void setFiftyLabelAmount(float fiftyLabelAmount) {
        this.fiftyLabelAmount = fiftyLabelAmount;
    }

    public float getTwentyFiveLabelAmount() {
        return twentyFiveLabelAmount;
    }

    public void setTwentyFiveLabelAmount(float twentyFiveLabelAmount) {
        this.twentyFiveLabelAmount = twentyFiveLabelAmount;
    }


    public float getTwentyLabelAmount() {
        return twentyLabelAmount;
    }

    public void setTwentyLabelAmount(float twentyLabelAmount) {
        this.twentyLabelAmount = twentyLabelAmount;
    }    

    public float getTenLabelAmount() {
        return tenLabelAmount;
    }

    public void setTenLabelAmount(float tenLabelAmount) {
        this.tenLabelAmount = tenLabelAmount;
    }

    public float getFiveLabelAmount() {
        return fiveLabelAmount;
    }

    public void setFiveLabelAmount(float fiveLabelAmount) {
        this.fiveLabelAmount = fiveLabelAmount;
    }

    public float getOneLabelAmount() {
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
        return differenceAmount;
    }

    public void setDifferenceAmount(float differenceAmount) {
        this.differenceAmount = differenceAmount;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    
}