
package com.exception.magicsnumberswebapp.dao;

import com.exception.magicsnumbersws.entities.Category;
import java.util.List;


/**
 *
 * @author fpimentel
 */
public interface CategoryDao {
    void loadCategoriesData(); 
    List<Category> getCategories();
}
