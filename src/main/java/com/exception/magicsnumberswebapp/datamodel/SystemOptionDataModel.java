package com.exception.magicsnumberswebapp.datamodel;

import com.exception.magicsnumbersws.entities.SystemOption;
import java.util.Collections;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;
import org.springframework.beans.BeanUtils;

public class SystemOptionDataModel extends ListDataModel<SystemOption> implements SelectableDataModel<SystemOption> {

    private List<SystemOption> systemOptions;

    public SystemOptionDataModel() {
    }

    public SystemOptionDataModel(List<SystemOption> data) {
        super(data);
        this.systemOptions = data;
    }

    public List<SystemOption> getSystemOptions() {
        return systemOptions;
    }

    public void setSystemOptions(List<SystemOption> systemOptions) {
        this.systemOptions = systemOptions;
    }

    public int nextSystemOptionId() {
        if (systemOptions == null || systemOptions.isEmpty()) {
            return 0;
        }
        SystemOption systemOption = Collections.max(systemOptions);
        return systemOption.getId() + 1;
    }

    @Override
    public SystemOption getRowData(String rowKey) {
        List<SystemOption> options = (List<SystemOption>) getWrappedData();
        for (SystemOption systemOption : options) {
            if (systemOption.getId().toString().equals(rowKey)) {
                SystemOption systemOptionCopy = new SystemOption();
                BeanUtils.copyProperties(systemOption, systemOptionCopy);
                return systemOptionCopy;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(SystemOption systemOption) {
        return systemOption.getId();
    }
}