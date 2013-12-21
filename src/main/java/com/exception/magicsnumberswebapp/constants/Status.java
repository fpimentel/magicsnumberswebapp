
package com.exception.magicsnumberswebapp.constants;

/**
 *
 * @author fpimentel
 */
public enum Status {

    ACTIVE(1, "ACTIVO", 1),
    VENDIDO(3, "Vendido", 2),
    WIN(4, "Ganado", 2);
    private int id;
    private String name;
    private int statusTypeId;

    Status(int id, String name, int statusTypeId) {
        this.id = id;
        this.name = name;
        this.statusTypeId = statusTypeId;
    }

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

    public int getStatusTypeId() {
        return statusTypeId;
    }

    public void setStatusTypeId(int statusTypeId) {
        this.statusTypeId = statusTypeId;
    }
}
