package com.github.nfwork.dbfound.starter.service;

import com.github.nfwork.dbfound.starter.dbprovide.DBFoundTransactionManager;
import com.nfwork.dbfound.core.Context;
import com.nfwork.dbfound.dto.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DBFoundTransactionService extends DBFoundDefaultService{

	@Autowired
	DBFoundTransactionManager dbFoundTransactionManager;

	@Override
	@Transactional(rollbackFor = Exception.class, transactionManager="dbfoundTransactionManager")
	public ResponseObject execute(Context context, String modelName, String executeName) {
		dbFoundTransactionManager.begin(context);
		return super.execute(context, modelName, executeName);
	}

	@Override
	@Transactional(rollbackFor = Exception.class, transactionManager="dbfoundTransactionManager")
	public ResponseObject batchExecute(Context context, String modelName, String executeName) {
		dbFoundTransactionManager.begin(context);
		return super.batchExecute(context, modelName, executeName);
	}
}
