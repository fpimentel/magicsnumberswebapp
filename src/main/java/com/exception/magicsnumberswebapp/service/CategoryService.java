package com.exception.magicsnumberswebapp.service;

import com.exception.magicsnumbersws.entities.Category;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author fpimentel
 */
@Service
public interface CategoryService {
    List<Category> getCategories();
}
