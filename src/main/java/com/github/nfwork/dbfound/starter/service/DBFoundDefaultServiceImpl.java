package com.github.nfwork.dbfound.starter.service;

import com.github.nfwork.dbfound.starter.ModelExecutor;
import com.nfwork.dbfound.core.Context;
import com.nfwork.dbfound.dto.ResponseObject;
import com.nfwork.dbfound.excel.ExcelWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DBFoundDefaultServiceImpl implements DBFoundDefaultService{

	@Autowired
	ModelExecutor modelExecutor;

	public ResponseObject query(Context context, String modelName, String queryName) {
		return modelExecutor.query(context, modelName, queryName);
	}

	public void export(Context context, String modelName, String queryName) throws Exception {
		 ExcelWriter.excelExport(context, modelName, queryName);
	}

	@Transactional("dbfoundTransactionManager")
	public ResponseObject execute(Context context, String modelName, String executeName) {
		return modelExecutor.execute(context, modelName, executeName);
	}

	@Transactional("dbfoundTransactionManager")
	public ResponseObject batchExecute(Context context, String modelName, String executeName) {
		return modelExecutor.batchExecute(context, modelName, executeName, null);
	}
}
