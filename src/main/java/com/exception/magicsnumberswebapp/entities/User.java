/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exception.magicsnumberswebapp.entities;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.xml.bind.annotation.XmlElement;


public class User implements Serializable {
        
    private Integer id;
    
    private static final long serialVersionUID = 1L;    
    @SerializedName("firtName")
    private String firtName;    
    private String lastName;    
    private String contactNumber;    
    private String userName;    
    private String password;   
    private int statusId;                             
    private Set<Profile> profiles = new HashSet<Profile>(0);      
    
    
    public User() {
        
    }

    public User(Integer id) {
        this.id = id;
    }

    public User(Integer id, String firtName, String lastName, String userName, String password, int statusId) {
        this.id = id;
        this.firtName = firtName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.statusId = statusId;
    }
        
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getFirtName() {
        return firtName;
    }

    public void setFirtName(String firtName) {
        this.firtName = firtName;
    }
    
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    @XmlElement
    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }
       
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public Set<Profile> getProfiles() {
        return this.profiles;
    }
    
    public void setProfiles(Set<Profile> profiles) {
        this.profiles = profiles;
    }
    @Override
    public String toString() {
        return "com.exception.magicsnumbersws.entities.User[ id=" + id + " ]";
    }
    
}
