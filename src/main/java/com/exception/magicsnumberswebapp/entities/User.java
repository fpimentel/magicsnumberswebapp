/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exception.magicsnumberswebapp.entities;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/** 
 * @author fpimentel
 */

public class User implements Serializable {
                
    private static final long serialVersionUID = 1L;  
    
    @SerializedName("id")
    private int id;   
    
    @SerializedName("firtName")
    private String firtName;    
    
    @SerializedName("lastName")
    private String lastName;

    @SerializedName("contactNumber")
    private String contactNumber;
    
    @SerializedName("password")
    private String password;

    
    //private Collection<Profile>profiles;
    
    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
            
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirtName() {
        return firtName;
    }

    public void setFirtName(String firtName) {
        this.firtName = firtName;
    }
    
    /*public Collection<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(Collection<Profile> profiles) {
        this.profiles = profiles;
    } */ 
}
