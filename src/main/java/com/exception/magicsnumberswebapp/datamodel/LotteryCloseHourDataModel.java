package com.exception.magicsnumberswebapp.datamodel;
import com.exception.magicsnumbersws.entities.Lottery;
import com.exception.magicsnumbersws.entities.LotteryCloseHour;
import java.util.Collections;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;
import org.springframework.beans.BeanUtils;

public class LotteryCloseHourDataModel extends ListDataModel<LotteryCloseHour> implements SelectableDataModel<LotteryCloseHour> {

    private List<LotteryCloseHour> lotteryCloseHour;

    public LotteryCloseHourDataModel() {
    }

    public LotteryCloseHourDataModel(List<LotteryCloseHour> data) {
        super(data);
        this.lotteryCloseHour = data;
    }

    public List<LotteryCloseHour> getLotteryCloseHour() {
        return lotteryCloseHour;
    }

    public void setLotteryCloseHour(List<LotteryCloseHour> lotteryCloseHour) {
        this.lotteryCloseHour = lotteryCloseHour;
    }
  

    
    public int nextLotteryCloseHourId() {
        if (this.lotteryCloseHour == null || this.lotteryCloseHour.isEmpty()) {
            return 0;
        }
        LotteryCloseHour lottery = Collections.max(this.lotteryCloseHour);
        return lottery.getId() + 1;
    }

    @Override
    public Object getRowKey(LotteryCloseHour lotteryCloseHour) {
        return (lotteryCloseHour.getId() == null ? 0:lotteryCloseHour.getId());
    }

    @Override
    public LotteryCloseHour getRowData(String rowkey) {
                List<LotteryCloseHour> lotteryCloseHours = (List<LotteryCloseHour>) getWrappedData();
        for (LotteryCloseHour lotteryCloseHour : lotteryCloseHours) {
            if (lotteryCloseHour.getId().toString().equals(rowkey)) {
                LotteryCloseHour lotteryCloseHourCopy = new LotteryCloseHour();
                BeanUtils.copyProperties(lotteryCloseHour, lotteryCloseHourCopy);
                return lotteryCloseHourCopy;
            }
        }
        return null;
    }
}