package com.iot.nero.api_gateway.demo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/9/6
 * Time   下午4:21
 */
public class Provider {
    public static void main( String[] args )
    {
        new ClassPathXmlApplicationContext(new String[]{"api_gateway/dubbo/provider.xml", "api_gateway/config/spring.xml"});
        while (true) {
        }
    }
}
