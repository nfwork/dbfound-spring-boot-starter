package com.github.nfwork.dbfound.starter.handler;

import com.github.nfwork.dbfound.starter.autoconfigure.DBFoundConfigProperties;
import com.nfwork.dbfound.dto.ResponseObject;

import jakarta.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class WebApiPermissionChecker {

    private final Set<String> apiAllowUrlSet = new HashSet<>();

    public WebApiPermissionChecker(DBFoundConfigProperties config) {
        List<String> apiAllowUrls = config == null || config.getWeb() == null ? null : config.getWeb().getApiAllowUrls();
        if (apiAllowUrls == null) {
            return;
        }
        for (String apiAllowUrl : apiAllowUrls) {
            String normalizedUrl = normalizePath(apiAllowUrl);
            if (normalizedUrl != null) {
                apiAllowUrlSet.add(normalizedUrl);
            }
        }
    }

    public boolean isForbidden(String requestPath) {
        if (apiAllowUrlSet.isEmpty()) {
            return false;
        }
        return !apiAllowUrlSet.contains(requestPath);
    }

    public ResponseObject forbiddenResponse(HttpServletResponse response, String requestPath) {
        response.setStatus(403);
        ResponseObject object = new ResponseObject();
        object.setSuccess(false);
        object.setMessage("URL access is forbidden: " + requestPath);
        return object;
    }

    private String normalizePath(String path) {
        if (path == null) {
            return null;
        }
        String normalizedPath = path.trim();
        if (normalizedPath.isEmpty()) {
            return null;
        }
        if (normalizedPath.startsWith("/")) {
            return normalizedPath;
        }
        return "/" + normalizedPath;
    }
}
