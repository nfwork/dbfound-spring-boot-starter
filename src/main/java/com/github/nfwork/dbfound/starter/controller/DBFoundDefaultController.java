package com.github.nfwork.dbfound.starter.controller;

import java.util.List;

import com.nfwork.dbfound.dto.FileDownloadResponseObject;
import com.nfwork.dbfound.exception.DBFoundErrorException;
import com.nfwork.dbfound.model.ModelOperator;
import com.nfwork.dbfound.web.file.FileDownloadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.nfwork.dbfound.starter.annotation.ContextAware;
import com.github.nfwork.dbfound.starter.exception.DBFoundExceptionHandler;
import com.github.nfwork.dbfound.starter.service.DBFoundDefaultService;
import com.nfwork.dbfound.core.Context;
import com.nfwork.dbfound.dto.ResponseObject;

@RestController
public class DBFoundDefaultController {

	@Autowired
	DBFoundDefaultService service;

	@Autowired
    DBFoundExceptionHandler exceptionHandler;

	@RequestMapping("/**/*.query")
	public ResponseObject query(@ContextAware Context context) {
		return query(context, null);
	}

	@RequestMapping("/**/*.query!{queryName}")
	public ResponseObject query(@ContextAware Context context, @PathVariable String queryName) {
		try {
			String uri = context.request.getServletPath();
			String modelName = uri.substring(1, uri.indexOf(".query"));
			ResponseObject object = service.query(context, modelName, queryName);
			if(context.isOutMessage()){
				return object;
			}
			return null;
		} catch (Exception exception) {
			return exceptionHandler.handle(exceptionHandler.getException(exception), context.request, context.response);
		} catch (Throwable throwable){
			Exception exception = new DBFoundErrorException("dbfound execute error, cause by "+ throwable.getMessage(), throwable);
			return exceptionHandler.handle(exception, context.request, context.response);
		}
	}

	@RequestMapping("/**/*.execute")
	public ResponseObject execute(@ContextAware Context context) {
		return execute(context, null);
	}

	@RequestMapping("/**/*.execute!{executeName}")
	public ResponseObject execute(@ContextAware Context context,  @PathVariable String executeName) {
		try {
			String uri = context.request.getServletPath();
			String modelName = uri.substring(1, uri.indexOf(".execute"));
			
			Object gridData = context.getData(ModelOperator.defaultBatchPath);

			ResponseObject object;
			if (gridData instanceof List) {
				object = service.batchExecute(context, modelName, executeName);
			}else {
				object = service.execute(context, modelName, executeName);
			}
			if(context.isOutMessage()){
				if(object instanceof FileDownloadResponseObject){
					FileDownloadResponseObject fd = (FileDownloadResponseObject) object;
					FileDownloadUtil.download(fd.getFileParam(),fd.getParams(),context.response);
				}else {
					return object;
				}
			}
			return null;
		} catch (Exception exception) {
			return exceptionHandler.handle(exceptionHandler.getException(exception), context.request, context.response);
		} catch (Throwable throwable){
			Exception exception = new DBFoundErrorException("dbfound execute error, cause by "+ throwable.getMessage(), throwable);
			return exceptionHandler.handle(exception, context.request, context.response);
		}
	}

	@RequestMapping("/**/*.export")
	public ResponseObject export(@ContextAware Context context) {
		return export(context, null);
	}

	@RequestMapping("/**/*.export!{queryName}")
	public ResponseObject export(@ContextAware Context context, @PathVariable String queryName) {
		try {
			String uri = context.request.getServletPath();
			String modelName = uri.substring(1, uri.indexOf(".export"));
			service.export(context,modelName,queryName);
			return null;
		} catch (Exception exception) {
			return exceptionHandler.handle(exceptionHandler.getException(exception), context.request, context.response);
		} catch (Throwable throwable){
			Exception exception = new DBFoundErrorException("dbfound execute error, cause by "+ throwable.getMessage(), throwable);
			return exceptionHandler.handle(exception, context.request, context.response);
		}
	}
}
