package com.ef.db.repository;

import org.springframework.data.repository.CrudRepository;

import com.ef.db.model.LogRecord;

public interface LogRecordRepository extends CrudRepository<LogRecord, Long> {

}
