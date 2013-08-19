/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exception.magicsnumberswebapp.entities;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 *
 * @author fpimentel
 */

public class Profile implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String name;
    /*
    @SerializedName("description")
    private String description;
    
    @SerializedName("creationUser")
    private String creationUser;
    
    @SerializedName("creationDate")
    private String creationDate;
    
    @SerializedName("statusId")
    private int statusId;
    */
    //@SerializedName("systemOptionId")
    //private SystemOption systemOptionId;
        
    
    public Profile() {
    }

    public Profile(Integer id) {
        this.id = id;
    }

    public Profile(Integer id, String name, String description, String creationUser, String creationDate, int statusId) {
        this.id = id;
        this.name = name;
        //this.description = description;
        //this.creationUser = creationUser;
        //this.creationDate = creationDate;
        //this.statusId = statusId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
   /*
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreationUser() {
        return creationUser;
    }

    public void setCreationUser(String creationUser) {
        this.creationUser = creationUser;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }
*/
    /*public SystemOption getSystemOptionId() {
        return systemOptionId;
    }

    public void setSystemOptionId(SystemOption systemOptionId) {
        this.systemOptionId = systemOptionId;
    }    
    */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Profile)) {
            return false;
        }
        Profile other = (Profile) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.exception.magicsnumbersws.entities.Profile[ id=" + id + " ]";
    }
    
}