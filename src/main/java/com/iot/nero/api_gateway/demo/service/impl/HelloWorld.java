package com.iot.nero.api_gateway.demo.service.impl;

import com.iot.nero.api_gateway.core.core.ApiMapping;
import com.iot.nero.api_gateway.demo.dto.Hello;
import com.iot.nero.api_gateway.demo.service.IHelloWorld;
import org.springframework.stereotype.Service;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/8/25
 * Time   下午7:44
 */
@Service
public class HelloWorld implements IHelloWorld {

    @ApiMapping("api.hello")
    public Hello sayHello(String name,String msg) {
        return new Hello(name,msg,1);
    }

    @ApiMapping("api.ni")
    public String niHao(String ni) {
        return "";
    }

    @ApiMapping("api.mockTest")
    public Integer mockTest(){
        return 0;
    }

}
