package com.github.nfwork.dbfound.starter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.nfwork.dbfound.starter.annotation.ContextAware;
import com.github.nfwork.dbfound.starter.exception.DBFoundExceptionhandle;
import com.github.nfwork.dbfound.starter.service.DBFoundDefaultService;
import com.nfwork.dbfound.core.Context;
import com.nfwork.dbfound.dto.ResponseObject;
import com.nfwork.dbfound.excel.ExcelWriter;
import com.nfwork.dbfound.model.ModelEngine;

@RestController
public class DBFoundDefaultController {

	@Autowired
	DBFoundDefaultService service;

	@Autowired
	DBFoundExceptionhandle exceptionHandle;

	@RequestMapping("/**/*.query")
	public ResponseObject query(@ContextAware Context context) {
		return query(context, null);
	}

	@RequestMapping("/**/*.query!{queryName}")
	public ResponseObject query(@ContextAware Context context, @PathVariable String queryName) {
		try {
			String uri = context.request.getRequestURI();
			String modelName = uri.substring(1, uri.indexOf(".query"));
			return service.query(context, modelName, queryName);
		} catch (Exception e) {
			return exceptionHandle.handle(e, context.request, context.response);
		}
	}

	@RequestMapping("/**/*.execute")
	public ResponseObject execute(@ContextAware Context context) {
		return execute(context, null);
	}

	@RequestMapping("/**/*.execute!{executeName}")
	public ResponseObject execute(@ContextAware Context context, @PathVariable String executeName) {
		try {
			String uri = context.request.getRequestURI();
			String modelName = uri.substring(1, uri.indexOf(".execute"));
			
			Object gridData = context.getData(ModelEngine.defaultBatchPath);
			if (gridData != null && gridData instanceof List) {
				return service.batchExecute(context, modelName, executeName);
			}else {
				return service.execute(context, modelName, executeName);
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
			String uri = context.request.getRequestURI();
			String modelName = uri.substring(1, uri.indexOf(".export"));
			ExcelWriter.excelExport(context, modelName, queryName);
			return null;
		} catch (Exception e) {
			return exceptionHandle.handle(e, context.request, context.response);
		}
	}
}
