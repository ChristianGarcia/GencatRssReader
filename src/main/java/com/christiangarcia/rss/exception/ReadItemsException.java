package com.christiangarcia.rss.exception;


public class ReadItemsException extends Exception {

	private static final long serialVersionUID = 1L;

	public ReadItemsException(Exception e) {
		super(e);
	}

}
