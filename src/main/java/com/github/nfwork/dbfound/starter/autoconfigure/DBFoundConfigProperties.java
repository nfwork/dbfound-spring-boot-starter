package com.github.nfwork.dbfound.starter.autoconfigure;

import com.nfwork.dbfound.core.DBFoundConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.transaction.annotation.Isolation;

import java.util.Map;
import java.util.LinkedHashMap;

@ConfigurationProperties(prefix = "dbfound", ignoreUnknownFields = false)
public class DBFoundConfigProperties {

	SystemConfig system = new SystemConfig();

	WebConfig web = new WebConfig();

	DataSrouce datasource = new DataSrouce();

	Map<String, DBItemConfig> datasourceExtension = new LinkedHashMap<>();

	public Map<String, DBItemConfig> getDatasourceExtension() {
		return datasourceExtension;
	}

	public void setDatasourceExtension(Map<String, DBItemConfig> datasourceExtend) {
		this.datasourceExtension = datasourceExtend;
	}

	public SystemConfig getSystem() {
		return system;
	}

	public void setSystem(SystemConfig system) {
		this.system = system;
	}

	public WebConfig getWeb() {
		return web;
	}

	public void setWeb(WebConfig web) {
		this.web = web;
	}

	public DataSrouce getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSrouce datasource) {
		this.datasource = datasource;
	}

	public static class DataSrouce {
		DBItemConfig db0 = new DBItemConfig();
		DBItemConfig db1 = new DBItemConfig();
		DBItemConfig db2 = new DBItemConfig();
		DBItemConfig db3 = new DBItemConfig();
		DBItemConfig db4 = new DBItemConfig();
		DBItemConfig db5 = new DBItemConfig();
		DBItemConfig db6 = new DBItemConfig();
		DBItemConfig db7 = new DBItemConfig();
		DBItemConfig db8 = new DBItemConfig();
		DBItemConfig db9 = new DBItemConfig();

		public DBItemConfig getDb0() {
			return db0;
		}
		public void setDb0(DBItemConfig db0) {
			this.db0 = db0;
		}
		public DBItemConfig getDb1() {
			return db1;
		}
		public void setDb1(DBItemConfig db1) {
			this.db1 = db1;
		}
		public DBItemConfig getDb2() {
			return db2;
		}
		public void setDb2(DBItemConfig db2) {
			this.db2 = db2;
		}
		public DBItemConfig getDb3() {
			return db3;
		}
		public void setDb3(DBItemConfig db3) {
			this.db3 = db3;
		}
		public DBItemConfig getDb4() {
			return db4;
		}
		public void setDb4(DBItemConfig db4) {
			this.db4 = db4;
		}
		public DBItemConfig getDb5() {
			return db5;
		}
		public void setDb5(DBItemConfig db5) {
			this.db5 = db5;
		}
		public DBItemConfig getDb6() {
			return db6;
		}
		public void setDb6(DBItemConfig db6) {
			this.db6 = db6;
		}
		public DBItemConfig getDb7() {
			return db7;
		}
		public void setDb7(DBItemConfig db7) {
			this.db7 = db7;
		}
		public DBItemConfig getDb8() {
			return db8;
		}
		public void setDb8(DBItemConfig db8) {
			this.db8 = db8;
		}
		public DBItemConfig getDb9() {
			return db9;
		}
		public void setDb9(DBItemConfig db9) {
			this.db9 = db9;
		}
	}

	public static class DBItemConfig {
		private String dialect = "MySqlDialect";
		private String driverClassName = "com.mysql.cj.jdbc.Driver";
		private String provideName = "_default";
		private String url;
		private String username;
		private String password;
		private int minimumIdle = 5;
		private int maximumPoolSize = 10;
		private String connectionTestQuery;
		private int ConnectionTimeout = 10000;

		public String getDialect() {
			return dialect;
		}

		public void setDialect(String dialect) {
			this.dialect = dialect;
		}

		public String getDriverClassName() {
			return driverClassName;
		}

		public void setDriverClassName(String driverClassName) {
			this.driverClassName = driverClassName;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getProvideName() {
			return provideName;
		}

		public void setProvideName(String provideName) {
			this.provideName = provideName;
		}

		public int getMinimumIdle() {
			return minimumIdle;
		}

		public void setMinimumIdle(int minimumIdle) {
			this.minimumIdle = minimumIdle;
		}

		public int getMaximumPoolSize() {
			return maximumPoolSize;
		}

		public void setMaximumPoolSize(int maximumPoolSize) {
			this.maximumPoolSize = maximumPoolSize;
		}

		public String getConnectionTestQuery() {
			return connectionTestQuery;
		}

		public void setConnectionTestQuery(String connectionTestQuery) {
			this.connectionTestQuery = connectionTestQuery;
		}

		public int getConnectionTimeout() {
			return ConnectionTimeout;
		}

		public void setConnectionTimeout(int connectionTimeout) {
			ConnectionTimeout = connectionTimeout;
		}
	}

	public static class WebConfig {

		private String i18nProvide;
		private String encoding = "UTF-8";
		private boolean openSession = true;
		private ApiExposeStrategy apiExposeStrategy = ApiExposeStrategy.dbfound_request_handler;
		private String basePath="";
		private boolean jsonStringAutoCover = true;

		public String getI18nProvide() {
			return i18nProvide;
		}

		public void setI18nProvide(String i18nProvide) {
			this.i18nProvide = i18nProvide;
		}

		public String getEncoding() {
			return encoding;
		}

		public void setEncoding(String encoding) {
			this.encoding = encoding;
		}

		public boolean isOpenSession() {
			return openSession;
		}

		public void setOpenSession(boolean openSession) {
			this.openSession = openSession;
		}

		public String getBasePath() {
			return basePath;
		}

		public void setBasePath(String basePath) {
			this.basePath = basePath;
		}

		public boolean isJsonStringAutoCover() {
			return jsonStringAutoCover;
		}

		public void setJsonStringAutoCover(boolean jsonStringAutoCover) {
			this.jsonStringAutoCover = jsonStringAutoCover;
		}

		public ApiExposeStrategy getApiExposeStrategy() {
			return apiExposeStrategy;
		}

		public void setApiExposeStrategy(ApiExposeStrategy apiExposeStrategy) {
			this.apiExposeStrategy = apiExposeStrategy;
		}
	}

	public static class SystemConfig {
		private boolean openLog = true;
		private boolean logWithParamSql = false;
		private boolean underscoreToCamelCase = false;
		private boolean camelCaseToUnderscore = false;
		private String modelRootPath = DBFoundConfig.CLASSPATH + "/model";
		private boolean modelModifyCheck = false;
		private String dateFormat = "yyyy-MM-dd";
		private String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
		private boolean openDSql = true;
		private boolean sqlCompareIgnoreCase = true;
		private Isolation transactionIsolation = Isolation.DEFAULT;

		public String getModelRootPath() {
			return modelRootPath;
		}

		public void setModelRootPath(String modelRootPath) {
			this.modelRootPath = modelRootPath;
		}

		public boolean isOpenLog() {
			return openLog;
		}

		public void setOpenLog(boolean openLog) {
			this.openLog = openLog;
		}

		public boolean isUnderscoreToCamelCase() {
			return underscoreToCamelCase;
		}

		public void setUnderscoreToCamelCase(boolean underscoreToCamelCase) {
			this.underscoreToCamelCase = underscoreToCamelCase;
		}

		public boolean isModelModifyCheck() {
			return modelModifyCheck;
		}

		public void setModelModifyCheck(boolean modelModifyCheck) {
			this.modelModifyCheck = modelModifyCheck;
		}

		public String getDateFormat() {
			return dateFormat;
		}

		public void setDateFormat(String dateFormat) {
			this.dateFormat = dateFormat;
		}

		public String getDateTimeFormat() {
			return dateTimeFormat;
		}

		public void setDateTimeFormat(String dateTimeFormat) {
			this.dateTimeFormat = dateTimeFormat;
		}

		public boolean isOpenDSql() {
			return openDSql;
		}

		public void setOpenDSql(boolean openDSql) {
			this.openDSql = openDSql;
		}

		public boolean isSqlCompareIgnoreCase() {
			return sqlCompareIgnoreCase;
		}

		public void setSqlCompareIgnoreCase(boolean sqlCompareIgnoreCase) {
			this.sqlCompareIgnoreCase = sqlCompareIgnoreCase;
		}

		public Isolation getTransactionIsolation() {
			return transactionIsolation;
		}

		public void setTransactionIsolation(Isolation transactionIsolation) {
			this.transactionIsolation = transactionIsolation;
		}

		public boolean isLogWithParamSql() {
			return logWithParamSql;
		}

		public void setLogWithParamSql(boolean logWithParamSql) {
			this.logWithParamSql = logWithParamSql;
		}

		public boolean isCamelCaseToUnderscore() {
			return camelCaseToUnderscore;
		}

		public void setCamelCaseToUnderscore(boolean camelCaseToUnderscore) {
			this.camelCaseToUnderscore = camelCaseToUnderscore;
		}
	}

	public enum ApiExposeStrategy{
		dbfound_request_handler, dbfound_default_controller, none
	}

}
