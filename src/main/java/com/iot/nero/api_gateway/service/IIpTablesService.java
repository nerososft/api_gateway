package com.iot.nero.api_gateway.service;

import java.io.IOException;
import java.util.List;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/9/5
 * Time   下午2:54
 */
public interface IIpTablesService {

    boolean setIpTableStatus(Boolean isOpen) throws IOException;

    List<String> getIP() throws IOException;

    boolean addIP(String ip) throws IOException;

    boolean delIP(String ip) throws IOException;

    boolean lookStatus() throws IOException;

}
