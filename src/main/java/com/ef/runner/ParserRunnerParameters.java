package com.ef.runner;

import java.text.ParseException;
import java.util.Date;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.ef.exception.CommandArgumentsEmptyParserException;
import com.ef.exception.ParserException;
import com.ef.runner.enums.Duration;
import com.ef.runner.enums.ParameterAlias;

public class ParserRunnerParameters {
	
	private static final String DEFAUL_DELIMITER = "=";
	private static final String DEFAUL_ACESSLOG = "./access.log";

	private String accesslog = DEFAUL_ACESSLOG;
	private Date startDate;
	private Duration duration;
	private Integer threshold;
	
	public String getAccesslog() {
		return accesslog;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Duration getDuration() {
		return duration;
	}

	public Integer getThreshold() {
		return threshold;
	}
	
	public static ParserRunnerParameters valueOf(String[] args, String delimiter) {
		ParserRunnerParameters res = null;
		if (args != null
				&& args.length>0) {
			
			res = new ParserRunnerParameters();
			
			Optional<Date> startDateOpt = Optional.empty();
			Optional<Duration> durationOpt = Optional.empty();
			Optional<Integer> thresholdOpt = Optional.empty();
			
			for (String arg : args) {
				String[] aliasValue = StringUtils.split(arg, delimiter);
				if (aliasValue != null 
						&& aliasValue.length==2) {
					String aliasStr = aliasValue[0];
					String value = aliasValue[1];
					ParameterAlias alias = ParameterAlias.valueOfAlias(aliasStr);
					
					switch (alias) {
					case ACCESS_LOG: 
						value = Optional.ofNullable(value).orElse(DEFAUL_ACESSLOG);
						res.accesslog = value;
						break;
					case START_DATE: 
						try {
							startDateOpt = Optional.ofNullable(DateUtils.parseDate(value, "yyyy-MM-dd.HH:mm:ss"));
						} catch (ParseException e) {
							throw new ParserException(prepareMessage(alias, value), e);
						}
						break;
					case DURATION: 
						durationOpt = Optional.ofNullable(Duration.valueOfLabel(value));
						if (!durationOpt.isPresent()) {
							throw new ParserException(prepareMessage(alias, value));
						}
						break;
					case THRESHOLD: 
						try {
							thresholdOpt = Optional.ofNullable(Integer.valueOf(value));
						} catch (NumberFormatException e) {
							throw new ParserException(prepareMessage(alias, value));
						}
						break;
	
					default:
						throw new ParserException(String.format("Command line argument [%s] is unknown", aliasStr));
					}
				} else {
					throw new ParserException(String.format("Can't parse command line argument [%s]", arg));
				}
			}
			
			res.startDate = prepareField(ParameterAlias.START_DATE, startDateOpt);
			res.duration = prepareField(ParameterAlias.DURATION, durationOpt);
			res.threshold = prepareField(ParameterAlias.THRESHOLD, thresholdOpt);
			
		} else {
			throw new CommandArgumentsEmptyParserException("Command line arguments are empty");
		}
		return res;
	}
	
	public static ParserRunnerParameters valueOf(String[] args) {
		return valueOf(args, DEFAUL_DELIMITER);
	}
	
	private static String prepareMessage(ParameterAlias alias, String value) {
		return String.format("Can't parse %s value [%s]", alias.getAlias(), value);
	}

	private static <T> T prepareField(ParameterAlias alias, Optional<T> value) {
		return value.orElseThrow(()->new ParserException(String.format("Argument %s is empty", alias.getAlias())));
	}
}
