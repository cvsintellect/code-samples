package com.scribtex.clsi;

import com.scribtex.exceptions.ClsiServiceFormatException;
import com.scribtex.exceptions.ClsiServiceUnavailableException;
import com.scribtex.model.ClsiLiteralInputResource;
import com.scribtex.model.ClsiOptions;
import com.scribtex.model.ClsiServiceCompileRequest;
import com.scribtex.model.ClsiServiceCompileResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

public class ClsiService {

	private String serviceUrl, serviceToken;

	public ClsiService(String serviceUrl, String serviceToken) {
		this.serviceUrl = serviceUrl;
		this.serviceToken = serviceToken;
	}

	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public String getServiceToken() {
		return serviceToken;
	}

	public void setServiceToken(String serviceToken) {
		this.serviceToken = serviceToken;
	}

	public ClsiServiceCompileResponse compile(String id, String name, String body, String compiler, String format) throws IOException, ClsiServiceUnavailableException,
			ClsiServiceFormatException {
		ClsiServiceCompileRequest request = new ClsiServiceCompileRequest(this.serviceToken);
		request.setId(id);
		request.setInstance(new Long(new Date().getTime()).toString());
		
		ClsiLiteralInputResource text = new ClsiLiteralInputResource(name, body);
		
		ClsiOptions options = new ClsiOptions();
		options.setCompiler(compiler);
		options.setOutputFormat(format);
		request.getResources().add(text, true);
		request.setOptions(options);
		
		return compile(request);
	}

	public ClsiServiceCompileResponse compile(ClsiServiceCompileRequest request) throws IOException, ClsiServiceUnavailableException,
			ClsiServiceFormatException {
		ClsiXmlRequestBuilder requestBuilder = new ClsiXmlRequestBuilder(request);

		URL url = new URL(this.serviceUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		connection.setConnectTimeout(0);
		connection.setReadTimeout(0);
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");

		requestBuilder.writeXml(connection.getOutputStream());
		connection.getOutputStream().close();

		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			InputStream stream = connection.getInputStream();
			ClsiXmlResponseParser responseParser = new ClsiXmlResponseParser();
			return responseParser.parse(stream);
		}
		else {
			throw new ClsiServiceUnavailableException();
		}
	}

	public void compileAsync(ClsiServiceCompileRequest request) throws IOException, ClsiServiceUnavailableException, ClsiServiceFormatException {
		try {
			ClsiXmlRequestBuilder requestBuilder = new ClsiXmlRequestBuilder(request);

			URL url = new URL(this.serviceUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setConnectTimeout(0);
			connection.setReadTimeout(100); // let it timeout
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");

			requestBuilder.writeXml(connection.getOutputStream());
			connection.getOutputStream().close();

			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			}
			else {
				throw new ClsiServiceUnavailableException();
			}
		}
		catch (IOException x) {
			// ignore timeouts
		}
	}

	public String compileRaw(ClsiServiceCompileRequest request) throws IOException, ClsiServiceUnavailableException, ClsiServiceFormatException {
		ClsiXmlRequestBuilder requestBuilder = new ClsiXmlRequestBuilder(request);

		URL url = new URL(this.serviceUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		connection.setConnectTimeout(0);
		connection.setReadTimeout(0);
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");

		requestBuilder.writeXml(connection.getOutputStream());
		connection.getOutputStream().close();

		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			InputStream stream = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			StringBuilder value = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				value.append(line);
			}
			return value.toString();
		}
		return null;
	}
}
