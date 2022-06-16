package com.millenium.falcon.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Target {

    @Id
    private String name;

    @Column(name = "cord_x")
    private int cordX;

    @Column(name = "cord_y")
    private int cordY;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCordX() {
        return cordX;
    }

    public void setCordX(int cordX) {
        this.cordX = cordX;
    }

    public int getCordY() {
        return cordY;
    }

    public void setCordY(int cordY) {
        this.cordY = cordY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Target target = (Target) o;
        return cordX == target.cordX &&
                cordY == target.cordY &&
                Objects.equals(name, target.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cordX, cordY);
    }
}
