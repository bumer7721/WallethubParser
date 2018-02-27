package com.ef.db.service.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Path;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ef.db.model.LogRecord;
import com.ef.db.repository.LogRecordRepository;
import com.ef.db.service.LogRecordService;
import com.ef.runner.enums.Duration;

@Service
public class LogRecordServiceImpl extends BaseServiceImpl<LogRecord> implements LogRecordService {
	
	@Autowired
	private LogRecordRepository logRecordRepository;

	@Override
	protected LogRecordRepository getRepository() {
		return logRecordRepository;
	}
	
	@Override
	public List<LogRecord> getLogRecordsThatExceedThreshold(Date startDate, Duration duration, Integer threshold) {
		Date endDate = DateUtils.addHours(startDate, duration.getHours());
		return findAll(exceedThreshold(startDate, endDate, Long.valueOf(threshold)));		
	}
	
	private Specification<LogRecord> exceedThreshold(Date startDate, Date endDate, Long threshold) {
		return (r, q, cb) -> {
			Path<String> pathToIp = r.get("ip");
			q.groupBy(pathToIp);
			q.having(cb.greaterThanOrEqualTo(cb.count(pathToIp), threshold));
			return cb.between(r.get("date"), startDate, endDate);
		};
	}
	
	@Override
	public List<LogRecord> getLogRecordsByIp(String ip) {
		return findAll(equalIp(ip));
	}
	
	private Specification<LogRecord> equalIp(String ip) {
		return (r,q,cb) -> cb.equal(r.get("ip"), ip);
	}
}
