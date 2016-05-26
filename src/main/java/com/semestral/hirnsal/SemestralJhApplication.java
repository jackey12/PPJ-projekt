package com.semestral.hirnsal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class SemestralJhApplication {

	private static final Logger logger = LoggerFactory.getLogger(SemestralJhApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SemestralJhApplication.class, args);

		logger.debug("Debbug, main");
		logger.error("Errorr, main");
		logger.info("Info, main");
		logger.trace("Trace, main");
		logger.warn("Warn, main");
	}
}
