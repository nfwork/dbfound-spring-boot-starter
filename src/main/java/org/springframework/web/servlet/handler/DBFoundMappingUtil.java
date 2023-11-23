package org.springframework.web.servlet.handler;

import com.github.nfwork.dbfound.starter.handler.DBFoundRequestHandlerMapping;
import org.springframework.boot.SpringBootVersion;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

public class DBFoundMappingUtil {

    public static void addInterceptors(DBFoundRequestHandlerMapping dbfoundMapping, RequestMappingHandlerMapping requestMapping){
        Object[] interceptors = requestMapping.getAdaptedInterceptors();
        if(interceptors != null){
            dbfoundMapping.setInterceptors(interceptors);
        }
    }

    public static void addCorsConfigurationSource(DBFoundRequestHandlerMapping dbfoundMapping, RequestMappingHandlerMapping requestMapping){
        String version = SpringBootVersion.getVersion();
        String miniVersion = "2.4.0";
        if(versionCompare(version, miniVersion)) {
            CorsConfigurationSource source = requestMapping.getCorsConfigurationSource();
            if (source != null) {
                dbfoundMapping.setCorsConfigurationSource(source);
            }
        }
    }

    private static boolean versionCompare(String version1, String version2){
        String[] v1 = version1.split("\\.");
        String[] v2 = version2.split("\\.");

        int i = 0;
        while (i < v1.length || i < v2.length) {
            int num1 = i < v1.length ? Integer.parseInt(v1[i]) : 0;
            int num2 = i < v2.length ? Integer.parseInt(v2[i]) : 0;

            if (num1 > num2) {
                return true;
            } else if (num1 < num2) {
                return false;
            }
            i++;
        }
        return true;
    }
}
