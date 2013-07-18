package com.christiangarcia.rss;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;
//import static org.hamcrest.Matchers.*;
//import static org.junit.Assert.*;

import com.christiangarcia.rss.RSSDateParser;

public class RssDateParserTest {

	@Test
	public void testParseDateIsNotNull() throws ParseException {
		Locale locale = new Locale("en");
		Date date = new RSSDateParser(locale).parse("Wed, 4 Jul 2001 12:08:56 -0700");
		assertThat(date, is(not(nullValue())));
	}

}
