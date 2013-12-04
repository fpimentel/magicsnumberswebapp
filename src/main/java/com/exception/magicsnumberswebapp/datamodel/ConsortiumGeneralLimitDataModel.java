package com.exception.magicsnumberswebapp.datamodel;
import com.exception.magicsnumbersws.entities.ConsortiumGeneralLimit;
import java.util.Collections;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;
import org.springframework.beans.BeanUtils;

public class ConsortiumGeneralLimitDataModel extends ListDataModel<ConsortiumGeneralLimit> implements SelectableDataModel<ConsortiumGeneralLimit> {

    private List<ConsortiumGeneralLimit> consortiumGeneralLimits;

    public ConsortiumGeneralLimitDataModel() {
    }
    public ConsortiumGeneralLimitDataModel(List<ConsortiumGeneralLimit> data) {
        super(data);
        this.consortiumGeneralLimits = data;
    }
    public List<ConsortiumGeneralLimit> getConsortiumGeneralLimits() {
        return consortiumGeneralLimits;
    }
    public void setConsortiumGenelLimits(List<ConsortiumGeneralLimit> consortiumGeneralLimits) {
        this.consortiumGeneralLimits = consortiumGeneralLimits;
    }    
    public int nextConsortiumGeneralLimitId() {
        if (this.consortiumGeneralLimits == null || this.consortiumGeneralLimits.isEmpty()) {
            return 0;
        }
        ConsortiumGeneralLimit consortiumGeneralLimit = Collections.max(this.consortiumGeneralLimits);
        return consortiumGeneralLimit.getId() + 1;
    }
    @Override
    public Object getRowKey(ConsortiumGeneralLimit consortiumGeneralLimit) {
        return consortiumGeneralLimit.getId();
    }
    @Override
    public ConsortiumGeneralLimit getRowData(String rowkey) {
                List<ConsortiumGeneralLimit> consortiumGeneralLimits = (List<ConsortiumGeneralLimit>) getWrappedData();
        for (ConsortiumGeneralLimit consortiumGeneralLimitCurrent : consortiumGeneralLimits) {
            if (consortiumGeneralLimitCurrent.getId().toString().equals(rowkey)) {
                ConsortiumGeneralLimit betBlockingNumberCopy = new ConsortiumGeneralLimit();
                BeanUtils.copyProperties(consortiumGeneralLimitCurrent, betBlockingNumberCopy);
                return betBlockingNumberCopy;
            }
        }
        return null;
    }
}