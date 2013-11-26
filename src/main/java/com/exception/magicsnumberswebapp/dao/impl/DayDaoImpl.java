package com.exception.magicsnumberswebapp.dao.impl;
import com.exception.magicsnumberswebapp.dao.DaysDao;
import com.exception.magicsnumbersws.endpoints.LookupTablesEndpoint;
import com.exception.magicsnumbersws.entities.Day;
import com.exception.magicsnumbersws.exception.FindDayException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author cpimentel
 * @since 12-oct-13
 */
@Repository
public class DayDaoImpl implements DaysDao {

    @Autowired
    private LookupTablesEndpoint lookupTablesEndpoint;

    public DayDaoImpl() {
    }

    @Override
    public List<Day> findAllDays() throws FindDayException {
        return this.lookupTablesEndpoint.findAllDays();
    }
}
