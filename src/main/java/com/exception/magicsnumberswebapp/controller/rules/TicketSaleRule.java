/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exception.magicsnumberswebapp.controller.rules;
import com.exception.magicsnumbersws.entities.BetBanking;
import com.exception.magicsnumbersws.entities.Lottery;
import com.exception.magicsnumbersws.entities.User;
import java.util.Set;

/**
 *
 * @author Cristian
 */
public class TicketSaleRule {
    
    public static Set<Lottery> getUserLoggedLotteries(User userLogged){
        BetBanking betBanking = (BetBanking) userLogged.getBetBankings().toArray()[0];
        return betBanking.getLotteries();
    }
   

}
