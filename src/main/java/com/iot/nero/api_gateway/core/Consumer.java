package com.iot.nero.api_gateway.core;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/9/6
 * Time   下午3:39
 */
public class Consumer {
    public static ClassPathXmlApplicationContext context = null;
    public static ClassPathXmlApplicationContext singleton() {
        if (context == null) {
            context = new ClassPathXmlApplicationContext(new String[] {"api_gateway/dubbo/consumer.xml"});
            context.start();
        }
        return context;
    }
}
