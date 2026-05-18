package com.github.nfwork.dbfound.starter;

import com.nfwork.dbfound.util.DataUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import com.github.nfwork.dbfound.starter.autoconfigure.DBFoundConfigProperties.DBItemConfig;
import com.github.nfwork.dbfound.starter.autoconfigure.DBFoundConfigProperties.SystemConfig;
import com.github.nfwork.dbfound.starter.autoconfigure.DBFoundConfigProperties.WebConfig;
import com.github.nfwork.dbfound.starter.dbprovide.SpringDataSourceProvide;
import com.nfwork.dbfound.core.DBFoundConfig;
import com.nfwork.dbfound.core.DBFoundInitToken;
import com.nfwork.dbfound.util.JsonUtil;
import com.nfwork.dbfound.util.LogUtil;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

/**
 * DBFoundEngine
 * @author John
 * 2019-11-15 15:24:56
 */
public class DBFoundEngine {
	
	private SystemConfig systemConfig;
	
	private WebConfig webConfig;

	private DBFoundInitToken dbfoundInitToken;


	/**
	 * init dbfound config
	 *
	 */
	public void init(SystemConfig systemConfig, WebConfig webConfig, ServletContext servletContext) {
		this.systemConfig = systemConfig;
		this.webConfig = webConfig;
		dbfoundInitToken = DBFoundConfig.initSpringBoot(createConfigDocument(systemConfig, webConfig), servletContext);
		LogUtil.info("dbfound engine init system success, config:"+JsonUtil.toJson(systemConfig));
		LogUtil.info("dbfound engine init web success, config:"+JsonUtil.toJson(webConfig));
	}

	private Document createConfigDocument(SystemConfig systemConfig, WebConfig webConfig) {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("dbfound");

		Element system = root.addElement("system");
		addElement(system, "openLog", systemConfig.isOpenLog());
		addElement(system, "logWithParamSql", systemConfig.isLogWithParamSql());
		addElement(system, "underscoreToCamelCase", systemConfig.isUnderscoreToCamelCase());
		addElement(system, "camelCaseToUnderscore", systemConfig.isCamelCaseToUnderscore());
		addElement(system, "modelRootPath", systemConfig.getModelRootPath());
		addElement(system, "modelOperator", systemConfig.getModelOperator());
		addElement(system, "modelModifyCheck", systemConfig.isModelModifyCheck());
		addElement(system, "dateFormat", systemConfig.getDateFormat());
		addElement(system, "dateTimeFormat", systemConfig.getDateTimeFormat());
		addElement(system, "sqlCompareIgnoreCase", systemConfig.isSqlCompareIgnoreCase());
		addElement(system, "openDSql", systemConfig.isOpenDSql());

		Element web = root.addElement("web");
		addElement(web, "i18nProvide", webConfig.getI18nProvide());
		addElement(web, "encoding", webConfig.getEncoding());
		addElement(web, "jsonStringAutoCover", webConfig.isJsonStringAutoCover());
		addElement(web, "basePath", webConfig.getBasePath());
		addElement(web, "openSession", webConfig.isOpenSession());

		return document;
	}

	private void addElement(Element parent, String name, Object value) {
		if (value != null) {
			parent.addElement(name).setText(String.valueOf(value));
		}
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
	 * destroy dbfound engine
	 */
	public void destroy() {
		LogUtil.info("NFWork dbfound " + DBFoundConfig.VERSION + " engine destroy begin");
		if (dbfoundInitToken != null) {
			DBFoundConfig.destroy(dbfoundInitToken);
		} else {
			LogUtil.info("dbfound engine destroy skipped, because init token is null");
		}
		LogUtil.info("NFWork dbfound " + DBFoundConfig.VERSION + " engine destroy success");
	}

	public SystemConfig getSystemConfig() {
		return systemConfig;
	}

	public WebConfig getWebConfig() {
		return webConfig;
	}

}
