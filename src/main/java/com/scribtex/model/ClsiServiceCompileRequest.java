package com.scribtex.model;

public class ClsiServiceCompileRequest {

	private String serviceToken, id, instance;
	private ClsiOptions options;
	private ClsiInputResourceList resources;

	public ClsiServiceCompileRequest(String serviceToken) {
		this.id = "GHwuFbBo8gWde34X5jpjwAyz6Ck_";
		this.instance = "1";
		this.resources = new ClsiInputResourceList();
		this.options = new ClsiOptions();
		this.options.setCompiler("latex");
		this.options.setOutputFormat("png");
		this.serviceToken = serviceToken;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

	public String getServiceToken() {
		return serviceToken;
	}

	public void setServiceToken(String serviceToken) {
		this.serviceToken = serviceToken;
	}

	public ClsiOptions getOptions() {
		return options;
	}

	public void setOptions(ClsiOptions options) {
		this.options = options;
	}

	public void setResources(ClsiInputResourceList resources) {
		this.resources = resources;
	}

	public ClsiInputResourceList getResources() {
		return this.resources;
	}
}
