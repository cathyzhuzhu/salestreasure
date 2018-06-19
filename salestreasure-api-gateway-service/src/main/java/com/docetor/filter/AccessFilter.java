package com.docetor.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.docetor.commons.Response;
import com.docetor.utils.JWTUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@CrossOrigin
public class AccessFilter extends ZuulFilter  {

    private static Logger logger = LoggerFactory.getLogger(AccessFilter.class);

    @Autowired
    private JWTUtil  jWTUtil;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Autowired
    private DiscoveryClient client;
    @Autowired
    private  RestTemplate restTemplate;
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response=ctx.getResponse();
        response.setContentType("text/html;charset=utf-8");
        String  requestURI=request.getRequestURI();
        try {
           String token =  "";
            Response rs = new Response();
            logger.info("获取token："+token);
            if(requestURI.indexOf("login-service/user/login")>0
                    ||requestURI.indexOf("login-service/user/register")>0
                    ||requestURI.indexOf("login-service/user/iForgetPasswordMail")>0){
                logger.info("开始调用login-service服务");
                BufferedReader bufferedReader = (BufferedReader)request.getReader();
                String str = getBodyString(bufferedReader);
                logger.info("输入参数："+str);
                JSONObject jsonObject =  JSONObject.fromObject(str);
                String email = jsonObject.get("email").toString();
//                addLogInfo(email,request);
                logger.info("结束调用login-service服务，记录日志");
            }
            else if(requestURI.indexOf("login-service/user/resetPassword")>0){
                logger.info("开始调用重置密码服务");
            }
            else if(requestURI.indexOf("login-service/user/updatePassword")>0){
                logger.info("开始调用修改密码服务");
            }
            else if(requestURI.indexOf("commons-service")>0){
                logger.info("调用公共服务");
            } else{
                logger.info("开始调用应用服务");
                BufferedReader bufferedReader = (BufferedReader)request.getReader();
                String str = getBodyString(bufferedReader);
                logger.info("输入参数："+str);
                JSONObject jsonObject =  JSONObject.fromObject(str);

                if (jsonObject.get("token") == null) {
                    ctx.setSendZuulResponse(false);
                    ctx.setResponseStatusCode(401);
                    logger.info("获取token为空");
                    rs.setStatus("0");
                    rs.setMessage("获取token为空");
                    ctx.setResponseBody(JSONObject.fromObject(rs).toString());
                    return "token is empty";
                } else {
                    token = jsonObject.get("token").toString();
                    boolean ret = jWTUtil.verify(token);
                    if (ret) {
//                        String account =  jWTUtil.getUsername(token);
//                        addLogInfo(account,request);
                        logger.info("获取token验证通过");
//                    rs.setStatus("1");
//                    rs.setMessage("token is empty");
//                    ctx.setResponseBody(JSONObject.fromObject(rs).toString());
                        return "access token ok";
                    } else {
                        ctx.setSendZuulResponse(false);
                        ctx.setResponseStatusCode(401);
                        logger.info("获取token验证失败");
                        rs.setStatus("0");
                        rs.setMessage("获取token验证失败");
                        String retStr = JSONObject.fromObject(rs).toString();
                        ctx.setResponseBody(retStr);
                        return "access token error";
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }

    public String getBodyString(BufferedReader br) {
        String inputLine;
        String str = "";
        try {
            while ((inputLine = br.readLine()) != null) {
                str += inputLine;
            }
            br.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
        return str;
    }



}
