package com.scribtex.model;

import java.util.ArrayList;

public class ClsiServiceCompileResponse {

	private String id, instance, status;
	private long time;
	private ArrayList<ClsiOutputFile> outputFiles;
	private ArrayList<ClsiOutputLogFile> outputLogs;
	private ArrayList<ClsiOutputError> outputErrors;

	public ClsiServiceCompileResponse() {
		this.outputFiles = new ArrayList<ClsiOutputFile>();
		this.outputLogs = new ArrayList<ClsiOutputLogFile>();
		this.outputErrors = new ArrayList<ClsiOutputError>();
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public ArrayList<ClsiOutputFile> getOutputFiles() {
		return outputFiles;
	}

	public void setOutputFiles(ArrayList<ClsiOutputFile> outputFiles) {
		this.outputFiles = outputFiles;
	}

	public ArrayList<ClsiOutputLogFile> getOutputLogs() {
		return outputLogs;
	}

	public void setOutputLogs(ArrayList<ClsiOutputLogFile> outputLogs) {
		this.outputLogs = outputLogs;
	}

	public ArrayList<ClsiOutputError> getOutputErrors() {
		return outputErrors;
	}

	public void setOutputErrors(ArrayList<ClsiOutputError> outputErrors) {
		this.outputErrors = outputErrors;
	}

}
