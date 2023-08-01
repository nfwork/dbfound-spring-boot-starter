package com.github.nfwork.dbfound.starter.autoconfigure;
import com.github.nfwork.dbfound.starter.controller.DBFoundDefaultController;
import com.github.nfwork.dbfound.starter.exception.DBFoundExceptionHandleImpl;
import com.github.nfwork.dbfound.starter.service.DBFoundDefaultServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.github.nfwork.dbfound.starter.DBFoundEngine;

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
    @ConditionalOnProperty(matchIfMissing = true, name = "dbfound.web.open-default-controller", havingValue = "true" )
    public DBFoundDefaultController dbFoundDefaultController() {
        return new DBFoundDefaultController();
    }

    @Bean
    @ConditionalOnMissingBean(type = "com.github.nfwork.dbfound.starter.service.DBFoundDefaultService")
    public DBFoundDefaultServiceImpl dbFoundDefaultServiceImpl() {
        return new DBFoundDefaultServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(type = "com.github.nfwork.dbfound.starter.exception.DBFoundExceptionHandle")
    public DBFoundExceptionHandleImpl dbFoundExceptionHandleImpl() {
        return new DBFoundExceptionHandleImpl();
    }
}
