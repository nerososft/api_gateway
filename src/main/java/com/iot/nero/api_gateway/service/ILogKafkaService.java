package com.iot.nero.api_gateway.service;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface ILogKafkaService {

    Boolean setLogKafkaBrokerList(String ip) throws IOException;

    String getLogKafkaBrokerList() throws IOException;

    Boolean setLogKafkaBrokerTopic(String topic) throws IOException;

    String setLogKafkaBrokerTopic() throws IOException;

}
