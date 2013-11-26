package com.exception.magicsnumberswebapp.datamodel;
import com.exception.magicsnumbersws.entities.Lottery;
import java.util.Collections;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;
import org.springframework.beans.BeanUtils;

public class LotteryDataModel extends ListDataModel<Lottery> implements SelectableDataModel<Lottery> {

    private List<Lottery> lottery;

    public LotteryDataModel() {
    }

    public LotteryDataModel(List<Lottery> data) {
        super(data);
        this.lottery = data;
    }

    public List<Lottery> getBetbankings() {
        return this.lottery;
    }

    public void setBetbankings(List<Lottery> lotteries) {
        this.lottery = lotteries;
    }

    
    public int nextLotteryId() {
        if (this.lottery == null || this.lottery.isEmpty()) {
            return 0;
        }
        Lottery lottery = Collections.max(this.lottery);
        return lottery.getId() + 1;
    }

    @Override
    public Object getRowKey(Lottery lottery) {
        return (lottery.getId() == null ? 0:lottery.getId());
    }

    @Override
    public Lottery getRowData(String rowkey) {
                List<Lottery> lotteries = (List<Lottery>) getWrappedData();
        for (Lottery lottery : lotteries) {
            if (lottery.getId().toString().equals(rowkey)) {
                Lottery lotteryCopy = new Lottery();
                BeanUtils.copyProperties(lottery, lotteryCopy);
                return lotteryCopy;
            }
        }
        return null;
    }
}