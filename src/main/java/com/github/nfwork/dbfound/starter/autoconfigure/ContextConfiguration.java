package com.github.nfwork.dbfound.starter.autoconfigure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.github.nfwork.dbfound.starter.DBFoundEngine;

import java.util.List;

@Configuration
public class ContextConfiguration implements WebMvcConfigurer {

	@Autowired
	DBFoundEngine dbfoundEngine;
	
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new ContextArgumentResolver(dbfoundEngine));
    }
}
