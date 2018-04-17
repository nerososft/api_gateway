package com.iot.nero.api_gateway.core.doc;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.iot.nero.api_gateway.common.Debug;
import com.iot.nero.api_gateway.common.UtilJson;
import com.iot.nero.api_gateway.core.core.ApiStore;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/8/29
 * Time   下午6:29
 */
public class ApiDoc {


    public List<Api> getApis(List<ApiStore.ApiRunnable> apiRunnableList) {


            UtilJson.JSON_MAPPER.configure(
                    SerializationFeature.WRITE_NULL_MAP_VALUES,true);

            List<Api> apis = new ArrayList<Api>();

            for(ApiStore.ApiRunnable apiRunnable:apiRunnableList) {
                Class<?>[] parameter = apiRunnable.getParamType();
                List<String> parList = new ArrayList<String>();
                for (Class<?> par : parameter) {
                    parList.add(par.toString());
                }
                String[] papa = new String[parList.size()];
                String[] parName = new String[apiRunnable.getParamsName().size()];

                apis.add(new Api(apiRunnable.getApiName(),
                        apiRunnable.getReturnType().toString(),
                        apiRunnable.getParamsName().toArray(parName),
                        parList.toArray(papa),
                        apiRunnable.getTargetMethod().toString()
                ));
            }
            return apis;
    }
}
