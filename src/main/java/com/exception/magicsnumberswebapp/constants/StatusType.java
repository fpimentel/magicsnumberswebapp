/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exception.magicsnumberswebapp.constants;

/**
 *
 * @author Cristian
 */

public enum  StatusType {
    BASIC (1,"BASICOS"),
    TICKET (2,"TICKET");
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    StatusType(int id,String name)    {
        this.id=id;
        this.name = name;
    }
}
