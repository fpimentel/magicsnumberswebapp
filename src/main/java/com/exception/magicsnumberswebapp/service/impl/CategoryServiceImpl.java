
package com.exception.magicsnumberswebapp.service.impl;

import com.exception.magicsnumberswebapp.dao.CategoryDao;
import com.exception.magicsnumberswebapp.dao.StatusDao;
import com.exception.magicsnumberswebapp.service.CategoryService;
import com.exception.magicsnumbersws.entities.Category;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author cpimentel
 */
@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryDao categoryDao;

    public CategoryDao getCategoryDao() {
        return this .categoryDao;
    }

    public void setCategoryDao(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }    

    @Override
    public List<Category> getCategories() {
        return this.categoryDao.getCategories();
    }
}
