package com.github.nfwork.dbfound.starter.autoconfigure;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.github.nfwork.dbfound.starter.DBFoundEngine;
import com.github.nfwork.dbfound.starter.ModelExecutor;
import com.github.nfwork.dbfound.starter.controller.DBFoundDefaultController;
import com.github.nfwork.dbfound.starter.exception.DBFoundExceptionhandle;
import com.github.nfwork.dbfound.starter.service.DBFoundDefaultService;
import com.nfwork.dbfound.core.DBFoundConfig;
import com.nfwork.dbfound.db.DataSourceConnectionProvide;
import com.nfwork.dbfound.exception.DBFoundRuntimeException;

@Configuration
public class DBFoundAutoConfigure {

	@Autowired
	private DBFoundConfigProperties config;

	@Bean(destroyMethod = "destory")
	public DBFoundEngine dbFoundEngine() throws Exception {

		DBFoundEngine dbFoundEngine = new DBFoundEngine();

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

		DBFoundConfig.setInited(true);

		return dbFoundEngine;
	}

	@Bean
	public DBFoundDefaultController dbFoundDefaultController() {
		return new DBFoundDefaultController();
	}

	@Bean
	public DBFoundDefaultService dbFoundDefaultService() {
		return new DBFoundDefaultService();
	}

	@Bean
	public DBFoundExceptionhandle dbFoundExceptionhandle() {
		return new DBFoundExceptionhandle();
	}

	@Bean
	public ModelExecutor modelExecutor() {
		return new ModelExecutor();
	}

	@Bean
	public ChainedTransactionManager dbfoundTransactionManager(DBFoundEngine dbFoundEngine) {
		List<DataSourceConnectionProvide> provideList = dbFoundEngine.getDatasourceProvideList();
		if (provideList == null || provideList.isEmpty()) {
			throw new DBFoundRuntimeException("init dbfound engine failed, at leat have one datasource config in springboot config file");
		}
		PlatformTransactionManager[] pmanagerList = new PlatformTransactionManager[provideList.size()];
		int index = 0;
		for (DataSourceConnectionProvide dataSourceConnectionProvide : provideList) {
			pmanagerList[index++] = new DataSourceTransactionManager(dataSourceConnectionProvide.getDataSource());
		}
		ChainedTransactionManager chainedManager = new ChainedTransactionManager(pmanagerList);
		return chainedManager;
	}
}
