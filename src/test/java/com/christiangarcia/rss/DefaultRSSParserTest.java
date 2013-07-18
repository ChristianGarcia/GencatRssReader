package com.christiangarcia.rss;

//import static org.hamcrest.Matchers.*;
//import static org.junit.Assert.*;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import com.christiangarcia.rss.DefaultRSSParser;
import com.christiangarcia.rss.RSSParser;
import com.christiangarcia.rss.exception.ReadItemsException;
import com.christiangarcia.rss.model.RSSItem;

public class DefaultRSSParserTest {

	RSSParser parser;

	@Before
	public void setUp() {
		Locale dateLocale = new Locale("en");
		parser = new DefaultRSSParser(dateLocale);
	}

	@Test
	public void testReadEmptyRSSIsNotNull() throws ReadItemsException, IOException {
		String emptyRSS = emptyRSS();
		List<RSSItem> rssItems = readItemsFromString(emptyRSS);
		assertThat(rssItems, is(not(nullValue())));
	}

	@Test
	public void testReadEmptyRSSIsEmpty() throws ReadItemsException {
		String emptyRSS = emptyRSS();
		List<RSSItem> rssItems = readItemsFromString(emptyRSS);
		assertThat(rssItems, is(empty()));
	}

	@Test
	public void testReadOneItemGivesSizeOne() throws ReadItemsException {
		List<RSSItem> rssItems = readItemsFromString(oneItem());
		assertThat(rssItems.size(), is(1));
	}

	@Test
	public void testReadTwoItemsGivesSizeTwo() throws ReadItemsException {
		List<RSSItem> rssItems = readItemsFromString(twoItems());
		assertThat(rssItems.size(), is(2));
	}

	@Test
	public void testReadItemHasCorrectTitle() throws ReadItemsException {
		List<RSSItem> rssItems = readItemsFromString(oneItem());
		RSSItem rssItem = rssItems.get(0);
		assertThat(rssItem.getTitle(), is("Acords de Govern del 9 de juliol de 2013"));
	}

	@Test
	public void testReadItemHasCorrectLink() throws ReadItemsException {
		List<RSSItem> rssItems = readItemsFromString(oneItem());
		RSSItem rssItem = rssItems.get(0);
		String expected = "http://www.govern.cat/pres_gov/govern/ca/govern/consell-executiu/acords-govern/acordgovern-3741.html?&WT.rss_a=acords_de_govern_del_9_de_juliol_de_2013&WT.rss_f=Informacio_i_serveis_de_la_Generalitat_de_Catalunya&WT.rss_ev=c";
		assertThat(rssItem.getLink(), equalTo(expected));
	}

	@Test
	public void testReadItemHasCorrectDescription() throws ReadItemsException {
		List<RSSItem> rssItems = readItemsFromString(oneItem());
		RSSItem rssItem = rssItems.get(0);
		String expected = "<p style=\"vertical-align:top\">El Govern impulsa la llei de transparència i sostenibilitat del sector de la comunicació per \"reforçar els mitjans públics\"</p>";
		assertThat(rssItem.getDescription(), is(expected));
	}

	@Test
	public void testReadItemHasCorrectCategory() throws ReadItemsException {
		List<RSSItem> rssItems = readItemsFromString(oneItem());
		RSSItem rssItem = rssItems.get(0);
		assertThat(rssItem.getCategory(), is("actualitat"));
	}

	@Test
	public void testReadItemHasCorrectPubDate() throws ReadItemsException {
		List<RSSItem> rssItems = readItemsFromString(oneItem());
		RSSItem rssItem = rssItems.get(0);
		Date expectedDate = new Date(2013 - 1900, Calendar.JULY, 9, 16, 45);
		assertThat(rssItem.getPubDate(), equalTo(expectedDate));
	}

	private List<RSSItem> readItemsFromString(String oneItemString) throws ReadItemsException {
		InputStream inputStream = getInputStreamFromString(oneItemString);
		List<RSSItem> rssItems = parser.readItems(inputStream);
		return rssItems;
	}

	private String oneItem() {
		return "<rss version=\"2.0\" xmlns:atom=\"http://www.w3.org/2005/Atom\">"
				+ "<channel>"
				+ "<item>"
				+ "<title>Acords de Govern del 9 de juliol de 2013</title>"
				+ "<link>http://www.govern.cat/pres_gov/govern/ca/govern/consell-executiu/acords-govern/acordgovern-3741.html?&amp;WT.rss_a=acords_de_govern_del_9_de_juliol_de_2013&amp;WT.rss_f=Informacio_i_serveis_de_la_Generalitat_de_Catalunya&amp;WT.rss_ev=c</link>"
				+ "<description>"
				+ "<![CDATA["
				+ "<p style=\"vertical-align:top\">El Govern impulsa la llei de transparència i sostenibilitat del sector de la comunicació per \"reforçar els mitjans públics\"</p>"
				+ "]]>" + "</description>" + " <category>actualitat</category>"
				+ "<pubDate>Tue, 9 Jul 2013 16:45:00 +0200</pubDate>" + "</item>" + "</channel>" + "</rss>";
	}

	private String twoItems() {
		return "<rss version=\"2.0\" xmlns:atom=\"http://www.w3.org/2005/Atom\">"
				+ "<channel>"
				+ "<item>"
				+ "<title>Acords de Govern del 9 de juliol de 2013</title>"
				+ "<link>http://www.govern.cat/pres_gov/govern/ca/govern/consell-executiu/acords-govern/acordgovern-3741.html?&amp;WT.rss_a=acords_de_govern_del_9_de_juliol_de_2013&amp;WT.rss_f=Informacio_i_serveis_de_la_Generalitat_de_Catalunya&amp;WT.rss_ev=c</link>"
				+ "<description>"
				+ " <![CDATA["
				+ "<p style=\"vertical-align:top\">El Govern impulsa la llei de transparència i sostenibilitat del sector de la comunicació per \"reforçar els mitjans públics\"</p>"
				+ "]]>"
				+ "</description>"
				+ " <category>actualitat</category>"
				+ "<pubDate>Tue, 9 Jul 2013 16:45:00 +0200</pubDate>"
				+ "</item>"
				+ "<item>"
				+ "<title>Acords de Govern del 9 de juliol de 2013</title>"
				+ "<link>http://www.govern.cat/pres_gov/govern/ca/govern/consell-executiu/acords-govern/acordgovern-3741.html?&amp;WT.rss_a=acords_de_govern_del_9_de_juliol_de_2013&amp;WT.rss_f=Informacio_i_serveis_de_la_Generalitat_de_Catalunya&amp;WT.rss_ev=c</link>"
				+ "<description>"
				+ " <![CDATA["
				+ "<p style=\"vertical-align:top\">El Govern impulsa la llei de transparència i sostenibilitat del sector de la comunicació per \"reforçar els mitjans públics\"</p>"
				+ "]]>" + "</description>" + " <category>actualitat</category>"
				+ "<pubDate>Tue, 9 Jul 2013 16:45:00 +0200</pubDate>" + "</item>" + "</channel>" + "</rss>";
	}

	private InputStream getInputStreamFromString(String emptyRSS) {
		InputStream inputStream = new ByteArrayInputStream(emptyRSS.getBytes());
		return inputStream;
	}

	private InputStream getInputStreamFromURL(String urlString) throws IOException {
		URL url = new URL(urlString);
		return url.openStream();
	}

	private String emptyRSS() {
		return "<rss version=\"2.0\" xmlns:atom=\"http://www.w3.org/2005/Atom\">" + "<channel></channel>" + "</rss>";
	}
}
