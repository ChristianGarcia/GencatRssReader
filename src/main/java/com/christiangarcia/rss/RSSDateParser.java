package com.christiangarcia.rss;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RSSDateParser {

	public static final String DATE_FORMAT = "EEE, d MMM yyyy HH:mm:ss Z";

	private SimpleDateFormat parser;

	public RSSDateParser(Locale locale) {
		parser = new SimpleDateFormat(DATE_FORMAT, locale);
	}

	public Date parse(String pubDateString) throws ParseException {
		return parser.parse(pubDateString);
	}

}
