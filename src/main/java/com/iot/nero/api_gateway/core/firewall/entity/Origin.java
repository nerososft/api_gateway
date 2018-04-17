package com.iot.nero.api_gateway.core.firewall.entity;

import java.io.Serializable;

/**
 * Created by wujindong on 2017/9/6.
 */
public class Origin implements Serializable{
    private String name;
    private String origin;

    public Origin(String name,String origin) {
        this.name = name;
        this.origin = origin;
    }

    public Origin() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }


    @Override
    public String toString() {
        return "Origin{" +
                "name='" + name + '\'' +
                ", origin='" + origin + '\'' +
                '}';
    }
}
