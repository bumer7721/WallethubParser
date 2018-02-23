package com.ef.reader;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import com.ef.reader.vo.LogRecordVO;

public class ParserLogRecordTest {
	
	private ParserLogRecord parserLogRecord = new ParserLogRecordImpl();
	
	public void parse() {
		String record = "2017-01-01 00:00:11.763|192.168.234.82|\"GET / HTTP/1.1\"|200|\"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0\"";
		
		LogRecordVO logRecord = parserLogRecord.parseRecord(record);
		
		assertThat(logRecord, notNullValue());
//		assertThat(logRecord.getDate(), DateMatchers.);
	}

}
