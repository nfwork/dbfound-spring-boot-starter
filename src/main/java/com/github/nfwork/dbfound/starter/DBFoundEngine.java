package com.github.nfwork.dbfound.starter;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.nfwork.dbfound.model.dsql.DSqlConfig;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.util.StringUtils;

import com.github.nfwork.dbfound.starter.autoconfigure.DBFoundConfigProperties.DBItemConfig;
import com.github.nfwork.dbfound.starter.autoconfigure.DBFoundConfigProperties.SystemConfig;
import com.github.nfwork.dbfound.starter.autoconfigure.DBFoundConfigProperties.WebConfig;
import com.github.nfwork.dbfound.starter.dbprovide.SpringDataSourceProvide;
import com.nfwork.dbfound.core.DBFoundConfig;
import com.nfwork.dbfound.db.DataSourceConnectionProvide;
import com.nfwork.dbfound.util.JsonUtil;
import com.nfwork.dbfound.util.LogUtil;
import com.nfwork.dbfound.web.i18n.MultiLangUtil;

/**
 * DBFoundEngine
 * @author John
 * 2019-11-15 15:24:56
 */
public class DBFoundEngine {
	
	private SystemConfig systemConfig;
	
	private WebConfig webConfig;


	/**
	 * init system config
	 *
	 */
	public void initSystem(SystemConfig config) {
		this.systemConfig = config;
		DBFoundConfig.setOpenLog(config.isOpenLog());
		DBFoundConfig.setModelLoadRoot(config.getModelRootPath());
		DBFoundConfig.setUnderscoreToCamelCase(config.isUnderscoreToCamelCase());
		DBFoundConfig.setCamelCaseToUnderscore(config.isCamelCaseToUnderscore());
		DBFoundConfig.setDateFormat(config.getDateFormat());
		DBFoundConfig.setDateTimeFormat(config.getDateTimeFormat());
		DBFoundConfig.setModelModifyCheck(config.isModelModifyCheck());
		DSqlConfig.setCompareIgnoreCase(config.isSqlCompareIgnoreCase());
		DSqlConfig.setOpenDSql(config.isOpenDSql());
		LogUtil.info("dbfound engine init system success, config:"+JsonUtil.toJson(systemConfig));
	}

	/**
	 * init web config
	 *
	 */
	public void initWeb(WebConfig config) {
		this.webConfig = config;
		if (config.getI18nProvide() != null) {
			MultiLangUtil.init(config.getI18nProvide());
		}
		DBFoundConfig.setEncoding(config.getEncoding());
		DBFoundConfig.setBasePath(webConfig.getBasePath());
		DBFoundConfig.setOpenSession(webConfig.isOpenSession());
		DBFoundConfig.setJsonStringAutoCover(config.isJsonStringAutoCover());
		LogUtil.info("dbfound engine init web success, config:"+JsonUtil.toJson(webConfig));
	}

	/**
	 * init dbitem config
	 *
	 */
	public void initDBItem(DBItemConfig dbconfig) throws IllegalAccessException, InvocationTargetException {
		if (!StringUtils.isEmpty(dbconfig.getUrl())) {
			BasicDataSource ds = new BasicDataSource();
			ds.setUrl(dbconfig.getUrl());
			ds.setDriverClassName(dbconfig.getDriverClassName());
			ds.setUsername(dbconfig.getUsername());
			ds.setPassword(dbconfig.getPassword());
			ds.setInitialSize(dbconfig.getInitialSize());
			ds.setMaxWait(dbconfig.getMaxWait());
			ds.setMaxIdle(dbconfig.getMaxIdle());
			ds.setMaxActive(dbconfig.getMaxActive());
			ds.setTestOnBorrow(dbconfig.isTestOnBorrow());
			ds.setValidationQuery(dbconfig.getValidationQuery());

			SpringDataSourceProvide provide = new SpringDataSourceProvide(dbconfig.getProvideName(), ds, dbconfig.getDialect());

			provide.regist();
			LogUtil.info("dbfound engine init datasource success, provideName:" +dbconfig.getProvideName() +", url:"+dbconfig.getUrl());
		}
	}
	
	/**
	 * get Datasource Provide List
	 */
	public  List<DataSourceConnectionProvide> getDatasourceProvideList() {
		return DBFoundConfig.getDsp();
	}

	/**
	 * destroy dbfound engine
	 */
	public void destroy() {
		LogUtil.info("NFWork dbfound " + DBFoundConfig.VERSION + " engine destroy begin");
		DBFoundConfig.destroy();
		LogUtil.info("NFWork dbfound " + DBFoundConfig.VERSION + " engine destroy success");
	}

	public SystemConfig getSystemConfig() {
		return systemConfig;
	}

	public WebConfig getWebConfig() {
		return webConfig;
	}

}
