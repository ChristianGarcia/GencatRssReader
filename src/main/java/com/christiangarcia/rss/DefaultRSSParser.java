package com.christiangarcia.rss;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.christiangarcia.dom.DefaultDOMParser;
import com.christiangarcia.rss.exception.ReadItemsException;
import com.christiangarcia.rss.model.RSSItem;

public class DefaultRSSParser extends DefaultDOMParser implements RSSParser {

	private Locale dateLocale;

	public DefaultRSSParser(Locale dateLocale) {
		this.dateLocale = dateLocale;
	}

	@Override
	public List<RSSItem> readItems(InputStream inputStream) throws ReadItemsException {
		try {
			Document domDocument = getDOMDocument(inputStream);
			Element documentElement = domDocument.getDocumentElement();
			NodeList itemNodes = documentElement.getElementsByTagName("item");
			return nodeListToRssItemList(itemNodes);
		} catch (ParserConfigurationException e) {
			throw new ReadItemsException(e);
		} catch (SAXException e) {
			throw new ReadItemsException(e);
		} catch (IOException e) {
			throw new ReadItemsException(e);
		}
	}

	private ArrayList<RSSItem> nodeListToRssItemList(NodeList itemNodes) {
		ArrayList<RSSItem> rssItemList = new ArrayList<RSSItem>();
		for (int i = 0; i < itemNodes.getLength(); i++) {
			Node itemNode = itemNodes.item(i);
			RSSItem rssItem;
			try {
				rssItem = buildRssItemFromNode(itemNode);
				rssItemList.add(rssItem);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return rssItemList;
	}

	private RSSItem buildRssItemFromNode(Node itemNode) throws ParseException {
		RSSItem rssItem = new RSSItem();
		String title = getTextFromChildNode(itemNode, "title");
		String link = getTextFromChildNode(itemNode, "link");
		String description = getTextFromChildNode(itemNode, "description");
		String category = getTextFromChildNode(itemNode, "category");
		String pubDateString = getTextFromChildNode(itemNode, "pubDate");
		rssItem.setCategory(category);
		rssItem.setDescription(description);
		rssItem.setLink(link);
		rssItem.setPubDate(new RSSDateParser(dateLocale).parse(pubDateString));
		rssItem.setTitle(title);
		return rssItem;
	}

}
