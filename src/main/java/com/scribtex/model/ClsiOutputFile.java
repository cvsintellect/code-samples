package com.scribtex.model;

import java.io.Serializable;

public class ClsiOutputFile implements Serializable {
	private static final long serialVersionUID = 1L;

	private String type, mimeType, url;

	public ClsiOutputFile() {

	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
