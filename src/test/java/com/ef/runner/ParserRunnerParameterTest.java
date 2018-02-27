package com.ef.runner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.text.ParseException;
import java.time.temporal.ChronoUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.exparity.hamcrest.date.DateMatchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.ef.exception.CommandArgumentsEmptyParserException;
import com.ef.exception.ParserException;
import com.ef.runner.enums.Duration;

public class ParserRunnerParameterTest {
	
	private static final String DATE_FORMAT = "yyyy-MM-dd.HH:mm:ss";
	
	@Rule 
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void parseCommandArgumentsTest() throws ParseException {
		String[] args = StringUtils.split("--startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100");
		
		ParserRunnerParameters parameters = ParserRunnerParameters.valueOf(args);
		
		assertThat(parameters, notNullValue());
		assertThat(parameters.getStartDate(), DateMatchers.within(0, ChronoUnit.MINUTES, DateUtils.parseDate("2017-01-01.13:00:00", DATE_FORMAT)));
		assertThat(parameters.getDuration(), equalTo(Duration.HOURLY));
		assertThat(parameters.getThreshold(), equalTo(100));
		assertThat(parameters.getAccesslog(), equalTo("./access.log"));
	}
	
	@Test
	public void parseCommandArgumentsNull() {
		String[] args = null;
		
		thrown.expect(CommandArgumentsEmptyParserException.class);
		thrown.expectMessage(equalTo("Command line arguments are empty"));
		ParserRunnerParameters.valueOf(args);
	}
	
	@Test
	public void parseCommandArgumentsEmpty() {
		String[] args = new String[0];
		
		thrown.expect(CommandArgumentsEmptyParserException.class);
		thrown.expectMessage(equalTo("Command line arguments are empty"));
		ParserRunnerParameters.valueOf(args);
	}

	@Test
	public void parseCommandArgumentsNotValidFormat() {
		String[] args = StringUtils.split("startDate+2017-01-01.13:00:00");
		
		thrown.expect(ParserException.class);
		thrown.expectMessage(equalTo("Can't parse command line argument [startDate+2017-01-01.13:00:00]"));
		ParserRunnerParameters.valueOf(args);
	}
	
	@Test
	public void parseCommandArgumentsNotValidStatDate() {
		String[] args = StringUtils.split("--startDate=201701-01.13:00:00 --duration=hourly --threshold=100");
		
		thrown.expect(ParserException.class);
		thrown.expectMessage(equalTo("Can't parse --startDate value [201701-01.13:00:00]"));
		ParserRunnerParameters.valueOf(args);
	}
	
	@Test
	public void parseCommandArgumentsNotValidDuration() {
		String[] args = StringUtils.split("--startDate=2017-01-01.13:00:00 --duration=hourl --threshold=100");
		
		thrown.expect(ParserException.class);
		thrown.expectMessage(equalTo("Can't parse --duration value [hourl]"));
		ParserRunnerParameters.valueOf(args);
	}

	@Test
	public void parseCommandArgumentsNotValidThreshold() {
		String[] args = StringUtils.split("--startDate=2017-01-01.13:00:00 --duration=hourly --threshold=1a0");
		
		thrown.expect(ParserException.class);
		thrown.expectMessage(equalTo("Can't parse --threshold value [1a0]"));
		ParserRunnerParameters.valueOf(args);
	}
}
