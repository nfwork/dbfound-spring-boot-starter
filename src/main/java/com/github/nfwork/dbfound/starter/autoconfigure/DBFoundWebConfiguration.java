package com.github.nfwork.dbfound.starter.autoconfigure;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nfwork.dbfound.starter.controller.DBFoundDefaultController;
import com.github.nfwork.dbfound.starter.exception.DBFoundExceptionHandle;
import com.github.nfwork.dbfound.starter.exception.DBFoundExceptionHandleImpl;
import com.github.nfwork.dbfound.starter.service.DBFoundDefaultService;
import com.github.nfwork.dbfound.starter.service.DBFoundDefaultServiceImpl;
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
    public DBFoundRequestHandlerMapping dbfoundRequestHandlerMapping(@Qualifier("requestMappingHandlerMapping")RequestMappingHandlerMapping requestMapping, DBFoundDefaultService service, DBFoundExceptionHandle exceptionHandle) throws NoSuchMethodException {
        DBFoundRequestHandlerMapping dbfoundMapping = new DBFoundRequestHandlerMapping(service, exceptionHandle);
        DBFoundMappingUtil.addInterceptors(dbfoundMapping,requestMapping);
        DBFoundMappingUtil.addCorsConfigurationSource(dbfoundMapping,requestMapping);
        return dbfoundMapping;
    }

    @Bean
    @ConditionalOnMissingBean(DBFoundDefaultService.class)
    public DBFoundDefaultServiceImpl dbfoundDefaultServiceImpl() {
        return new DBFoundDefaultServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(DBFoundExceptionHandle.class)
    public DBFoundExceptionHandleImpl dbfoundExceptionHandleImpl() {
        return new DBFoundExceptionHandleImpl();
    }
}
