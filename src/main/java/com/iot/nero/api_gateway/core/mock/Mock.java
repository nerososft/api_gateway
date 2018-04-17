package com.iot.nero.api_gateway.core.mock;

import com.iot.nero.api_gateway.common.ConfigUtil;
import com.iot.nero.api_gateway.common.IOUtils;
import com.iot.nero.api_gateway.core.core.ApiGatewayHandler;
import com.iot.nero.api_gateway.core.core.ApiMapping;
import com.iot.nero.api_gateway.core.core.ApiStore;
import com.iot.nero.api_gateway.core.exceptions.MockApiNotFoundException;
import com.iot.nero.api_gateway.core.mock.Entity.ApiMock;
import com.iot.nero.utils.spring.PropertyPlaceholder;
import javafx.beans.property.Property;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;
/*定义mock类

 */
public class Mock {

    private static Map<String,ApiMock> apiMockCache = new HashMap<String,ApiMock>();
    private static final String MOCK_FILR_DIR = PropertyPlaceholder.getProperty("mock.file").toString();


    public Mock() throws IOException {

    }


    public static void init() throws IOException {

        apiMockCache = ConfigUtil.apiMocksToMap();
        ApiGatewayHandler.setIsMockInit(true);

        }


    public static Object run(ApiStore.ApiRunnable apiRunnable) throws MockApiNotFoundException {
        Object result;
        Class<?> returnType = apiRunnable.getReturnType();
        if(returnType.getName().equals(String.class.getName())){
            result = new String("sdsdsdsd");
        }else if(returnType.getName().equals((Integer.class.getName()))){
            result = new Integer(23333);
        }else if(returnType.getName().equals(Double.class.getName())){
            result = new Double(2.33);
        }else if(returnType.getName().equals(Boolean.class.getName())){
            result = new Boolean(false);
        }else if(returnType.getName().equals(Long.class.getName())){
            result = new Long(23333333);
        }else if(returnType.getName().equals(Character.class.getName())){
            result = 'd';
        }else if(returnType.getName().equals(Float.class.getName())) {
            result = new Float(2.33);
        }else {
            //如果是其他类型，请自行构造


            //查缓存
            if((result = apiMockCache.get(apiRunnable.getApiName()))!=null){
                return result;
            }else{
                    throw new MockApiNotFoundException("API: "+ apiRunnable.getApiName() +"未找到，请在"+PropertyPlaceholder.getProperty("mock.file").toString()+"中添加！");
                }
            }
        return result;
    }

    public static Map<String,ApiMock> getMocks() {
        return apiMockCache;
    }

    public static List<ApiMock> getMockList() {

        List<ApiMock> apiMocks = new ArrayList<>();

        for (ApiMock apiMock : apiMockCache.values()) {
            apiMocks.add(apiMock);
        }

        return apiMocks;
    }
}