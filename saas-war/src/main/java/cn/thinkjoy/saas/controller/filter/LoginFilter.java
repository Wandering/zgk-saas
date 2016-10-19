package cn.thinkjoy.saas.controller.filter;

import cn.thinkjoy.saas.core.Constant;
import cn.thinkjoy.saas.enums.ErrorCode;
import cn.thinkjoy.saas.service.common.ExceptionUtil;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by yangguorong on 16/10/17.
 */
public class LoginFilter implements Filter {

    private static final Logger logger = Logger.getLogger(LoginFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 获得在下面代码中要用的request,response,session对象
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        HttpSession session = servletRequest.getSession();

        // 获得用户请求的URI
        String path = servletRequest.getRequestURI();

        logger.info("user request path : " + path);

         // 无需过滤的页面
        if(path.equals("/")){
            chain.doFilter(servletRequest, servletResponse);
            return;
        }

         for(int i=0;i<Constant.NoFilter_Pages.length;i++){
             if (path.indexOf(Constant.NoFilter_Pages[i]) != -1) {
                 chain.doFilter(servletRequest, servletResponse);
                 return;
             }
         }

        // 从session里取用户ID
        Object userId = session.getAttribute("userId");

        // 判断如果没有取到员工信息,就跳转到登陆页面
        if (userId == null || "".equals(userId)) {
            // TODO 须重定向至登陆页面
            ExceptionUtil.throwException(ErrorCode.NOT_LOGIN);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
