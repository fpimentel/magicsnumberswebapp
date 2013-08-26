/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exception.magicsnumberswebapp.datamodel;

import com.exception.magicsnumbersws.entities.User;
import java.util.List;  
import javax.faces.model.ListDataModel;  
  
import org.primefaces.model.SelectableDataModel;  
  
public class CarDataModel extends ListDataModel<User> implements SelectableDataModel<User> {    
  
    public CarDataModel() {  
    }  
  
    public CarDataModel(List<User> data) {  
        super(data);  
    }  
      
    @Override  
    public User getRowData(String rowKey) {  
        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data  
          
        List<User> cars = (List<User>) getWrappedData();  
          
        for(User car : cars) {  
            if(car.getUserName().equals(rowKey))  
                return car;  
        }  
          
        return null;  
    }  
  
    @Override  
    public Object getRowKey(User car) {  
        return car.getUserName();
    }  
}
