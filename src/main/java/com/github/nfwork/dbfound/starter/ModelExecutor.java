package com.github.nfwork.dbfound.starter;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.github.nfwork.dbfound.starter.dbprovide.DBFoundTransactionManager;
import com.github.nfwork.dbfound.starter.dbprovide.SpringDataSourceProvide;
import com.nfwork.dbfound.core.Context;
import com.nfwork.dbfound.db.ConnectionProvide;
import com.nfwork.dbfound.db.ConnectionProvideManager;
import com.nfwork.dbfound.dto.QueryResponseObject;
import com.nfwork.dbfound.dto.ResponseObject;
import com.nfwork.dbfound.exception.SqlExecuteException;
import com.nfwork.dbfound.model.ModelEngine;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

import javax.sql.DataSource;

/**
 * model executor
 *
 * @author John
 *
 */
public class ModelExecutor {

	private DBFoundTransactionManager dbFoundTransactionManager;

	private final Map<String,SQLExceptionTranslator> sqlExceptionTranslatorMap = new ConcurrentHashMap<>();

	/**
	 * execute xml sql, user param
	 * @param modelName model name
	 * @param executeName execute name
	 * @param param param
	 * @return ResponseObject
	 */
	public  ResponseObject execute(String modelName, String executeName, Object param){
		Context context = new Context();
		context.setParamData("data",param);
		return execute(context, modelName, executeName, "param.data");
	}

	/**
	 * execute xml sql, include insert update delete; currentPaht is default
	 * @param context context
	 * @param modelName model name
	 * @param executeName execute name
	 * @return ResponseObject
	 */
	public ResponseObject execute(Context context, String modelName, String executeName) {
		return execute(context, modelName, executeName, ModelEngine.defaultPath);
	}

	/**
	 * execute xml sql, include insert update delete;
	 * @param context context
	 * @param modelName model name
	 * @param executeName execute name
	 * @param currentPath currentPath
	 * @return ResponseObject
	 */
	public ResponseObject execute(Context context, String modelName, String executeName, String currentPath) {
		try {
			if(dbFoundTransactionManager != null){
				dbFoundTransactionManager.registContext(context);
			}
			return ModelEngine.execute(context, modelName, executeName, currentPath);
		}catch (SqlExecuteException exception){
			throw translateException(exception);
		}
	}

	/**
	 * user list data,batch execute
	 * @param modelName model name
	 * @param executeName execute name
	 * @param dataList  a list param
	 * @return ResponseObject
	 */
	public ResponseObject batchExecute(String modelName, String executeName, List<?> dataList) {
		Context context = new Context();
		context.setParamData("dataList",dataList);
		return batchExecute(context, modelName, executeName,"param.dataList");
	}

	/**
	 * batch execute xml sql, include insert update delete; currentPaht is default param.GridData;
	 * @param context context
	 * @param modelName model name
	 * @param executeName execute name
	 * @return ResponseObject
	 */
	public ResponseObject batchExecute(Context context, String modelName, String executeName) {
		return batchExecute(context, modelName, executeName, ModelEngine.defaultBatchPath);
	}

	/**
	 * batch execute xml sql, include insert update delete;
	 * @param context context
	 * @param modelName model name
	 * @param executeName execute name
	 * @param currentPath current path
	 * @return ResponseObject
	 */
	public ResponseObject batchExecute(Context context, String modelName, String executeName, String currentPath) {
		try {
			if(dbFoundTransactionManager != null){
				dbFoundTransactionManager.registContext(context);
			}
			return ModelEngine.batchExecute(context, modelName, executeName, currentPath);
		}catch (SqlExecuteException exception){
			throw translateException(exception);
		}
	}

	/**
	 * query list , user param
	 * @param modelName model name
	 * @param queryName query name
	 * @param param param object
	 * @return List data
	 */
	public List<Map<String,Object>> queryList(String modelName, String queryName, Object param) {
		Context context = new Context();
		context.setParamData("data",param);
		QueryResponseObject<Map<String,Object>> object = query(context, modelName, queryName, "param.data",false);
		return object.getDatas();
	}

	/**
	 * query list , user param
	 * @param modelName model name
	 * @param queryName query name
	 * @param param param object
	 * @param class1 entity class
	 * @param <T> T
	 * @return list T
	 */
	public <T> List<T> queryList(String modelName, String queryName, Object param, Class<T> class1) {
		Context context = new Context();
		context.setParamData("data",param);
		return query(context, modelName, queryName, "param.data",false,class1).getDatas();
	}

	/**
	 * query list
	 * @param context context
	 * @param modelName model name
	 * @param queryName query name
	 * @return List
	 */
	public List<Map<String,Object>> queryList(Context context, String modelName, String queryName) {
		QueryResponseObject<Map<String,Object>> object = query(context, modelName, queryName, ModelEngine.defaultPath,false);
		return object.getDatas();
	}

	/**
	 * query list data, return list object
	 * @param context context
	 * @param modelName model name
	 * @param queryName query name
	 * @param class1 entity class
	 * @param <T> T
	 * @return list of T
	 */
	public <T> List<T> queryList(Context context, String modelName, String queryName, Class<T> class1) {
		return query(context, modelName, queryName, ModelEngine.defaultPath, false, class1).getDatas();
	}


	/**
	 * query one line data, return a map
	 * @param modelName model name
	 * @param queryName query name
	 * @param param param object
	 * @return Map<String,Object>
	 */
	public Map<String,Object> queryOne(String modelName, String queryName, Object param) {
		List<Map<String,Object>> dataList = queryList(modelName, queryName,param);
		if (dataList != null && dataList.size() > 0) {
			return dataList.get(0);
		} else {
			return null;
		}
	}

	/**
	 * query one line data, return a object
	 * @param context context
	 * @param modelName model name
	 * @param queryName query name
	 * @return Map<String,Object>
	 */
	public Map<String,Object> queryOne(Context context, String modelName, String queryName) {
		List<Map<String,Object>> dataList = queryList(context, modelName, queryName);
		if (dataList != null && dataList.size() > 0) {
			return dataList.get(0);
		} else {
			return null;
		}
	}

	/**
	 * query one line data, return a Object T
	 * @param modelName model name
	 * @param queryName query name
	 * @param param param object
	 * @param class1 entity class
	 * @param <T> T
	 * @return T
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
	 * @param context context
	 * @param modelName model name
	 * @param queryName query name
	 * @param class1 entity class
	 * @param <T> T
	 * @return T
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
	 * @param modelName model name
	 * @param queryName query name
	 * @param param param object
	 * @param start start with
	 * @param limit limit pager size
	 * @return QueryResponseObject
	 */
	public QueryResponseObject<Map<String,Object>> query(String modelName, String queryName, Object param, int start,int limit) {
		Context context = new Context();
		context.setParamData("data",param);
		context.setParamData("start",start);
		context.setParamData("limit",limit);
		return query(context, modelName, queryName, "param.data",true);
	}

	/**
	 * query page list , user param
	 * @param modelName model name
	 * @param queryName query name
	 * @param param param object
	 * @return QueryResponseObject
	 */
	public QueryResponseObject<Map<String,Object>> query(String modelName, String queryName, Object param) {
		Context context = new Context();
		context.setParamData("data",param);
		return query(context, modelName, queryName, "param.data",false);
	}

	/**
	 * query page list , user param
	 * @param modelName model name
	 * @param queryName query name
	 * @param param param object
	 * @param start start with
	 * @param limit limit pager size
	 * @param class1 entity class
	 * @param <T> T
	 * @return QueryResponseObject T
	 */
	public <T> QueryResponseObject<T> query( String modelName, String queryName, Object param, int start,int limit,Class<T> class1) {
		Context context = new Context();
		context.setParamData("data",param);
		context.setParamData("start",start);
		context.setParamData("limit",limit);
		return query(context, modelName, queryName, "param.data",true, class1);
	}

	/**
	 * query page list , user param
	 * @param modelName model name
	 * @param queryName query name
	 * @param param param object
	 * @param class1 entity class
	 * @param <T> T
	 * @return QueryResponseObject T
	 */
	public <T> QueryResponseObject<T> query( String modelName, String queryName, Object param, Class<T> class1) {
		Context context = new Context();
		context.setParamData("data",param);
		return query(context, modelName, queryName, "param.data",false, class1);
	}

	/**
	 *  query xml sql, include select , reuturn QueryResponseObject Map
	 * @param context context
	 * @param modelName model name
	 * @param queryName query name
	 * @return QueryResponseObject
	 */
	public QueryResponseObject<Map<String,Object>> query(Context context, String modelName, String queryName) {
		return query(context, modelName, queryName, true, null);
	}

	/**
	 * query xml sql, include select , return QueryResponseObject T
	 * @param context context
	 * @param modelName model name
	 * @param queryName query name
	 * @param class1 entity class
	 * @param <T> T
	 * @return QueryResponseObject T
	 */
	public <T> QueryResponseObject<T> query(Context context, String modelName, String queryName, Class<T> class1) {
		return query(context, modelName, queryName, ModelEngine.defaultPath, true, class1);
	}

	/**
	 * query xml sql, include select , reuturn QueryResponseObject  Map
	 * @param context context
	 * @param modelName model name
	 * @param queryName query name
	 * @param autoPaging auto paging
	 * @return QueryResponseObject
	 */
	public QueryResponseObject<Map<String,Object>> query(Context context, String modelName, String queryName, boolean autoPaging) {
		return query(context, modelName, queryName, ModelEngine.defaultPath, autoPaging, null);
	}

	/**
	 * query xml sql, include select , return QueryResponseObject T
	 * @param context context
	 * @param modelName model name
	 * @param queryName query name
	 * @param autoPaging auto paging
	 * @param class1 entity class
	 * @param <T> T
	 * @return QueryResponseObject T
	 */
	public <T> QueryResponseObject<T> query(Context context, String modelName, String queryName, boolean autoPaging, Class<T> class1) {
		return query(context, modelName, queryName, ModelEngine.defaultPath, autoPaging, class1);
	}

	/**
	 * query xml sql, include select , reuturn QueryResponseObject  Map
	 * @param context context
	 * @param modelName model name
	 * @param queryName query name
	 * @param currentPath current path
	 * @param autoPaging auto paging
	 * @return QueryResponseObject
	 */
	public QueryResponseObject<Map<String,Object>> query(Context context, String modelName, String queryName, String currentPath, boolean autoPaging) {
		return query(context, modelName, queryName, currentPath, autoPaging, null);
	}

	/**
	 * query xml sql, include select , return QueryResponseObject T
	 * @param context context
	 * @param modelName model name
	 * @param queryName query name
	 * @param currentPath current path
	 * @param autoPaging auto paging
	 * @param class1 entity class
	 * @param <T> T
	 * @return QueryResponseObject T
	 */
	public <T> QueryResponseObject<T> query(Context context, String modelName, String queryName, String currentPath, boolean autoPaging, Class<T> class1) {
		try {
			if (dbFoundTransactionManager != null) {
				dbFoundTransactionManager.registContext(context);
			}
			return ModelEngine.query(context, modelName, queryName, currentPath, autoPaging, class1);
		}catch (SqlExecuteException exception){
			throw translateException(exception);
		}
	}

	public DBFoundTransactionManager getDbFoundTransactionManager() {
		return dbFoundTransactionManager;
	}

	public void setDbFoundTransactionManager(DBFoundTransactionManager dbFoundTransactionManager) {
		this.dbFoundTransactionManager = dbFoundTransactionManager;
	}

	protected DataAccessException translateException(SqlExecuteException ex) {
		DataAccessException dae = getExceptionTranslator(ex.getProvideName()).translate(ex.getTask(), ex.getSql(), (SQLException) ex.getCause());
		return (dae != null ? dae : new UncategorizedSQLException(ex.getTask(), ex.getSql(), (SQLException) ex.getCause()));
	}

	private SQLExceptionTranslator getExceptionTranslator(String provideName) {
		SQLExceptionTranslator exceptionTranslator = sqlExceptionTranslatorMap.get(provideName);
		if (exceptionTranslator != null) {
			return exceptionTranslator;
		}
		synchronized (this) {
			exceptionTranslator = sqlExceptionTranslatorMap.get(provideName);
			if (exceptionTranslator == null) {
				ConnectionProvide provide = ConnectionProvideManager.getConnectionProvide(provideName);
				if(provide instanceof SpringDataSourceProvide) {
					DataSource dataSource = ((SpringDataSourceProvide) provide).getDataSource();
					exceptionTranslator = new SQLErrorCodeSQLExceptionTranslator(dataSource);
				}
				else {
					exceptionTranslator = new SQLStateSQLExceptionTranslator();
				}
				sqlExceptionTranslatorMap.put(provideName, exceptionTranslator);
			}
			return exceptionTranslator;
		}
	}
}
