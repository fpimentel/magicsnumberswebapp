package com.exception.magicsnumberswebapp.controller;

import com.exception.magicsnumberswebapp.service.ConsortiumService;
import com.exception.magicsnumbersws.entities.Consortium;
import com.exception.magicsnumbersws.exception.SearchAllConsortiumException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.event.ValueChangeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author cpimentel
 */
@Controller
@Scope("view")
public class TicketReportController {

    private String betBankingName;
    @Autowired
    private LoginController loginController;
    private Consortium selectedConsortium;
    private List<Consortium> consortiums;
    @Autowired
    private ConsortiumService consortiumService;

    public TicketReportController() {
    }

    public Consortium getSelectedConsortium() {
        return selectedConsortium;
    }

    public void setSelectedConsortium(Consortium selectedConsortium) {
        this.selectedConsortium = selectedConsortium;
    }

    public List<Consortium> getConsortiums() {
        if (this.consortiums == null) {
            try {
                this.consortiums = this.consortiumService.findAll(this.loginController.getUser().getId());
            } catch (SearchAllConsortiumException ex) {
                Logger.getLogger(TicketReportController.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (Exception ex) {
                Logger.getLogger(TicketReportController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return this.consortiums;
    }
    public void setConsortiums(List<Consortium> consortiums) {
        this.consortiums = consortiums;
    }

    public void getBetBankingByConsortiumOnChange(ValueChangeEvent event) {
    }
}