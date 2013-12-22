package com.scribtex.model;

import java.util.Date;

public class ClsiLinkedInputResource extends ClsiInputResource {

	private String url;
	private Date modified;

	public ClsiLinkedInputResource(String path, String url, Date modified) {
		this.path = path;
		this.url = url;
		this.modified = modified;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}
}
