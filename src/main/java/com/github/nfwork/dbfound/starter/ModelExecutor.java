package com.github.nfwork.dbfound.starter;

import java.util.List;
import java.util.Map;

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
	 * @param modelName
	 * @param executeName
	 * @param param
	 * @return
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
	 *
	 * @param context
	 * @param modelName
	 * @param executeName
	 * @return
	 */
	public ResponseObject execute(Context context, String modelName, String executeName) {
		if(dbFoundTransactionManager != null){
			dbFoundTransactionManager.registContext(context);
		}
		return ModelEngine.execute(context, modelName, executeName);
	}

	/**
	 * execute xml sql, include insert update delete;
	 *
	 * @param context
	 * @param modelName
	 * @param executeName
	 * @param currentPath
	 * @return
	 */
	public ResponseObject execute(Context context, String modelName, String executeName, String currentPath) {
		if(dbFoundTransactionManager != null){
			dbFoundTransactionManager.registContext(context);
		}
		return ModelEngine.execute(context, modelName, executeName, currentPath);
	}

	/**
	 *  user list data,batch execute
	 * @param modelName
	 * @param executeName
	 * @param dataList
	 * @return
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
	 *
	 * @param context
	 * @param modelName
	 * @param executeName
	 * @return
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
	 * @param context
	 * @param modelName
	 * @param executeName
	 * @param currentPath
	 * @return
	 */
	public ResponseObject batchExecute(Context context, String modelName, String executeName, String currentPath) {
		if(dbFoundTransactionManager != null){
			dbFoundTransactionManager.registContext(context);
		}
		return ModelEngine.batchExecute(context, modelName, executeName, currentPath);
	}

	/**
	 * query list , user param
	 * @param modelName
	 * @param queryName
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>>  queryList(String modelName, String queryName, Object param) {
		Context context = new Context();
		context.setParamData("data",param);
		List<Map<String, Object>>  dataList = ModelEngine.query(context, modelName, queryName, "param.data",false).getDatas();
		return dataList;
	}

	/**
	 * query list , user param
	 * @param modelName
	 * @param queryName
	 * @param param
	 * @param class1
	 * @param <T>
	 * @return
	 */
	public <T> List<T> queryList(String modelName, String queryName, Object param, Class<T> class1) {
		Context context = new Context();
		context.setParamData("data",param);
		List<T> dataList = ModelEngine.query(context, modelName, queryName, "param.data",false,class1).getDatas();
		return dataList;
	}

	/**
	 * query list, return list map
	 *
	 * @param context
	 * @param modelName
	 * @param queryName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryList(Context context, String modelName, String queryName) {
		List<Map<String, Object>> dataList = ModelEngine.query(context, modelName, queryName, ModelEngine.defaultPath,
				false).getDatas();
		return dataList;
	}

	/**
	 * query list data, return list object
	 *
	 * @param context
	 * @param modelName
	 * @param queryName
	 * @param class1
	 * @return
	 */
	public <T> List<T> queryList(Context context, String modelName, String queryName, Class<T> class1) {
		List<T> dataList = ModelEngine.query(context, modelName, queryName, ModelEngine.defaultPath, false, class1)
				.getDatas();
		return dataList;
	}


	/**
	 * query one line data, return a map
	 * @param modelName
	 * @param queryName
	 * @param param
	 * @return
	 */
	public Map<String, Object> queryOne(String modelName, String queryName, Object param) {
		List<Map<String, Object>> dataList = queryList(modelName, queryName,param);
		if (dataList != null && dataList.size() > 0) {
			return dataList.get(0);
		} else {
			return null;
		}
	}

	/**
	 * query one line data, return a map
	 *
	 * @param context
	 * @param modelName
	 * @param queryName
	 * @return
	 */
	public Map<String, Object> queryOne(Context context, String modelName, String queryName) {
		List<Map<String, Object>> dataList = queryList(context, modelName, queryName);
		if (dataList != null && dataList.size() > 0) {
			return dataList.get(0);
		} else {
			return null;
		}
	}

	/**
	 * query one line data, return a Object T
	 * @param modelName
	 * @param queryName
	 * @param param
	 * @param class1
	 * @param <T>
	 * @return
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
	 *
	 * @param context
	 * @param modelName
	 * @param queryName
	 * @param class1
	 * @return
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
	 * @param modelName
	 * @param queryName
	 * @param param
	 * @param start
	 * @param limit
	 * @return
	 */
	public QueryResponseObject query(String modelName, String queryName, Object param, int start,int limit) {
		Context context = new Context();
		context.setParamData("data",param);
		context.setParamData("start",start);
		context.setParamData("limit",limit);
		return ModelEngine.query(context, modelName, queryName, "param.data",true);
	}

	/**
	 * query page list , user param
	 * @param modelName
	 * @param queryName
	 * @param param
	 * @param start
	 * @param limit
	 * @param class1
	 * @param <T>
	 * @return
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
	 *
	 * @param context
	 * @param modelName
	 * @param queryName
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public QueryResponseObject query(Context context, String modelName, String queryName) {
		return ModelEngine.query(context, modelName, queryName);
	}

	/**
	 * query xml sql, include select , return QueryResponseObject T
	 *
	 * @param context
	 * @param modelName
	 * @param queryName
	 * @param class1
	 * @return
	 */
	public <T> QueryResponseObject<T> query(Context context, String modelName, String queryName, Class<T> class1) {
		return ModelEngine.query(context, modelName, queryName, class1);
	}

	/**
	 * query xml sql, include select , reuturn QueryResponseObject  Map
	 *
	 * @param context
	 * @param modelName
	 * @param queryName
	 * @param currentPath
	 * @param autoPaging
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public QueryResponseObject query(Context context, String modelName, String queryName, String currentPath,
			boolean autoPaging) {
		return ModelEngine.query(context, modelName, queryName, currentPath, autoPaging);
	}

	/**
	 * query xml sql, include select , return QueryResponseObject T
	 *
	 * @param context
	 * @param modelName
	 * @param queryName
	 * @param currentPath
	 * @param autoPaging
	 * @param class1
	 * @return
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
