package com.christiangarcia.dom;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public interface DOMParser {

	String getTextFromNode(Node node);

	String getTextFromChildNode(Node node, String childNodeName);

	Document getDOMDocument(InputStream inputStream)
			throws ParserConfigurationException, SAXException, IOException;

	Node getChildNode(Node parentNode, String childNodeName);

}
