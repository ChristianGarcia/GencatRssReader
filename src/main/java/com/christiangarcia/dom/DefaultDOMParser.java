package com.christiangarcia.dom;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public abstract class DefaultDOMParser implements DOMParser {

	@Override
	public Document getDOMDocument(InputStream inputStream)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(inputStream);
		return document;
	}
	
	@Override
	public String getTextFromNode(Node node) {
		StringBuilder text = new StringBuilder();
		NodeList textFragments = node.getChildNodes();
		for (int i = 0; i < textFragments.getLength(); i++) {
			text.append(textFragments.item(i).getNodeValue());
		}
		return text.toString().trim();
	}

	@Override
	public String getTextFromChildNode(Node parentNode, String childNodeName) {
		Node childNode = getChildNode(parentNode, childNodeName);
		if (childNode != null) {
			return getTextFromNode(childNode);
		}
		return null;
	}

	@Override
	public Node getChildNode(Node parentNode, String childNodeName) {
		Node childNode = null;
		if (parentNode instanceof Element) {
			NodeList potentialChildren = ((Element) parentNode)
					.getElementsByTagName(childNodeName);
			if (potentialChildren != null && potentialChildren.getLength() > 0) {
				childNode = potentialChildren.item(0);
			}
		}
		return childNode;
	}

}
