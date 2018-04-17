package com.iot.nero.api_gateway.common;

import com.iot.nero.api_gateway.core.mock.Entity.ApiMock;
import com.iot.nero.api_gateway.core.mock.Mock;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.HashMap;
import java.util.Map;



public class ConfigUtil {

    private static WebApplicationContext webApplicationContext;
    private static ServletContext servletContext;
    private static String savePath;

    public static Map<String, String> configToMap() throws IOException {

        webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        servletContext = webApplicationContext.getServletContext();
        savePath = servletContext.getRealPath("/WEB-INF/classes/api_gateway/config/config.properties");

        Map<String, String> configMap = new HashMap<String, String>();
        InputStream inputStream = new FileInputStream(savePath);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line;
        String[] lines;
        while((line = bufferedReader.readLine()) != null){

            if("".equals(line))continue;
            if(line.startsWith("#"))continue;

            line = line.trim();
            lines = line.split("=");
            configMap.put(lines[0], lines[1]);
        }
        inputStream.close();
        inputStreamReader.close();
        bufferedReader.close();
    return configMap;
    }

    public static Boolean mapToConfig(Map<String, String> configMap) throws IOException {

        webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        servletContext = webApplicationContext.getServletContext();
        savePath = servletContext.getRealPath("/WEB-INF/classes/api_gateway/config/config.properties");

        OutputStream outputStream = new FileOutputStream(savePath);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

        String line;
        for(Map.Entry<String, String> m : configMap.entrySet()){
            line = m.getKey()+"="+m.getValue()+"\n";
            line = line.trim();
            bufferedWriter.newLine();
            bufferedWriter.write(line);
            bufferedWriter.flush();
        }

        outputStream.close();
        outputStreamWriter.close();
        bufferedWriter.close();

        return true;
    }

    public static Map<String, String> logToMap() throws IOException {

        webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        servletContext = webApplicationContext.getServletContext();
        savePath = servletContext.getRealPath("/WEB-INF/classes/api_gateway/config/log4j.properties");

        Map<String, String> configMap = new HashMap<String, String>();
        InputStream inputStream = new FileInputStream(savePath);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line;
        String[] lines;
        while((line = bufferedReader.readLine()) != null){

            if("".equals(line))continue;
            if(line.startsWith("#"))continue;

            line = line.trim();
            lines = line.split("=");
            configMap.put(lines[0], lines[1]);
        }
        inputStream.close();
        inputStreamReader.close();
        bufferedReader.close();
        return configMap;
    }

    public static Boolean mapToLog(Map<String, String> configMap) throws IOException {

        webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        servletContext = webApplicationContext.getServletContext();
        savePath = servletContext.getRealPath("/WEB-INF/classes/api_gateway/config/log4j.properties");

        OutputStream outputStream = new FileOutputStream(savePath);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

        String line;
        for(Map.Entry<String, String> m : configMap.entrySet()){
            line = m.getKey()+"="+m.getValue()+"\n";
            line = line.trim();
            bufferedWriter.newLine();
            bufferedWriter.write(line);
            bufferedWriter.flush();
        }

        outputStream.close();
        outputStreamWriter.close();
        bufferedWriter.close();

        return true;
    }

    public static Map<String, ApiMock> apiMocksToMap() throws IOException {

        webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        servletContext = webApplicationContext.getServletContext();
        savePath = servletContext.getRealPath("/WEB-INF/classes/api_gateway/config/mock_api.txt");

        Map<String, ApiMock> apiMocks = new HashMap<String, ApiMock>();
        InputStream inputStream = new FileInputStream(savePath);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line;
        String[] lines;
        while((line = bufferedReader.readLine()) != null){

            lines = line.split("#");
            if (apiMocks.get(lines[0]) == null) {
                apiMocks.put(lines[0], new ApiMock(lines[0], lines[1]));
            }
        }
        inputStream.close();
        inputStreamReader.close();
        bufferedReader.close();
        return apiMocks;
    }

    public static Boolean mapToApiMocks(Map<String,ApiMock> apiMocks) throws IOException {
        webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        servletContext = webApplicationContext.getServletContext();
        savePath = servletContext.getRealPath("/WEB-INF/classes/api_gateway/config/mock_api.txt");

        OutputStream outputStream = new FileOutputStream(savePath);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

        for(Map.Entry<String, ApiMock> entry : apiMocks.entrySet()){
            bufferedWriter.write(entry.getKey()+"#"+entry.getValue().getApiReturn()+"\n");
            bufferedWriter.flush();
        }

        bufferedWriter.close();
        outputStreamWriter.close();
        outputStream.close();

        return true;
    }

}
