package com.exception.magicsnumberswebapp.dao.impl;

import com.exception.magicsnumberswebapp.dao.ConsortiumDao;
import com.exception.magicsnumberswebapp.dao.SystemOptionDao;

import com.exception.magicsnumbersws.endpoints.SecurityEndPoint;
import com.exception.magicsnumbersws.entities.Consortium;

import com.exception.magicsnumbersws.entities.User;
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
    private SecurityEndPoint securityEndpoint;

    public ConsortiumDaoImpl() {
    }

    @Override
    public List<Consortium> findAll(int userId) throws SearchAllConsortiumException {
        return securityEndpoint.findConsortiumByUserId(userId);
    }
}
