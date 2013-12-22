package com.scribtex.model;

public class ClsiLiteralInputResource extends ClsiInputResource {

	private String contents;

	public ClsiLiteralInputResource(String path, String contents) {
		this.path = path;
		this.contents = contents;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}
}
