/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exception.magicsnumberswebapp.controller;

import com.exception.magicsnumberswebapp.datamodel.CarDataModel;
import com.exception.magicsnumbersws.entities.User;
import java.io.Serializable;  
import java.util.ArrayList;  
import java.util.Date;  
import java.util.List;  
import java.util.UUID;  
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
  
import org.primefaces.event.SelectEvent;  
import org.primefaces.event.UnselectEvent;  
  
 
@ManagedBean
public class TableBean {  
  
    
  
    private final static String[] colors;  
  
    private final static String[] manufacturers;  
  
     static {  
       colors = new String[10];  
        colors[0] = "Black";  
        colors[1] = "White";  
        colors[2] = "Green";  
        colors[3] = "Red";  
        colors[4] = "Blue";  
        colors[5] = "Orange";  
        colors[6] = "Silver";  
        colors[7] = "Yellow";  
        colors[8] = "Brown";  
        colors[9] = "Maroon";  
  
         manufacturers = new String[10];  
        manufacturers[0] = "Mercedes";  
        manufacturers[1] = "BMW";  
        manufacturers[2] = "Volvo";  
        manufacturers[3] = "Audi";  
        manufacturers[4] = "Renault";  
        manufacturers[5] = "Opel";  
        manufacturers[6] = "Volkswagen";  
        manufacturers[7] = "Chrysler";  
        manufacturers[8] = "Ferrari";  
        manufacturers[9] = "Ford";  
    } 
    private List<User> carsSmall;  
      
    private User selectedCar;  
  
    private CarDataModel mediumCarsModel;  
  
    public TableBean() {  
        carsSmall = new ArrayList<User>();  
          
        populateRandomCars(carsSmall, 50);  
  
        mediumCarsModel = new CarDataModel(carsSmall);  
    }  
  
    private void populateRandomCars(List<User> list, int size) {  
        for(int i = 0 ; i < size ; i++)  
            list.add(new User(1, "fasdf", "sdfad", "fdasf", "fdsaf", 1));                          
    }  
  
    public User getSelectedCar() {  
        return selectedCar;  
    }  
    public void setSelectedCar(User selectedCar) {  
        this.selectedCar = selectedCar;  
    }  
  
    public CarDataModel getMediumCarsModel() {  
        return mediumCarsModel;  
    }  
  
    public void onRowSelect(SelectEvent event) {  
        FacesMessage msg = new FacesMessage("Car Selected", ((User) event.getObject()).getUserName());  
  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }  
  
    public void onRowUnselect(UnselectEvent event) {  
        FacesMessage msg = new FacesMessage("Car Unselected", ((User) event.getObject()).getUserName());  
  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }  
}