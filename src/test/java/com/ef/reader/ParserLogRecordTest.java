package com.ef.reader;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.text.ParseException;
import java.time.temporal.ChronoUnit;

import org.apache.commons.lang3.time.DateUtils;
import org.exparity.hamcrest.date.DateMatchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.ef.exception.ParserException;
import com.ef.reader.vo.LogRecordVO;

public class ParserLogRecordTest {
	
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
	
	@Rule 
	public ExpectedException thrown = ExpectedException.none();
	
	private ParserLogRecord parserLogRecord = new ParserLogRecordImpl();
	
	@Test
	public void parseTest() throws ParseException {
		String record = "2017-01-01 00:00:11.763|192.168.234.82|\"GET / HTTP/1.1\"|200|\"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0\"";
		
		LogRecordVO logRecord = parserLogRecord.parseRecord(record);
		
		assertThat(logRecord, notNullValue());
		assertThat(logRecord.getDate(), DateMatchers.within(0, ChronoUnit.MILLIS, DateUtils.parseDate("2017-01-01 00:00:11.763", DATE_FORMAT)));
		assertThat(logRecord.getIp(), equalTo("192.168.234.82"));
		assertThat(logRecord.getRequest(), equalTo("GET / HTTP/1.1"));
		assertThat(logRecord.getStatus(), equalTo(200));
		assertThat(logRecord.getUserAgent(), equalTo("swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0"));
	}
	
	@Test
	public void parseNotValidDate() throws ParseException {
		String record = "a2017-01-01 00:00:11.763|192.168.234.82|\"GET / HTTP/1.1\"|200|\"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0\"";
		
		thrown.expect(ParserException.class);
		thrown.expectMessage(equalTo("Can't parse date: [a2017-01-01 00:00:11.763]"));
		parserLogRecord.parseRecord(record);
	}

	@Test
	public void parseNotValidStatus() throws ParseException {
		String record = "2017-01-01 00:00:11.763|192.168.234.82|\"GET / HTTP/1.1\"|200a|\"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0\"";
		
		thrown.expect(ParserException.class);
		thrown.expectMessage(equalTo("Can't parse number: [200a]"));
		parserLogRecord.parseRecord(record);
	}
	
	@Test
	public void parseNotValidRecord() throws ParseException {
		String record = "2017-01-01 00:00:11.763|192.168.234.82|\"GET / HTTP/1.1\"|200";
		
		thrown.expect(ParserException.class);
		thrown.expectMessage(equalTo(String.format("Can't parse [%s]", record)));
		parserLogRecord.parseRecord(record);
	}

}
