package com.github.nfwork.dbfound.starter.service;

import com.nfwork.dbfound.core.Context;
import com.nfwork.dbfound.dto.ResponseObject;

public interface DBFoundDefaultService {

	ResponseObject query(Context context, String modelName, String queryName) ;

	void export(Context context, String modelName, String queryName) throws Exception ;

	ResponseObject execute(Context context, String modelName, String executeName);

	ResponseObject batchExecute(Context context, String modelName, String executeName);
}
