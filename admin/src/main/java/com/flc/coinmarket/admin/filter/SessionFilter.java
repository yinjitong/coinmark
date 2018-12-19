package com.flc.coinmarket.admin.filter;

import com.alibaba.fastjson.JSONObject;
import com.flc.coinmarket.core.base.BaseResponse;
import com.flc.coinmarket.core.base.ResponseCode;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SessionFilter implements Filter {

    //标示符：表示当前用户未登录(可根据自己项目需要改为json样式)
    String NO_LOGIN = "您还未登录";

    //不需要登录就可以访问的路径(比如:注册登录等)
    String[] includeUrls = new String[]{"/admin/manage/sysuser/login","/swagger-ui.html","/admin/manage/sysuser/getSysTime"
    ,"/admin/manage/statistics/exportXls"};

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        String uri = request.getRequestURI();
        BaseResponse<Integer> baseResponse = new BaseResponse<>();
        response.setHeader("Content-Type", "application/json");
        response.setCharacterEncoding("UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();

        //是否需要过滤
        boolean needFilter = isNeedFilter(uri);


        if (!needFilter) { //不需要过滤直接传给下一个过滤器
            filterChain.doFilter(servletRequest, servletResponse);
        } else { //需要过滤器
            // session中包含user对象,则是登录状态
            if(session!=null&&session.getAttribute("sysUser") != null){
                // System.out.println("user:"+session.getAttribute("user"));
                filterChain.doFilter(request, response);
            }else{
                String requestType = request.getHeader("X-Requested-With");
                //判断是否是ajax请求
                if(requestType!=null && "XMLHttpRequest".equals(requestType)){
                    outputStream.write(this.NO_LOGIN.getBytes());
                }else{
                    //返回session失效验证码
                    baseResponse.setResponseMsg(ResponseCode.SESSION_TIME_OUT.getMessage());
                    baseResponse.setResponseCode(ResponseCode.SESSION_TIME_OUT.getCode());
                    String jsonString  = JSONObject.toJSONString(baseResponse);
                    outputStream.write(jsonString.getBytes());
//                    response.sendRedirect(request.getContextPath()+"/user/login.html");
                }
                outputStream.flush();
                outputStream.close();
                return;
            }
        }
    }

    /**
     * @Author: xxxxx
     * @Description: 是否需要过滤
     * @Date: 2018-03-12 13:20:54
     * @param uri
     */
    public boolean isNeedFilter(String uri) {

        for (String includeUrl : includeUrls) {
            if(includeUrl.equals(uri)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}