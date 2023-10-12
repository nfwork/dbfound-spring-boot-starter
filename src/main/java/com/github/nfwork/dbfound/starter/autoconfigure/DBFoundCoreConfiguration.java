package com.github.nfwork.dbfound.starter.autoconfigure;

import com.github.nfwork.dbfound.starter.dbprovide.DBFoundRoutingDataSource;
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
	public DBFoundEngine dbFoundEngine() {

		DBFoundEngine dbFoundEngine = new DBFoundEngine();
		
		DBFoundConfig.setInited(true);

		LogUtil.info("NFWork dbfound "+ DBFoundConfig.VERSION+" engine init begin");

		// init system
		dbFoundEngine.initSystem(config.getSystem());

		// init web
		dbFoundEngine.initWeb(config.getWeb());

		// init db
		dbFoundEngine.initDBItem(config.getDatasource().db0,applicationContext);
		dbFoundEngine.initDBItem(config.getDatasource().db1,applicationContext);
		dbFoundEngine.initDBItem(config.getDatasource().db2,applicationContext);
		dbFoundEngine.initDBItem(config.getDatasource().db3,applicationContext);
		dbFoundEngine.initDBItem(config.getDatasource().db4,applicationContext);
		dbFoundEngine.initDBItem(config.getDatasource().db5,applicationContext);
		dbFoundEngine.initDBItem(config.getDatasource().db6,applicationContext);
		dbFoundEngine.initDBItem(config.getDatasource().db7,applicationContext);
		dbFoundEngine.initDBItem(config.getDatasource().db8,applicationContext);
		dbFoundEngine.initDBItem(config.getDatasource().db9,applicationContext);

		for(DBFoundConfigProperties.DBItemConfig dbItemConfig : config.getDatasourceExtension().values()){
			dbFoundEngine.initDBItem(dbItemConfig,applicationContext);
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

	/**
	 * 声明一个DBFoundRoutingDataSource，解决dbfound数据源 health监控不到问题
	 * @return null
	 */
	@Bean
	public DBFoundRoutingDataSource dbfoundRoutingDataSource(){
		return new DBFoundRoutingDataSource();
	}

}
