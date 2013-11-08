package com.exception.magicsnumberswebapp.datamodel;
import com.exception.magicsnumbersws.entities.BlockingNumberBetBanking;
import java.util.Collections;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;
import org.springframework.beans.BeanUtils;

public class BetBlockingNumberDataModel extends ListDataModel<BlockingNumberBetBanking> implements SelectableDataModel<BlockingNumberBetBanking> {

    private List<BlockingNumberBetBanking> betBlockingNumbers;

    public BetBlockingNumberDataModel() {
    }
    public BetBlockingNumberDataModel(List<BlockingNumberBetBanking> data) {
        super(data);
        this.betBlockingNumbers = data;
    }
    public List<BlockingNumberBetBanking> getBetBlockingNumbers() {
        return betBlockingNumbers;
    }
    public void setBetBlockingNumbers(List<BlockingNumberBetBanking> betBlockingNumbers) {
        this.betBlockingNumbers = betBlockingNumbers;
    }    
    public int nextBlockingId() {
        if (this.betBlockingNumbers == null || this.betBlockingNumbers.isEmpty()) {
            return 0;
        }
        BlockingNumberBetBanking betBlockNumber = Collections.max(this.betBlockingNumbers);
        return betBlockNumber.getId() + 1;
    }
    @Override
    public Object getRowKey(BlockingNumberBetBanking blockingNumberBetBanking) {
        return blockingNumberBetBanking.getId();
    }
    @Override
    public BlockingNumberBetBanking getRowData(String rowkey) {
                List<BlockingNumberBetBanking> blockingNumbersBetBanking = (List<BlockingNumberBetBanking>) getWrappedData();
        for (BlockingNumberBetBanking blockingNumberBetBanking : blockingNumbersBetBanking) {
            if (blockingNumberBetBanking.getId().toString().equals(rowkey)) {
                BlockingNumberBetBanking betBlockingNumberCopy = new BlockingNumberBetBanking();
                BeanUtils.copyProperties(blockingNumberBetBanking, betBlockingNumberCopy);
                return betBlockingNumberCopy;
            }
        }
        return null;
    }
}