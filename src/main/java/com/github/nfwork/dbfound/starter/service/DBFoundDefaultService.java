package com.github.nfwork.dbfound.starter.service;

import com.nfwork.dbfound.excel.ExcelWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.nfwork.dbfound.starter.ModelExecutor;
import com.nfwork.dbfound.core.Context;
import com.nfwork.dbfound.dto.ResponseObject;
import com.nfwork.dbfound.model.ModelEngine;

@Service
public class DBFoundDefaultService {

	@Autowired
	ModelExecutor modelExecutor;

	public ResponseObject query(Context context, String modelName, String queryName) {
		return modelExecutor.query(context, modelName, queryName);
	}

	public void export(Context context, String modelName, String queryName) throws Exception {
		 ExcelWriter.excelExport(context, modelName, queryName);
	}

	public ResponseObject execute(Context context, String modelName, String executeName) {
		return modelExecutor.execute(context, modelName, executeName);
	}

	public ResponseObject batchExecute(Context context, String modelName, String executeName) {
		return modelExecutor.batchExecute(context, modelName, executeName, ModelEngine.defaultBatchPath);
	}
}
