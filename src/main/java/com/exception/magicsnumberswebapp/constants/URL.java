package com.exception.magicsnumberswebapp.constants;

/**
 *
 * @author Cristian
 */

public enum  URL {
    MASTER_PAGE ("/faces/masterpage.xhtml"),
    LOGIN_PAGE("/faces/login.xhtml");
    
    private String path;
    
    URL(String path)    {        
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
}
