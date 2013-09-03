
package com.exception.magicsnumberswebapp.dao;


import com.exception.magicsnumbersws.entities.Consortium;
import com.exception.magicsnumbersws.exception.SearchAllConsortiumException;
import java.util.List;


/**
 *
 * @author cpimentel
 * @since  02-sept-13
 */
public interface ConsortiumDao {
   
   List<Consortium> findAll(int userId)throws SearchAllConsortiumException;
}
