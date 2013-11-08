package com.exception.magicsnumberswebapp.datamodel;

import com.exception.magicsnumbersws.entities.BetBankingBetLimit;
import java.util.Collections;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;
import org.springframework.beans.BeanUtils;

/**
 *
 * @author Cristian
 */
public class BetLimitDataModel extends ListDataModel<BetBankingBetLimit> implements SelectableDataModel<BetBankingBetLimit> {

    List<BetBankingBetLimit> betLimits;

    public List<BetBankingBetLimit> getBetLimits() {
        return betLimits;
    }

    public BetLimitDataModel() {
    }

    public BetLimitDataModel(List<BetBankingBetLimit> data) {
        super(data);
        this.betLimits = data;
    }

    public void setBetLimits(List<BetBankingBetLimit> betLimits) {
        this.betLimits = betLimits;
    }

    @Override
    public BetBankingBetLimit getRowData(String rowKey) {
        List<BetBankingBetLimit> betBankingBetLimits = (List<BetBankingBetLimit>) getWrappedData();
        if (betBankingBetLimits != null) {
            for (BetBankingBetLimit betBankingBetLimit : betBankingBetLimits) {
                if (betBankingBetLimit.getId().toString().equals(rowKey)) {
                    BetBankingBetLimit betBankingBetLimitCopy = new BetBankingBetLimit();
                    BeanUtils.copyProperties(betBankingBetLimit, betBankingBetLimitCopy);
                    return betBankingBetLimitCopy;
                }
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(BetBankingBetLimit betBankingBetLimit) {
        return betBankingBetLimit.getId();
    }

    public int nextBetBankingBetLimitId() {
        if (this.betLimits == null || this.betLimits.isEmpty()) {
            return 0;
        }
        BetBankingBetLimit betBankingBetLimit = Collections.max(this.betLimits);
        return betBankingBetLimit.getId() + 1;
    }
}
