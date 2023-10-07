package com.github.nfwork.dbfound.starter.dbprovide;

import java.sql.Connection;

import org.springframework.jdbc.datasource.DataSourceUtils;

import com.nfwork.dbfound.db.DataSourceConnectionProvide;

import javax.sql.DataSource;

public class SpringDataSourceProvide extends DataSourceConnectionProvide {

	public SpringDataSourceProvide(String provideName, DataSource ds,
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

}
