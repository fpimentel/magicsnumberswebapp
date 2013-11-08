package com.exception.magicsnumberswebapp.datamodel;
import com.exception.magicsnumbersws.entities.BetBanking;
import java.util.Collections;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;
import org.springframework.beans.BeanUtils;

public class BetBankingDataModel extends ListDataModel<BetBanking> implements SelectableDataModel<BetBanking> {

    private List<BetBanking> betbankings;

    public BetBankingDataModel() {
    }

    public BetBankingDataModel(List<BetBanking> data) {
        super(data);
        this.betbankings = data;
    }

    public List<BetBanking> getBetbankings() {
        return betbankings;
    }

    public void setBetbankings(List<BetBanking> betbankings) {
        this.betbankings = betbankings;
    }

    
    public int nextBetBankingId() {
        if (this.betbankings == null || this.betbankings.isEmpty()) {
            return 0;
        }
        BetBanking betBanking = Collections.max(this.betbankings);
        return betBanking.getId() + 1;
    }

    @Override
    public Object getRowKey(BetBanking betBanking) {
        return (betBanking.getId() == null ? 0:betBanking.getId());
    }

    @Override
    public BetBanking getRowData(String rowkey) {
                List<BetBanking> betBankings = (List<BetBanking>) getWrappedData();
        for (BetBanking betBanking : betBankings) {
            if (betBanking.getId().toString().equals(rowkey)) {
                BetBanking betBankingCopy = new BetBanking();
                BeanUtils.copyProperties(betBanking, betBankingCopy);
                return betBankingCopy;
            }
        }
        return null;
    }
}