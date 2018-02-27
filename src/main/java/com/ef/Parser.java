package com.ef;

import java.io.IOException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.ef.runner.ParserRunner;
import com.ef.runner.ParserRunnerParameters;

@SpringBootApplication
public class Parser {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Parser.class);
	
	public static void main(String [] args) throws IOException {
		ApplicationContext context = SpringApplication.run(Parser.class, args);
		
		ParserRunnerParameters parameters = ParserRunnerParameters.valueOf(args);
		
		LOGGER.info("Started process of parsing...");		
		ParserRunner parserRunner = context.getBean(ParserRunner.class);
		parserRunner.run(parameters);
		
		LOGGER.info("Parsing finished...");
		promptEnterKey();
		System.exit(0);
	}
	
	private static void promptEnterKey() {
		LOGGER.info("Press \"ENTER\" to exit...");
		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();
		scanner.close();
	}
}
