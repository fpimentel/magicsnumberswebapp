package com.exception.magicsnumberswebapp.dao;

import com.exception.magicsnumbersws.entities.Profile;
import com.exception.magicsnumbersws.exception.SearchAllProfileException;
import java.util.List;

/**
 *
 * @author fpimentel
 */
public interface ProfileDao {
    List<Profile> findAll() throws SearchAllProfileException;
}
