package com.daoming.web.global;

import com.daoming.utils.LogUtils;
import com.daoming.web.login.entity.UserInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyInterceptor implements HandlerInterceptor {
    private static final Log log = LogFactory.getLog(MyInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LogUtils.d("拦截处理:" + request.getRequestURL());
        boolean flag = true;
        UserInfo user = (UserInfo) request.getSession().getAttribute("user");
        if (null == user) {
            LogUtils.d("拦截");
            response.sendRedirect("http://localhost:8080/login");
            flag = false;
        } else {
            LogUtils.d("不拦截");
            flag = true;
        }
        return flag;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
