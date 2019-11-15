package com.github.nfwork.dbfound.starter;

import java.util.List;
import java.util.Map;

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
		return ModelEngine.execute(context, modelName, executeName, currentPath);
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
		return ModelEngine.batchExecute(context, modelName, executeName, currentPath);
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
		List<Map<String, Object>> datasList = ModelEngine.query(context, modelName, queryName, ModelEngine.defaultPath,
				false).getDatas();
		return datasList;
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
		List<Map<String, Object>> datasList = queryList(context, modelName, queryName);
		if (datasList != null && datasList.size() > 0) {
			return datasList.get(0);
		} else {
			return null;
		}
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
		List<T> datasList = ModelEngine.query(context, modelName, queryName, ModelEngine.defaultPath, false, class1)
				.getDatas();
		return datasList;
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
		List<T> datasList = queryList(context, modelName, queryName, class1);
		if (datasList != null && datasList.size() > 0) {
			return datasList.get(0);
		} else {
			return null;
		}
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

}
