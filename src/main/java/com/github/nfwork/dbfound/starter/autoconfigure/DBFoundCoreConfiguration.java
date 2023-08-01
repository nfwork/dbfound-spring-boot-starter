package com.github.nfwork.dbfound.starter.autoconfigure;

import com.github.nfwork.dbfound.starter.dbprovide.DBFoundTransactionManager;
import com.github.nfwork.dbfound.starter.model.SpringAdapterFactory;
import com.nfwork.dbfound.util.LogUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.transaction.PlatformTransactionManager;

import com.github.nfwork.dbfound.starter.DBFoundEngine;
import com.github.nfwork.dbfound.starter.ModelExecutor;
import com.nfwork.dbfound.core.DBFoundConfig;

@Configuration
public class DBFoundCoreConfiguration implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Autowired
	private DBFoundConfigProperties config;

	@Bean(destroyMethod = "destroy")
	public DBFoundEngine dbFoundEngine() throws Exception {

		DBFoundEngine dbFoundEngine = new DBFoundEngine();
		
		DBFoundConfig.setInited(true);

		LogUtil.info("NFWork dbfound "+ DBFoundConfig.VERSION+" engine init begin");

		// init system
		dbFoundEngine.initSystem(config.getSystem());

		// init web
		dbFoundEngine.initWeb(config.getWeb());

		// init db
		dbFoundEngine.initDBItem(config.getDatasource().db0);
		dbFoundEngine.initDBItem(config.getDatasource().db1);
		dbFoundEngine.initDBItem(config.getDatasource().db2);
		dbFoundEngine.initDBItem(config.getDatasource().db3);
		dbFoundEngine.initDBItem(config.getDatasource().db4);
		dbFoundEngine.initDBItem(config.getDatasource().db5);
		dbFoundEngine.initDBItem(config.getDatasource().db6);
		dbFoundEngine.initDBItem(config.getDatasource().db7);
		dbFoundEngine.initDBItem(config.getDatasource().db8);
		dbFoundEngine.initDBItem(config.getDatasource().db9);

		for(DBFoundConfigProperties.DBItemConfig dbItemConfig : config.getDatasourceExtension().values()){
			dbFoundEngine.initDBItem(dbItemConfig);
		}
		//init adapter factory
		new SpringAdapterFactory(applicationContext);

		return dbFoundEngine;
	}

	@Bean
	@DependsOn("dbFoundEngine")
	public ModelExecutor modelExecutor() {
		return new ModelExecutor();
	}

	@Bean
	public PlatformTransactionManager dbfoundTransactionManager(ModelExecutor modelExecutor){
		return new DBFoundTransactionManager(modelExecutor,config.getSystem().getTransactionIsolation());
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}