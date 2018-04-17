package com.iot.nero.api_gateway.core.mock.Entity;

import java.io.Serializable;

public class ApiMock implements Serializable{
    private String ApiName;
    private String ApiReturn;

    public ApiMock(){

    }

    public ApiMock(String apiName, String apiReturn) {
        ApiName = apiName;
        ApiReturn = apiReturn;
    }

    public String getApiName() {
        return ApiName;
    }

    public void setApiName(String apiName) {
        ApiName = apiName;
    }

    public String getApiReturn() {
        return ApiReturn;
    }

    public void setApiReturn(String apiReturn) {
        ApiReturn = apiReturn;
    }

    @Override
    public String toString() {
        return "ApiMock{" +
                "ApiName='" + ApiName + '\'' +
                ", ApiReturn='" + ApiReturn + '\'' +
                '}';
    }
}
