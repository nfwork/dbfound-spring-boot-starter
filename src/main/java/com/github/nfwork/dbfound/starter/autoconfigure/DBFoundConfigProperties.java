package com.github.nfwork.dbfound.starter.autoconfigure;

import com.nfwork.dbfound.core.DBFoundConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "dbfound", ignoreUnknownFields = false)
public class DBFoundConfigProperties {

	SystemConfig system = new SystemConfig();

	WebConfig web = new WebConfig();

	DataSrouce datasource = new DataSrouce();

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
		DBItemconfig db0 = new DBItemconfig();
		DBItemconfig db1 = new DBItemconfig();
		DBItemconfig db2 = new DBItemconfig();
		DBItemconfig db3 = new DBItemconfig();
		DBItemconfig db4 = new DBItemconfig();
		DBItemconfig db5 = new DBItemconfig();
		DBItemconfig db6 = new DBItemconfig();
		DBItemconfig db7 = new DBItemconfig();
		DBItemconfig db8 = new DBItemconfig();
		DBItemconfig db9 = new DBItemconfig();

		DBItemconfig db10 = new DBItemconfig();
		DBItemconfig db11 = new DBItemconfig();
		DBItemconfig db12 = new DBItemconfig();
		DBItemconfig db13 = new DBItemconfig();
		DBItemconfig db14 = new DBItemconfig();
		DBItemconfig db15 = new DBItemconfig();
		DBItemconfig db16 = new DBItemconfig();
		DBItemconfig db17 = new DBItemconfig();
		DBItemconfig db18 = new DBItemconfig();
		DBItemconfig db19 = new DBItemconfig();

		public DBItemconfig getDb0() {
			return db0;
		}
		public void setDb0(DBItemconfig db0) {
			this.db0 = db0;
		}
		public DBItemconfig getDb1() {
			return db1;
		}
		public void setDb1(DBItemconfig db1) {
			this.db1 = db1;
		}
		public DBItemconfig getDb2() {
			return db2;
		}
		public void setDb2(DBItemconfig db2) {
			this.db2 = db2;
		}
		public DBItemconfig getDb3() {
			return db3;
		}
		public void setDb3(DBItemconfig db3) {
			this.db3 = db3;
		}
		public DBItemconfig getDb4() {
			return db4;
		}
		public void setDb4(DBItemconfig db4) {
			this.db4 = db4;
		}
		public DBItemconfig getDb5() {
			return db5;
		}
		public void setDb5(DBItemconfig db5) {
			this.db5 = db5;
		}
		public DBItemconfig getDb6() {
			return db6;
		}
		public void setDb6(DBItemconfig db6) {
			this.db6 = db6;
		}
		public DBItemconfig getDb7() {
			return db7;
		}
		public void setDb7(DBItemconfig db7) {
			this.db7 = db7;
		}
		public DBItemconfig getDb8() {
			return db8;
		}
		public void setDb8(DBItemconfig db8) {
			this.db8 = db8;
		}
		public DBItemconfig getDb9() {
			return db9;
		}
		public void setDb9(DBItemconfig db9) {
			this.db9 = db9;
		}

		public DBItemconfig getDb10() {
			return db10;
		}

		public void setDb10(DBItemconfig db10) {
			this.db10 = db10;
		}

		public DBItemconfig getDb11() {
			return db11;
		}

		public void setDb11(DBItemconfig db11) {
			this.db11 = db11;
		}

		public DBItemconfig getDb12() {
			return db12;
		}

		public void setDb12(DBItemconfig db12) {
			this.db12 = db12;
		}

		public DBItemconfig getDb13() {
			return db13;
		}

		public void setDb13(DBItemconfig db13) {
			this.db13 = db13;
		}

		public DBItemconfig getDb14() {
			return db14;
		}

		public void setDb14(DBItemconfig db14) {
			this.db14 = db14;
		}

		public DBItemconfig getDb15() {
			return db15;
		}

		public void setDb15(DBItemconfig db15) {
			this.db15 = db15;
		}

		public DBItemconfig getDb16() {
			return db16;
		}

		public void setDb16(DBItemconfig db16) {
			this.db16 = db16;
		}

		public DBItemconfig getDb17() {
			return db17;
		}

		public void setDb17(DBItemconfig db17) {
			this.db17 = db17;
		}

		public DBItemconfig getDb18() {
			return db18;
		}

		public void setDb18(DBItemconfig db18) {
			this.db18 = db18;
		}

		public DBItemconfig getDb19() {
			return db19;
		}

		public void setDb19(DBItemconfig db19) {
			this.db19 = db19;
		}
	}

	public  static  class DBItemconfig {
		private String dialect = "MySqlDialect";
		private String driverClassName = "com.mysql.jdbc.Driver";
		private String provideName = "_default";
		private String url = "";
		private String username = "";
		private String password = "";
		private int initialSize = 5;
		private int maxActive = 10;
		private int maxIdle = 5;
		private boolean testOnBorrow = true;
		private String validationQuery = "select 1";

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

		public int getInitialSize() {
			return initialSize;
		}

		public void setInitialSize(int initialSize) {
			this.initialSize = initialSize;
		}

		public int getMaxActive() {
			return maxActive;
		}

		public void setMaxActive(int maxActive) {
			this.maxActive = maxActive;
		}

		public int getMaxIdle() {
			return maxIdle;
		}

		public void setMaxIdle(int maxIdle) {
			this.maxIdle = maxIdle;
		}

		public boolean isTestOnBorrow() {
			return testOnBorrow;
		}

		public void setTestOnBorrow(boolean testOnBorrow) {
			this.testOnBorrow = testOnBorrow;
		}

		public String getValidationQuery() {
			return validationQuery;
		}

		public void setValidationQuery(String validationQuery) {
			this.validationQuery = validationQuery;
		}

		public String getProvideName() {
			return provideName;
		}

		public void setProvideName(String provideName) {
			this.provideName = provideName;
		}
	}

	public static class WebConfig {

		private String i18nProvide;
		private String encoding = "utf-8";
		private boolean openSession = true;
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
	}

	public static class SystemConfig {
		private boolean openLog = true;
		private boolean underscoreToCamelCase = false;
		private String modeRootPath = DBFoundConfig.CLASSPATH + "/model";
		private boolean queryLimit = true;
		private int queryLimitSize = 5000;
		private int reportQueryLimitSize = 50000;
		private boolean ModelModifyCheck = true;
		private String DateFormat = "yyyy-MM-dd";
		private String DateTimeFormat = "yyyy-MM-dd HH:mm:ss";

		public String getModeRootPath() {
			return modeRootPath;
		}

		public void setModeRootPath(String modeRootPath) {
			this.modeRootPath = modeRootPath;
		}

		public boolean isOpenLog() {
			return openLog;
		}

		public void setOpenLog(boolean openLog) {
			this.openLog = openLog;
		}

		public boolean isQueryLimit() {
			return queryLimit;
		}

		public void setQueryLimit(boolean queryLimit) {
			this.queryLimit = queryLimit;
		}

		public int getQueryLimitSize() {
			return queryLimitSize;
		}

		public void setQueryLimitSize(int queryLimitSize) {
			this.queryLimitSize = queryLimitSize;
		}

		public int getReportQueryLimitSize() {
			return reportQueryLimitSize;
		}

		public void setReportQueryLimitSize(int reportQueryLimitSize) {
			this.reportQueryLimitSize = reportQueryLimitSize;
		}

		public boolean isUnderscoreToCamelCase() {
			return underscoreToCamelCase;
		}

		public void setUnderscoreToCamelCase(boolean underscoreToCamelCase) {
			this.underscoreToCamelCase = underscoreToCamelCase;
		}

		public boolean isModelModifyCheck() {
			return ModelModifyCheck;
		}

		public void setModelModifyCheck(boolean modelModifyCheck) {
			ModelModifyCheck = modelModifyCheck;
		}

		public String getDateFormat() {
			return DateFormat;
		}

		public void setDateFormat(String dateFormat) {
			DateFormat = dateFormat;
		}

		public String getDateTimeFormat() {
			return DateTimeFormat;
		}

		public void setDateTimeFormat(String dateTimeFormat) {
			DateTimeFormat = dateTimeFormat;
		}
	}
}
