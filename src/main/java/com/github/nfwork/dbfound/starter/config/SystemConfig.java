package com.github.nfwork.dbfound.starter.config;

import com.nfwork.dbfound.core.DBFoundConfig;

public class SystemConfig {
	private boolean openLog = true;
	private boolean underscoreToCamelCase = false;
	private String modeRootPath = DBFoundConfig.CLASSPATH + "/model";
	private boolean queryLimit = true;
	private int queryLimitSize = 5000;
	private int reportQueryLimitSize = 50000;


	public String getModeRootPath() {
		return modeRootPath;
	}

	public void setModeRootPath(String modeRootPath) {
		this.modeRootPath = modeRootPath;
	}

	public boolean isOpenLog() {
		return openLog;
	}

	public void setOpenLog(boolean openLog) {
		this.openLog = openLog;
	}

	public boolean isQueryLimit() {
		return queryLimit;
	}

	public void setQueryLimit(boolean queryLimit) {
		this.queryLimit = queryLimit;
	}

	public int getQueryLimitSize() {
		return queryLimitSize;
	}

	public void setQueryLimitSize(int queryLimitSize) {
		this.queryLimitSize = queryLimitSize;
	}

	public int getReportQueryLimitSize() {
		return reportQueryLimitSize;
	}

	public void setReportQueryLimitSize(int reportQueryLimitSize) {
		this.reportQueryLimitSize = reportQueryLimitSize;
	}

	public boolean isUnderscoreToCamelCase() {
		return underscoreToCamelCase;
	}

	public void setUnderscoreToCamelCase(boolean underscoreToCamelCase) {
		this.underscoreToCamelCase = underscoreToCamelCase;
	}
}