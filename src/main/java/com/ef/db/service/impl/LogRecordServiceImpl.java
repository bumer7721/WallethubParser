package com.ef.db.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ef.db.model.LogRecord;
import com.ef.db.repository.LogRecordRepository;

@Service
public class LogRecordServiceImpl extends BaseServiceImpl<LogRecord> {
	
	@Autowired
	private LogRecordRepository logRecordRepository;

	@Override
	protected LogRecordRepository getRepository() {
		return logRecordRepository;
	}
}
