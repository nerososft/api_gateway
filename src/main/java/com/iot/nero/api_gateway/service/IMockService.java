package com.iot.nero.api_gateway.service;

import com.iot.nero.api_gateway.core.mock.Entity.ApiMock;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Author 何少帅
 * Email  15107558620@163.com.com
 * Date   2017/9/5
 * Time   下午1:09
 */
public interface IMockService {

    Boolean setMockStatus(Boolean isOpen) throws IOException;

    Boolean getMockStatus() throws IOException;

    List<ApiMock> getMocks() throws IOException;

    Boolean addMock(String apiName, String apiReturn) throws  IOException;

    Boolean delMock(String mockName) throws IOException;

}
