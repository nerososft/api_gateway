package com.iot.nero.api_gateway.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/8/30
 * Time   下午3:25
 */
public class NetUtil {


    private static Logger logger = LoggerFactory.getLogger(NetUtil.class);

    public final static String getRealIP(HttpServletRequest request) throws IOException {
            // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址

            String ip = request.getHeader("X-Forwarded-For");
            if (logger.isInfoEnabled()) {
                logger.info("getIpAddress(HttpServletRequest) - X-Forwarded-For - String ip=" + ip);
            }

            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("Proxy-Client-IP");
                    if (logger.isInfoEnabled()) {
                        logger.info("getIpAddress(HttpServletRequest) - Proxy-Client-IP - String ip=" + ip);
                    }
                }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("WL-Proxy-Client-IP");
                    if (logger.isInfoEnabled()) {
                        logger.info("getIpAddress(HttpServletRequest) - WL-Proxy-Client-IP - String ip=" + ip);
                    }
                }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("HTTP_CLIENT_IP");
                    if (logger.isInfoEnabled()) {
                        logger.info("getIpAddress(HttpServletRequest) - HTTP_CLIENT_IP - String ip=" + ip);
                    }
                }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("HTTP_X_FORWARDED_FOR");
                    if (logger.isInfoEnabled()) {
                        logger.info("getIpAddress(HttpServletRequest) - HTTP_X_FORWARDED_FOR - String ip=" + ip);
                    }
                }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getRemoteAddr();
                    if (logger.isInfoEnabled()) {
                        logger.info("getIpAddress(HttpServletRequest) - getRemoteAddr - String ip=" + ip);
                    }
                }
            } else if (ip.length() > 15) {
                String[] ips = ip.split(",");
                for (int index = 0; index < ips.length; index++) {
                    String strIp = (String) ips[index];
                    if (!("unknown".equalsIgnoreCase(strIp))) {
                        ip = strIp;
                        break;
                    }
                }
            }
            return ip;
    }
}
