package io.renren.common.annotation;

import io.renren.modules.app.controller.PublicController;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.handler.WebRequestHandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.*;

/**
 * api接口，忽略Token验证
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-23 15:44
 */
@Target({ElementType.METHOD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthIgnore  {



}
