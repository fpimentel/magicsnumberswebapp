/*
 * Bean to manage the jasper report data for tickets.
 */
package com.exception.magicsnumberswebapp.reports.beans;

/**
 *
 * @author fpimentel
 * 
 */
public class TicketSaleData {
    private String lottery;
    private String time;
    private String bet;
    private String numbers;
    private float betAmount;

    public String getLottery() {
        return lottery;
    }

    public void setLottery(String lottery) {
        this.lottery = lottery;
    }
    
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBet() {
        return bet;
    }

    public void setBet(String bet) {
        this.bet = bet;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }

    public float getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(float betAmount) {
        this.betAmount = betAmount;
    }        
}
