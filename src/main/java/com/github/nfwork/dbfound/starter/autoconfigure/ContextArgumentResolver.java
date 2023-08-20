package com.github.nfwork.dbfound.starter.autoconfigure;

import com.github.nfwork.dbfound.starter.fileupload.FileUploadManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.github.nfwork.dbfound.starter.DBFoundEngine;
import com.github.nfwork.dbfound.starter.annotation.ContextAware;
import com.nfwork.dbfound.core.Context;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.multipart.support.MultipartResolutionDelegate;

public class ContextArgumentResolver implements HandlerMethodArgumentResolver {

	DBFoundEngine dbfoundEngine;

	public ContextArgumentResolver(DBFoundEngine dbfoundEngine) {
		this.dbfoundEngine = dbfoundEngine;
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(ContextAware.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory){
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
		Context context = Context.getCurrentContext(request, response);

		MultipartRequest multipartRequest = MultipartResolutionDelegate.resolveMultipartRequest(webRequest);
		if(multipartRequest != null){
			FileUploadManager.initUpload(context,multipartRequest);
		}
		return context;
	}

}
