package com.ef.runner;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.ef.db.model.BlockedIp;
import com.ef.db.model.LogRecord;
import com.ef.db.service.BlockedIpService;
import com.ef.db.service.LogRecordService;
import com.ef.exception.ParserException;
import com.ef.reader.ParserLogRecord;
import com.ef.reader.ParserLogRecordImpl;
import com.ef.reader.vo.LogRecordVO;

@Component
public class ParserRunner {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ParserRunner.class);
	private static final String DATE_FORMAT = "yyyy-MM-dd.HH:mm:ss";
	
	@Autowired
	private ParserLogRecord parserLogRecord;
	
	@Autowired
	private LogRecordService logRecordService;
	
	@Autowired
	private BlockedIpService blockedIpService;
	
	public ParserRunner() {
		setParserLogRecord(new ParserLogRecordImpl());
	}
	
	public void setParserLogRecord(ParserLogRecordImpl parserLogRecord) {
		this.parserLogRecord = parserLogRecord;
	}

	public void run(ParserRunnerParameters parameters) {
		LineIterator it = null;
		try {
			it = FileUtils.lineIterator(new File(parameters.getAccesslog()));
		} catch (IOException e) {
			throw new ParserException(String.format("Can't open %s", parameters.getAccesslog()), e);
		}
		
		int lineNumber = 0;
		try {
		    while (it.hasNext()) {
		    	++lineNumber;
		    	System.out.print("\rProcess row #" + lineNumber);
		        String line = it.nextLine();
		        try {
		        	LogRecordVO logRecord = parserLogRecord.parseRecord(line);
		        	LogRecord logRecordEntity = voToEntity(logRecord);
		        	logRecordEntity = logRecordService.create(logRecordEntity);
				} catch (ParserException e) {
					LOGGER.warn(String.format("Record #%d: %s. It will be skipped.", lineNumber, e.getMessage()));
				}
		    }
		} finally {
		    LineIterator.closeQuietly(it);
		}
		
		List<LogRecord> logRecordsThatExceedThreshold = logRecordService
				.getLogRecordsThatExceedThreshold(parameters.getStartDate(), parameters.getDuration(), parameters.getThreshold());
		if (!CollectionUtils.isEmpty(logRecordsThatExceedThreshold)) {
			Date endDate = DateUtils.addHours(parameters.getStartDate(), parameters.getDuration().getHours()); 
			String commonMessage = String.format("made more than %d requests starting from %s to %s"
					, parameters.getThreshold()
					, DateFormatUtils.format(parameters.getStartDate(), DATE_FORMAT)
					, DateFormatUtils.format(endDate, DATE_FORMAT));
			LOGGER.info("IPs that " + commonMessage);
			for (LogRecord logRecord : logRecordsThatExceedThreshold) {
				LOGGER.info(logRecord.getIp());
				moveToBlokedIp(logRecord, "It "+commonMessage);
			}
		}
	}
	
	private LogRecord voToEntity(LogRecordVO src) {
		LogRecord logRecord = new LogRecord();
		logRecord.setDate(src.getDate());		
		logRecord.setIp(src.getIp());		
		logRecord.setRequest(src.getRequest());		
		logRecord.setStatus(src.getStatus());		
		logRecord.setUserAgent(src.getUserAgent());
		return logRecord;
	}
	
	private BlockedIp moveToBlokedIp(LogRecord logRecord, String comment) {
		BlockedIp blockedIp = new BlockedIp();
		blockedIp.setIp(logRecord.getIp());
		blockedIp.setComment(comment);
		return blockedIpService.create(blockedIp);
	}
}
