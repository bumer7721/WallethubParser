package com.ef.reader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.ef.exception.ParserException;
import com.ef.reader.vo.LogRecordVO;

public class ParserLogRecordImpl implements ParserLogRecord {
	
	private static final String STRIP_QUOTER = "\"";
	
	private String delimiter;
	
	private SimpleDateFormat dateFormat;
	
	public ParserLogRecordImpl() {
		this(null, null);
	}
	
	public ParserLogRecordImpl(String delimiter, String dateFormat) {
		super();
		this.delimiter = Optional.ofNullable(delimiter).orElse("|");
		
		String dateFormatPattern = Optional.ofNullable(dateFormat).orElse("yyyy-MM-dd HH:mm:ss.SSS");
		this.dateFormat = new SimpleDateFormat(dateFormatPattern);
		this.dateFormat.setLenient(false);		
	}

	@Override
	public LogRecordVO parseRecord(String record) {
		
		String[] strings = StringUtils.split(record, delimiter);
		LogRecordVO res = null;
		
		if (strings != null
				&& strings.length == 5) {
			res = new LogRecordVO();
			res.setDate(parseDate(strings[0]));
			res.setIp(strings[1]);
			res.setRequest(StringUtils.strip(strings[2], STRIP_QUOTER));
			res.setStatus(parseInteger(strings[3]));
			res.setUserAgent(StringUtils.strip(strings[4], STRIP_QUOTER));
		} else {
			throw new ParserException(String.format("Can't parse [%s]", record));
		}
		
		return res;
	}
	
	private Date parseDate(String dateValue) {
		Date parsedDate = null;
		
		try {
			parsedDate = dateFormat.parse(dateValue);
		} catch (ParseException e) {
			throw new ParserException(String.format("Can't parse date: [%s]", dateValue), e);
		}
		
		return parsedDate;
	}
	
	private Integer parseInteger(String integerValue) {
		Integer parsedInteger = null;
		
		try {
			parsedInteger = Integer.valueOf(integerValue);
		} catch (NumberFormatException e) {
			throw new ParserException(String.format("Can't parse number: [%s]", integerValue), e);
		}
		
		return parsedInteger;
	}

}
