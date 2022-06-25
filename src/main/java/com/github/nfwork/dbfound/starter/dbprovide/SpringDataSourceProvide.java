package com.github.nfwork.dbfound.starter.dbprovide;

import java.sql.Connection;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.datasource.DataSourceUtils;

import com.nfwork.dbfound.db.DataSourceConnectionProvide;

public class SpringDataSourceProvide extends DataSourceConnectionProvide {

	private boolean joinChainedTransaction = true;

	public SpringDataSourceProvide(String provideName, BasicDataSource ds,
			String dialect) {
		super(provideName, ds, dialect);
	}

	@Override
	public void closeConnection(Connection connection) {
		DataSourceUtils.releaseConnection(connection, getDataSource());
	}

	@Override
	public Connection getConnection() {
		return DataSourceUtils.getConnection(getDataSource());
	}

	public boolean isJoinChainedTransaction() {
		return joinChainedTransaction;
	}

	public void setJoinChainedTransaction(boolean joinChainedTransaction) {
		this.joinChainedTransaction = joinChainedTransaction;
	}
}
