/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.monolithic.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import ai.gebo.architecture.environment.EnvironmentHolder;
import ai.gebo.config.service.IGGeboConfigService;

/**
 * Main class for the Gebo AI Monolithic Application. This class is responsible
 * for bootstrapping the application using Spring Boot. It implements the
 * CommandLineRunner interface to execute code on startup.
 *
 * AI generated comments
 */
@SpringBootApplication
@ComponentScan(basePackages = "ai.gebo")
@EnableAutoConfiguration
@EnableConfigurationProperties
@EnableAsync
@EnableScheduling
@EnableMongoRepositories(basePackages = "ai.gebo")
@EnableNeo4jRepositories(basePackages = "ai.gebo")
@EnableSpringDataWebSupport(pageSerializationMode = PageSerializationMode.VIA_DTO)
public class Main implements CommandLineRunner {

	static String errorMessage = "The java property or environment variable GEBO_HOME must be set";
	private static Logger LOG = LoggerFactory.getLogger(Main.class);

	@Autowired
	IGGeboConfigService configService;

	/**
	 * The main entry point of the application. It verifies the presence of the
	 * GEBO_HOME environment variable and property. If not set, it logs an error and
	 * terminates the application.
	 * 
	 * @param args command-line arguments passed to the application
	 */
	public static void main(String[] args) {
		LOG.info("STARTING THE APPLICATION");
		if (EnvironmentHolder.GEBO_HOME_ENVIRONMENT_VALUE == null
				|| EnvironmentHolder.GEBO_HOME_ENVIRONMENT_VALUE.trim().length() == 0) {
			LOG.error(errorMessage);
			System.out.println(errorMessage);
			System.err.println(errorMessage);
			System.exit(-1);
		}
		SpringApplication.run(Main.class, args);
		LOG.info("APPLICATION FINISHED");
	}

	/**
	 * Method to be run on startup, implementing logic to ensure environment
	 * variables are set.
	 *
	 * @param args command-line arguments passed to the application
	 */
	@Override
	public void run(String... args) {
		// scheduler.schedule(); // Uncomment if a scheduler setup is needed.
		if (!configService.isGeboHomeSet()) {
			LOG.error(errorMessage);
			System.out.println(errorMessage);
			System.err.println(errorMessage);
			System.exit(-1);
		} else {
			LOG.info(EnvironmentHolder.GEBO_HOME + "=" + configService.getGeboHome());
			if (configService.isGeboWorkDirectorySet()) {
				LOG.info(EnvironmentHolder.GEBO_WORK_DIRECTORY + "=" + configService.getGeboWorkDirectory());
			} else {
				LOG.error("Seems that " + EnvironmentHolder.GEBO_WORK_DIRECTORY + " is not set yet");
			}
		}
	}
}