package com.christiangarcia.rss;

import java.io.InputStream;
import java.util.List;

import com.christiangarcia.rss.exception.ReadItemsException;
import com.christiangarcia.rss.model.RSSItem;


public interface RSSParser {

	List<RSSItem> readItems(InputStream inputStream) throws ReadItemsException;
	
}
