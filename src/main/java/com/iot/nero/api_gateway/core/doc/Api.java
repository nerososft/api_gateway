package com.iot.nero.api_gateway.core.doc;

import java.io.Serializable;
import java.util.Arrays;

public class Api implements Serializable {

    private String apiName;
    private String returnType;
    private String[] params;
    private String[] paramsType;
    private String targetMethod;


    public Api(String apiName, String returnType, String[] params, String[] paramsType, String targetMethod) {
        this.apiName = apiName;
        this.returnType = returnType;
        this.params = params;
        this.paramsType = paramsType;
        this.targetMethod = targetMethod;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }

    public String[] getParamsType() {
        return paramsType;
    }

    public void setParamsType(String[] paramsType) {
        this.paramsType = paramsType;
    }

    public String getTargetMethod() {
        return targetMethod;
    }

    public void setTargetMethod(String targetMethod) {
        this.targetMethod = targetMethod;
    }

    @Override
    public String toString() {
        return "Api{" +
                "apiName='" + apiName + '\'' +
                ", returnType='" + returnType + '\'' +
                ", params=" + Arrays.toString(params) +
                ", paramsType=" + Arrays.toString(paramsType) +
                ", targetMethod='" + targetMethod + '\'' +
                '}';
    }
}
