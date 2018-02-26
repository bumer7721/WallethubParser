package com.ef.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ef.reader.ParserLogRecord;

@Component
public class ParserRunner {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ParserRunner.class);
	
	private ParserLogRecord parserLogRecord;
	
	public void run() {
		LOGGER.info("run");
	}
}
