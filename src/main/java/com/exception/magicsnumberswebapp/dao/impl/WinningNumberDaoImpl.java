package com.exception.magicsnumberswebapp.dao.impl;
import com.exception.magicsnumberswebapp.dao.WinningNumberDao;
import com.exception.magicsnumbersws.endpoints.BusinessEndpoint;
import com.exception.magicsnumbersws.entities.WinningNumber;
import com.exception.magicsnumbersws.exception.SaveWinningNumberDataException;
import com.exception.magicsnumbersws.exception.SearchWinningNumbersException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author cpimentel
 * @since 21-Dic-13
 */
@Repository
public class WinningNumberDaoImpl implements WinningNumberDao {

    @Autowired
    private BusinessEndpoint bussinesEndpoint;

    public WinningNumberDaoImpl() {
    }    

    @Override
    public List<WinningNumber> findWinningNumbers(String fromDate, String ToDate) throws SearchWinningNumbersException {
        return this.bussinesEndpoint.findWinningNumbers(fromDate, ToDate);
    }

    @Override
    public void saveWinningNumberInfo(WinningNumber winningNumber) throws SaveWinningNumberDataException {
        this.bussinesEndpoint.saveWinningNumberInfo(winningNumber);
    }

}
