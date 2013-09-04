package com.exception.magicsnumberswebapp.dao.impl;
import com.exception.magicsnumberswebapp.dao.ConsortiumDao;
import com.exception.magicsnumbersws.endpoints.LookupTablesEndpoint;
import com.exception.magicsnumbersws.entities.Consortium;
import com.exception.magicsnumbersws.exception.SaveConsortiumDataException;
import com.exception.magicsnumbersws.exception.SearchAllConsortiumException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author cpimentel
 */
@Repository
public class ConsortiumDaoImpl implements ConsortiumDao {

    @Autowired
    private LookupTablesEndpoint securityEndpoint;

    public ConsortiumDaoImpl() {
    }

    @Override
    public List<Consortium> findAll(int userId) throws SearchAllConsortiumException {
        return securityEndpoint.findConsortiumByUserId(userId);
    }

    @Override
    public void saveConsortiumsData(List<Consortium> list) throws SaveConsortiumDataException {
        securityEndpoint.saveConsortiumsData(list);
    }
}
