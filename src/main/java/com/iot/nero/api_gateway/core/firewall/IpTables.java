package com.iot.nero.api_gateway.core.firewall;
import com.iot.nero.api_gateway.common.NetUtil;
import com.iot.nero.api_gateway.core.exceptions.IPNotAccessException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static com.iot.nero.api_gateway.core.CONSTANT.IP_NOT_ACCESS;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/8/29
 * Time   下午6:52
 */
public class IpTables {

    public IpTables() {

    }

    private IpCache ipCache;

    public void filter(HttpServletRequest request, HttpServletResponse response) throws IOException, IPNotAccessException {

            String ip = NetUtil.getRealIP(request);
            ipCache =new IpCache();
            if(IpCache.flag==true) {
                ipCache.cacheSet();
                IpCache.flag=false;
            }
            //查黑名单缓存
            if(ipCache.findIP(ip)!=null){
                //有，拒绝
                throw new IPNotAccessException(IP_NOT_ACCESS);
            }

            //没有，过

    }
}
