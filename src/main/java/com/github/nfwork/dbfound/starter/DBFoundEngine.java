package com.github.nfwork.dbfound.starter;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.util.StringUtils;

import com.github.nfwork.dbfound.starter.autoconfigure.DBFoundConfigProperties.DBItemconfig;
import com.github.nfwork.dbfound.starter.autoconfigure.DBFoundConfigProperties.SystemConfig;
import com.github.nfwork.dbfound.starter.autoconfigure.DBFoundConfigProperties.WebConfig;
import com.github.nfwork.dbfound.starter.dbprovide.SpringDataSourceProvide;
import com.nfwork.dbfound.core.Context;
import com.nfwork.dbfound.core.DBFoundConfig;
import com.nfwork.dbfound.db.DataSourceConnectionProvide;
import com.nfwork.dbfound.model.ModelReader;
import com.nfwork.dbfound.util.JsonUtil;
import com.nfwork.dbfound.util.LogUtil;
import com.nfwork.dbfound.util.URLUtil;
import com.nfwork.dbfound.web.WebWriter;
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
	 * @param config
	 */
	public void initSystem(SystemConfig config) {
		this.systemConfig = config;
		LogUtil.setOpenLog(config.isOpenLog());
		ModelReader.setModelLoadRoot(config.getModeRootPath());
		DBFoundConfig.setQueryLimit(config.isQueryLimit());
		DBFoundConfig.setQueryLimitSize(config.getQueryLimitSize());
		DBFoundConfig.setReportQueryLimitSize(config.getReportQueryLimitSize());
		DBFoundConfig.setUnderscoreToCamelCase(config.isUnderscoreToCamelCase());
		DBFoundConfig.setDateFormat(config.getDateFormat());
		DBFoundConfig.setDateTimeFormat(config.getDateTimeFormat());
		DBFoundConfig.setModelModifyCheck(config.isModelModifyCheck());
		LogUtil.info("dbfound engine init system success, config:"+JsonUtil.beanToJson(systemConfig));
	}

	/**
	 * init web config
	 * 
	 * @param config
	 */
	public void initWeb(WebConfig config) {
		this.webConfig = config;
		if (config.getI18nProvide() != null) {
			MultiLangUtil.init(config.getI18nProvide());
		}
		WebWriter.setEncoding(config.getEncoding());
		URLUtil.setBasePath(webConfig.getBasePath());
		Context.setOpenSession(webConfig.isOpenSession());
		DBFoundConfig.setJsonStringAutoCover(config.isJsonStringAutoCover());
		LogUtil.info("dbfound engine init web success, config:"+JsonUtil.beanToJson(webConfig));
	}

	/**
	 * init dbitem config
	 * 
	 * @param dbconfig
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public void initDBItem(DBItemconfig dbconfig) throws IllegalAccessException, InvocationTargetException {
		if (!StringUtils.isEmpty(dbconfig.getUrl())) {
			BasicDataSource ds = new BasicDataSource();
			BeanUtils.copyProperties(ds, dbconfig);
			SpringDataSourceProvide provide = new SpringDataSourceProvide(dbconfig.getProvideName(), ds,
					dbconfig.getDialect());
			provide.regist();
			DBFoundConfig.getDsp().add(provide);
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
	 * destory dbfound engine
	 */
	public void destory() {
		DBFoundConfig.destory();
		LogUtil.info("dbfound engine destory success");
	}

	public SystemConfig getSystemConfig() {
		return systemConfig;
	}

	public WebConfig getWebConfig() {
		return webConfig;
	}

}
