package com.scribtex.clsi;

import com.scribtex.model.*;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.OutputStream;

public class ClsiXmlRequestBuilder {

	private ClsiServiceCompileRequest request;

	public ClsiXmlRequestBuilder(ClsiServiceCompileRequest request) {
		this.request = request;
	}

	public void writeXml(OutputStream stream) {
		try {
			XMLStreamWriter xmlWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(stream);
			xmlWriter.writeStartDocument();
			xmlWriter.writeStartElement("compile");
			xmlWriter.writeStartElement("token");
			xmlWriter.writeCharacters(request.getServiceToken());
			xmlWriter.writeEndElement();
			xmlWriter.writeStartElement("id");
			xmlWriter.writeCharacters(request.getId());
			xmlWriter.writeEndElement();
			xmlWriter.writeStartElement("instance");
			xmlWriter.writeCharacters(request.getInstance());
			xmlWriter.writeEndElement();
			if (request.getOptions() != null) {
				ClsiOptions options = request.getOptions();
				xmlWriter.writeStartElement("options");
				if (options.getCompiler() != null) {
					xmlWriter.writeStartElement("compiler");
					xmlWriter.writeCharacters(options.getCompiler());
					xmlWriter.writeEndElement();
				}
				if (options.getOutputFormat() != null) {
					xmlWriter.writeStartElement("output-format");
					xmlWriter.writeCharacters(options.getOutputFormat());
					xmlWriter.writeEndElement();
				}
				xmlWriter.writeEndElement();
			}
			if (request.getResources() != null) {
				ClsiInputResourceList resources = request.getResources();
				if (resources.size() > 0) {
					ClsiInputResource primaryResource = resources.get(resources.getPrimaryResource());
					xmlWriter.writeStartElement("resources");
					xmlWriter.writeAttribute("root-resource-path", primaryResource.getPath());
					for (ClsiInputResource resource : resources) {
						xmlWriter.writeStartElement("resource");
						xmlWriter.writeAttribute("path", resource.getPath());
						if (resource instanceof ClsiLinkedInputResource) {
							ClsiLinkedInputResource linkedResource = (ClsiLinkedInputResource) resource;
							xmlWriter.writeAttribute("url", linkedResource.getUrl());
							xmlWriter.writeAttribute("modified", linkedResource.getModified().toString());
						}
						else {
							ClsiLiteralInputResource literalResource = (ClsiLiteralInputResource) resource;
							xmlWriter.writeCData(literalResource.getContents());
						}
						xmlWriter.writeEndElement();
					}
					xmlWriter.writeEndElement();
				}
			}
			xmlWriter.writeEndElement();
			xmlWriter.writeEndDocument();
			xmlWriter.flush();
		}
		catch (XMLStreamException e) {
			e.printStackTrace();
		}
		catch (FactoryConfigurationError e) {
			e.printStackTrace();
		}
	}
}