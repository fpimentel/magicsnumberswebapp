/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exception.magicsnumberswebapp.entities;

import java.io.Serializable;

/**
 *
 * @author fpimentel
 */

public class SystemOption implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private String description;
    private int optionCategory;
    private int statusId;    

    public SystemOption() {
    }

    public SystemOption(Integer id) {
        this.id = id;
    }

    public SystemOption(Integer id, String name, int optionCategory, int statusId) {
        this.id = id;
        this.name = name;
        this.optionCategory = optionCategory;
        this.statusId = statusId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOptionCategory() {
        return optionCategory;
    }

    public void setOptionCategory(int optionCategory) {
        this.optionCategory = optionCategory;
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
        if (!(object instanceof SystemOption)) {
            return false;
        }
        SystemOption other = (SystemOption) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.exception.magicsnumbersws.entities.SystemOption[ id=" + id + " ]";
    }
    
}
