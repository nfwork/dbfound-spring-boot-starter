package com.github.nfwork.dbfound.starter.autoconfigure;
import com.github.nfwork.dbfound.starter.controller.DBFoundDefaultController;
import com.github.nfwork.dbfound.starter.exception.DBFoundExceptionHandler;
import com.github.nfwork.dbfound.starter.exception.DBFoundExceptionHandlerImpl;
import com.github.nfwork.dbfound.starter.service.DBFoundDefaultService;
import com.github.nfwork.dbfound.starter.service.DBFoundDefaultServiceImpl;
import com.github.nfwork.dbfound.starter.handler.WebApiPermissionChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.github.nfwork.dbfound.starter.DBFoundEngine;
import com.github.nfwork.dbfound.starter.handler.DBFoundRequestHandlerMapping;
import org.springframework.web.servlet.handler.DBFoundMappingUtil;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.List;

@Configuration
@ConditionalOnClass(WebMvcConfigurer.class)
public class DBFoundWebConfiguration implements WebMvcConfigurer {

	@Autowired
	DBFoundEngine dbfoundEngine;

    @Autowired
    DBFoundConfigProperties config;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new ContextArgumentResolver(dbfoundEngine));
    }

    @Bean
    @ConditionalOnProperty(name = "dbfound.web.api-expose-strategy", havingValue = "dbfound_default_controller" )
    public DBFoundDefaultController dbfoundDefaultController() {
        return new DBFoundDefaultController();
    }

    @Bean
    @ConditionalOnProperty(matchIfMissing = true, name = "dbfound.web.api-expose-strategy", havingValue = "dbfound_request_handler" )
    public DBFoundRequestHandlerMapping dbfoundRequestHandlerMapping(@Qualifier("requestMappingHandlerMapping")RequestMappingHandlerMapping requestMapping, DBFoundDefaultService service, DBFoundExceptionHandler exceptionHandle, WebApiPermissionChecker permissionChecker) throws NoSuchMethodException {
        DBFoundRequestHandlerMapping dbfoundMapping = new DBFoundRequestHandlerMapping(service, exceptionHandle, permissionChecker);
        DBFoundMappingUtil.addInterceptors(dbfoundMapping,requestMapping);
        DBFoundMappingUtil.addCorsConfigurationSource(dbfoundMapping,requestMapping);
        return dbfoundMapping;
    }

    @Bean
    public WebApiPermissionChecker webApiPermissionChecker() {
        return new WebApiPermissionChecker(config);
    }

    @Bean
    @ConditionalOnMissingBean(DBFoundDefaultService.class)
    public DBFoundDefaultServiceImpl dbfoundDefaultServiceImpl() {
        return new DBFoundDefaultServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(DBFoundExceptionHandler.class)
    public DBFoundExceptionHandlerImpl dbfoundExceptionHandlerImpl() {
        return new DBFoundExceptionHandlerImpl();
    }
}
