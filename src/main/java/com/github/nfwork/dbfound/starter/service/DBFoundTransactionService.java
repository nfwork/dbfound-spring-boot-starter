package com.github.nfwork.dbfound.starter.service;

import com.nfwork.dbfound.core.Context;
import com.nfwork.dbfound.dto.ResponseObject;
import org.springframework.stereotype.Service;

@Service
public class DBFoundTransactionService extends DBFoundDefaultService{

	public ResponseObject execute(Context context, String modelName, String executeName) {
		try {
			context.getTransaction().begin();
			ResponseObject responseObject = super.execute(context,modelName,executeName);
			context.getTransaction().commit();
			return responseObject;
		}catch (Exception exception){
			context.getTransaction().rollback();
			throw exception;
		}finally {
			context.getTransaction().end();
		}
	}

	public ResponseObject batchExecute(Context context, String modelName, String executeName) {
		try {
			context.getTransaction().begin();
			ResponseObject responseObject = super.batchExecute(context, modelName, executeName);
			context.getTransaction().commit();
			return responseObject;
		}catch (Exception exception){
			context.getTransaction().rollback();
			throw exception;
		}finally {
			context.getTransaction().end();
		}
	}
}
