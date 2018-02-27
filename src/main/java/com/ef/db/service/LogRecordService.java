package com.ef.db.service;

import java.util.Date;
import java.util.List;

import com.ef.db.model.LogRecord;
import com.ef.runner.enums.Duration;

public interface LogRecordService extends BaseService<LogRecord> {

	List<LogRecord> getLogRecordsThatExceedThreshold(Date startDate, Duration duration, Integer threshold);

	List<LogRecord> getLogRecordsByIp(String ip);

}
