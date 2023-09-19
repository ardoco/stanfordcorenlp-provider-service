package edu.kit.kastel.mcse.ardoco.stanfordnlp.interceptors;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoggerInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(LoggerInterceptor.class);

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        if (logger.isInfoEnabled()) {
            logger.info("Receiving {} connection from {} with URI {}", request.getMethod(), getIpAddress(request), request.getRequestURI());
        }
        return true;
    }

    @Override
    public void postHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, ModelAndView modelAndView)
            throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
        if (logger.isInfoEnabled()) {
            logger.info("Finished processing for {} from {} with URI {}", request.getMethod(), getIpAddress(request), request.getRequestURI());
        }
    }

    private static String getIpAddress(HttpServletRequest request) {
        String ipFromHeader = request.getHeader("X-FORWARDED-FOR");
        if (ipFromHeader != null && !ipFromHeader.isEmpty()) {
            return ipFromHeader;
        }
        return request.getRemoteAddr();
    }
}
