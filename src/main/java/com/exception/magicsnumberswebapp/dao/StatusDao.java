
package com.exception.magicsnumberswebapp.dao;

import com.exception.magicsnumbersws.entities.Status;
import java.util.List;


/**
 *
 * @author fpimentel
 */
public interface StatusDao {
    void loadStatusData(); 
    List<Status> getStatus();
}
