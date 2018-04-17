package com.iot.nero.api_gateway.service.impl;
import com.iot.nero.api_gateway.common.ConfigUtil;
import com.iot.nero.api_gateway.core.core.ApiGatewayHandler;
import com.iot.nero.api_gateway.core.core.ApiMapping;
import com.iot.nero.api_gateway.core.firewall.IpCache;
import com.iot.nero.api_gateway.service.IIpTablesService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/9/5
 * Time   下午2:56
 */
public class IpTablesService implements IIpTablesService {
    Map<String, String> configMap;


    @ApiMapping("sys.ipTables.status.set")
    public boolean setIpTableStatus(Boolean isOpen) throws IOException {
        String status;
        if(isOpen==true) status="yes";
        else status="no";
        configMap = ConfigUtil.configToMap();
        configMap.replace("ipTable.isOpen ", configMap.get("ipTable.isOpen "),status);
        ApiGatewayHandler.setIpTableOpen(status);
        return ConfigUtil.mapToConfig(configMap);
    }

    @ApiMapping("sys.ipTables.list")
    public List<String> getIP() throws IOException {
        return new ArrayList<String>(IpCache.getIPSet());
    }

    @ApiMapping("sys.ipTables.add")
    public boolean addIP(String ip) throws IOException{
        ip=ip.trim();
        if(isIP(ip)){
            IpCache ipCache = new IpCache();
            return ipCache.createBlankIP(ip);
        }else{
            return false;
        }

    }

    @ApiMapping("sys.ipTables.del")
    public boolean delIP(String ip) throws IOException {
        if(isIP(ip.trim())){
            IpCache ipCache = new IpCache();
            return ipCache.deleteIP(ip.trim());
        }else{
            return false;
        }

    }
    @ApiMapping("sys.ipTables.status.get")
    public boolean lookStatus() throws IOException{
        configMap = ConfigUtil.configToMap();
        String status=configMap.get("ipTable.isOpen ");
        if("yes".equals(status.trim())) {return true;}
        else if("no".equals(status.trim())){return false;}
        else {return true;}
    }

    private boolean isIP(String addr)
    {
        if(addr.length() < 7 || addr.length() > 15 || "".equals(addr))
        {
            return false;
        }
        /**
         * 判断IP格式和范围
         */
        String rexp = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
        Pattern pat = Pattern.compile(rexp);
        Matcher mat = pat.matcher(addr);
        boolean ipAddress = mat.find();
        return ipAddress;
    }
}
