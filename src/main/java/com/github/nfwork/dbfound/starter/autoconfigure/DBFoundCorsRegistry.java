package com.github.nfwork.dbfound.starter.autoconfigure;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.Map;

public class DBFoundCorsRegistry extends CorsRegistry {

    @Override
    protected Map<String, CorsConfiguration> getCorsConfigurations() {
        return super.getCorsConfigurations();
    }
}
