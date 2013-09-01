package com.exception.magicsnumberswebapp.view.converter;
import com.exception.magicsnumberswebapp.service.CategoryService;
import com.exception.magicsnumbersws.entities.Category;
import com.exception.magicsnumbersws.entities.Status;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author fpimentel
 */
@Component
@FacesConverter("categoryConverter")
public class CategoryConverter implements Converter {

    @Autowired
    private CategoryService categoryService;
    private List<Category> categories;

    public List<Category> getCategories() {
        if (categories == null) {
            categories= categoryService.getCategories();
        }
        return this.categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String submittedValue) {
        int id = 0;
        try {
            id = Integer.parseInt(submittedValue);
            for (Category currCategory : getCategories() ){
                if (currCategory .getId() == id) {
                    return currCategory ;
                }
            }
        } catch (Exception ex) {
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            return String.valueOf(((Category) value).getId());
        }
    }
}
