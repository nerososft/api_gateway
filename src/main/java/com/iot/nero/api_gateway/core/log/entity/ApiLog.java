package com.iot.nero.api_gateway.core.log.entity;

import java.io.Serializable;

public class ApiLog  implements Serializable{

    private String ip;
    private String method;
    private String params;
    private String sysParams;
    private String url;

    public ApiLog() {
    }

    public ApiLog(String ip, String method, String params, String sysParams, String url) {
        this.ip = ip;
        this.method = method;
        this.params = params;
        this.sysParams = sysParams;
        this.url = url;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getSysParams() {
        return sysParams;
    }

    public void setSysParams(String sysParams) {
        this.sysParams = sysParams;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ApiLog{" +
                "ip='" + ip + '\'' +
                ", method='" + method + '\'' +
                ", params='" + params + '\'' +
                ", sysParams='" + sysParams + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
