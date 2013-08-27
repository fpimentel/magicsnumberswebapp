package com.exception.magicsnumberswebapp.service;
import com.exception.magicsnumbersws.entities.SystemOption;
import com.exception.magicsnumbersws.exception.SearchAllSystemOptionException;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author cpimentel
 */
@Service
public interface SystemOptionService {
    List<SystemOption> findAll()throws SearchAllSystemOptionException;
    void loadSytemOptionData() throws SearchAllSystemOptionException;
}
