
package com.exception.magicsnumberswebapp.dao;

import com.exception.magicsnumbersws.entities.Status;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author fpimentel
 */
public interface StatusDao {
    void loadStatusData(); 
    List<Status> getStatus();
}
