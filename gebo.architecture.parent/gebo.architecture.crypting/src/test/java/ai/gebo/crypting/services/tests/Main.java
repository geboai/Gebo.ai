/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.crypting.services.tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Gebo.ai comment agent
 * Main class annotated with @SpringBootApplication to enable Spring Boot's auto-configuration features.
 * Implements CommandLineRunner interface to execute custom code after the Spring application context loads.
 */
@SpringBootApplication
@ComponentScan(basePackages ={ "ai.gebo.crypting"})
@EnableAutoConfiguration

public class Main implements CommandLineRunner {

	// Logger instance for logging information during application start and end.
	private static Logger LOG = LoggerFactory.getLogger(Main.class);

	/**
	 * Main method where the application starts. It also logs the beginning and end of application execution.
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		LOG.info("STARTING THE APPLICATION");

		SpringApplication.run(Main.class, args);
		LOG.info("APPLICATION FINISHED");
	}

	/**
	 * Callback used to run the bean after the Spring application context has been loaded.
	 * @param args Arguments passed to the run method
	 */
	@Override
	public void run(String... args) {
		// Method for executing additional functionality once the application has started.
		// Uncomment and define scheduler.schedule() to use scheduling.
		//scheduler.schedule();
	}
}