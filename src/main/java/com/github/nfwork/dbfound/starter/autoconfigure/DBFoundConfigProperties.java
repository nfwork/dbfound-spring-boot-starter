package com.github.nfwork.dbfound.starter.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.github.nfwork.dbfound.starter.config.DBItemconfig;
import com.github.nfwork.dbfound.starter.config.SystemConfig;
import com.github.nfwork.dbfound.starter.config.WebConfig;

@ConfigurationProperties(prefix = "dbfound", ignoreUnknownFields = false)
public class DBFoundConfigProperties {

	SystemConfig system = new SystemConfig();

	WebConfig web = new WebConfig();

	DataSrouce datasource = new DataSrouce();

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
	}

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
	
}
