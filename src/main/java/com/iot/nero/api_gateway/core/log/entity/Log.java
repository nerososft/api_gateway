package com.iot.nero.api_gateway.core.log.entity;

import java.io.Serializable;

public class Log<T> implements Serializable {
    private  Integer type;
    private  T content;
    private Long createTime;

    public Log() {
    }

    public Log(Integer type ,T content, Long createTime) {
        this.type = type;
        this.content = content;
        this.createTime = createTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }


    @Override
    public String toString() {
        return "Log{" +
                "type=" + type +
                ", content=" + content +
                ", createTime=" + createTime +
                '}';
    }
}
