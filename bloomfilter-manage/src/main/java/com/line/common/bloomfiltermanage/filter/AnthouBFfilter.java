package com.line.common.bloomfiltermanage.filter;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: yangcs
 * @Date: 2020/12/4 12:48
 * @Description:
 */
@Component
public class AnthouBFfilter extends OncePerRequestFilter {

    AntPathMatcher antPathMatcher = new AntPathMatcher();
    /**
     * 排除校验的地址;
     * 内部系统间的调用(获取和尝试创建过滤器等功能 不做校验)
     * ddl操作 做简单的拦截密码校验
     */
    private List<String> excludeUrlPatterns = new ArrayList(Arrays.asList("/api/**" , "/h2-console/**"));

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String url = request.getServletPath();
        boolean matched = false;
        for (String pattern : excludeUrlPatterns) {
            matched = antPathMatcher.match(pattern, url);
            if (matched) {
                break;
            }
        }
        return matched;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = httpServletRequest.getHeader("token");
        if (!"ycs".equals(token)) {
            //401
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}
