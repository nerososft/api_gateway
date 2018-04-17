package com.iot.nero.api_gateway.core.core;

import com.alibaba.dubbo.common.json.ParseException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.iot.nero.api_gateway.common.Debug;
import com.iot.nero.api_gateway.common.IOUtils;
import com.iot.nero.api_gateway.common.NetUtil;
import com.iot.nero.api_gateway.common.UtilJson;
import com.iot.nero.api_gateway.core.CONSTANT;
import com.iot.nero.api_gateway.core.doc.ApiDoc;
import com.iot.nero.api_gateway.core.exceptions.*;
import com.iot.nero.api_gateway.core.firewall.entity.Admin;
import com.iot.nero.api_gateway.core.firewall.AdminAuth;
import com.iot.nero.api_gateway.core.firewall.IpTables;
import com.iot.nero.api_gateway.core.log.entity.ApiLog;
import com.iot.nero.api_gateway.core.log.entity.Log;
import com.iot.nero.api_gateway.core.mock.Entity.ApiMock;
import com.iot.nero.api_gateway.core.mock.Mock;
import com.iot.nero.api_gateway.core.trafficmanage.DataTrafficManage;
import com.iot.nero.utils.spring.PropertyPlaceholder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import sun.security.util.Length;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Author 和少帅
 * Email  15107558620@163.com.
 * Date   2017/9 1
 * Time   下午12:41
 */
public class ApiGatewayHandler implements InitializingBean, ApplicationContextAware {


    private static final Logger logger = LoggerFactory.getLogger(ApiGatewayHandler.class);

    private static final String METHOD = "method";
    private static final String PARAMS = "params";
    private static final String SYS_PARAMS = "sysParams";

    private static String TRAFFIC_OPEN = PropertyPlaceholder.getProperty("trafficManager.isOpen").toString();
    private static String TRAFFIC_AVG = PropertyPlaceholder.getProperty("trafficManager.avgFlow").toString();
    private static String TRAFFIC_MAX = PropertyPlaceholder.getProperty("trafficManager.maxPool").toString();
    private static String MOCK_OPEN = PropertyPlaceholder.getProperty("mock.isOpen").toString();
    private static String IP_TABLE_OPEN = PropertyPlaceholder.getProperty("ipTable.isOpen").toString();

    public static void setIpTableOpen(String ipTableOpen) {
        IP_TABLE_OPEN = ipTableOpen;
    }

    public static void setTrafficOpen(String trafficOpen) {
        TRAFFIC_OPEN = trafficOpen;
    }

    public static void setTrafficAvg(String trafficAvg) {
        TRAFFIC_AVG = trafficAvg;
    }

    public static void setTrafficMax(String trafficMax) {
        TRAFFIC_MAX = trafficMax;
    }

    public static void setMockOpen(String mockOpen) {
        MOCK_OPEN = mockOpen;
    }


    public static String getIpTableOpen() {
        return IP_TABLE_OPEN;
    }

    public static String getTrafficOpen() {
        return TRAFFIC_OPEN;
    }

    public static String getTrafficAvg() {
        return TRAFFIC_AVG;
    }

    public static String getTrafficMax() {
        return TRAFFIC_MAX;
    }

    public static String getMockOpen() {
        return MOCK_OPEN;
    }

    private ApiDoc apiDoc;
    private IpTables ipTables;
    private AdminAuth adminAuth;
    private static Boolean isMockInit = false;

    public static Boolean getIsMockInit() {
        return isMockInit;
    }

    public static void setIsMockInit(Boolean isMockInit) {
        ApiGatewayHandler.isMockInit = isMockInit;
    }

    ApiStore apiStore;
    final ParameterNameDiscoverer parameterNameDiscoverer;

    DataTrafficManage tokenBucket;

    public ApiGatewayHandler() {
        parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
        tokenBucket  = DataTrafficManage.newBuilder().avgFlowRate(Integer.valueOf(TRAFFIC_AVG)).maxFlowRate(Integer.valueOf(TRAFFIC_MAX)).build();
        apiDoc = new ApiDoc();
        ipTables = new IpTables();
        adminAuth = new AdminAuth();
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        apiStore = new ApiStore(applicationContext, parameterNameDiscoverer);
    }
/*
异常处理
 */
    public void afterPropertiesSet() throws Exception {
        apiStore.loadApiFromSpringBeans();
    }

    public void handle(HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new Gson();
        Date date = new Date();
        String method = request.getParameter(METHOD);
        String params = request.getParameter(PARAMS);
        String sysParams = request.getParameter(SYS_PARAMS);

        Object result;
        ApiStore.ApiRunnable apiRunnable = null;
        try {

            if ("yes".equals(TRAFFIC_OPEN)) {
                boolean tokens = tokenBucket.getTokens("a".getBytes());
                if (!tokens) {
                    throw new FlowOverException(CONSTANT.FLOW_OVER);
                }
            }
            logger.info(gson.toJson(new Log(0001, new ApiLog(NetUtil.getRealIP(request), method, params, sysParams, request.getRequestURL().toString()), date.getTime())));

            if ("yes".equals(IP_TABLE_OPEN)) {
                ipTables.filter(request, response);
            }

            paramsValdate(request);
            if(!isMockInit){
                Mock.init();
            }
            /*
            循环并处理异常
             */
            if ("sys".equals(method.subSequence(0, 3))) {
                sysParamsValid(request);
                adminAuth.auth(sysParams);
                if ("sys.doc".equals(method)) {
                    result = apiDoc.getApis(apiStore.findApiRunnables());
                } else {
                    apiRunnable = sysParamsValdate(request);
                    Object[] args = buildParams(apiRunnable, params, request, response);
                    result = apiRunnable.run(args);
                }

            } else {
                apiRunnable = sysParamsValdate(request);

                if ("yes".equals(MOCK_OPEN)) {
                    result = Mock.run(apiRunnable);
                } else {
                    Object[] args = buildParams(apiRunnable, params, request, response);
                    result = apiRunnable.run(args);
                }
            }
        } catch (ApiException e) {
            response.setStatus(500);
            result = handleErr(e.fillInStackTrace());
        } catch (IllegalAccessException e) {
            response.setStatus(500);
            result = handleErr(e.fillInStackTrace());
        } catch (InvocationTargetException e) {
            response.setStatus(500);
            result = handleErr(e.getTargetException());
        } catch (AuthFailedException e) {
            response.setStatus(500);
            result = handleErr(e.fillInStackTrace());
        } catch (IOException e) {
            response.setStatus(500);
            result = handleErr(e.fillInStackTrace());
        } catch (IPNotAccessException e) {
            response.setStatus(500);
            result = handleErr(e.fillInStackTrace());
        } catch (MockApiNotFoundException e) {
            response.setStatus(500);
            result = handleErr(e.fillInStackTrace());
        } catch (JsonSyntaxException e) {
            response.setStatus(500);
            result = handleErr(e.fillInStackTrace());
        } catch (FlowOverException e) {
            response.setStatus(500);
            result = handleErr(e.fillInStackTrace());
        } catch (InstantiationException e) {
            response.setStatus(500);
            result = handleErr(e.fillInStackTrace());
        }

        returnResult(result, response);
    }

    private void sysParamsValid(HttpServletRequest request) throws ApiException {
        String sysParams = request.getParameter(SYS_PARAMS);
        if (sysParams == null || "".equals(sysParams)) {
            throw new ApiException("调用失败，参数 " + SYS_PARAMS + " 为空");
        }
    }

    private Object handleErr(Throwable e) {
        String code = "";
        String message = "";
        if (e instanceof ApiException) {
            code = "0001";
            message = e.getMessage();
        } else if (e instanceof FlowOverException) {
            code = "0002";
            message = e.getMessage();
            e.printStackTrace();
        } else if (e instanceof IPNotAccessException) {
            code = "0003";
            message = e.getMessage();
            e.printStackTrace();
        } else {
            code = "0004";
            message = e.getMessage();
            e.printStackTrace();
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("error", code);
        result.put("msg", message);
        //ByteArrayOutputStream out = new ByteArrayOutputStream();
        //PrintStream stream = new PrintStream(out);
        //e.printStackTrace(stream);
        //result.put("track", e.getStackTrace());

        return result;
    }


    private void paramsValdate(HttpServletRequest request) throws ApiException {
        String apiName = request.getParameter(METHOD);
        String json = request.getParameter(PARAMS);

        if (apiName == null || "".equals(apiName)) {
            throw new ApiException("调用失败，参数 'method' 为空");
        } else if (json == null || "".equals(json)) {
            throw new ApiException("调用失败，" +
                    "参数 'params' 为空");
        }
    }

    private ApiStore.ApiRunnable sysParamsValdate(HttpServletRequest request) throws ApiException {
        String apiName = request.getParameter(METHOD);
        ApiStore.ApiRunnable apiRunnable;
        paramsValdate(request);
        if ((apiRunnable = apiStore.findApiRunnable(apiName)) == null) {
            throw new ApiException("调用失败：指定API不存在，API：" + apiName);
        }
        return apiRunnable;
    }

/*
调用函数
 */
    private Object[] buildParams(ApiStore.ApiRunnable apiRunnable, String params, HttpServletRequest request, HttpServletResponse response) throws ApiException {
        Map<String, Object> map = null;
        try {
            map = UtilJson.toMap(params);

        } catch (IllegalArgumentException e) {
            throw new ApiException("调用失败：JSON字符串格式化失败，请检查params参数");
        } catch (ParseException e) {
            throw new ApiException("调用失败：JSON字符串toMap失败，请检查params参数");
        }
        logger.error("________________MAP : " + map.toString());
        if (map == null) {
            map = new HashMap<String, Object>();
        }
        Method method = apiRunnable.getTargetMethod();
        List<String> paramsNames = Arrays.asList(parameterNameDiscoverer.getParameterNames(method));
        logger.error("_______________NAMES : " + paramsNames.toString());
        Class<?>[] paramType = method.getParameterTypes();

        for (Map.Entry<String, Object> m : map.entrySet()) {
            if (!paramsNames.contains(m.getKey())) {
                throw new ApiException("调用失败，接口 '" + apiRunnable.getApiName() + "' 对应方法 '" + method.getName() + "' 不存在 '" + m.getKey() + "' 参数");
            }
        }

        Object[] args = new Object[paramType.length];
        for (int i = 0; i < paramType.length; i++) {
            if (paramType[i].isAssignableFrom(HttpServletRequest.class)) {
                args[i] = request;
            } else if (map.containsKey(paramsNames.get(i))) {
                try {
                    args[i] = convertJsonToBean(map.get(paramsNames.get(i)), paramType[i]);
                } catch (Exception e) {
                    throw new ApiException("调用失败：指定参数格式错误或值错误 '" + paramsNames.get(i) + "' :" + e.getMessage());
                }
            } else {
                args[i] = null;
            }
        }
        logger.error("__________ARGS : " + args.toString());
        return args;
    }

    private <T> Object convertJsonToBean(Object val, Class<?> targetClass) {
        Object result = null;
        if (val == null) {
            return null;
        } else if (Integer.class.equals(targetClass)) {
            result = Integer.parseInt(val.toString());
        } else if (Long.class.equals(targetClass)) {
            result = Long.parseLong(val.toString());
        } else if (Date.class.equals(targetClass)) {
            if (val.toString().matches("[0-9]+")) {
                result = new Date(Long.parseLong(val.toString()));
            } else {
                throw new IllegalArgumentException("日期必须是长整型的时间戳");
            }
        } else if (String.class.equals(targetClass)) {
            if (val instanceof String) {
                result = val;
            } else {
                throw new IllegalArgumentException("转换目标类型为字符串");
            }
        } else {
            result = UtilJson.convertValue(val, targetClass);
        }
        return result;
    }


    private void returnResult(Object result, HttpServletResponse response) {
        try {
            UtilJson.JSON_MAPPER.configure(
                    SerializationFeature.WRITE_NULL_MAP_VALUES, true);


            Map<String, Object> returnResult = new HashMap<String, Object>();
            returnResult.put("code", 1000);
            returnResult.put("data", result);

            String json = UtilJson.writeValueAsString(returnResult);

            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html/json;charset=utf-8");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
            if (json != null) {
                response.getWriter().write(json);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
