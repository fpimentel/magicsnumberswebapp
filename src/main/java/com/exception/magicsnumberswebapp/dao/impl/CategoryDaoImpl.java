/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exception.magicsnumberswebapp.dao.impl;

import com.exception.magicsnumberswebapp.dao.CategoryDao;
import com.exception.magicsnumbersws.endpoints.LookupTablesEndpoint;
import com.exception.magicsnumbersws.entities.Category;
import com.exception.magicsnumbersws.entities.Status;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author cpimentel
 */
@Repository
public class CategoryDaoImpl implements CategoryDao{
   
    @Autowired
    private LookupTablesEndpoint lookupTablesEndpoint;
    
    private List<Category> categories;
    
    public CategoryDaoImpl() {
        
    }    

    
    public LookupTablesEndpoint getLookupTablesEndpoint() {
        return lookupTablesEndpoint;
    }

    public void setLookupTablesEndpoint(LookupTablesEndpoint lookupTablesEndpoint) {
        this.lookupTablesEndpoint = lookupTablesEndpoint;
    }         
    
    @Override
    public List<Category> getCategories() {
        return this.categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    

    @Override
    @PostConstruct
    public void loadCategoriesData() {
        this.categories = lookupTablesEndpoint.getAllCategories();
    }

        
}
