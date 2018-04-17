package com.iot.nero.api_gateway.service.impl;

import com.alibaba.dubbo.common.json.JSONArray;
import com.alibaba.dubbo.common.json.JSONObject;
import com.iot.nero.api_gateway.core.core.ApiMapping;
import com.iot.nero.api_gateway.core.firewall.entity.Origin;
import com.iot.nero.api_gateway.core.firewall.filter.CORSFilter;
import com.iot.nero.api_gateway.service.IOriginFilterService;
import com.iot.nero.utils.spring.PropertyPlaceholder;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import java.io.*;
import java.util.*;
import javax.servlet.ServletContext;
/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/9/5
 * Time   下午4:11
 */
public class OriginService implements IOriginFilterService {
    private static final String CROS_FILE_DIR = PropertyPlaceholder.getProperty("crosFilter.file").toString();

    private WebApplicationContext webApplicationContext;
    private ServletContext servletContext;
    private String savePath;
    private String encoding = "utf-8";
    @ApiMapping("sys.origin.add")
    public boolean addOrigin(String name,String origin) throws IOException {
        webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        servletContext = webApplicationContext.getServletContext();
        savePath = servletContext.getRealPath("/WEB-INF/classes"+CROS_FILE_DIR);
        Map<String ,String >crosMap = new HashMap<String,String>();
        File f = new File(savePath);

            String line;

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                        new FileInputStream(f), encoding));
                //判断配置文件中是否已存在信任站点
                while ((line = bufferedReader.readLine())!=null){
                    String key = line.split(":")[0];
                    String value = line.split(":")[1];
                    if (origin.equals(value)){
                        return false;
                    }
                    if (name.equals(key)){
                        name = name+3;
                    }
                    crosMap.put(key,value);
                }
                crosMap.put(name,origin);
                Set set = crosMap.entrySet();
                Iterator iterator = set.iterator();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f),encoding));
                while (iterator.hasNext()){
                    Map.Entry entry = (Map.Entry)iterator.next();
                    String add = entry.getKey().toString()+":"+entry.getValue().toString();
                    bufferedWriter.write(add);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();

                }
                //若配置文件中不存在则写入配置文件
                bufferedWriter.close();
                CORSFilter.loadOriginMap();
                return true;
    }

    @ApiMapping("sys.origin.del")
    public boolean delOrigin(String name,String origin) throws IOException {
        webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        servletContext = webApplicationContext.getServletContext();
        savePath = servletContext.getRealPath("/WEB-INF/classes"+CROS_FILE_DIR);
        File f = new File(savePath);
        Map<String ,String >crosMap = new HashMap<String,String>();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(f), encoding));
            String line;
            //将从配置文件中读取的不删除的信息加入map中
            while ((line=bufferedReader.readLine())!=null){
                String key = line.split(":")[0];
                String value = line.split(":")[1];
                if (!(key.equals(name)&&value.equals(origin))){
                    crosMap.put(key,value);
                }
            }
            Set set = crosMap.entrySet();
            Iterator iterator = set.iterator();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f),encoding));
            while (iterator.hasNext()){
                Map.Entry entry = (Map.Entry)iterator.next();
                String add = entry.getKey().toString()+":"+entry.getValue().toString();
                bufferedWriter.write(add);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
            bufferedReader.close();
            CORSFilter.loadOriginMap();
            return true;
    }


    @ApiMapping("sys.origin.list")

    public List<Origin> getOrigin() throws IOException {
       webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        servletContext = webApplicationContext.getServletContext();
        savePath = servletContext.getRealPath("/WEB-INF/classes"+CROS_FILE_DIR);
        File f = new File(savePath);

            // 以utf-8的方式打开文件
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(f), encoding
            ));

            List<Origin> list = new ArrayList<Origin>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String key = line.split(":")[0];
                String value = line.split(":")[1];
                list.add(new Origin(key, value));
            }
            return list;
    }
}
