package com.epam.entity;

import java.io.Serializable;

/**
 *This class defines the role entity
 * and provides an interface for working with it
 */
public class Role implements Serializable {
    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "role{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
