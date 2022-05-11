package com.millenium.falcon.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

import java.util.Objects;

@Document
public class Target {

    @Version
    private long version;

    @Id
    private String name;

    @Field
    private int CordX;

    @Field
    private int CordY;

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCordX() {
        return CordX;
    }

    public void setCordX(int cordX) {
        this.CordX = cordX;
    }

    public int getCordY() {
        return CordY;
    }

    public void setCordY(int cordY) {
        this.CordY = cordY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Target target = (Target) o;
        return version == target.version &&
                CordX == target.CordX &&
                CordY == target.CordY &&
                name.equals(target.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, name, CordX, CordY);
    }
}
