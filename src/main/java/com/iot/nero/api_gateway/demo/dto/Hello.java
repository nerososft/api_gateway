package com.iot.nero.api_gateway.demo.dto;

import java.io.Serializable;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/8/25
 * Time   下午7:44
 */
public class Hello implements Serializable {
    private String name;
    private String what;
    private Integer count;

    public Hello(String name, String what, Integer count) {
        this.name = name;
        this.what = what;
        this.count = count;
    }

    public Hello() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Hello{" +
                "name='" + name + '\'' +
                ", what='" + what + '\'' +
                ", count=" + count +
                '}';
    }
}
