package com.github.nfwork.dbfound.starter;

import java.util.List;

import com.nfwork.dbfound.model.dsql.DSqlConfig;
import com.nfwork.dbfound.util.DataUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import com.github.nfwork.dbfound.starter.autoconfigure.DBFoundConfigProperties.DBItemConfig;
import com.github.nfwork.dbfound.starter.autoconfigure.DBFoundConfigProperties.SystemConfig;
import com.github.nfwork.dbfound.starter.autoconfigure.DBFoundConfigProperties.WebConfig;
import com.github.nfwork.dbfound.starter.dbprovide.SpringDataSourceProvide;
import com.nfwork.dbfound.core.DBFoundConfig;
import com.nfwork.dbfound.db.DataSourceConnectionProvide;
import com.nfwork.dbfound.util.JsonUtil;
import com.nfwork.dbfound.util.LogUtil;
import com.nfwork.dbfound.web.i18n.MultiLangUtil;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import javax.sql.DataSource;

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
		DBFoundConfig.setLogWithParamSql(config.isLogWithParamSql());
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
	public void initDBItem(DBItemConfig config, ApplicationContext applicationContext){
		if (DataUtil.isNotNull(config.getUrl())) {
			HikariConfig hikari = new HikariConfig();
			hikari.setPoolName(getDataSourceName(config.getProvideName()));
			hikari.setJdbcUrl(config.getUrl());
			hikari.setDriverClassName(config.getDriverClassName());
			hikari.setPassword(config.getPassword());
			hikari.setUsername(config.getUsername());
			hikari.setConnectionTimeout(config.getConnectionTimeout());
			hikari.setConnectionTestQuery(config.getConnectionTestQuery());
			hikari.setMaximumPoolSize(config.getMaximumPoolSize());
			hikari.setMinimumIdle(config.getMinimumIdle());

			HikariDataSource ds = new HikariDataSource(hikari);
			addToContext(applicationContext, ds.getPoolName(), ds);
			SpringDataSourceProvide provide = new SpringDataSourceProvide(config.getProvideName(), ds, config.getDialect());
			provide.register();
			LogUtil.info("dbfound engine init datasource success, provideName:" +config.getProvideName() +", url:"+config.getUrl());
		}
	}

	public static String getDataSourceName(String provideName){
		return "dataSource_" + (provideName.startsWith("_")?provideName.substring(1):provideName);
	}

	private boolean isPrimary = true;
	private void addToContext(ApplicationContext applicationContext, String name ,HikariDataSource dataSource){
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(DataSource.class, () -> dataSource);
		BeanDefinition beanDefinition = builder.getRawBeanDefinition();
		beanDefinition.setPrimary(isPrimary);
		isPrimary = false;
		ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;
		DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();
		defaultListableBeanFactory.registerBeanDefinition(name, beanDefinition);
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
