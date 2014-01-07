package com.exception.magicsnumberswebapp.datamodel;

import com.exception.magicsnumbersws.entities.WinningNumber;
import java.util.Collections;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;
import org.springframework.beans.BeanUtils;

public class WinningNumberDataModel extends ListDataModel<WinningNumber> implements SelectableDataModel<WinningNumber> {

    private List<WinningNumber> winningNumbers;

    public WinningNumberDataModel() {
    }

    public WinningNumberDataModel(List<WinningNumber> data) {
        super(data);
        this.winningNumbers = data;
    }

    public List<WinningNumber> getWinningNumbers() {
        return winningNumbers;
    }

    public void setWinningNumbers(List<WinningNumber> winningNumbers) {
        this.winningNumbers = winningNumbers;
    }        
    
    public int nextWinningNumberId() {
        if (this.winningNumbers == null || this.winningNumbers.isEmpty()) {
            return 0;
        }
        WinningNumber winningNumber = Collections.max(this.winningNumbers);
        return winningNumber.getId() + 1;
    }

    @Override
    public Object getRowKey(WinningNumber winningNumber) {
        return (winningNumber.getId() == null ? 0 : winningNumber.getId());
    }

    @Override
    public WinningNumber getRowData(String rowkey) {
        List<WinningNumber> winningNumbers = (List<WinningNumber>) getWrappedData();
        for (WinningNumber currWinningNumber : winningNumbers) {
            if (currWinningNumber.getId().toString().equals(rowkey)) {
                WinningNumber winningNumberCopy = new WinningNumber();
                BeanUtils.copyProperties(currWinningNumber, winningNumberCopy);
                return winningNumberCopy;
            }
        }
        return null;
    }
}