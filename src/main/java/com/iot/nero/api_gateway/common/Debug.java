package com.iot.nero.api_gateway.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/8/30
 * Time   下午3:42
 */
public class Debug {



    public static void debug(Object obj, HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        if (obj != null) {
            try {
                response.getWriter().write("<p style='text-indent:10px;padding-top:10px;padding-bottom:10px;color:#fff;background:green;'><strong>DEBUG</strong> : "+obj.toString()+"</p>");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
