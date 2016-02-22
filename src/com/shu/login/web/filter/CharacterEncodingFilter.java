package com.shu.login.web.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by Qjkobe on 2016/2/19.
 */
public class CharacterEncodingFilter implements Filter {
    //存储系统使用的字符编码
    private String encoding = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //encoding在web.xml中指定
        this.encoding = filterConfig.getInitParameter("encoding");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        //解决表单提交时的中文乱码问题
        request.setCharacterEncoding(encoding);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
