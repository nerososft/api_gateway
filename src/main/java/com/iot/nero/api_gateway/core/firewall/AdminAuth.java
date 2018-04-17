package com.iot.nero.api_gateway.core.firewall;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.ParseException;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.iot.nero.api_gateway.core.CONSTANT;
import com.iot.nero.api_gateway.core.exceptions.AuthFailedException;
import com.iot.nero.api_gateway.core.firewall.entity.Admin;
import com.iot.nero.utils.spring.PropertyPlaceholder;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/8/29
 * Time   下午6:57
 */
public class AdminAuth {



    public void auth(String params) throws JsonSyntaxException, AuthFailedException {

        //加载配置文件中用户名和密码
        Gson gson = new Gson();
        try {
            Admin ad = gson.fromJson(params, Admin.class);

            //认证
            if (!PropertyPlaceholder.getProperty("auth.username").toString().equals(ad.getUserName())) {
                throw new AuthFailedException(CONSTANT.ADMIN_NOT_EXISTS);
            }
            if (!PropertyPlaceholder.getProperty("auth.username").toString().equals(ad.getPassWord())) {
                throw new AuthFailedException(CONSTANT.ADMIN_PASSWORD_INCORRECT);
            }
        }catch (JsonSyntaxException e){
            throw e;
        }
    }
}
