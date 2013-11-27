package com.exception.magicsnumberswebapp.dao.impl;
import com.exception.magicsnumberswebapp.dao.TimeDao;
import com.exception.magicsnumbersws.endpoints.LookupTablesEndpoint;
import com.exception.magicsnumbersws.entities.Day;
import com.exception.magicsnumbersws.entities.Time;
import com.exception.magicsnumbersws.exception.FindDayException;
import com.exception.magicsnumbersws.exception.FindTimeException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author cpimentel
 * @since 12-oct-13
 */
@Repository
public class TimeDaoImpl implements TimeDao {

    @Autowired
    private LookupTablesEndpoint lookupTablesEndpoint;

    public TimeDaoImpl() {
    }

    @Override
    public List<Time> findAllTimes() throws FindTimeException {
        return this.lookupTablesEndpoint.findAllTimes();
    }

    @Override
    public Time findTimeById(int timeId) throws FindTimeException {
        return this.lookupTablesEndpoint.findTimeById(timeId);
    }



}
