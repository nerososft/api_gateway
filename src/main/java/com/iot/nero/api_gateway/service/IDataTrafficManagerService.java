package com.iot.nero.api_gateway.service;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/9/5
 * Time   下午3:04
 */
public interface IDataTrafficManagerService {

    Boolean setTrafficManagerStatus(Boolean isOpen) throws IOException;

    Boolean setMaxTraffic(Integer maxPool) throws IOException;

    Boolean setAvgTraffic(Integer avgPool) throws IOException;

    Boolean getTrafficManagerStatus() throws IOException;

    String getMaxTraffic() throws IOException;

    String getAvgTraffic() throws IOException;

}
