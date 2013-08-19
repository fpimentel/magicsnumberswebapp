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
public class ProfileContainer {
    @SerializedName("profiles")
    private Profile users;

    public Profile getUsers() {
        return users;
    }

    public void setUsers(Profile users) {
        this.users = users;
    }
    
    
}
