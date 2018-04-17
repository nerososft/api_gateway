package com.iot.nero.api_gateway.service;

import com.iot.nero.api_gateway.core.firewall.entity.Origin;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/9/5
 * Time   下午4:10
 */
public interface IOriginFilterService {


    boolean addOrigin(String name,String origin) throws IOException;

    boolean delOrigin(String name,String origin) throws IOException;

    List<Origin> getOrigin() throws IOException;
}
