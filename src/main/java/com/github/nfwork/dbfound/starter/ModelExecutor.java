package com.github.nfwork.dbfound.starter;

import java.util.List;

import com.github.nfwork.dbfound.starter.dbprovide.DBFoundTransactionManager;
import com.nfwork.dbfound.core.Context;
import com.nfwork.dbfound.dto.QueryResponseObject;
import com.nfwork.dbfound.dto.ResponseObject;
import com.nfwork.dbfound.model.ModelEngine;

/**
 * model executor
 *
 * @author John
 *
 */
public class ModelExecutor {

	DBFoundTransactionManager dbFoundTransactionManager;

	/**
	 *  execute xml sql, user param
	 */
	public  ResponseObject execute(String modelName, String executeName, Object param){
		Context context = new Context();
		context.setParamData("data",param);
		if(dbFoundTransactionManager != null){
			dbFoundTransactionManager.registContext(context);
		}
		return ModelEngine.execute(context, modelName, executeName, "param.data");
	}

	/**
	 * execute xml sql, include insert update delete; currentPaht is default
	 * param.
	 */
	public ResponseObject execute(Context context, String modelName, String executeName) {
		if(dbFoundTransactionManager != null){
			dbFoundTransactionManager.registContext(context);
		}
		return ModelEngine.execute(context, modelName, executeName);
	}

	/**
	 * execute xml sql, include insert update delete;
	 */
	public ResponseObject execute(Context context, String modelName, String executeName, String currentPath) {
		if(dbFoundTransactionManager != null){
			dbFoundTransactionManager.registContext(context);
		}
		return ModelEngine.execute(context, modelName, executeName, currentPath);
	}

	/**
	 *  user list data,batch execute
	 */
	public ResponseObject batchExecute(String modelName, String executeName, List<Object> dataList) {
		Context context = new Context();
		context.setParamData("dataList",dataList);
		if(dbFoundTransactionManager != null){
			dbFoundTransactionManager.registContext(context);
		}
		return ModelEngine.batchExecute(context, modelName, executeName,"param.dataList");
	}

	/**
	 * batch execute xml sql, include insert update delete; currentPaht is
	 * default param.GridData;
	 */
	public ResponseObject batchExecute(Context context, String modelName, String executeName) {
		if(dbFoundTransactionManager != null){
			dbFoundTransactionManager.registContext(context);
		}
		return ModelEngine.batchExecute(context, modelName, executeName);
	}

	/**
	 * batch execute xml sql, include insert update delete;
	 *
	 */
	public ResponseObject batchExecute(Context context, String modelName, String executeName, String currentPath) {
		if(dbFoundTransactionManager != null){
			dbFoundTransactionManager.registContext(context);
		}
		return ModelEngine.batchExecute(context, modelName, executeName, currentPath);
	}

	/**
	 * query list , user param
	 */
	@SuppressWarnings("rawtypes")
	public List queryList(String modelName, String queryName, Object param) {
		Context context = new Context();
		context.setParamData("data",param);
		return ModelEngine.query(context, modelName, queryName, "param.data",false).getDatas();
	}

	/**
	 * query list , user param
	 */
	public <T> List<T> queryList(String modelName, String queryName, Object param, Class<T> class1) {
		Context context = new Context();
		context.setParamData("data",param);
		return ModelEngine.query(context, modelName, queryName, "param.data",false,class1).getDatas();
	}

	/**
	 * query list, return list map
	 */
	@SuppressWarnings("rawtypes")
	public List queryList(Context context, String modelName, String queryName) {
		return ModelEngine.query(context, modelName, queryName, ModelEngine.defaultPath,false).getDatas();
	}

	/**
	 * query list data, return list object
	 */
	public <T> List<T> queryList(Context context, String modelName, String queryName, Class<T> class1) {
		return ModelEngine.query(context, modelName, queryName, ModelEngine.defaultPath, false, class1).getDatas();
	}


	/**
	 * query one line data, return a map
	 */
	public Object queryOne(String modelName, String queryName, Object param) {
		List dataList = queryList(modelName, queryName,param);
		if (dataList != null && dataList.size() > 0) {
			return dataList.get(0);
		} else {
			return null;
		}
	}

	/**
	 * query one line data, return a map
	 */
	@SuppressWarnings("rawtypes")
	public Object queryOne(Context context, String modelName, String queryName) {
		List dataList = queryList(context, modelName, queryName);
		if (dataList != null && dataList.size() > 0) {
			return dataList.get(0);
		} else {
			return null;
		}
	}

	/**
	 * query one line data, return a Object T
	 */
	public <T> T queryOne( String modelName, String queryName, Object param,Class<T> class1) {
		List<T> dataList = queryList( modelName, queryName, param, class1);
		if (dataList != null && dataList.size() > 0) {
			return dataList.get(0);
		} else {
			return null;
		}
	}

	/**
	 * query one line data, return a Object T
	 */
	public <T> T queryOne(Context context, String modelName, String queryName, Class<T> class1) {
		List<T> dataList = queryList(context, modelName, queryName, class1);
		if (dataList != null && dataList.size() > 0) {
			return dataList.get(0);
		} else {
			return null;
		}
	}


	/**
	 * query page list , user param
	 */
	@SuppressWarnings("rawtypes")
	public QueryResponseObject query(String modelName, String queryName, Object param, int start,int limit) {
		Context context = new Context();
		context.setParamData("data",param);
		context.setParamData("start",start);
		context.setParamData("limit",limit);
		return ModelEngine.query(context, modelName, queryName, "param.data",true);
	}

	/**
	 * query page list , user param
	 */
	public <T> QueryResponseObject<T> query( String modelName, String queryName, Object param, int start,int limit,Class<T> class1) {
		Context context = new Context();
		context.setParamData("data",param);
		context.setParamData("start",start);
		context.setParamData("limit",limit);
		return ModelEngine.query(context, modelName, queryName, "param.data",true, class1);
	}

	/**
	 * query xml sql, include select , reuturn QueryResponseObject Map
	 */
	@SuppressWarnings("rawtypes")
	public QueryResponseObject query(Context context, String modelName, String queryName) {
		return ModelEngine.query(context, modelName, queryName);
	}

	/**
	 * query xml sql, include select , return QueryResponseObject T
	 *
	 */
	public <T> QueryResponseObject<T> query(Context context, String modelName, String queryName, Class<T> class1) {
		return ModelEngine.query(context, modelName, queryName, class1);
	}

	/**
	 * query xml sql, include select , reuturn QueryResponseObject  Map
	 *
	 */
	@SuppressWarnings("rawtypes")
	public QueryResponseObject query(Context context, String modelName, String queryName, String currentPath,
			boolean autoPaging) {
		return ModelEngine.query(context, modelName, queryName, currentPath, autoPaging);
	}

	/**
	 * query xml sql, include select , return QueryResponseObject T
	 *
	 */
	public <T> QueryResponseObject<T> query(Context context, String modelName, String queryName, String currentPath,
			boolean autoPaging, Class<T> class1) {
		return ModelEngine.query(context, modelName, queryName, currentPath, autoPaging, class1);
	}

	public DBFoundTransactionManager getDbFoundTransactionManager() {
		return dbFoundTransactionManager;
	}

	public void setDbFoundTransactionManager(DBFoundTransactionManager dbFoundTransactionManager) {
		this.dbFoundTransactionManager = dbFoundTransactionManager;
	}
}
