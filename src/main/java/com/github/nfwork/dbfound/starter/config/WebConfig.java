package com.github.nfwork.dbfound.starter.config;

public class WebConfig {

	private String i18nProvide;
	private String encoding = "utf-8";
	private boolean openSession = true;

	public String getI18nProvide() {
		return i18nProvide;
	}

	public void setI18nProvide(String i18nProvide) {
		this.i18nProvide = i18nProvide;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public boolean isOpenSession() {
		return openSession;
	}

	public void setOpenSession(boolean openSession) {
		this.openSession = openSession;
	}

}