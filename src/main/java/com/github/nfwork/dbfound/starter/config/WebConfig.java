package com.github.nfwork.dbfound.starter.config;

public class WebConfig {

	private String i18nProvide;
	private String encoding = "utf-8";
	private String uploadFolder;
	private int maxUploadSize = 10;

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

	public String getUploadFolder() {
		return uploadFolder;
	}

	public void setUploadFolder(String uploadFolder) {
		this.uploadFolder = uploadFolder;
	}

	public int getMaxUploadSize() {
		return maxUploadSize;
	}

	public void setMaxUploadSize(int maxUploadSize) {
		this.maxUploadSize = maxUploadSize;
	}

}