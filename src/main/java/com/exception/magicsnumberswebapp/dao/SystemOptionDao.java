
package com.exception.magicsnumberswebapp.dao;


import com.exception.magicsnumbersws.entities.SystemOption;
import com.exception.magicsnumbersws.exception.SaveSystemOptionsDataException;
import com.exception.magicsnumbersws.exception.SearchAllSystemOptionException;
import java.util.List;


/**
 *
 * @author cpimentel
 */
public interface SystemOptionDao {
   
   List<SystemOption> findAll()throws SearchAllSystemOptionException;
   void saveAllSystemOptionData(List<SystemOption> systemOptions)throws SaveSystemOptionsDataException;
}
