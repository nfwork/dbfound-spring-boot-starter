package com.github.nfwork.dbfound.starter.controller;

import java.util.List;
import java.util.Map;

import com.github.nfwork.dbfound.starter.fileupload.FileUploadManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.nfwork.dbfound.starter.annotation.ContextAware;
import com.github.nfwork.dbfound.starter.exception.DBFoundExceptionHandle;
import com.github.nfwork.dbfound.starter.service.DBFoundDefaultService;
import com.nfwork.dbfound.core.Context;
import com.nfwork.dbfound.dto.ResponseObject;
import com.nfwork.dbfound.model.ModelEngine;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class DBFoundDefaultController {

	@Autowired
	DBFoundDefaultService service;

	@Autowired
	DBFoundExceptionHandle exceptionHandle;

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
			}else{
				return null;
			}
		} catch (Exception e) {
			return exceptionHandle.handle(e, context.request, context.response);
		}
	}

	@RequestMapping("/**/*.execute")
	public ResponseObject execute(@ContextAware Context context,@RequestParam Map<String, MultipartFile> fileMap) {
		return execute(context, fileMap, null);
	}

	@RequestMapping("/**/*.execute!{executeName}")
	public ResponseObject execute(@ContextAware Context context,@RequestParam Map<String,MultipartFile> fileMap, @PathVariable String executeName) {
		try {
			FileUploadManager.initUpload(context, fileMap);
			String uri = context.request.getServletPath();
			String modelName = uri.substring(1, uri.indexOf(".execute"));
			
			Object gridData = context.getData(ModelEngine.defaultBatchPath);

			ResponseObject object;
			if (gridData instanceof List) {
				object = service.batchExecute(context, modelName, executeName);
			}else {
				object = service.execute(context, modelName, executeName);
			}
			if(context.isOutMessage()){
				return object;
			}else{
				return null;
			}
		} catch (Exception e) {
			return exceptionHandle.handle(e, context.request, context.response);
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
		} catch (Exception e) {
			return exceptionHandle.handle(e, context.request, context.response);
		}
	}
}
