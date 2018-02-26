package com.ef;

import java.io.IOException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ef.db.model.LogRecord;
import com.ef.db.repository.LogRecordRepository;
import com.ef.runner.ParserRunner;

@SpringBootApplication
public class Parser implements CommandLineRunner {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Parser.class);
	
	@Autowired
	private LogRecordRepository logRecordRepository;
	
	@Autowired
	private ParserRunner parserRunner;
	
	public static void main(String [] args) throws IOException {
		SpringApplication.run(Parser.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		LOGGER.info("Started process of parsing...");
		
		Iterable<LogRecord> logRecord = logRecordRepository.findAll();
		logRecord.forEach(x->{
			LOGGER.info(x.getId().toString());
			LOGGER.info(x.getDate().toString());
		});
		
		parserRunner.run();
		
//		ParserLogRecord parserLogRecord = new ParserLogRecordImpl();
//		LineIterator it = FileUtils.lineIterator(new File("./access.log"));
//		try {
//		    while (it.hasNext()) {
//		        String line = it.nextLine();
////		        LogRecordVO logRecord = parserLogRecord.parseRecord(line);
////		        LOGGER.info(logRecord.getIp());
//		    }
//		} finally {
//		    LineIterator.closeQuietly(it);
//		}
		
		LOGGER.info("Parsing finished...");
		promptEnterKey();
		System.exit(0);
		
	}

	private static void promptEnterKey() {
		LOGGER.info("Press \"ENTER\" to exit...");
		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();
		scanner.close();
	}
}
