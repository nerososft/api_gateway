package com.iot.nero.api_gateway.service.impl;

import com.iot.nero.api_gateway.common.ConfigUtil;
import com.iot.nero.api_gateway.core.core.ApiMapping;
import com.iot.nero.api_gateway.core.mock.Entity.ApiMock;
import com.iot.nero.api_gateway.core.mock.Mock;
import com.iot.nero.api_gateway.service.ILogKafkaService;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class LogKafkaService implements ILogKafkaService{

    private WebApplicationContext webApplicationContext;
    private ServletContext servletContext;
    private String savePath;
    Map<String,String> logMap;

    @ApiMapping("sys.log.kafka.broker.set")
    public Boolean setLogKafkaBrokerList(String ip) throws IOException {

        logMap = ConfigUtil.logToMap();
        logMap.replace("log4j.appender.kafka.brokerList", logMap.get("log4j.appender.kafka.brokerList"),ip);

        return ConfigUtil.mapToLog(logMap);
    }

    @ApiMapping("sys.log.kafka.broker.get")
    public String getLogKafkaBrokerList() throws IOException {

        logMap = ConfigUtil.logToMap();

        return logMap.get("log4j.appender.kafka.brokerList");
    }

    @ApiMapping("sys.log.kafka.broker.topic.set")
    public Boolean setLogKafkaBrokerTopic(String topic) throws IOException {
        logMap = ConfigUtil.logToMap();
        logMap.replace("log4j.appender.kafka.topic", logMap.get("log4j.appender.kafka.topic"),topic);

        return ConfigUtil.mapToLog(logMap);
    }

    @ApiMapping("sys.log.kafka.broker.topic.get")
    public String setLogKafkaBrokerTopic() throws IOException {
        logMap = ConfigUtil.logToMap();
        return logMap.get("log4j.appender.kafka.topic");
    }

}



















