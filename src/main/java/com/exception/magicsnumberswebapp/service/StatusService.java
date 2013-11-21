package com.exception.magicsnumberswebapp.service;

import com.exception.magicsnumbersws.entities.Status;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author fpimentel
 */
@Service
public interface StatusService {
    List<Status> getStatus();
    List<Status> getStatusByStatusType(int statusTypeId);
}
