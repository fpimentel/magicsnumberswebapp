package com.exception.magicsnumberswebapp.datamodel;

import com.exception.magicsnumbersws.entities.Consortium;
import com.exception.magicsnumbersws.entities.SystemOption;
import java.util.Collections;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;
import org.springframework.beans.BeanUtils;

public class ConsortiumDataModel extends ListDataModel<Consortium> implements SelectableDataModel<Consortium> {

    private List<Consortium> consortiums;

    public ConsortiumDataModel() {
    }

    public ConsortiumDataModel(List<Consortium> data) {
        super(data);
        this.consortiums = data;
    }

    public List<Consortium> getConsortiums() {
        return consortiums;
    }

    public void setConsortiums(List<Consortium> consortiums) {
        this.consortiums = consortiums;
    }

    

    public int nextConsortiumId() {
        if (this.consortiums == null || this.consortiums.isEmpty()) {
            return 0;
        }
        Consortium Consortium = Collections.max(this.consortiums);
        return Consortium.getId() + 1;
    }

    @Override
    public Consortium getRowData(String rowKey) {
        List<Consortium> consortiums = (List<Consortium>) getWrappedData();
        for (Consortium consortium : consortiums) {
            if (consortium.getId().toString().equals(rowKey)) {
                Consortium consortiumCopy = new Consortium();
                BeanUtils.copyProperties(consortium, consortiumCopy);
                return consortiumCopy;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(Consortium consortium) {
        return consortium.getId();
    }
}