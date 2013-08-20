/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exception.magicsnumberswebapp.entities;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author fpimentel
 */
public class UserContainer {
    @SerializedName("User")
    private User users;        
        
    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
    }                    
}
