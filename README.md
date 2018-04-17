# api_gateway
### 管理员用户名密码配置
#### AuthConfig.xml
```
<?xml version="1.0" encoding="UTF-8"?>
<auth>
    <username>root</username>
    <password>root</password>
</auth>
```
### API网关配置
#### config.properties
```
auth.username = root
auth.password = root

mock.isOpen = no
mock.file =  /api_gateway/config/mock_api.txt

ipTable.isOpen = yes
ipTable.file =  /api_gateway/config/ip_tables.txt

trafficManager.isOpen = yes
trafficManager.maxPool = 1024
trafficManager.avgFlow = 10

crosFilter.file = /api_gateway/config/cros_filter.txt

```
### 跨域配置
#### cros_filter.txt
```
本地测试:localhost
```
### 黑名单配置
#### ip_tables.txt
```
202.*.*.1;202.34.*.23;
```
### 日志配置
#### log4f.properties
```
log4j.rootLogger=INFO,console

# for package com.demo.kafka, log would be sent to kafka appender.
log4j.logger.com.demo.kafka=DEBUG,kafka

# appender kafka
log4j.appender.kafka=kafka.producer.KafkaLog4jAppender
log4j.appender.kafka.topic=log
# multiple brokers are separated by comma ",".
log4j.appender.kafka.brokerList=47.94.251.146:9092
log4j.appender.kafka.compressionType=none
log4j.appender.kafka.syncSend=true
log4j.appender.kafka.layout=org.apache.log4j.PatternLayout
log4j.appender.kafka.layout.ConversionPattern=%d [%-5p] [%t] - [%l] %m%n

# appender console
log4j.appender.console=org.apache.log4j.ConsoleAppender
log4j.appender.console.target=System.out
log4j.appender.console.layout=org.apache.log4j.PatternLayout
log4j.appender.console.layout.ConversionPattern=%d [%-5p] [%t] - [%l] %m%n
```
### mock 配置
#### mock_api.txt
```
api.hello#{User:{name:"Bob",msg:"nihao"}}
api.ni#{User:{name:"Bob",msg:"nihao"}}
api.hehe#{User:{name:"Bob",msg:"nihao"}}
api.he#{User:{name:"Bob",msg:"nihao"}}
api.lo#{User:{name:"Bob",msg:"nihao"}}
```
## UI
### api_gateway_console[api_gateway_console](https://github.com/nerososft/api_gateway_console)
