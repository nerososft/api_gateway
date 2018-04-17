package com.iot.nero.api_gateway.demo.service;

import com.iot.nero.api_gateway.demo.dto.Hello;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/8/25
 * Time   下午7:43
 */
public interface IHelloWorld {

    Hello sayHello(String name,String msg);


    String niHao(String ni);

    Integer mockTest();
}
