package com.ef.reader;

import com.ef.reader.vo.LogRecordVO;

public interface ParserLogRecord {

	LogRecordVO parseRecord(String record);
	
}
