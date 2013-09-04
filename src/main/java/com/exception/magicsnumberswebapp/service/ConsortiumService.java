package com.exception.magicsnumberswebapp.service;
import com.exception.magicsnumbersws.entities.Consortium;
import com.exception.magicsnumbersws.entities.User;
import com.exception.magicsnumbersws.exception.SaveConsortiumDataException;
import com.exception.magicsnumbersws.exception.SearchAllConsortiumException;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author cpimentel
 */
@Service
public interface ConsortiumService {
    List<Consortium> findAll(int userId)throws SearchAllConsortiumException;
  public void saveConsortiumsData(List<Consortium> list) throws SaveConsortiumDataException;
}
