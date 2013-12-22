package com.scribtex.clsi;

import com.scribtex.exceptions.ClsiServiceFormatException;
import com.scribtex.model.ClsiOutputError;
import com.scribtex.model.ClsiOutputFile;
import com.scribtex.model.ClsiOutputLogFile;
import com.scribtex.model.ClsiServiceCompileResponse;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;

public class ClsiXmlResponseParser extends DefaultHandler {

	private ClsiServiceCompileResponse parsedResponse;
	private ClsiOutputError parsedError;
	private String currentNode;

	public ClsiXmlResponseParser() {

	}

	public ClsiServiceCompileResponse parse(InputStream stream) throws IOException, ClsiServiceFormatException {
		parsedResponse = new ClsiServiceCompileResponse();
		try {
			SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
			parser.parse(new InputSource(stream), this);
		}
		catch (ParserConfigurationException e) {
			e.printStackTrace();
			throw new ClsiServiceFormatException();
		}
		catch (SAXException e) {
			e.printStackTrace();
			throw new ClsiServiceFormatException();
		}
		return parsedResponse;
	}

	@Override
	public void endElement(String uri, String localName, String qName) {
		if (qName.equalsIgnoreCase("error")) {
			if (this.parsedError != null) {
				this.parsedResponse.getOutputErrors().add(this.parsedError);
				this.parsedError = null;
			}
		}
		this.currentNode = "";
	}

	@Override
	public void endDocument() {

	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (this.currentNode.equalsIgnoreCase("status")) {
			this.parsedResponse.setStatus(new String(ch, start, length).trim());
		}
		else if (this.currentNode.equalsIgnoreCase("id")) {
			this.parsedResponse.setId(new String(ch, start, length).trim());
		}
		else if (this.currentNode.equalsIgnoreCase("instance")) {
			this.parsedResponse.setInstance(new String(ch, start, length).trim());
		}
		else if (this.currentNode.equalsIgnoreCase("time")) {
			String time = new String(ch, start, length).trim();
			if (!time.equals("")) {
				this.parsedResponse.setTime(Long.parseLong(time));
			}
		}
		else if (this.currentNode.equalsIgnoreCase("type")) {
			this.parsedError.setType(new String(ch, start, length).trim());
		}
		else if (this.currentNode.equalsIgnoreCase("message")) {
			this.parsedError.setMessage(new String(ch, start, length).trim());
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) {
		this.currentNode = qName;
		if (qName.equalsIgnoreCase("file")) {
			String type = attributes.getValue("", "type");
			String url = attributes.getValue("", "url");
			String mimeType = attributes.getValue("", "mimetype");
			if (type != null && url != null && mimeType != null) {
				if (type.equalsIgnoreCase("log")) {
					ClsiOutputLogFile output = new ClsiOutputLogFile();
					output.setMimeType(mimeType);
					output.setType(type);
					output.setUrl(url);
					this.parsedResponse.getOutputLogs().add(output);
				}
				else {
					ClsiOutputFile output = new ClsiOutputFile();
					output.setMimeType(mimeType);
					output.setType(type);
					output.setUrl(url);
					this.parsedResponse.getOutputFiles().add(output);
				}
			}
		}
		else if (qName.equalsIgnoreCase("error")) {
			this.parsedError = new ClsiOutputError();
		}
	}
}