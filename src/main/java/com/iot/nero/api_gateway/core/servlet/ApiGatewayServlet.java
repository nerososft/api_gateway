package com.iot.nero.api_gateway.core.servlet;

import com.iot.nero.api_gateway.core.core.ApiGatewayHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/8/25
 * Time   下午12:40
 */
public class ApiGatewayServlet extends HttpServlet {
        private static  final long serialVersionUID = 1L;

        ApplicationContext applicationContext;
        private ApiGatewayHandler apiGatewayHand;

        @Override
        public void init() throws ServletException {
                super.init();
                applicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
                apiGatewayHand = applicationContext.getBean(ApiGatewayHandler.class);
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
               apiGatewayHand.handle(req, resp);
        }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                apiGatewayHand.handle(req, resp);
        }

}
